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
import io.art.generator.meta.constants.OBJECT_CLASS_NAME
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.JavaMetaTypeKind.*

fun JavaMetaType.toPoet(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> TypeName.get(originalType)

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.toPoet())

    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> subtypeOf(bound.toPoet()) }
            ?: wildcardSuperBound?.toPoet()?.let(WildcardTypeName::supertypeOf)
            ?: WildcardTypeName.get(originalType)

    VARIABLE_KIND -> {
        val bounds = typeVariableBounds.map(JavaMetaType::toPoet).toTypedArray()
        TypeVariableName.get(typeName, *bounds)
    }

    CLASS_KIND, INTERFACE_KIND -> {
        when {
            typeParameters.isNotEmpty() -> {
                val parameters = typeParameters
                        .values
                        .map(JavaMetaType::toPoet)
                        .toTypedArray()
                ParameterizedTypeName.get(ClassName.get(classPackageName, className), *parameters)
            }
            else -> ClassName.get(classPackageName, className)
        }
    }
}

fun JavaMetaType.withoutVariables(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> toPoet()
    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.withoutVariables())
    CLASS_KIND, INTERFACE_KIND -> when {
        typeParameters.isEmpty() -> toPoet()
        else -> {
            val parameters = typeParameters.values.map { parameter ->
                when (parameter.kind) {
                    VARIABLE_KIND -> subtypeOf(Object::class.java)
                    else -> parameter.withoutVariables()
                }
            }
            val rawType = ClassName.get(classPackageName, className)
            ParameterizedTypeName.get(rawType, *parameters.toTypedArray())
        }
    }
    VARIABLE_KIND -> OBJECT_CLASS_NAME
    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> subtypeOf(bound.withoutVariables()) }
            ?: wildcardSuperBound?.withoutVariables()?.let(WildcardTypeName::supertypeOf)
            ?: subtypeOf(Object::class.java)
}

fun JavaMetaType.extractClass(): TypeName = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> toPoet()
    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.extractClass())
    CLASS_KIND, INTERFACE_KIND -> ClassName.get(classPackageName, className)
    VARIABLE_KIND -> OBJECT_CLASS_NAME
    WILDCARD_KIND -> wildcardExtendsBound?.extractClass()
            ?: wildcardSuperBound?.extractClass()
            ?: OBJECT_CLASS_NAME
}
