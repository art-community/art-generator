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

import com.squareup.javapoet.*
import com.squareup.javapoet.WildcardTypeName.subtypeOf
import io.art.generator.constants.META_METHOD_EXCLUSIONS
import io.art.generator.constants.OBJECT_CLASS_NAME
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.model.*
import io.art.generator.model.JavaMetaTypeKind.*
import java.lang.Void.TYPE
import javax.lang.model.element.Modifier.*

fun JavaMetaType.withoutVariables(): TypeName = when (kind) {
    PRIMITIVE_KIND -> TypeName.get(originalType)

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.withoutVariables())

    ENUM_KIND -> ClassName.get(classPackageName, className)

    CLASS_KIND, INTERFACE_KIND -> when {
        typeParameters.isEmpty() -> ClassName.get(classPackageName, className)
        else -> {
            val parameters = typeParameters.map { parameter ->
                when (parameter.kind) {
                    VARIABLE_KIND -> subtypeOf(OBJECT_CLASS_NAME)
                    else -> parameter.withoutVariables()
                }
            }
            val rawType = ClassName.get(classPackageName, className)
            ParameterizedTypeName.get(rawType, *parameters.toTypedArray())
        }
    }

    VARIABLE_KIND -> OBJECT_CLASS_NAME

    WILDCARD_KIND -> wildcardExtendsBound?.withoutVariables()?.let(WildcardTypeName::subtypeOf)
            ?: wildcardSuperBound?.withoutVariables()?.let(WildcardTypeName::supertypeOf)
            ?: subtypeOf(OBJECT_CLASS_NAME)

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun JavaMetaType.excludeVariables(exclusions: Set<String>): JavaMetaType = when (kind) {
    PRIMITIVE_KIND, ENUM_KIND -> this

    VARIABLE_KIND -> if (exclusions.contains(typeName)) OBJECT_META_TYPE else this

    ARRAY_KIND -> JavaMetaType(
            originalType = originalType,
            typeName = typeName,
            kind = kind,
            arrayComponentType = arrayComponentType?.excludeVariables(exclusions))

    CLASS_KIND, INTERFACE_KIND -> when {
        typeParameters.isEmpty() -> this
        else -> {
            val parameters = typeParameters
                    .filter { parameter -> parameter.kind != VARIABLE_KIND || !exclusions.contains(parameter.typeName) }
                    .map { parameter -> parameter.excludeVariables(exclusions) }
            JavaMetaType(
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

    WILDCARD_KIND -> JavaMetaType(
            originalType = originalType,
            typeName = typeName,
            kind = kind,
            wildcardSuperBound = wildcardSuperBound?.excludeVariables(exclusions),
            wildcardExtendsBound = wildcardExtendsBound?.excludeVariables(exclusions)
    )

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun JavaMetaType.extractClass(): TypeName = when (kind) {
    PRIMITIVE_KIND -> {
        if (typeName == TYPE.name) TypeName.get(originalType).box() else TypeName.get(originalType)
    }

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.extractClass())

    CLASS_KIND, INTERFACE_KIND, ENUM_KIND -> ClassName.get(classPackageName, className)

    VARIABLE_KIND -> OBJECT_CLASS_NAME

    WILDCARD_KIND -> wildcardExtendsBound?.extractClass()
            ?: wildcardSuperBound?.extractClass()
            ?: OBJECT_CLASS_NAME

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun JavaMetaType.hasVariable(): Boolean = kind == VARIABLE_KIND || arrayComponentType?.hasVariable() ?: false

fun JavaMetaClass.couldBeGenerated() = type.kind != ENUM_KIND && modifiers.contains(PUBLIC)

fun JavaMetaMethod.couldBeGenerated() = !META_METHOD_EXCLUSIONS.contains(name) && modifiers.contains(PUBLIC)

fun JavaMetaClass.parentMethods() = parent
        ?.methods
        ?.filter { method ->
            method.modifiers.contains(PUBLIC)
                    || method.modifiers.contains(PROTECTED)
                    || !(method.modifiers.contains(PRIVATE) && parent.type.classPackageName == type.classPackageName)
        }
        ?: emptyList()

fun JavaMetaClass.parentFields() = parent
        ?.fields
        ?.filter { field ->
            field.value.modifiers.contains(PUBLIC)
                    || field.value.modifiers.contains(PROTECTED)
                    || !(field.value.modifiers.contains(PRIVATE) && parent.type.classPackageName == type.classPackageName)
        }
        ?: emptyMap()

fun JavaMetaParameter.excludeVariables(exclusions: Set<String>) = JavaMetaParameter(
        name,
        type.excludeVariables(exclusions),
        modifiers
)
