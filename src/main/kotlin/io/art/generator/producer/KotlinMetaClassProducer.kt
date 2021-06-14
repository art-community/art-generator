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

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.FunSpec.Companion.constructorBuilder
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import com.squareup.kotlinpoet.jvm.throws
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.parentMethods
import io.art.generator.extension.parentProperties
import io.art.generator.model.*
import io.art.generator.templates.*
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isUnit
import org.jetbrains.kotlin.descriptors.Modality.ABSTRACT
import org.jetbrains.kotlin.descriptors.Visibilities.Public
import java.util.Objects.isNull

fun TypeSpec.Builder.generateClass(metaClass: KotlinMetaClass) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = kotlinMetaClassClassName(metaClass.type.className)
    val typeName = metaClass.type.asPoetType()
    classBuilder(metaClassName)
            .superclass(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(typeName))
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaClassSuperStatement(metaClass))
                    .build())
            .apply { if (metaClass.modality != ABSTRACT) generateConstructors(metaClass, typeName) }
            .apply { generateProperties(metaClass) }
            .apply { generateMethods(metaClass) }
            .apply {
                metaClass.innerClasses
                        .values
                        .filter(KotlinMetaClass::couldBeGenerated)
                        .forEach { inner -> generateClass(inner) }
            }
            .build()
            .apply(::addType)
    PropertySpec.builder(className, metaClassName)
            .addModifiers(PRIVATE)
            .initializer(kotlinRegisterNewStatement(metaClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(className)
            .returns(metaClassName)
            .addCode(kotlinReturnStatement(className))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateProperties(metaClass: KotlinMetaClass) {
    val properties = metaClass.parentProperties() + metaClass.properties
    properties.entries.forEach { property ->
        val propertyTypeName = property.value.type.asPoetType()
        val propertyMetaType = KOTLIN_META_FIELD_CLASS_NAME.parameterizedBy(propertyTypeName)
        val propertyName = metaFieldName(property.key)
        PropertySpec.builder(propertyName, propertyMetaType)
                .addModifiers(PRIVATE)
                .initializer(kotlinRegisterMetaFieldStatement(property.key, property.value))
                .build()
                .apply(::addProperty)
        FunSpec.builder(propertyName)
                .returns(propertyMetaType)
                .addCode(kotlinReturnStatement(propertyName))
                .build()
                .let(::addFunction)
        if (property.value.visibility.delegate != Public) return@forEach
        property.value.getter
                ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
                ?.apply { generateGetter(property.value, metaClass) }
        property.value.setter
                ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
                ?.apply { generateSetter(property.value, metaClass) }
    }
}

private fun TypeSpec.Builder.generateConstructors(metaClass: KotlinMetaClass, typeName: TypeName) {
    val type = metaClass.type
    metaClass.constructors
            .filter(KotlinMetaFunction::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += index
                val constructorClassName = metaConstructorClassName(name)
                classBuilder(constructorClassName)
                        .superclass(KOTLIN_META_CONSTRUCTOR_CLASS_NAME.parameterizedBy(typeName))
                        .addFunction(constructorBuilder()
                                .addModifiers(INTERNAL)
                                .callSuperConstructor(kotlinMetaConstructorSuperStatement(type))
                                .build())
                        .apply { generateConstructorInvocations(type, constructor) }
                        .apply { generateParameters(constructor) }
                        .build()
                        .apply(::addType)
                val reference = ClassName(EMPTY_STRING, constructorClassName)
                PropertySpec.builder(name, reference)
                        .addModifiers(PRIVATE)
                        .initializer(kotlinRegisterNewStatement(reference))
                        .build()
                        .apply(::addProperty)
                FunSpec.builder(name)
                        .returns(reference)
                        .addCode(kotlinReturnStatement(name))
                        .build()
                        .let(::addFunction)
            }
}

private fun TypeSpec.Builder.generateConstructorInvocations(type: KotlinMetaType, constructor: KotlinMetaFunction) {
    val parameters = constructor.parameters
    val template = FunSpec.builder(INVOKE_NAME)
            .addModifiers(OVERRIDE)
            .throws(THROWABLE)
            .returns(type.asPoetType())
            .build()
    template.toBuilder()
            .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
            .addCode(kotlinInvokeConstructorStatement(type, parameters))
            .build()
            .apply(::addFunction)
    when (parameters.size) {
        0 -> {
            template.toBuilder()
                    .addCode(kotlinInvokeConstructorStatement(type))
                    .build()
                    .apply(::addFunction)
        }
        1 -> {
            template.toBuilder()
                    .addParameter(ARGUMENT_NAME, ANY)
                    .addCode(kotlinInvokeConstructorStatement(type, parameters.values.first()))
                    .build()
                    .apply(::addFunction)
        }
    }
}

private fun TypeSpec.Builder.generateMethods(metaClass: KotlinMetaClass) {
    (metaClass.functions + metaClass.parentMethods())
            .asSequence()
            .filter(KotlinMetaFunction::couldBeGenerated)
            .groupBy { method -> method.name }
            .map { grouped -> grouped.value.forEachIndexed { methodIndex, method -> generateMethod(method, methodIndex, metaClass) } }
}

private fun TypeSpec.Builder.generateMethod(function: KotlinMetaFunction, index: Int, ownerClass: KotlinMetaClass) {
    var name = function.name
    if (index > 0) name += index
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = function.returnType
    val returnTypeName = returnType?.asPoetType() ?: UNIT
    val static = ownerClass.isObject
    val parent = when {
        static -> KOTLIN_STATIC_META_METHOD_CLASS_NAME.parameterizedBy(returnTypeName)
        else -> KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerClass.type.asPoetType(), returnTypeName)
    }
    classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaMethodSuperStatement(function.name, returnType))
                    .build())
            .apply { generateMethodInvocations(ownerClass, function.name, function) }
            .apply { generateParameters(function) }
            .build()
            .apply(::addType)
    PropertySpec.builder(methodName, methodClassName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(kotlinRegisterNewStatement(methodClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(methodName)
            .returns(methodClassName)
            .addCode(kotlinReturnStatement(methodName))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateGetter(property: KotlinMetaProperty, ownerClass: KotlinMetaClass) {
    val name = GET_NAME + capitalize(property.name)
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = property.type
    val returnTypeName = returnType.asPoetType()
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerClass.type.asPoetType(), returnTypeName)
    classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaMethodSuperStatement(name, returnType))
                    .build())
            .apply {
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerClass.type.asPoetType())
                        .addCode(kotlinReturnGetStatement(INSTANCE_NAME, property))
                        .build()
                        .apply(::addFunction)
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerClass.type.asPoetType())
                        .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
                        .addCode(kotlinReturnGetStatement(INSTANCE_NAME, property))
                        .build()
                        .apply(::addFunction)
            }
            .build()
            .apply(::addType)
    PropertySpec.builder(methodName, methodClassName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(kotlinRegisterNewStatement(methodClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(methodName)
            .returns(methodClassName)
            .addCode(kotlinReturnStatement(methodName))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateSetter(property: KotlinMetaProperty, ownerClass: KotlinMetaClass) {
    val name = SET_NAME + capitalize(property.name)
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = property.type
    val returnTypeName = returnType.asPoetType()
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerClass.type.asPoetType(), returnTypeName)
    classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaMethodSuperStatement(name, returnType))
                    .build())
            .apply {
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerClass.type.asPoetType())
                        .addParameter(ARGUMENT_NAME, ANY)
                        .addCode(kotlinSetStatementBySingle(INSTANCE_NAME, property))
                        .build()
                        .apply(::addFunction)
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerClass.type.asPoetType())
                        .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
                        .addCode(kotlinSetStatementByArray(INSTANCE_NAME, property))
                        .build()
                        .apply(::addFunction)
            }
            .build()
            .apply(::addType)
    PropertySpec.builder(methodName, methodClassName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(kotlinRegisterNewStatement(methodClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(methodName)
            .returns(methodClassName)
            .addCode(kotlinReturnStatement(methodName))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateMethodInvocations(ownerClass: KotlinMetaClass, name: String, function: KotlinMetaFunction) {
    val static = ownerClass.isObject
    val parameters = function.parameters
    val template = FunSpec.builder(INVOKE_NAME)
            .addModifiers(OVERRIDE)
            .throws(THROWABLE)
            .returns(ANY.copy(nullable = true))
            .apply { if (!static) addParameter(INSTANCE_NAME, ownerClass.type.asPoetType()) }
            .build()
    template.toBuilder().apply {
        val invoke = when {
            static -> kotlinInvokeStaticStatement(name, ownerClass.type, parameters)
            else -> kotlinInvokeInstanceStatement(name, parameters)
        }
        when {
            isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
            isUnit(function.returnType!!.originalType) -> addLines(invoke, kotlinReturnNullStatement())
            else -> addCode(kotlinReturnStatement(invoke))
        }
        addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
        addFunction(build())
    }
    when (parameters.size) {
        0 -> template.toBuilder().apply {
            val invoke = when {
                static -> kotlinInvokeStaticStatement(name, ownerClass.type)
                else -> kotlinInvokeInstanceStatement(name)
            }
            when {
                isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
                isUnit(function.returnType!!.originalType) -> addLines(invoke, kotlinReturnNullStatement())
                else -> addCode(kotlinReturnStatement(invoke))
            }
            addFunction(build())
        }
        1 -> template.toBuilder().apply {
            addParameter(ARGUMENT_NAME, ANY)
            val invoke = when {
                static -> kotlinInvokeStaticStatement(name, ownerClass.type, parameters.values.first())
                else -> kotlinInvokeInstanceStatement(name, parameters.values.first())
            }
            when {
                isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
                isUnit(function.returnType!!.originalType) -> addLines(invoke, kotlinReturnNullStatement())
                else -> addCode(kotlinReturnStatement(invoke))
            }
            addFunction(build())
        }
    }
}

private fun TypeSpec.Builder.generateParameters(function: KotlinMetaFunction) {
    function.parameters.entries.forEachIndexed { parameterIndex, parameter ->
        val parameterTypeName = parameter.value.type.asPoetType()
        val metaParameterType = KOTLIN_META_PARAMETER_CLASS_NAME.parameterizedBy(parameterTypeName)
        val parameterName = metaParameterName(parameter.key)
        PropertySpec.builder(parameterName, metaParameterType)
                .addModifiers(PRIVATE)
                .initializer(kotlinRegisterMetaParameterStatement(parameterIndex, parameter.key, parameter.value))
                .build()
                .apply(::addProperty)
        FunSpec.builder(parameterName)
                .returns(metaParameterType)
                .addCode(kotlinReturnStatement(parameterName))
                .build()
                .let(::addFunction)
    }
}
