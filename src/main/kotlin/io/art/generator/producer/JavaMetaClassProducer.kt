/*
 * ART
 *
 * Copyright 2019-2021 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.art.generator.producer

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.constants.*
import io.art.generator.extension.*
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaMethod
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind.VARIABLE_KIND
import io.art.generator.templates.*
import javax.lang.model.element.Modifier.*
import javax.lang.model.type.TypeKind.VOID

fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = javaMetaClassClassName(metaClass.type.className)
    val typeName = metaClass.type.withoutVariables()
    val constructorStatement = javaMetaClassSuperStatement(metaClass)
    classBuilder(metaClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(ParameterizedTypeName.get(META_CLASS_CLASS_NAME, typeName.box()))
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(constructorStatement)
                    .build())
            .apply { generateConstructors(metaClass, typeName) }
            .apply { generateProperties(metaClass) }
            .apply { generateMethods(metaClass) }
            .apply {
                metaClass.innerClasses
                        .values
                        .filter(JavaMetaClass::couldBeGenerated)
                        .forEach { inner -> generateClass(inner) }
            }
            .build()
            .apply { alwaysQualify(metaClass.type.className) }
            .apply(::addType)
    FieldSpec.builder(metaClassName, className)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterNewStatement(metaClassName))
            .build()
            .apply(::addField)
    methodBuilder(className)
            .addModifiers(PUBLIC)
            .returns(metaClassName)
            .addCode(javaReturnStatement(className))
            .build()
            .let(::addMethod)
}

private fun TypeSpec.Builder.generateProperties(metaClass: JavaMetaClass) {
    val fields = metaClass.parentFields() + metaClass.fields
    fields.entries.forEach { field ->
        val fieldTypeName = field.value.type.withoutVariables()
        val fieldMetaType = ParameterizedTypeName.get(META_FIELD_CLASS_NAME, fieldTypeName.box())
        val fieldName = metaFieldName(field.key)
        FieldSpec.builder(fieldMetaType, fieldName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(javaRegisterMetaFieldStatement(field.key, field.value))
                .build()
                .apply(::addField)
        methodBuilder(fieldName)
                .addModifiers(PUBLIC)
                .returns(fieldMetaType)
                .addCode(javaReturnStatement(fieldName))
                .build()
                .let(::addMethod)
    }
}

private fun TypeSpec.Builder.generateConstructors(metaClass: JavaMetaClass, typeName: TypeName) {
    val type = metaClass.type
    metaClass.constructors
            .filter(JavaMetaMethod::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += index
                classBuilder(name)
                        .addModifiers(PUBLIC, FINAL, STATIC)
                        .superclass(ParameterizedTypeName.get(META_CONSTRUCTOR_CLASS_NAME, typeName.box()))
                        .addMethod(constructorBuilder()
                                .addModifiers(PRIVATE)
                                .addCode(javaMetaConstructorSuperStatement(type, constructor.modifiers))
                                .build())
                        .apply { generateConstructorInvocations(type, constructor) }
                        .apply { generateParameters(constructor) }
                        .build()
                        .apply(::addType)
                val constructorClassName = ClassName.get(EMPTY_STRING, name)
                FieldSpec.builder(constructorClassName, name)
                        .addModifiers(PRIVATE, FINAL)
                        .initializer(javaRegisterNewStatement(constructorClassName))
                        .build()
                        .apply(::addField)
                methodBuilder(name)
                        .addModifiers(PUBLIC)
                        .returns(constructorClassName)
                        .addCode(javaReturnStatement(name))
                        .build()
                        .let(::addMethod)
            }
}

private fun TypeSpec.Builder.generateConstructorInvocations(type: JavaMetaType, constructor: JavaMetaMethod) {
    val parameters = constructor.parameters
    val template = methodBuilder(INVOKE_NAME)
            .addModifiers(PUBLIC)
            .addException(THROWABLE_CLASS_NAME)
            .addAnnotation(OVERRIDE_CLASS_NAME)
            .returns(type.withoutVariables())
            .build()
    template.toBuilder()
            .addParameter(ArrayTypeName.of(OBJECT_CLASS_NAME), ARGUMENTS_NAME)
            .addCode(javaReturnInvokeConstructorStatement(type, parameters))
            .build()
            .apply(::addMethod)
    when (parameters.size) {
        0 -> {
            template.toBuilder()
                    .addCode(javaReturnInvokeWithoutArgumentsConstructorStatement(type))
                    .build()
                    .apply(::addMethod)
        }
        1 -> {
            template.toBuilder()
                    .addParameter(OBJECT_CLASS_NAME, ARGUMENT_NAME)
                    .addCode(javaReturnInvokeOneArgumentConstructorStatement(type, parameters.values.first()))
                    .build()
                    .apply(::addMethod)
        }
    }
}

private fun TypeSpec.Builder.generateMethods(metaClass: JavaMetaClass) {
    val type = metaClass.type
    val methods = metaClass.methods + metaClass.parentMethods()
    methods.asSequence()
            .filter(JavaMetaMethod::couldBeGenerated)
            .groupBy { method -> method.name }
            .map { grouped -> grouped.value.forEachIndexed { methodIndex, method -> generateMethod(method, methodIndex, type) } }
}

private fun TypeSpec.Builder.generateMethod(method: JavaMetaMethod, index: Int, ownerType: JavaMetaType) {
    var name = method.name
    if (index > 0) name += index
    val methodName = metaMethodName(name)
    val methodClassName = javaMetaMethodClassName(name)
    val returnType = method.returnType
    val returnTypeName = returnType.withoutVariables().box()
    val static = method.modifiers.contains(STATIC)
    val parent = when {
        static -> ParameterizedTypeName.get(STATIC_META_METHOD_CLASS_NAME, returnTypeName)
        else -> ParameterizedTypeName.get(INSTANCE_META_METHOD_CLASS_NAME, ownerType.withoutVariables(), returnTypeName)
    }
    val variableExclusions = method.typeParameters
            .filter { parameter -> parameter.kind == VARIABLE_KIND }
            .map { parameter -> parameter.typeName }
            .toSet()
    classBuilder(methodClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(parent)
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(javaMetaMethodSuperStatement(method.name, returnType.excludeVariables(variableExclusions), method.modifiers))
                    .build())
            .apply { generateMethodInvocations(ownerType, method.name, method) }
            .apply { generateParameters(method) }
            .build()
            .apply(::addType)
    FieldSpec.builder(methodClassName, methodName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterNewStatement(methodClassName))
            .build()
            .apply(::addField)
    methodBuilder(methodName)
            .addModifiers(PUBLIC)
            .returns(methodClassName)
            .addCode(javaReturnStatement(methodName))
            .build()
            .let(::addMethod)
}

private fun TypeSpec.Builder.generateMethodInvocations(type: JavaMetaType, name: String, method: JavaMetaMethod) {
    val static = method.modifiers.contains(STATIC)
    val parameters = method.parameters
    val template = methodBuilder(INVOKE_NAME)
            .addModifiers(PUBLIC)
            .addAnnotation(OVERRIDE_CLASS_NAME)
            .addException(THROWABLE_CLASS_NAME)
            .returns(OBJECT_CLASS_NAME)
            .apply { if (!static) addParameter(type.withoutVariables(), INSTANCE_NAME) }
            .build()
    template.toBuilder().apply {
        val invoke = when {
            static -> javaInvokeStaticStatement(name, type, parameters)
            else -> javaInvokeInstanceStatement(name, parameters)
        }
        when (method.returnType.originalType.kind) {
            VOID -> addCode(invoke).addCode(javaReturnNullStatement())
            else -> addCode(javaReturnStatement(invoke))
        }
        addParameter(ArrayTypeName.of(OBJECT_CLASS_NAME), ARGUMENTS_NAME)
        addMethod(build())
    }
    when (parameters.size) {
        0 -> template.toBuilder().apply {
            val invoke = when {
                static -> javaInvokeWithoutArgumentsStaticStatement(name, type)
                else -> javaInvokeWithoutArgumentsInstanceStatement(name)
            }
            when (method.returnType.originalType.kind) {
                VOID -> addCode(invoke).addCode(javaReturnNullStatement())
                else -> addCode(javaReturnStatement(invoke))
            }
            addMethod(build())
        }
        1 -> template.toBuilder().apply {
            addParameter(OBJECT_CLASS_NAME, ARGUMENT_NAME)
            val invoke = when {
                static -> javaInvokeOneArgumentStaticStatement(name, type, parameters.values.first())
                else -> javaInvokeOneArgumentInstanceStatement(name, parameters.values.first())
            }
            when (method.returnType.originalType.kind) {
                VOID -> addCode(invoke).addCode(javaReturnNullStatement())
                else -> addCode(javaReturnStatement(invoke))
            }
            addMethod(build())
        }
    }
}

private fun TypeSpec.Builder.generateParameters(method: JavaMetaMethod) {
    val variableExclusions = method.typeParameters
            .filter { parameter -> parameter.kind == VARIABLE_KIND }
            .map { parameter -> parameter.typeName }
            .toSet()
    method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
        val parameterTypeName = parameter.value.type.withoutVariables()
        val metaParameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameterTypeName.box())
        val parameterName = metaParameterName(parameter.key)
        FieldSpec.builder(metaParameterType, parameterName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(javaRegisterMetaParameterStatement(parameterIndex, parameter.key, parameter.value.excludeVariables(variableExclusions)))
                .build()
                .apply(::addField)
        methodBuilder(parameterName)
                .addModifiers(PUBLIC)
                .returns(metaParameterType)
                .addCode(javaReturnStatement(parameterName))
                .build()
                .let(::addMethod)
    }
}
