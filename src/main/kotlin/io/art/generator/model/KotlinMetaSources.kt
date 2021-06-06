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

package io.art.generator.model

import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import java.nio.file.Path
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

enum class KotlinMetaTypeKind {
    CLASS_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    INTERFACE_KIND,
    PRIMITIVE_KIND,
    VARIABLE_KIND,
    ARRAY_KIND,
    UNKNOWN_KIND
}

lateinit var KOTLIN_OBJECT_META_TYPE: KotlinMetaType
fun hasKotlinObjectMetaType() = ::KOTLIN_OBJECT_META_TYPE.isInitialized

data class KotlinMetaType(
        val originalType: TypeMirror,

        val typeName: String,
        val kind: KotlinMetaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,

        val arrayComponentType: KotlinMetaType? = null,

        val typeParameters: MutableList<KotlinMetaType> = mutableListOf(),
        val typeVariableBounds: MutableList<KotlinMetaType> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as JavaMetaType
        if (originalType != other.originalType) return false
        return true
    }

    override fun hashCode(): Int = originalType.hashCode()
}

data class KotlinMetaClass(
        val type: KotlinMetaType,
        val source: Path,
        val fields: Map<String, KotlinMetaField>,
        val constructors: List<KotlinMetaMethod>,
        val innerClasses: Map<String, KotlinMetaClass>,
        val methods: List<KotlinMetaMethod>,
        val modifiers: Set<Modifier>,
        val parent: KotlinMetaClass? = null,
        val interfaces: List<KotlinMetaClass> = emptyList(),
)

data class KotlinMetaField(
        val name: String,
        val type: KotlinMetaType,
        val modifiers: Set<Modifier>
)

data class KotlinMetaParameter(
        val name: String,
        val type: KotlinMetaType,
        val modifiers: Set<Modifier>
)

data class KotlinMetaMethod(
        val name: String,
        val returnType: KotlinMetaType,
        val parameters: Map<String, KotlinMetaParameter>,
        val modifiers: Set<Modifier>,
        val typeParameters: List<KotlinMetaType> = emptyList(),
        val exceptions: List<KotlinMetaType> = emptyList(),
)
