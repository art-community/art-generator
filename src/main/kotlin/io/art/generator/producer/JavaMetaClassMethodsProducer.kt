package io.art.generator.producer;

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.methodBuilder
import io.art.core.constants.StringConstants
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.superMethods
import io.art.generator.model.JavaMetaClassName
import io.art.generator.model.JavaMetaMethod
import io.art.generator.model.JavaMetaType
import io.art.generator.templates.*
import javax.lang.model.element.Modifier.*

internal fun TypeSpec.Builder.generateConstructors(metaClassName: JavaMetaClassName) {
    val type = metaClassName.type.type
    metaClassName.type.constructors
            .filter(JavaMetaMethod::couldBeGenerated)
            .mapIndexed { index, constructor ->
                var name = CONSTRUCTOR_NAME
                if (index > 0) name += "_$index"
                val constructorClassName = metaConstructorClassName(name)
                TypeSpec.classBuilder(constructorClassName)
                        .addModifiers(PUBLIC, FINAL)
                        .superclass(ParameterizedTypeName.get(JAVA_META_CONSTRUCTOR_CLASS_NAME, metaClassName.metaName, metaClassName.typeName.box()))
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(PRIVATE)
                                .addParameter(metaClassName.metaName, OWNER_NAME)
                                .addCode(javaMetaConstructorSuperStatement(type))
                                .build())
                        .apply { generateConstructorInvocations(type, constructor) }
                        .apply { generateParameters(constructor) }
                        .build()
                        .apply(::addType)
                val reference = ClassName.get(StringConstants.EMPTY_STRING, constructorClassName)
                FieldSpec.builder(reference, name)
                        .addModifiers(PRIVATE, FINAL)
                        .initializer(javaRegisterConstructor(reference))
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

internal fun TypeSpec.Builder.generateMethods(metaClassName: JavaMetaClassName) {
    val parentMethods = metaClassName.type.superMethods()
    val methods = metaClassName.type
            .methods
            .asSequence()
            .filter { method -> parentMethods.none { parent -> parent.withoutModifiers() == method.withoutModifiers() } }
    (methods + parentMethods)
            .filter(JavaMetaMethod::couldBeGenerated)
            .groupBy { method -> method.name }
            .map { grouped -> grouped.value.forEachIndexed { methodIndex, method -> generateMethod(method, methodIndex, metaClassName) } }
}

private fun TypeSpec.Builder.generateMethod(method: JavaMetaMethod, index: Int, metaClassName: JavaMetaClassName) {
    var name = method.name
    if (index > 0) name += "_$index"
    val methodName = metaMethodName(name)
    val methodClassName = javaMetaMethodClassName(name)
    val returnType = method.returnType
    val returnTypeName = returnType.asPoetType().box()
    val static = method.modifiers.contains(STATIC)
    val parent = when {
        static -> ParameterizedTypeName.get(JAVA_STATIC_META_METHOD_CLASS_NAME, metaClassName.metaName, returnTypeName)
        else -> ParameterizedTypeName.get(JAVA_INSTANCE_META_METHOD_CLASS_NAME, metaClassName.metaName, metaClassName.type.type.asPoetType(), returnTypeName)
    }
    TypeSpec.classBuilder(methodClassName)
            .addModifiers(PUBLIC, FINAL)
            .superclass(parent)
            .addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addParameter(metaClassName.metaName, OWNER_NAME)
                    .addCode(javaMetaMethodSuperStatement(method.name, returnType))
                    .build())
            .apply { generateMethodInvocations(metaClassName.type.type, method.name, method) }
            .apply { generateParameters(method) }
            .build()
            .apply(::addType)
    FieldSpec.builder(methodClassName, methodName)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterMethod(methodClassName))
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
