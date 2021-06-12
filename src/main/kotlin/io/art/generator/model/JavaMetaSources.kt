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

import com.sun.tools.javac.code.Type
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import org.jetbrains.kotlin.types.KotlinType
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

enum class JavaMetaTypeKind {
    CLASS_KIND,
    ENUM_KIND,
    WILDCARD_KIND,
    PRIMITIVE_KIND,
    VARIABLE_KIND,
    ARRAY_KIND,
    UNKNOWN_KIND
}

val JAVA_OBJECT_META_TYPE: JavaMetaType = JavaMetaType(
        javaOriginalType = Type.JCNoType(),
        typeName = Object::class.java.name,
        kind = JavaMetaTypeKind.CLASS_KIND,
        classFullName = Object::class.java.name,
        className = Object::class.java.simpleName,
        classPackageName = Object::class.java.packageName
)

val JAVA_UNIT_META_TYPE: JavaMetaType = JavaMetaType(
        javaOriginalType = Type.JCNoType(),
        typeName = Unit::class.java.name,
        kind = JavaMetaTypeKind.CLASS_KIND,
        classFullName = Unit::class.java.name,
        className = Unit::class.java.simpleName,
        classPackageName = Unit::class.java.packageName
)

data class JavaMetaType(
        val javaOriginalType: TypeMirror? = null,

        val kotlinOriginalType: KotlinType? = null,

        val typeName: String,
        val kind: JavaMetaTypeKind,

        val classFullName: String? = null,
        val className: String? = null,
        val classPackageName: String? = null,

        val arrayComponentType: JavaMetaType? = null,

        val wildcardExtendsBound: JavaMetaType? = null,
        val wildcardSuperBound: JavaMetaType? = null,

        val typeParameters: MutableList<JavaMetaType> = mutableListOf(),
        val typeVariableBounds: MutableList<JavaMetaType> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as JavaMetaType
        if (javaOriginalType != other.javaOriginalType) return false
        if (kotlinOriginalType != other.kotlinOriginalType) return false
        return true
    }

    override fun hashCode(): Int = javaOriginalType?.hashCode() ?: kotlinOriginalType.hashCode()
}

data class JavaMetaClass(
        val type: JavaMetaType,
        val fields: Map<String, JavaMetaField>,
        val constructors: List<JavaMetaMethod>,
        val innerClasses: Map<String, JavaMetaClass>,
        val methods: List<JavaMetaMethod>,
        val modifiers: Set<Modifier>,
        val parent: JavaMetaClass? = null,
        val interfaces: List<JavaMetaClass> = emptyList(),
)

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
        val parameters: Map<String, JavaMetaParameter>,
        val modifiers: Set<Modifier>,
        val typeParameters: List<JavaMetaType> = emptyList(),
)
