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
import io.art.generator.meta.model.MetaJavaType
import io.art.generator.meta.model.MetaJavaTypeKind.*
import javax.lang.model.type.WildcardType

fun MetaJavaType.asPoetType(): TypeName = when (kind) {
    JDK_KIND, PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> {
        originalType?.let(TypeName::get)?.let(TypeName::box) ?: TypeName.get(reflectionType!!).let(TypeName::box)
    }

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetType())

    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> WildcardTypeName.subtypeOf(bound.asPoetType()) }
            ?: wildcardSuperBound?.asPoetType()?.let(WildcardTypeName::supertypeOf)
            ?: WildcardTypeName.get(originalType as? WildcardType ?: reflectionType as WildcardType)

    VARIABLE_KIND -> {
        val bounds = typeVariableBounds.values.map(MetaJavaType::asPoetType).toTypedArray()
        TypeVariableName.get(typeName, *bounds)
    }

    CLASS_KIND, INTERFACE_KIND -> {
        val rawType = ClassName.get(classPackageName!!, className)
        when {
            classTypeParameters.isNotEmpty() -> {
                val parameters = classTypeParameters
                        .values
                        .map(MetaJavaType::asPoetType)
                        .toTypedArray()
                ParameterizedTypeName.get(rawType, *parameters)
            }
            else -> rawType
        }
    }
}
