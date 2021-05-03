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

package io.art.generator.meta.model

import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

enum class MetaJavaTypeKind {
    CLASS_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    INTERFACE_KIND,
    JDK_KIND,
    PRIMITIVE_KIND,
    VARIABLE_KIND,
    ARRAY_KIND,
    UNKNOWN_KIND
}

data class MetaJavaType(
        val originalType: TypeMirror? = null,
        val reflectionType: Type? = null,

        val typeName: String,
        val kind: MetaJavaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,
        val classTypeParameters: Map<String, MetaJavaType> = emptyMap(),
        val classSuperClass: MetaJavaType? = null,
        val classSuperInterfaces: List<MetaJavaType> = emptyList(),

        val arrayComponentType: MetaJavaType? = null,

        val wildcardExtendsBound: MetaJavaType? = null,
        val wildcardSuperBound: MetaJavaType? = null,

        val typeVariableBounds: Map<String, MetaJavaType> = emptyMap(),
)

data class MetaJavaClass(
        val type: MetaJavaType,
        val fields: Map<String, MetaJavaField>,
        val constructors: List<MetaJavaMethod>,
        val innerClasses: Map<String, MetaJavaClass>,
        val methods: List<MetaJavaMethod>,
        val modifiers: Set<Modifier>
)

data class MetaJavaField(
        val name: String,
        val type: MetaJavaType,
        val modifiers: Set<Modifier>
)

data class MetaJavaParameter(
        val name: String,
        val type: MetaJavaType,
        val modifiers: Set<Modifier>
)

data class MetaJavaMethod(
        val name: String,
        val returnType: MetaJavaType,
        val parameters: Map<String, MetaJavaParameter>,
        val modifiers: Set<Modifier>,
        val typeParameters: Map<String, MetaJavaType> = emptyMap(),
        val exceptions: List<MetaJavaType> = emptyList(),
)