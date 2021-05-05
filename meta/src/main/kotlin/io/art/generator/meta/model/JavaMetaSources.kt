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
import java.nio.file.Path
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

enum class JavaMetaTypeKind {
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

data class JavaMetaType(
        val originalType: TypeMirror? = null,
        val reflectionType: Type? = null,

        val typeName: String,
        val kind: JavaMetaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,
        val classTypeParameters: Map<String, JavaMetaType> = emptyMap(),
        val classSuperClass: JavaMetaType? = null,
        val classSuperInterfaces: List<JavaMetaType> = emptyList(),

        val arrayComponentType: JavaMetaType? = null,

        val wildcardExtendsBound: JavaMetaType? = null,
        val wildcardSuperBound: JavaMetaType? = null,

        val typeVariableBounds: Map<String, JavaMetaType> = emptyMap(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JavaMetaType

        if (typeName != other.typeName) return false
        if (kind != other.kind) return false
        if (classFullName != other.classFullName) return false
        if (className != other.className) return false
        if (classPackageName != other.classPackageName) return false
        if (classTypeParameters != other.classTypeParameters) return false
        if (classSuperClass != other.classSuperClass) return false
        if (classSuperInterfaces != other.classSuperInterfaces) return false
        if (arrayComponentType != other.arrayComponentType) return false
        if (wildcardExtendsBound != other.wildcardExtendsBound) return false
        if (wildcardSuperBound != other.wildcardSuperBound) return false
        if (typeVariableBounds != other.typeVariableBounds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = typeName.hashCode()
        result = 31 * result + kind.hashCode()
        result = 31 * result + (classFullName?.hashCode() ?: 0)
        result = 31 * result + (className?.hashCode() ?: 0)
        result = 31 * result + (classPackageName?.hashCode() ?: 0)
        result = 31 * result + classTypeParameters.hashCode()
        result = 31 * result + (classSuperClass?.hashCode() ?: 0)
        result = 31 * result + classSuperInterfaces.hashCode()
        result = 31 * result + (arrayComponentType?.hashCode() ?: 0)
        result = 31 * result + (wildcardExtendsBound?.hashCode() ?: 0)
        result = 31 * result + (wildcardSuperBound?.hashCode() ?: 0)
        result = 31 * result + typeVariableBounds.hashCode()
        return result
    }
}

data class JavaMetaClass(
        val type: JavaMetaType,
        val source: Path,
        val fields: Map<String, JavaMetaField>,
        val constructors: List<JavaMetaMethod>,
        val innerClasses: Map<String, JavaMetaClass>,
        val methods: List<JavaMetaMethod>,
        val modifiers: Set<Modifier>
)

data class JavaMetaField(
        val name: String,
        val type: JavaMetaType,
        val modifiers: Set<Modifier>
)

data class JavaMetaParameter(
        val name: String,
        val type: JavaMetaType,
        val modifiers: Set<Modifier>
)

data class JavaMetaMethod(
        val name: String,
        val returnType: JavaMetaType,
        val parameters: Map<String, JavaMetaParameter>,
        val modifiers: Set<Modifier>,
        val typeParameters: Map<String, JavaMetaType> = emptyMap(),
        val exceptions: List<JavaMetaType> = emptyList(),
)
