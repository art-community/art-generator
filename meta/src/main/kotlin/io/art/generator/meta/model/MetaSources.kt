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

import com.sun.tools.javac.code.Symbol.*
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import javax.lang.model.element.TypeElement

data class JavaMetaType(
        val element: TypeElement,
        val fullName: String,
        val className: String,
        val packageName: String
)

data class MetaJavaClass(
        val symbol: ClassSymbol,
        val type: JavaMetaType,
        val fields: Map<String, MetaJavaField>,
        val constructors: Set<MetaJavaMethod>,
        val innerClasses: Map<String, MetaJavaClass>,
        val methods: Set<MetaJavaMethod>
)

data class MetaJavaField(
        val symbol: VarSymbol,
        val name: String,
        val type: JavaMetaType
)

data class MetaJavaParameter(
        val symbol: VarSymbol,
        val name: String,
        val type: JavaMetaType
)

data class MetaJavaMethod(
        val name: String,
        val symbol: MethodSymbol,
        val returnType: JavaMetaType,
        val parameters: Map<String, MetaJavaParameter>
)
