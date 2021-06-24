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
import com.squareup.javapoet.TypeName.*
import com.squareup.javapoet.WildcardTypeName.subtypeOf
import io.art.core.constants.StringConstants.DOT
import io.art.generator.constants.JAVA_OBJECT_CLASS_NAME
import io.art.generator.constants.META_METHOD_EXCLUSIONS
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaMethod
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind.*
import javax.lang.model.element.Modifier.PUBLIC

fun JavaMetaType.asPoetType(): TypeName = when (kind) {
    PRIMITIVE_KIND -> asPrimitive()

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetType())

    ENUM_KIND -> extractClassName()

    CLASS_KIND -> when {
        typeParameters.isEmpty() -> extractClassName()
        else -> {
            val parameters = typeParameters.map { parameter -> parameter.asPoetType() }
            val rawType = extractClassName()
            ParameterizedTypeName.get(rawType, *parameters.toTypedArray())
        }
    }

    WILDCARD_KIND -> wildcardExtendsBound?.asPoetType()?.let(WildcardTypeName::subtypeOf)
            ?: wildcardSuperBound?.asPoetType()?.let(WildcardTypeName::supertypeOf)
            ?: subtypeOf(JAVA_OBJECT_CLASS_NAME)

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun JavaMetaType.extractClass(): TypeName = when (kind) {
    PRIMITIVE_KIND -> asPrimitive()

    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.extractClass())

    CLASS_KIND, ENUM_KIND -> extractClassName()

    WILDCARD_KIND -> wildcardExtendsBound?.extractClass() ?: JAVA_OBJECT_CLASS_NAME

    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $this")
}

fun JavaMetaClass.couldBeGenerated() = type.kind != ENUM_KIND && modifiers.contains(PUBLIC)

fun JavaMetaMethod.couldBeGenerated() = !META_METHOD_EXCLUSIONS.contains(name) && modifiers.contains(PUBLIC)

fun JavaMetaClass.parentMethods() = parent
        ?.methods
        ?: emptyList()

fun JavaMetaClass.parentFields() = parent
        ?.fields
        ?: emptyMap()

private fun JavaMetaType.extractClassName(): ClassName {
    val nestedClasses = classFullName!!.substringAfter("$classPackageName.$className")
            .split(DOT)
            .filter { part -> part.isNotBlank() }
            .toTypedArray()
    if (nestedClasses.isEmpty()) {
        return ClassName.get(classPackageName!!, className!!)
    }
    return ClassName.get(classPackageName!!, className!!, *nestedClasses)
}

private fun JavaMetaType.asPrimitive() = when (typeName) {
    Boolean::class.java.typeName -> BOOLEAN
    Int::class.java.typeName -> INT
    Short::class.java.typeName -> SHORT
    Double::class.java.typeName -> DOUBLE
    Byte::class.java.typeName -> BYTE
    Long::class.java.typeName -> LONG
    Char::class.java.typeName -> CHAR
    Float::class.java.typeName -> FLOAT
    else -> VOID.box()
}
