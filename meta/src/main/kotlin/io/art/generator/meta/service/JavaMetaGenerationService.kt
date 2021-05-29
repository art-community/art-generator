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
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.asTree
import io.art.generator.meta.templates.COMPUTE_STATEMENT
import io.art.generator.meta.templates.META_CLASS_NAME
import io.art.generator.meta.templates.NEW_STATEMENT
import io.art.generator.meta.templates.RETURN_STATEMENT
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile()
        val moduleName = configuration.moduleName
        classBuilder(META_CLASS_NAME(moduleName))
                .addModifiers(PUBLIC)
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(META_CLASS_NAME(moduleName), META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(NEW_STATEMENT, META_CLASS_NAME(moduleName), META_NAME)
                        .build()
                )
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(META_CLASS_NAME(moduleName))
                        .addCode(RETURN_STATEMENT, META_NAME)
                        .build()
                )
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(COMPUTE_STATEMENT)
                        .build()
                )
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                }
    }

    private fun TypeSpec.Builder.generatePackageTree(classes: Sequence<JavaMetaClass>) {
        val moduleName = configuration.moduleName
        val tree = classes
                .filter { metaClass -> META_CLASS_NAME(metaClass.type.className!!) != META_CLASS_NAME(moduleName) }
                .asTree()
        tree.values
                .asSequence()
                .map { rootPackage -> rootPackage.packageShortName }
                .forEach { name ->
                    addField(FieldSpec.builder(ClassName.get(META_NAME, name), name)
                            .addModifiers(PRIVATE, FINAL)
                            .initializer("register($NEW_STATEMENT)", ClassName.get(META_NAME, name))
                            .build()
                    )
                    addMethod(methodBuilder(name)
                            .addModifiers(PUBLIC)
                            .returns(ClassName.get(META_NAME, name))
                            .addCode(RETURN_STATEMENT, name)
                            .build()
                    )
                }
    }

    private fun JavaMetaClass.generateMeta() {

    }
}
