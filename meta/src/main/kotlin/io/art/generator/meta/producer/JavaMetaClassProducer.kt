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

package io.art.generator.meta.producer

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaField
import io.art.generator.meta.model.JavaMetaMethod
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.service.withoutVariables
import io.art.generator.meta.templates.*
import javax.lang.model.element.Modifier.*
import javax.lang.model.type.TypeKind.VOID

fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass) {
    val className = metaName(metaClass.type.className!!)
    val metaClassName = metaClassName(META_NAME, metaClass.type.className)
    val reference = metaClassName(EMPTY_STRING, metaClass.type.className)
    val typeName = metaClass.type.withoutVariables()
    val constructorStatement = metaTypeSuperStatement(metaClass.type)
    classBuilder(metaClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(ParameterizedTypeName.get(META_CLASS_CLASS_NAME, typeName.box()))
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(constructorStatement)
                    .build())
            .apply { generateConstructors(metaClass.constructors, metaClass.type, typeName) }
            .apply { generateFields(metaClass.fields) }
            .apply { generateMethods(metaClass.methods, metaClass.type) }
            .apply { metaClass.innerClasses.values.forEach { inner -> generateClass(inner) } }
            .build()
            .apply(::addType)
    FieldSpec.builder(reference, className)
            .addModifiers(PRIVATE, FINAL)
            .initializer(registerNewStatement(), reference)
            .build()
            .apply(::addField)
    methodBuilder(className)
            .addModifiers(PUBLIC)
            .returns(reference)
            .addCode(returnStatement(), className)
            .build()
            .let(::addMethod)
}

private fun TypeSpec.Builder.generateFields(fields: Map<String, JavaMetaField>) {
    fields.entries.forEach { field ->
        val fieldType = field.value.type
        val fieldTypeName = field.value.type.withoutVariables()
        val fieldMetaType = ParameterizedTypeName.get(META_FIELD_CLASS_NAME, fieldTypeName.box())
        FieldSpec.builder(fieldMetaType, field.key)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerMetaFieldStatement(field.key, fieldType))
                .build()
                .apply(::addField)
    }
}


private fun TypeSpec.Builder.generateConstructors(constructors: List<JavaMetaMethod>, type: JavaMetaType, typeName: TypeName) {
    constructors.mapIndexed { index, constructor ->
        var name = CONSTRUCTOR_NAME
        if (index > 0) name += index
        classBuilder(name)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(ParameterizedTypeName.get(META_CONSTRUCTOR_CLASS_NAME, typeName.box()))
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(metaTypeSuperStatement(type))
                        .build())
                .apply { generateConstructorInvocations(type, constructor) }
                .apply { generateParameters(constructor) }
                .build()
                .apply(::addType)
        val constructorClassName = ClassName.get(EMPTY_STRING, name)
        FieldSpec.builder(constructorClassName, name)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerNewStatement(), constructorClassName)
                .build()
                .apply(::addField)
        methodBuilder(name)
                .addModifiers(PUBLIC)
                .returns(constructorClassName)
                .addCode(returnStatement(), name)
                .build()
                .let(::addMethod)
    }
}

private fun TypeSpec.Builder.generateConstructorInvocations(type: JavaMetaType, constructor: JavaMetaMethod) {
    val template = methodBuilder(INVOKE_NAME)
            .addModifiers(PUBLIC)
            .addAnnotation(Override::class.java)
            .returns(type.withoutVariables())
            .build()
    template.toBuilder()
            .addParameter(ArrayTypeName.of(Object::class.java), ARGUMENTS_NAME)
            .addCode(returnInvokeConstructorStatement(type, constructor.parameters.size))
            .build()
            .apply(::addMethod)
    when (constructor.parameters.size) {
        0 -> {
            template.toBuilder()
                    .addCode(returnInvokeWithoutArgumentsConstructorStatement(type))
                    .build()
                    .apply(::addMethod)
        }
        1 -> {
            template.toBuilder()
                    .addParameter(ArrayTypeName.of(Object::class.java), ARGUMENT_NAME)
                    .addCode(returnInvokeOneArgumentConstructorStatement(type))
                    .build()
                    .apply(::addMethod)
        }
    }
}


