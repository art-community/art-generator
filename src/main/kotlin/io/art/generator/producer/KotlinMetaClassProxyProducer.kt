package io.art.generator.producer

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.jvm.throws
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.model.KotlinMetaClass
import io.art.generator.templates.*
import org.jetbrains.kotlin.descriptors.Visibilities


internal fun TypeSpec.Builder.generateProxy(metaClass: KotlinMetaClass) {
    val proxyClassName = metaProxyClassName(metaClass.type.className!!)
    val proxyClass = TypeSpec.classBuilder(proxyClassName)
            .addModifiers(PUBLIC, INNER)
            .superclass(KOTLIN_META_PROXY_CLASS_NAME)
            .addSuperinterface(metaClass.type.asPoetType())
            .apply {
                FunSpec.constructorBuilder()
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
            .addCode(kotlinReturnNewProxyStatement(ClassName.bestGuess(proxyClassName)))
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
                                if (throws.isNotEmpty()) throws(throws)
                            }
                            .returns(method.returnType?.asPoetType() ?: UNIT)
                            .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.key, parameter.value.type.asPoetType()).build() })
                            .addCode(kotlinCallInvocationStatement(method, invocationName))
                            .build())
                    constructor.addCode(kotlinGetInvocationStatement(name))
                }
            }

    metaClass.properties.values
            .asSequence()
            .filter { property -> property.visibility.delegate == Visibilities.Public }
            .forEach { property ->
                addProperty(PropertySpec.builder(property.name, property.type.asPoetType())
                        .addModifiers(FINAL, OVERRIDE)
                        .getter(FunSpec.getterBuilder().addCode(kotlinNotImplementedStatement()).build())
                        .build())
            }
}
