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
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.types.KotlinType

enum class KotlinMetaTypeKind {
    CLASS_KIND,
    ARRAY_KIND,
    FUNCTION_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    UNKNOWN_KIND
}

enum class KotlinMetaPropertyFunctionKind {
    GETTER,
    SETTER
}

enum class KotlinTypeVariance {
    IN,
    OUT,
    INVARIANT
}

data class KotlinMetaType(
        val originalType: KotlinType,

        val typeName: String,
        val kind: KotlinMetaTypeKind,
        val nullable: Boolean = false,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,

        val arrayComponentType: KotlinMetaType? = null,

        val typeParameters: MutableList<KotlinMetaType> = mutableListOf(),

        val functionArgumentTypes: MutableList<KotlinMetaType> = mutableListOf(),
        val functionResultType: KotlinMetaType? = null,

        val typeVariance: KotlinTypeVariance? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as KotlinMetaType
        if (originalType != other.originalType) return false
        return true
    }

    override fun hashCode(): Int = originalType.hashCode()
}

data class KotlinMetaClass(
        val type: KotlinMetaType,
        val properties: Map<String, KotlinMetaProperty>,
        val constructors: List<KotlinMetaFunction>,
        val innerClasses: Map<String, KotlinMetaClass>,
        val functions: List<KotlinMetaFunction>,
        val visibility: DescriptorVisibility,
        val modality: Modality,
        val parent: KotlinMetaClass? = null,
        val interfaces: List<KotlinMetaClass> = emptyList(),
        val isObject: Boolean = false,
        val isInterface: Boolean = false,
)

data class KotlinMetaProperty(
        val name: String,
        val type: KotlinMetaType,
        val visibility: DescriptorVisibility,
        val getter: KotlinMetaPropertyFunction?,
        val setter: KotlinMetaPropertyFunction?,
)

data class KotlinMetaPropertyFunction(
        val kind: KotlinMetaPropertyFunctionKind,
        val visibility: DescriptorVisibility,
)

data class KotlinMetaParameter(
        val name: String,
        val type: KotlinMetaType,
        val visibility: DescriptorVisibility,
        val varargs: Boolean,
)

data class KotlinMetaFunction(
        val name: String,
        val returnType: KotlinMetaType?,
        val throws: Set<KotlinMetaType>,
        val parameters: Map<String, KotlinMetaParameter>,
        val visibility: DescriptorVisibility,
        val modality: Modality,
) {
    fun withoutModifiers(): KotlinMetaFunctionWithoutModifiers = KotlinMetaFunctionWithoutModifiers(
            name,
            returnType,
            throws,
            parameters
    )
}

data class KotlinMetaFunctionWithoutModifiers(
        val name: String,
        val returnType: KotlinMetaType?,
        val throws: Set<KotlinMetaType>,
        val parameters: Map<String, KotlinMetaParameter>,
)