private fun TypeSpec.Builder.generateMethods(methods: List<JavaMetaMethod>, type: JavaMetaType) {
    methods.filter { method -> !configuration.metaMethodExclusions.contains(method.name) }
            .groupBy { method -> method.name }
            .map { grouped ->
                grouped.value.forEachIndexed { methodIndex, method ->
                    var name = method.name
                    if (methodIndex > 0) name += methodIndex
                    val returnTypeName = method.returnType.withoutVariables()
                    val static = method.modifiers.contains(STATIC)
                    val parent = when {
                        static -> {
                            ParameterizedTypeName.get(STATIC_META_METHOD_CLASS_NAME, returnTypeName.box())
                        }
                        else -> {
                            ParameterizedTypeName.get(INSTANCE_META_METHOD_CLASS_NAME, type.withoutVariables(), returnTypeName.box())
                        }
                    }
                    classBuilder(name)
                            .addModifiers(PUBLIC, FINAL, STATIC)
                            .superclass(parent)
                            .addMethod(constructorBuilder()
                                    .addModifiers(PRIVATE)
                                    .addCode(metaNamedSuperStatement(name, method.returnType))
                                    .build())
                            .apply { generateMethodInvocations(type, name, method) }
                            .apply { generateParameters(method) }
                            .build()
                            .apply(::addType)
                    val methodClassName = ClassName.get(EMPTY_STRING, name)
                    FieldSpec.builder(methodClassName, name)
                            .addModifiers(PRIVATE, FINAL)
                            .initializer(registerNewStatement(), methodClassName)
                            .build()
                            .apply(::addField)
                    methodBuilder(name)
                            .addModifiers(PUBLIC)
                            .returns(methodClassName)
                            .addCode(returnStatement(), name)
                            .build()
                            .let(::addMethod)
                }

            }
}

private fun TypeSpec.Builder.generateMethodInvocations(type: JavaMetaType, name: String, method: JavaMetaMethod) {
    val static = method.modifiers.contains(STATIC)
    val template = methodBuilder(INVOKE_NAME)
            .addModifiers(PUBLIC)
            .addAnnotation(Override::class.java)
            .returns(OBJECT_CLASS_NAME)
            .apply { if (!static) addParameter(type.withoutVariables(), INSTANCE_NAME) }
            .build()
    template.toBuilder().apply {
        val invoke = when {
            static -> invokeStaticStatement(name, type, method.parameters.size)
            else -> invokeInstanceStatement(name, method.parameters.size)
        }
        when (method.returnType.originalType.kind) {
            VOID -> addCode(invoke).addCode(returnNullStatement())
            else -> addCode(returnStatement(invoke))
        }
        addParameter(ArrayTypeName.of(Object::class.java), ARGUMENTS_NAME)
        addMethod(build())
    }
    when (method.parameters.size) {
        0 -> template.toBuilder().apply {
            val invoke = when {
                static -> invokeWithoutArgumentsStaticStatement(name, type)
                else -> invokeWithoutArgumentsInstanceStatement(name)
            }
            when (method.returnType.originalType.kind) {
                VOID -> addCode(invoke).addCode(returnNullStatement())
                else -> addCode(returnStatement(invoke))
            }
            addMethod(build())
        }
        1 -> template.toBuilder().apply {
            addParameter(ClassName.get(Object::class.java), ARGUMENT_NAME)
            val invoke = when {
                static -> invokeOneArgumentStaticStatement(name, type)
                else -> invokeOneArgumentInstanceStatement(name)
            }
            when (method.returnType.originalType.kind) {
                VOID -> addCode(invoke).addCode(returnNullStatement())
                else -> addCode(returnStatement(invoke))
            }
            addMethod(build())
        }
    }
}


private fun TypeSpec.Builder.generateParameters(method: JavaMetaMethod) {
    method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
        val parameterType = parameter.value.type
        val parameterTypeName = parameter.value.type.withoutVariables()
        val metaParameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameterTypeName.box())
        FieldSpec.builder(metaParameterType, parameter.key)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerMetaParameterStatement(parameterIndex, parameter.key, parameterType))
                .build()
                .apply(::addField)
        methodBuilder(parameter.key)
                .addModifiers(PUBLIC)
                .returns(metaParameterType)
                .addCode(returnStatement(), parameter.key)
                .build()
                .let(::addMethod)
    }
}
