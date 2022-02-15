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

@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.model

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

enum class JavaMetaTypeKind {
    CLASS_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    PRIMITIVE_KIND,
    ARRAY_KIND,
    UNKNOWN_KIND
}

data class JavaMetaType(
        val javaOriginalType: TypeMirror? = null,

        val typeName: String,
        val kind: JavaMetaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,

        val arrayComponentType: JavaMetaType? = null,

        val wildcardExtendsBound: JavaMetaType? = null,
        val wildcardSuperBound: JavaMetaType? = null,

        val typeParameters: MutableList<JavaMetaType> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as JavaMetaType
        javaOriginalType?.let { type -> if (type != other.javaOriginalType) return false }
        if (typeName != other.typeName) return false
        return true
    }

    override fun hashCode(): Int = javaOriginalType?.hashCode() ?: typeName.hashCode()
}

data class JavaMetaClass(
        val type: JavaMetaType,
        val fields: Map<String, JavaMetaField>,
        val constructors: List<JavaMetaMethod>,
        val methods: List<JavaMetaMethod>,
        val modifiers: Set<Modifier>,
        val parent: JavaMetaClass? = null,
        val innerClasses: MutableMap<String, JavaMetaClass> = mutableMapOf(),
        val interfaces: MutableList<JavaMetaClass> = mutableListOf(),
        val isInterface: Boolean = false,
) {
    override fun hashCode(): Int = type.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as JavaMetaClass
        return type == other.type
    }
}

data class JavaMetaField(
        val name: String,
        val type: JavaMetaType,
        val modifiers: Set<Modifier>,
)

data class JavaMetaParameter(
        val name: String,
        val type: JavaMetaType,
        val modifiers: Set<Modifier>,
)

data class JavaMetaMethod(
        val name: String,
        val returnType: JavaMetaType,
        val throws: Set<JavaMetaType>,
        val parameters: Map<String, JavaMetaParameter>,
        val modifiers: Set<Modifier>,
) {
    fun withoutModifiers() = JavaMetaMethodWithoutModifiers(
            name,
            returnType,
            throws,
            parameters
    )
}


data class JavaMetaMethodWithoutModifiers(
        val name: String,
        val returnType: JavaMetaType,
        val throws: Set<JavaMetaType>,
        val parameters: Map<String, JavaMetaParameter>,
)


data class JavaMetaClassName(
        val metaName: ClassName,
        val type: JavaMetaClass,
        val typeName: TypeName
)

