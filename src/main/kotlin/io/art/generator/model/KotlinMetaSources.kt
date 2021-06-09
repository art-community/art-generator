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
import org.jetbrains.kotlin.descriptors.DescriptorVisibility
import org.jetbrains.kotlin.types.KotlinType
import java.beans.Visibility
import java.nio.file.Path
import javax.lang.model.element.Modifier

enum class KotlinMetaTypeKind {
    CLASS_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    INTERFACE_KIND,
    VARIABLE_KIND,
    UNKNOWN_KIND
}

enum class KotlinTypeVariableVariance {
    IN,
    OUT,
    INVARIANT
}

lateinit var KOTLIN_ANY_META_TYPE: KotlinMetaType
fun hasKotlinAnyMetaType() = ::KOTLIN_ANY_META_TYPE.isInitialized

data class KotlinMetaType(
        val originalType: KotlinType,

        val typeName: String,
        val kind: KotlinMetaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,

        val arrayComponentType: KotlinMetaType? = null,

        val typeParameters: MutableList<KotlinMetaType> = mutableListOf(),

        val typeVariableVariance: KotlinTypeVariableVariance? = null,
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
        val source: Path?,
        val properties: Map<String, KotlinMetaProperty>,
        val constructors: List<KotlinMetaMethod>,
        val innerClasses: Map<String, KotlinMetaClass>,
        val methods: List<KotlinMetaMethod>,
        val visibility: DescriptorVisibility,
        val parent: KotlinMetaClass? = null,
        val interfaces: List<KotlinMetaClass> = emptyList(),
)

data class KotlinMetaProperty(
        val name: String,
        val type: KotlinMetaType,
        val visibility: DescriptorVisibility
)

data class KotlinMetaParameter(
        val name: String,
        val type: KotlinMetaType,
        val visibility: DescriptorVisibility
)

data class KotlinMetaMethod(
        val name: String,
        val returnType: KotlinMetaType?,
        val parameters: Map<String, KotlinMetaParameter>,
        val visibility: DescriptorVisibility,
        val typeParameters: List<KotlinMetaType> = emptyList()
)
