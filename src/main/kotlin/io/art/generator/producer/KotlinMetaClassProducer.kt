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
import com.squareup.kotlinpoet.ClassName.Companion.bestGuess
import com.squareup.kotlinpoet.FunSpec.Companion.constructorBuilder
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import com.squareup.kotlinpoet.jvm.throws
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.superFunctions
import io.art.generator.extension.superProperties
import io.art.generator.factory.NameFactory
import io.art.generator.model.*
import io.art.generator.templates.*
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isUnit
import org.jetbrains.kotlin.descriptors.Modality.ABSTRACT
import org.jetbrains.kotlin.descriptors.Visibilities.Public
import java.util.Objects.isNull

fun TypeSpec.Builder.generateClass(metaClass: KotlinMetaClass, nameFactory: NameFactory) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = kotlinMetaClassClassName(metaClass.type.className, nameFactory)
    val typeName = metaClass.type.asPoetType()
    classBuilder(metaClassName)
            .superclass(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(typeName))
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaClassSuperStatement(metaClass))
                    .build())
            .apply { if (metaClass.modality != ABSTRACT) generateConstructors(metaClass, typeName) }
            .apply { generateProperties(metaClass) }
            .apply { generateFunctions(metaClass) }
            .apply {
                if (metaClass.isInterface) {
                    generateProxy(metaClass)
                }
            }
            .apply {
                metaClass.innerClasses
                        .values
                        .filter(KotlinMetaClass::couldBeGenerated)
                        .forEach { inner -> generateClass(inner, nameFactory) }
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
    val parentProperties = metaClass.superProperties()
    parentProperties.forEach { property -> generateProperty(property.value, true, metaClass) }
    metaClass.properties
            .entries
            .filter { property -> !parentProperties.containsKey(property.key) }
            .forEach { property -> generateProperty(property.value, false, metaClass) }
}

private fun TypeSpec.Builder.generateProperty(property: KotlinMetaProperty, inherited: Boolean, owner: KotlinMetaClass) {
    val propertyTypeName = property.type.asPoetType()
    val propertyMetaType = KOTLIN_META_FIELD_CLASS_NAME.parameterizedBy(propertyTypeName)
    val propertyName = metaFieldName(property.name)
    PropertySpec.builder(propertyName, propertyMetaType)
            .addModifiers(PRIVATE)
            .initializer(kotlinRegisterMetaFieldStatement(property, inherited))
            .build()
            .apply(::addProperty)
    FunSpec.builder(propertyName)
            .returns(propertyMetaType)
            .addCode(kotlinReturnStatement(propertyName))
            .build()
            .let(::addFunction)
    if (property.visibility.delegate != Public) return
    property.getter
            ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
            ?.apply { generateGetter(property, owner) }
    property.setter
            ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
            ?.apply { generateSetter(property, owner) }
}

private fun TypeSpec.Builder.generateConstructors(metaClass: KotlinMetaClass, typeName: TypeName) {
    val type = metaClass.type
    metaClass.constructors
            .filter(KotlinMetaFunction::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += "_$index"
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

private fun TypeSpec.Builder.generateFunctions(metaClass: KotlinMetaClass) {
    val parentFunctions = metaClass.superFunctions()
    val functions = metaClass
            .functions
            .filter { function -> parentFunctions.none { parent -> parent.withoutModifiers() == function.withoutModifiers() } }
    (functions + parentFunctions)
            .asSequence()
            .filter(KotlinMetaFunction::couldBeGenerated)
            .groupBy { function -> function.name }
            .map { grouped -> grouped.value.forEachIndexed { index, function -> generateFunction(function, index, metaClass) } }
}

private fun TypeSpec.Builder.generateFunction(function: KotlinMetaFunction, index: Int, ownerClass: KotlinMetaClass) {
    var name = function.name
    if (index > 0) name += "_$index"
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
            .apply { generateFunctionInvocations(ownerClass, function.name, function) }
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
    val ownerType = ownerClass.type.asPoetType() as ClassName
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerType, returnTypeName)
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
                        .addParameter(INSTANCE_NAME, ownerType)
                        .addCode(kotlinReturnGetStatement(INSTANCE_NAME, ownerType.member(property.name)))
                        .build()
                        .apply(::addFunction)
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerType)
                        .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
                        .addCode(kotlinReturnGetStatement(INSTANCE_NAME, ownerType.member(property.name)))
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
    val ownerType = ownerClass.type.asPoetType() as ClassName
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerType, returnTypeName)
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
                        .addParameter(INSTANCE_NAME, ownerType)
                        .addParameter(ARGUMENT_NAME, ANY)
                        .addCode(kotlinSetStatementBySingle(INSTANCE_NAME, property, ownerType.member(property.name)))
                        .build()
                        .apply(::addFunction)
                FunSpec.builder(INVOKE_NAME)
                        .addModifiers(OVERRIDE)
                        .throws(THROWABLE)
                        .returns(ANY.copy(nullable = true))
                        .addParameter(INSTANCE_NAME, ownerType)
                        .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
                        .addCode(kotlinSetStatementByArray(INSTANCE_NAME, property, ownerType.member(property.name)))
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

