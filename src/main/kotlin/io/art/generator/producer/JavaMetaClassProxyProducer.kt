package io.art.generator.producer

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.constants.*
import io.art.generator.extension.asPoetType
import io.art.generator.extension.asUnboxedPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.model.JavaMetaClass
import io.art.generator.templates.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.Modifier.PUBLIC


internal fun TypeSpec.Builder.generateProxy(metaClass: JavaMetaClass) {
    val proxyClassName = metaProxyClassName(metaClass.type.className!!)
    val proxyClass = classBuilder(proxyClassName)
            .addModifiers(PUBLIC)
            .superclass(JAVA_META_PROXY_CLASS_NAME)
            .addSuperinterface(metaClass.type.asPoetType())
            .apply {
                qualifyImports(mutableSetOf(), metaClass)
                constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(ParameterSpec.builder(JAVA_MAP_META_METHOD_FUNCTION_TYPE_NAME, INVOCATIONS_NAME).build())
                        .addStatement(javaProxySuperStatement())
                        .apply { generateProxyInvocations(metaClass, this) }
                        .build()
                        .apply(::addMethod)
            }
            .build()
    addType(proxyClass)
    addMethod(methodBuilder(PROXY_NAME)
            .addAnnotation(JAVA_OVERRIDE_CLASS_NAME)
            .addModifiers(PUBLIC)
            .returns(JAVA_META_PROXY_CLASS_NAME)
            .addParameter(ParameterSpec.builder(JAVA_MAP_META_METHOD_FUNCTION_TYPE_NAME, INVOCATIONS_NAME).build())
            .addStatement(javaReturnNewProxyStatement(ClassName.get(EMPTY_STRING, proxyClassName)))
            .build())

}

private fun TypeSpec.Builder.generateProxyInvocations(metaClass: JavaMetaClass, constructor: MethodSpec.Builder) {
    metaClass
            .methods
            .asSequence()
            .filter { method -> method.couldBeGenerated() && !method.modifiers.contains(Modifier.STATIC) }
            .groupBy { method -> method.name }
            .forEach { grouped ->
                grouped.value.forEachIndexed { methodIndex, method ->
                    var name = method.name
                    if (methodIndex > 0) name += "_$methodIndex"
                    val invocationName = metaProxyInvocationName(name)
                    addField(FieldSpec.builder(JAVA_FUNCTION_TYPE_NAME, invocationName)
                            .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                            .build())
                    addMethod(methodBuilder(method.name)
                            .addModifiers(PUBLIC)
                            .addExceptions(method.throws.map { type -> type.asPoetType() })
                            .addAnnotation(JAVA_OVERRIDE_CLASS_NAME)
                            .returns(method.returnType.asUnboxedPoetType())
                            .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
                            .addCode(javaCallInvocationStatement(method, invocationName))
                            .build())
                    constructor.addStatement(javaGetInvocationStatement(name))
                }
            }
}
