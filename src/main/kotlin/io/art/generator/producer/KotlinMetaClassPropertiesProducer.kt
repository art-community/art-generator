package io.art.generator.producer;

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.throws
import io.art.core.extensions.StringExtensions
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.superProperties
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaProperty
import io.art.generator.model.KotlinMetaPropertyFunction
import io.art.generator.templates.*
import org.jetbrains.kotlin.descriptors.Visibilities


internal fun TypeSpec.Builder.generateProperties(metaClass: KotlinMetaClass) {
    val parentProperties = metaClass.superProperties()
    parentProperties.forEach { property -> generateProperty(property.value, true, metaClass) }
    metaClass.properties
            .entries
            .filter { property -> !parentProperties.containsKey(property.key) }
            .forEach { property -> generateProperty(property.value, false, metaClass) }
}

internal fun TypeSpec.Builder.generateProperty(property: KotlinMetaProperty, inherited: Boolean, owner: KotlinMetaClass) {
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
    if (property.visibility.delegate != Visibilities.Public) return
    property.getter
            ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
            ?.apply { generateGetter(property, owner) }
    property.setter
            ?.takeIf(KotlinMetaPropertyFunction::couldBeGenerated)
            ?.apply { generateSetter(property, owner) }
}

private fun TypeSpec.Builder.generateGetter(property: KotlinMetaProperty, ownerClass: KotlinMetaClass) {
    val name = GET_NAME + StringExtensions.capitalize(property.name)
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = property.type
    val returnTypeName = returnType.asPoetType()
    val ownerType = ownerClass.type.asPoetType() as ClassName
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerType, returnTypeName)
    TypeSpec.classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(FunSpec.constructorBuilder()
                    .addModifiers(INTERNAL)
                    .addParameter(OWNER_NAME, KOTLIN_META_CLASS_PARAMETRIZED_CLASS_NAME)
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
            .initializer(kotlinRegisterOwnedNewStatement(methodClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(methodName)
            .returns(methodClassName)
            .addCode(kotlinReturnStatement(methodName))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateSetter(property: KotlinMetaProperty, ownerClass: KotlinMetaClass) {
    val name = SET_NAME + StringExtensions.capitalize(property.name)
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = property.type
    val returnTypeName = returnType.asPoetType()
    val ownerType = ownerClass.type.asPoetType() as ClassName
    val parent = KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(ownerType, returnTypeName)
    TypeSpec.classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(FunSpec.constructorBuilder()
                    .addModifiers(INTERNAL)
                    .addParameter(OWNER_NAME, KOTLIN_META_CLASS_PARAMETRIZED_CLASS_NAME)
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
            .initializer(kotlinRegisterOwnedNewStatement(methodClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(methodName)
            .returns(methodClassName)
            .addCode(kotlinReturnStatement(methodName))
            .build()
            .let(::addFunction)
}