private fun TypeSpec.Builder.generateFunctionInvocations(ownerClass: KotlinMetaClass, name: String, function: KotlinMetaFunction) {
    val static = ownerClass.isObject
    val parameters = function.parameters
    val ownerTypeName = ownerClass.type.asPoetType() as ClassName
    val template = FunSpec.builder(INVOKE_NAME)
            .addModifiers(OVERRIDE)
            .throws(THROWABLE)
            .returns(ANY.copy(nullable = true))
            .apply { if (!static) addParameter(INSTANCE_NAME, ownerClass.type.asPoetType()) }
            .build()
    template.toBuilder().apply {
        val invoke = when {
            static -> kotlinInvokeStaticStatement(ownerTypeName.member(name), ownerClass.type, parameters)
            else -> kotlinInvokeInstanceStatement(ownerTypeName.member(name), parameters)
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
                static -> kotlinInvokeStaticStatement(ownerTypeName.member(name), ownerClass.type)
                else -> kotlinInvokeInstanceStatement(ownerTypeName.member(name))
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
                static -> kotlinInvokeStaticStatement(ownerTypeName.member(name), ownerClass.type, parameters.values.first())
                else -> kotlinInvokeInstanceStatement(ownerTypeName.member(name), parameters.values.first())
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
                .initializer(kotlinRegisterMetaParameterStatement(parameterIndex, parameter.value))
                .build()
                .apply(::addProperty)
        FunSpec.builder(parameterName)
                .returns(metaParameterType)
                .addCode(kotlinReturnStatement(parameterName))
                .build()
                .let(::addFunction)
    }
}

private fun TypeSpec.Builder.generateProxy(metaClass: KotlinMetaClass) {
    val proxyClassName = metaProxyClassName(metaClass.type.className!!)
    val proxyClass = classBuilder(proxyClassName)
            .addModifiers(PUBLIC)
            .superclass(KOTLIN_META_PROXY_CLASS_NAME)
            .addSuperinterface(metaClass.type.asPoetType())
            .apply {
                constructorBuilder()
                        .callSuperConstructor(INVOCATIONS_NAME)
                        .addModifiers(PUBLIC)
                        .addParameter(ParameterSpec.builder(INVOCATIONS_NAME, KOTLIN_MAP_META_METHOD_FUNCTION_TYPE_NAME).build())
                        .apply { generateProxyInvocations(metaClass, this) }
                        .build()
                        .apply(::addFunction)
            }
            .build()
    addType(proxyClass)
    addFunction(FunSpec.builder(PROXY_NAME)
            .addModifiers(PUBLIC, OVERRIDE)
            .returns(KOTLIN_META_PROXY_CLASS_NAME)
            .addParameter(ParameterSpec.builder(INVOCATIONS_NAME, KOTLIN_MAP_META_METHOD_FUNCTION_TYPE_NAME).build())
            .addCode(kotlinReturnNewProxyStatement(bestGuess(proxyClassName)))
            .build())

}

private fun TypeSpec.Builder.generateProxyInvocations(metaClass: KotlinMetaClass, constructor: FunSpec.Builder) {
    metaClass
            .functions
            .asSequence()
            .filter { method -> method.couldBeGenerated() }
            .groupBy { method -> method.name }
            .forEach { grouped ->
                grouped.value.forEachIndexed { methodIndex, method ->
                    var name = method.name
                    if (methodIndex > 0) name += "_$methodIndex"
                    val invocationName = metaProxyInvocationName(name)
                    addProperty(PropertySpec.builder(invocationName, KOTLIN_FUNCTION_TYPE_NAME)
                            .addModifiers(PRIVATE, FINAL)
                            .build())
                    addFunction(FunSpec.builder(method.name)
                            .addModifiers(PUBLIC, OVERRIDE)
                            .apply {
                                val throws = method.throws.map { exception -> exception.asPoetType() }
                                if (throws.isNotEmpty()) {
                                    throws(throws)
                                }
                            }
                            .returns(method.returnType?.asPoetType() ?: UNIT)
                            .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.key, parameter.value.type.asPoetType()).build() })
                            .addCode(kotlinCallInvocationStatement(method, invocationName))
                            .build())
                    constructor.addLines(kotlinGetInvocationStatement(name))
                }
            }

}
