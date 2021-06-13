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

package io.art.generator.extension

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ClassName.Companion.bestGuess
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.art.generator.constants.META_METHOD_EXCLUSIONS
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaMethod
import io.art.generator.model.KotlinMetaType
import io.art.generator.model.KotlinMetaTypeKind.*
import org.jetbrains.kotlin.synthetic.isVisibleOutside

fun KotlinMetaType.asPoetType(): TypeName = when (kind) {
    ARRAY_KIND -> ARRAY.parameterizedBy(arrayComponentType!!.asPoetType())

    ENUM_KIND -> bestGuess(classFullName!!)

    CLASS_KIND -> when {
        typeParameters.isEmpty() -> bestGuess(classFullName!!)
        else -> {
            val parameters = typeParameters.map { parameter -> parameter.asPoetType() }
            val rawType = bestGuess(classFullName!!)
            rawType.parameterizedBy(*parameters.toTypedArray())
        }
    }

    WILDCARD_KIND -> STAR

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")

    FUNCTION_KIND -> LambdaTypeName.get(
            parameters = functionArgumentTypes.map { argument -> argument.asPoetType() }.toTypedArray(),
            returnType = functionResultType?.asPoetType() ?: UNIT
    )
}

fun KotlinMetaType.extractClass(): TypeName = when (kind) {
    ARRAY_KIND -> ARRAY

    CLASS_KIND, ENUM_KIND -> ClassName(classPackageName!!, className!!)

    WILDCARD_KIND -> ANY

    FUNCTION_KIND -> LambdaTypeName.get(
            parameters = functionArgumentTypes.map { argument -> argument.extractClass() }.toTypedArray(),
            returnType = functionResultType?.extractClass() ?: UNIT
    )

    UNKNOWN_KIND -> STAR
}

fun KotlinMetaClass.couldBeGenerated() = type.kind != ENUM_KIND && visibility.isVisibleOutside()

fun KotlinMetaMethod.couldBeGenerated() = !META_METHOD_EXCLUSIONS.contains(name) && visibility.isVisibleOutside()

fun KotlinMetaClass.parentMethods() = parent
        ?.methods
        ?.filter { method -> method.visibility.isVisibleOutside() }
        ?: emptyList()

fun KotlinMetaClass.parentProperties() = parent
        ?.properties
        ?.filter { property -> property.value.visibility.isVisibleOutside() }
        ?: emptyMap()
