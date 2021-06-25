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
import io.art.generator.factory.NameFactory
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaField
import io.art.generator.model.JavaMetaMethod
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind.PRIMITIVE_KIND
import io.art.generator.templates.*
import javax.lang.model.element.Modifier.*

fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass, nameFactory: NameFactory) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = javaMetaClassClassName(metaClass.type.className, nameFactory)
    val typeName = metaClass.type.asPoetType()
    val constructorStatement = javaMetaClassSuperStatement(metaClass)
    classBuilder(metaClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(ParameterizedTypeName.get(JAVA_META_CLASS_CLASS_NAME, typeName.box()))
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(constructorStatement)
                    .build())
            .apply { if (!metaClass.modifiers.contains(ABSTRACT)) generateConstructors(metaClass, typeName) }
            .apply { generateFields(metaClass) }
            .apply { generateMethods(metaClass) }
            .apply {
                metaClass.innerClasses
                        .values
                        .filter(JavaMetaClass::couldBeGenerated)
                        .forEach { inner -> generateClass(inner, nameFactory) }
            }
            .build()
            .apply { qualifyImports(metaClass) }
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

private fun TypeSpec.Builder.qualifyImports(metaClass: JavaMetaClass) {
    qualifyImports(metaClass.type)
    metaClass.constructors.forEach { constructor ->
        constructor.parameters.values.forEach { parameter ->
            qualifyImports(parameter.type)
        }
    }
    metaClass.methods.forEach { method ->
        qualifyImports(method.returnType)
        method.parameters.values.forEach { parameter ->
            qualifyImports(parameter.type)
        }
    }
    metaClass.fields.forEach { field ->
        qualifyImports(field.value.type)
    }
    metaClass.innerClasses.values.forEach(::qualifyImports)
    metaClass.parent?.let(::qualifyImports)
    metaClass.interfaces.forEach(::qualifyImports)
}

private fun TypeSpec.Builder.qualifyImports(metaType: JavaMetaType) {
    if (metaType.kind == PRIMITIVE_KIND) return
    metaType.className?.let { name ->
        alwaysQualify(metaType.extractOwnerClassName())
        alwaysQualify(name)
    }
    metaType.typeParameters.forEach(::qualifyImports)
    metaType.arrayComponentType?.let(::qualifyImports)
    metaType.wildcardExtendsBound?.let(::qualifyImports)
    metaType.wildcardSuperBound?.let(::qualifyImports)
}

private fun TypeSpec.Builder.generateFields(metaClass: JavaMetaClass) {
    val parentFields = metaClass.parentFields()
    parentFields.entries.forEach { field -> generateField(field.value, true) }
    metaClass.fields.entries.forEach { field -> generateField(field.value, false) }
}

private fun TypeSpec.Builder.generateField(field: JavaMetaField, inherited: Boolean) {
    val fieldTypeName = field.type.asPoetType()
    val fieldMetaType = ParameterizedTypeName.get(JAVA_META_FIELD_CLASS_NAME, fieldTypeName.box())
    val fieldName = metaFieldName(field.name)
    FieldSpec.builder(fieldMetaType, fieldName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterMetaFieldStatement(field, inherited))
            .build()
            .apply(::addField)
    methodBuilder(fieldName)
            .addModifiers(PUBLIC)
            .returns(fieldMetaType)
            .addCode(javaReturnStatement(fieldName))
            .build()
            .let(::addMethod)
}

private fun TypeSpec.Builder.generateConstructors(metaClass: JavaMetaClass, typeName: TypeName) {
    val type = metaClass.type
    metaClass.constructors
            .filter(JavaMetaMethod::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += index
                val constructorClassName = metaConstructorClassName(name)
                classBuilder(constructorClassName)
                        .addModifiers(PUBLIC, FINAL, STATIC)
                        .superclass(ParameterizedTypeName.get(JAVA_META_CONSTRUCTOR_CLASS_NAME, typeName.box()))
                        .addMethod(constructorBuilder()
                                .addModifiers(PRIVATE)
                                .addCode(javaMetaConstructorSuperStatement(type))
                                .build())
                        .apply { generateConstructorInvocations(type, constructor) }
                        .apply { generateParameters(constructor) }
                        .build()
                        .apply(::addType)
                val reference = ClassName.get(EMPTY_STRING, constructorClassName)
                FieldSpec.builder(reference, name)
                        .addModifiers(PRIVATE, FINAL)
                        .initializer(javaRegisterNewStatement(reference))
                        .build()
                        .apply(::addField)
                methodBuilder(name)
                        .addModifiers(PUBLIC)
                        .returns(reference)
                        .addCode(javaReturnStatement(name))
                        .build()
                        .let(::addMethod)
            }
}

