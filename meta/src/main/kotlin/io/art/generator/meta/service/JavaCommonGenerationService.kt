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

package io.art.generator.meta.service

import com.squareup.javapoet.*
import com.squareup.javapoet.WildcardTypeName.subtypeOf
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.constants.JAVA_PACKAGE_PREFIX
import io.art.generator.meta.constants.OBJECT_CLASS_NAME
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.JavaMetaTypeKind.*

fun JavaMetaType.asPoetType(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> TypeName.get(originalType)

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetType())

    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> subtypeOf(bound.asPoetType()) }
            ?: wildcardSuperBound?.asPoetType()?.let(WildcardTypeName::supertypeOf)
            ?: WildcardTypeName.get(originalType)

    VARIABLE_KIND -> {
        val bounds = typeVariableBounds.map(JavaMetaType::asPoetType).toTypedArray()
        TypeVariableName.get(typeName, *bounds)
    }

    CLASS_KIND, INTERFACE_KIND -> {
        when {
            typeName.startsWith(JAVA_PACKAGE_PREFIX) -> TypeName.get(originalType)
            classTypeParameters.isNotEmpty() -> {
                val parameters = classTypeParameters
                        .values
                        .map(JavaMetaType::asPoetType)
                        .toTypedArray()
                ParameterizedTypeName.get(ClassName.get(classPackageName, className), *parameters)
            }
            else -> ClassName.get(classPackageName, className)
        }
    }
}

fun JavaMetaType.asPoetTypeWithoutVariables(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> asPoetType()
    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetTypeWithoutVariables())
    CLASS_KIND, INTERFACE_KIND -> when {
        typeName.startsWith(JAVA_PACKAGE_PREFIX) -> ClassName.get(typeName.substringBeforeLast(DOT), typeName.substringAfterLast(DOT))
        classTypeParameters.isEmpty() -> asPoetType().box()
        else -> {
            val parameters = classTypeParameters.values.map { parameter -> parameter.asPoetTypeWithoutVariables() }
            val rawType = ClassName.get(classPackageName, className)
            ParameterizedTypeName.get(rawType, *parameters.toTypedArray())
        }
    }
    VARIABLE_KIND -> subtypeOf(Object::class.java)
    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> subtypeOf(bound.asPoetTypeWithoutVariables()) }
            ?: wildcardSuperBound?.asPoetTypeWithoutVariables()?.let(WildcardTypeName::supertypeOf)
            ?: OBJECT_CLASS_NAME
}

fun JavaMetaType.extractPoetClass(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> asPoetType()
    ARRAY_KIND -> arrayComponentType!!.extractPoetClass()
    CLASS_KIND, INTERFACE_KIND -> ClassName.get(classPackageName, classFullName)
    VARIABLE_KIND -> OBJECT_CLASS_NAME
    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> subtypeOf(bound.extractPoetClass()) }
            ?: wildcardSuperBound?.extractPoetClass()?.let(WildcardTypeName::supertypeOf)
            ?: OBJECT_CLASS_NAME
}
