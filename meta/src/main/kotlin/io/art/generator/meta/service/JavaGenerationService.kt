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

@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.meta.service

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.exception.NotImplementedException
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.model.MetaJavaType
import io.art.generator.meta.model.MetaJavaTypeKind.*
import io.art.generator.meta.templates.STUB_METHOD_STRING
import io.art.generator.meta.templates.THROW_EXCEPTION_STATEMENT
import javax.lang.model.type.WildcardType

fun generateJavaStubs(classes: Set<MetaJavaClass>) {
    val root = generatorConfiguration.stubRoot
    classes.forEach { metaJavaClass -> metaJavaClass.asPoetFile().writeTo(root) }
}

fun MetaJavaClass.asPoetFile(): JavaFile = JavaFile.builder(type.classPackageName!!, asPoetClass()).build()

private fun MetaJavaType.asPoetType(): TypeName = when (kind) {
    JDK_KIND, PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> originalType
            ?.let(TypeName::get)
            ?: TypeName.get(reflectionType!!)
    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetType())
    WILDCARD_KIND -> wildcardSuperBound
            ?.let { bound -> WildcardTypeName.subtypeOf(bound.asPoetType()) }
            ?: wildcardExtendsBound?.asPoetType()?.let(WildcardTypeName::supertypeOf)
            ?: WildcardTypeName.get(originalType as? WildcardType ?: reflectionType as WildcardType)
    INTERSECTION_KIND -> TypeVariableName.get(typeName, *intersectionBounds.toList().map { bound -> bound.asPoetType() }.toTypedArray())
    VARIABLE_KIND -> TypeVariableName.get(typeName, *arrayOf(variableUpperBounds, variableLowerBounds).filterNotNull().map { bound -> bound.asPoetType() }.toTypedArray())
    CLASS_KIND, INTERFACE_KIND -> when {
        classTypeArguments.isNotEmpty() -> ParameterizedTypeName.get(ClassName.get(classPackageName!!, className), *classTypeArguments.values.map { parameter -> parameter.asPoetType() }.toTypedArray())
        else -> ClassName.get(classPackageName!!, className)
    }
}

private fun MetaJavaClass.asPoetClass(): TypeSpec = classBuilder(type.className)
        .apply {
            type.classSuperClass?.let { superClass -> superclass(superClass.asPoetType()) }
            type.classSuperInterfaces.forEach { interfaceType -> addSuperinterface(interfaceType.asPoetType()) }
            type.classTypeArguments.forEach { parameter ->
                addTypeVariable(parameter.value.asPoetType() as TypeVariableName)
            }
            fields.forEach { field ->
                addField(field.value.type.asPoetType(), field.key)
            }
            methods.forEach { method ->
                addMethod(methodBuilder(method.name)
                        .returns(method.returnType.asPoetType())
                        .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
                        .apply { addCode(THROW_EXCEPTION_STATEMENT, NotImplementedException::class.java, STUB_METHOD_STRING) }
                        .build())
            }
            constructors.forEach { constructor ->
                addMethod(constructorBuilder()
                        .addParameters(constructor.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
                        .build())
            }
            innerClasses.forEach { inner ->
                addType(inner.value.asPoetClass())
            }
        }
        .build()
