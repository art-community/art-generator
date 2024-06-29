package io.art.generator.producer

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.throws
import io.art.core.constants.StringConstants
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.superFunctions
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaClassName
import io.art.generator.model.KotlinMetaFunction
import io.art.generator.model.KotlinMetaType
import io.art.generator.service.analyzing.KotlinAnalyzerBuiltins
import io.art.generator.templates.*
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import java.util.*

internal fun TypeSpec.Builder.generateConstructors(metaClassName: KotlinMetaClassName) {
    metaClassName.type.constructors
            .filter(KotlinMetaFunction::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = (metaClassName.type.type.className ?: CONSTRUCTOR_NAME)
                if (index > 0) name += "_$index"
                val constructorClassName = metaConstructorClassName(name)
                TypeSpec.classBuilder(constructorClassName)
                        .superclass(KOTLIN_META_CONSTRUCTOR_CLASS_NAME.parameterizedBy(metaClassName.metaName, metaClassName.typeName))
                        .addFunction(FunSpec.constructorBuilder()
                                .addParameter(OWNER_NAME, metaClassName.metaName)
                                .addModifiers(KModifier.INTERNAL)
                                .callSuperConstructor(kotlinMetaConstructorSuperStatement(metaClassName.type.type))
                                .build())
                        .apply { generateConstructorInvocations(metaClassName.type.type, constructor) }
                        .apply { generateParameters(constructor) }
                        .build()
                        .apply(::addType)
                val reference = ClassName(StringConstants.EMPTY_STRING, constructorClassName)
                PropertySpec.builder(name, reference)
                        .addModifiers(KModifier.PRIVATE)
                        .initializer(kotlinRegisterConstructor(reference))
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
            .addModifiers(KModifier.OVERRIDE)
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

internal fun TypeSpec.Builder.generateFunctions(metaClassName: KotlinMetaClassName) {
    val parentFunctions = metaClassName.type.superFunctions()
    val functions = metaClassName.type
            .functions
            .filter { function -> parentFunctions.none { parent -> parent.withoutModifiers() == function.withoutModifiers() } }
    (functions + parentFunctions)
            .asSequence()
            .filter(KotlinMetaFunction::couldBeGenerated)
            .groupBy { function -> function.name }
            .map { grouped -> grouped.value.forEachIndexed { index, function -> generateFunction(metaClassName, function, index) } }
}

private fun TypeSpec.Builder.generateFunction(metaClassName: KotlinMetaClassName, function: KotlinMetaFunction, index: Int) {
    var name = function.name
    if (index > 0) name += "_$index"
    val methodName = metaMethodName(name)
    val methodClassName = kotlinMetaMethodClassName(name)
    val returnType = function.returnType
    val returnTypeName = returnType?.asPoetType() ?: UNIT
    val static = metaClassName.type.isObject
    val parent = when {
        static -> KOTLIN_STATIC_META_METHOD_CLASS_NAME.parameterizedBy(metaClassName.metaName, returnTypeName)
        else -> KOTLIN_INSTANCE_META_METHOD_CLASS_NAME.parameterizedBy(metaClassName.metaName, metaClassName.type.type.asPoetType(), returnTypeName)
    }
    TypeSpec.classBuilder(methodClassName)
            .superclass(parent)
            .addFunction(FunSpec.constructorBuilder()
                    .addModifiers(KModifier.INTERNAL)
                    .addParameter(OWNER_NAME, metaClassName.metaName)
                    .callSuperConstructor(kotlinMetaMethodSuperStatement(function.name, returnType))
                    .build())
            .apply { generateFunctionInvocations(metaClassName.type, function.name, function) }
            .apply { generateParameters(function) }
            .build()
            .apply(::addType)
    PropertySpec.builder(methodName, methodClassName)
            .addModifiers(KModifier.PRIVATE, KModifier.FINAL)
            .initializer(kotlinRegisterMethod(methodClassName))
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
            .addModifiers(KModifier.OVERRIDE)
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
            Objects.isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
            function.returnType!!.originalType == KotlinAnalyzerBuiltins.builtins.unitType -> addLines(invoke, kotlinReturnNullStatement())
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
                Objects.isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
                function.returnType!!.originalType == KotlinAnalyzerBuiltins.builtins.unitType -> addLines(invoke, kotlinReturnNullStatement())
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
                Objects.isNull(function.returnType) -> addLines(invoke, kotlinReturnNullStatement())
                function.returnType!!.originalType == KotlinAnalyzerBuiltins.builtins.unitType -> addLines(invoke, kotlinReturnNullStatement())
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
                .addModifiers(KModifier.PRIVATE)
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
