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

package io.art.generator.meta.service

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.exception.NotImplementedException
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.templates.STUB_METHOD_STRING
import io.art.generator.meta.templates.THROW_EXCEPTION_STATEMENT

fun generateJavaStubs(classes: Set<MetaJavaClass>) {
    val root = generatorConfiguration.stubRoot
    classes.forEach { metaJavaClass -> metaJavaClass.asPoetFile().writeTo(root) }
}

fun MetaJavaClass.asPoetFile(): JavaFile = JavaFile.builder(type.packageName, asPoetClass()).build()

private fun JavaMetaType.asPoetType() = ClassName.get(element)

private fun MetaJavaClass.asPoetClass(): TypeSpec = classBuilder(type.className)
        .apply {
            fields.forEach { field ->
                addField(field.value.type.asPoetType(), field.key, *field.value.symbol.modifiers.toTypedArray())
            }
            methods.forEach { method ->
                addMethod(methodBuilder(method.name)
                        .returns(method.returnType.asPoetType())
                        .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
                        .apply { addCode(THROW_EXCEPTION_STATEMENT, NotImplementedException::class.java, STUB_METHOD_STRING) }
                        .build())
            }
            constructors.forEach { constructor ->
                addMethod(constructorBuilder()
                        .addParameters(constructor.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
                        .build())
            }
            innerClasses.forEach { inner ->
                addType(inner.value.asPoetClass())
            }
        }
        .build()
