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
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.art.generator.constants.META_METHOD_EXCLUSIONS
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaTypeKind.*
import org.jetbrains.kotlin.synthetic.isVisibleOutside

fun KotlinMetaType.withoutVariables(): TypeName = when (kind) {
    ARRAY_KIND -> ARRAY.parameterizedBy(arrayComponentType!!.withoutVariables())

    ENUM_KIND -> ClassName(classPackageName!!, className!!)

    CLASS_KIND -> when {
        typeParameters.isEmpty() -> ClassName(classPackageName!!, className!!)
        else -> {
            val parameters = typeParameters.map { parameter ->
                when (parameter.kind) {
                    VARIABLE_KIND -> STAR
                    else -> parameter.withoutVariables()
                }
            }
            val rawType = ClassName(classPackageName!!, className!!)
            rawType.parameterizedBy(*parameters.toTypedArray())
        }
    }

    VARIABLE_KIND -> ANY

    WILDCARD_KIND -> STAR

    UNKNOWN_KIND -> STAR

    FUNCTION_KIND -> LambdaTypeName.get(
            parameters = functionArgumentTypes.map { argument -> argument.withoutVariables() }.toTypedArray(),
            returnType = functionResultType?.withoutVariables() ?: UNIT
    )
}

fun KotlinMetaType.excludeVariables(exclusions: Set<String>): KotlinMetaType = when (kind) {
    ENUM_KIND, WILDCARD_KIND -> this

    VARIABLE_KIND -> if (exclusions.contains(typeName)) KOTLIN_ANY_META_TYPE else this

    ARRAY_KIND -> KotlinMetaType(
            originalType = originalType,
            typeName = typeName,
            kind = kind,
            arrayComponentType = arrayComponentType?.excludeVariables(exclusions))

    CLASS_KIND -> when {
        typeParameters.isEmpty() -> this
        else -> {
            val parameters = typeParameters
                    .filter { parameter -> parameter.kind != VARIABLE_KIND || !exclusions.contains(parameter.typeName) }
                    .map { parameter -> parameter.excludeVariables(exclusions) }
            KotlinMetaType(
                    originalType = originalType,
                    typeName = typeName,
                    kind = kind,
                    classFullName = classFullName,
                    className = className,
                    classPackageName = classPackageName,
                    typeParameters = parameters.toMutableList()
            )
        }
    }

    FUNCTION_KIND -> KotlinMetaType(
            originalType = originalType,
            typeName = typeName,
            kind = kind,
            functionArgumentTypes = functionArgumentTypes.map { type -> type.excludeVariables(exclusions) }.toMutableList(),
            functionResultType = functionResultType?.excludeVariables(exclusions)
    )

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun KotlinMetaType.extractClass(): TypeName = when (kind) {
    ARRAY_KIND -> ARRAY

    CLASS_KIND, ENUM_KIND -> ClassName(classPackageName!!, className!!)

    WILDCARD_KIND, VARIABLE_KIND -> ANY

    FUNCTION_KIND -> LambdaTypeName.get(
            parameters = functionArgumentTypes.map { argument -> argument.extractClass() }.toTypedArray(),
            returnType = functionResultType?.extractClass() ?: UNIT
    )

    UNKNOWN_KIND -> STAR
}

fun KotlinMetaType.hasVariable(): Boolean = kind == VARIABLE_KIND || arrayComponentType?.hasVariable() ?: false

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

fun KotlinMetaParameter.excludeVariables(exclusions: Set<String>) = KotlinMetaParameter(
        name,
        type.excludeVariables(exclusions),
        visibility,
        varargs
)