private fun TypeSpec.Builder.generateConstructorInvocations(type: JavaMetaType, constructor: JavaMetaMethod) {
    val parameters = constructor.parameters
    val template = methodBuilder(INVOKE_NAME)
            .addModifiers(PUBLIC)
            .addException(JAVA_THROWABLE_CLASS_NAME)
            .addAnnotation(JAVA_OVERRIDE_CLASS_NAME)
            .returns(type.asPoetType())
            .build()
    template.toBuilder()
            .addParameter(ArrayTypeName.of(JAVA_OBJECT_CLASS_NAME), ARGUMENTS_NAME)
            .addCode(javaInvokeConstructorStatement(type, parameters))
            .build()
            .apply(::addMethod)
    when (parameters.size) {
        0 -> {
            template.toBuilder()
                    .addCode(javaInvokeConstructorStatement(type))
                    .build()
                    .apply(::addMethod)
        }
        1 -> {
            template.toBuilder()
                    .addParameter(JAVA_OBJECT_CLASS_NAME, ARGUMENT_NAME)
                    .addCode(javaInvokeConstructorStatement(type, parameters.values.first()))
                    .build()
                    .apply(::addMethod)
        }
    }
}

private fun TypeSpec.Builder.generateMethods(metaClass: JavaMetaClass) {
    val parentMethods = metaClass.parentMethods()
    val methods = metaClass
            .methods
            .asSequence()
            .filter { method -> parentMethods.none { parent -> parent.withoutModifiers() == method.withoutModifiers() } }
    (methods + parentMethods)
            .filter(JavaMetaMethod::couldBeGenerated)
            .groupBy { method -> method.name }
            .map { grouped -> grouped.value.forEachIndexed { methodIndex, method -> generateMethod(method, methodIndex, metaClass.type) } }
}

private fun TypeSpec.Builder.generateMethod(method: JavaMetaMethod, index: Int, ownerType: JavaMetaType) {
    var name = method.name
    if (index > 0) name += index
    val methodName = metaMethodName(name)
    val methodClassName = javaMetaMethodClassName(name)
    val returnType = method.returnType
    val returnTypeName = returnType.asPoetType().box()
    val static = method.modifiers.contains(STATIC)
    val parent = when {
        static -> ParameterizedTypeName.get(JAVA_STATIC_META_METHOD_CLASS_NAME, returnTypeName)
        else -> ParameterizedTypeName.get(JAVA_INSTANCE_META_METHOD_CLASS_NAME, ownerType.asPoetType(), returnTypeName)
    }
    classBuilder(methodClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(parent)
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(javaMetaMethodSuperStatement(method.name, returnType))
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
            .addAnnotation(JAVA_OVERRIDE_CLASS_NAME)
            .addException(JAVA_THROWABLE_CLASS_NAME)
            .returns(JAVA_OBJECT_CLASS_NAME)
            .apply { if (!static) addParameter(type.asPoetType(), INSTANCE_NAME) }
            .build()
    template.toBuilder().apply {
        val invoke = when {
            static -> javaInvokeStaticStatement(name, type, parameters)
            else -> javaInvokeInstanceStatement(name, parameters)
        }
        when (method.returnType.typeName == Void.TYPE.typeName) {
            true -> addLines(invoke, javaReturnNullStatement())
            false -> addCode(javaReturnStatement(invoke))
        }
        addParameter(ArrayTypeName.of(JAVA_OBJECT_CLASS_NAME), ARGUMENTS_NAME)
        addMethod(build())
    }
    when (parameters.size) {
        0 -> template.toBuilder().apply {
            val invoke = when {
                static -> javaInvokeStaticStatement(name, type)
                else -> javaInvokeInstanceStatement(name)
            }
            when (method.returnType.typeName == Void.TYPE.typeName) {
                true -> addLines(invoke, javaReturnNullStatement())
                false -> addCode(javaReturnStatement(invoke))
            }
            addMethod(build())
        }
        1 -> template.toBuilder().apply {
            addParameter(JAVA_OBJECT_CLASS_NAME, ARGUMENT_NAME)
            val invoke = when {
                static -> javaInvokeStaticStatement(name, type, parameters.values.first())
                else -> javaInvokeInstanceStatement(name, parameters.values.first())
            }
            when (method.returnType.typeName == Void.TYPE.typeName) {
                true -> addLines(invoke, javaReturnNullStatement())
                false -> addCode(javaReturnStatement(invoke))
            }
            addMethod(build())
        }
    }
}

private fun TypeSpec.Builder.generateParameters(method: JavaMetaMethod) {
    method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
        val parameterTypeName = parameter.value.type.asPoetType()
        val metaParameterType = ParameterizedTypeName.get(JAVA_META_PARAMETER_CLASS_NAME, parameterTypeName.box())
        val parameterName = metaParameterName(parameter.key)
        FieldSpec.builder(metaParameterType, parameterName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(javaRegisterMetaParameterStatement(parameterIndex, parameter.value))
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
