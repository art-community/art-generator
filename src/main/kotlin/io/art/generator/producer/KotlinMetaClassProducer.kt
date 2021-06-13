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
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.parentMethods
import io.art.generator.extension.parentProperties
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaMethod
import io.art.generator.model.KotlinMetaType
import io.art.generator.templates.*
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isUnit
import java.util.Objects.isNull

fun TypeSpec.Builder.generateClass(metaClass: KotlinMetaClass) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = kotlinMetaClassClassName(metaClass.type.className)
    val typeName = metaClass.type.asPoetType()
    TypeSpec.classBuilder(metaClassName)
            .superclass(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(typeName))
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaClassSuperStatement(metaClass))
                    .build())
            .apply { generateConstructors(metaClass, typeName) }
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
    val fields = metaClass.parentProperties() + metaClass.properties
    fields.entries.forEach { field ->
        val fieldTypeName = field.value.type.asPoetType()
        val fieldMetaType = KOTLIN_META_FIELD_CLASS_NAME.parameterizedBy(fieldTypeName)
        val fieldName = metaFieldName(field.key)
        PropertySpec.builder(fieldName, fieldMetaType)
                .addModifiers(PRIVATE)
                .initializer(kotlinRegisterMetaFieldStatement(field.key, field.value))
                .build()
                .apply(::addProperty)
        FunSpec.builder(fieldName)
                .returns(fieldMetaType)
                .addCode(kotlinReturnStatement(fieldName))
                .build()
                .let(::addFunction)
    }
}

private fun TypeSpec.Builder.generateConstructors(metaClass: KotlinMetaClass, typeName: TypeName) {
    val type = metaClass.type
    metaClass.constructors
            .filter(KotlinMetaMethod::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += index
                val constructorClassName = metaConstructorClassName(name)
                classBuilder(constructorClassName)
                        .superclass(KOTLIN_META_CONSTRUCTOR_CLASS_NAME.parameterizedBy(typeName))
                        .addFunction(constructorBuilder()
                                .addModifiers(INTERNAL)
                                .callSuperConstructor(kotlinMetaConstructorSuperStatement(type, constructor.visibility))
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

private fun TypeSpec.Builder.generateConstructorInvocations(type: KotlinMetaType, constructor: KotlinMetaMethod) {
    val parameters = constructor.parameters
    val template = FunSpec.builder(INVOKE_NAME)
            .addModifiers(OVERRIDE)
            .throws(THROWABLE)
            .returns(type.asPoetType())
            .build()
    template.toBuilder()
            .addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
            .addCode(kotlinReturnInvokeConstructorStatement(type, parameters))
            .build()
            .apply(::addFunction)
    when (parameters.size) {
        0 -> {
            template.toBuilder()
                    .addCode(kotlinReturnInvokeWithoutArgumentsConstructorStatement(type))
                    .build()
                    .apply(::addFunction)
        }
        1 -> {
            template.toBuilder()
                    .addParameter(ARGUMENT_NAME, ANY)
                    .addCode(kotlinReturnInvokeOneArgumentConstructorStatement(type, parameters.values.first()))
                    .build()
                    .apply(::addFunction)
        }
    }
}

private fun TypeSpec.Builder.generateMethods(metaClass: KotlinMetaClass) {
    (metaClass.methods + metaClass.parentMethods())
            .asSequence()
            .filter(KotlinMetaMethod::couldBeGenerated)
            .groupBy { method -> method.name }
            .map { grouped -> grouped.value.forEachIndexed { methodIndex, method -> generateMethod(method, methodIndex, metaClass) } }
}

private fun TypeSpec.Builder.generateMethod(method: KotlinMetaMethod, index: Int, ownerClass: KotlinMetaClass) {
    var name = method.name
    if (index > 0) name += index
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = method.returnType
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
                    .callSuperConstructor(kotlinMetaMethodSuperStatement(method.name, returnType, method.visibility))
                    .build())
            .apply { generateMethodInvocations(ownerClass, method.name, method) }
            .apply { generateParameters(method) }
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

private fun TypeSpec.Builder.generateMethodInvocations(ownerClass: KotlinMetaClass, name: String, method: KotlinMetaMethod) {
    val static = ownerClass.isObject
    val parameters = method.parameters
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
            isNull(method.returnType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
            isUnit(method.returnType!!.originalType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
            else -> addCode(kotlinReturnStatement(invoke))
        }
        addParameter(ARGUMENTS_NAME, ARRAY.parameterizedBy(ANY))
        addFunction(build())
    }
    when (parameters.size) {
        0 -> template.toBuilder().apply {
            val invoke = when {
                static -> kotlinInvokeWithoutArgumentsStaticStatement(name, ownerClass.type)
                else -> kotlinInvokeWithoutArgumentsInstanceStatement(name)
            }
            when {
                isNull(method.returnType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
                isUnit(method.returnType!!.originalType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
                else -> addCode(kotlinReturnStatement(invoke))
            }
            addFunction(build())
        }
        1 -> template.toBuilder().apply {
            addParameter(ARGUMENT_NAME, ANY)
            val invoke = when {
                static -> kotlinInvokeOneArgumentStaticStatement(name, ownerClass.type, parameters.values.first())
                else -> kotlinInvokeOneArgumentInstanceStatement(name, parameters.values.first())
            }
            when {
                isNull(method.returnType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
                isUnit(method.returnType!!.originalType) -> addCode(invoke).addCode(kotlinReturnNullStatement())
                else -> addCode(kotlinReturnStatement(invoke))
            }
            addFunction(build())
        }
    }
}

private fun TypeSpec.Builder.generateParameters(method: KotlinMetaMethod) {
    method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
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
