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

package io.art.generator.meta.model

import com.squareup.javapoet.*
import com.squareup.javapoet.TypeSpec.classBuilder
import org.jetbrains.kotlin.load.java.structure.JavaClass

data class MetaType(val fullName: String, val packageName: String = fullName.substringBeforeLast("."), val className: String = fullName.substringAfterLast("."))

data class MetaJavaClass(
        val descriptor: JavaClass,
        val type: MetaType,
        val fields: Map<String, MetaJavaField>,
        val methods: Map<String, MetaJavaMethod>
)

data class MetaJavaField(
        val name: String,
        val type: MetaType
)

data class MetaJavaParameter(
        val name: String,
        val type: MetaType
)

data class MetaJavaMethod(
        val name: String,
        val returnType: MetaType,
        val parameters: Map<String, MetaJavaParameter>
)

fun MetaJavaClass.generateSource() = JavaFile.builder(type.packageName, classBuilder(type.fullName)
        .addFields(
                fields.map { field ->
                    FieldSpec
                            .builder(ClassName.get(field.value.type.packageName, field.value.type.className), field.key)
                            .build()
                }
        )
        .addMethod(
                MethodSpec.methodBuilder("toMap")
                        .returns(TypeName.VOID)
                        .addCode(CodeBlock.builder()
                                .build())
                        .build()
        )
        .build())
        .build()
        .toString()
