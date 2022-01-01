/*
 * ART
 *
 * Copyright 2019-2022 ART
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

package io.art.generator.extension

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.WildcardTypeName.Companion.consumerOf
import com.squareup.kotlinpoet.WildcardTypeName.Companion.producerOf
import io.art.core.constants.StringConstants.DOT
import io.art.generator.constants.KOTLIN
import io.art.generator.constants.META_METHOD_EXCLUSIONS
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaTypeKind.*
import io.art.generator.model.KotlinTypeVariance.*
import org.jetbrains.kotlin.descriptors.Visibilities.Public

fun KotlinMetaType.asPoetType(): TypeName {
    val rawType = when (kind) {
        ARRAY_KIND -> ARRAY
                .parameterizedBy(arrayComponentType!!.asPoetType())
                .copy(nullable = nullable)

        ENUM_KIND -> asClassName().copy(nullable = nullable)

        CLASS_KIND -> when {
            typeParameters.isEmpty() -> asClassName().copy(nullable = nullable)
            else -> {
                val parameters = typeParameters.map { parameter -> parameter.asPoetType().copy(nullable = parameter.nullable) }
                val rawType = asClassName()
                rawType.parameterizedBy(*parameters.toTypedArray()).copy(nullable = nullable)
            }
        }

        WILDCARD_KIND -> STAR

        FUNCTION_KIND -> LambdaTypeName.get(
                parameters = lambdaArgumentTypes.map { argument -> argument.asPoetType() }.toTypedArray(),
                returnType = lambdaResultType?.asPoetType() ?: UNIT
        ).copy(nullable = nullable)

        UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
    }
    return when (typeVariance) {
        IN -> consumerOf(rawType)
        OUT -> producerOf(rawType)
        INVARIANT -> rawType
        null -> rawType
    }
}

fun KotlinMetaType.extractClass(): TypeName = when (kind) {
    ARRAY_KIND -> ARRAY

    CLASS_KIND, ENUM_KIND -> asClassName()

    WILDCARD_KIND -> ANY

    FUNCTION_KIND -> ClassName(KOTLIN, typeName)

    UNKNOWN_KIND -> STAR
}

fun KotlinMetaClass.couldBeGenerated() = type.kind != ENUM_KIND && visibility.delegate == Public

fun KotlinMetaFunction.couldBeGenerated() = !META_METHOD_EXCLUSIONS.contains(name) && visibility.delegate == Public

fun KotlinMetaPropertyFunction.couldBeGenerated() = visibility.delegate == Public

fun KotlinMetaClass.superFunctions(): List<KotlinMetaFunction> {
    val parentFunctions = (parent?.functions ?: emptyList()) + (parent?.superFunctions() ?: emptyList())
    val interfaceFunctions = interfaces.flatMap { parent -> parent.superFunctions() }
    return parentFunctions + interfaceFunctions
}

fun KotlinMetaClass.superProperties(): Map<String, KotlinMetaProperty> {
    return (parent?.properties ?: emptyMap()) + (parent?.superProperties() ?: emptyMap())
}

private fun KotlinMetaType.asClassName(): ClassName {
    val classes = classFullName!!.substringAfter(classPackageName!!)
            .split(DOT)
            .filter { part -> part.isNotBlank() }
            .toTypedArray()
    return ClassName(classPackageName, *classes)
}
