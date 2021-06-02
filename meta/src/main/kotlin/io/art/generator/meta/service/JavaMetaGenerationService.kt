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

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeSpec.classBuilder
import com.squareup.kotlinpoet.TypeName
import io.art.core.caster.Caster
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaNode
import io.art.generator.meta.model.asTree
import io.art.generator.meta.producer.generateClass
import io.art.generator.meta.templates.*
import io.art.meta.model.MetaType
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile().apply { parentFile.mkdirs() }
        val moduleName = configuration.moduleName
        val metaModuleName = metaName(META_NAME, moduleName)
        val reference = metaName(EMPTY_STRING, moduleName)
        TypeName
        classBuilder(metaModuleName)
                .addModifiers(PUBLIC)
                .addAnnotation(suppressAnnotation())
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(reference, META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(newStatement(), metaModuleName)
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(reference)
                        .addCode(returnStatement(), META_NAME)
                        .build())
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(computeStatement())
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .addStaticImport(MetaType::class.java, META_TYPE_NAME, META_ARRAY_NAME, META_VARIABLE_NAME)
                            .addStaticImport(Caster::class.java, CAST_NAME)
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                    JAVA_LOGGER.info(GENERATED_MESSAGE(metaClass.name))
                }
    }

    private fun TypeSpec.Builder.generateTree(classes: Sequence<JavaMetaClass>) {
        classes.asTree()
                .values
                .asSequence()
                .forEach { node -> generateTree(node) }
    }

    private fun TypeSpec.Builder.generateTree(node: JavaMetaNode) {
        val metaPackageName = metaPackageName(META_NAME, node.packageShortName)
        val reference = metaPackageName(EMPTY_STRING, node.packageShortName)
        FieldSpec.builder(reference, node.packageShortName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerNewStatement(), reference)
                .build()
                .apply(::addField)
        methodBuilder(node.packageShortName)
                .addModifiers(PUBLIC)
                .returns(reference)
                .addCode(returnStatement(), node.packageShortName)
                .build()
                .let(::addMethod)
        val packageBuilder = classBuilder(metaPackageName)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(META_PACKAGE_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(namedSuperStatement(node.packageShortName))
                        .build())
        node.classes.forEach { metaClass -> packageBuilder.generateClass(metaClass) }
        node.children.values.forEach { child -> packageBuilder.generateTree(child) }
        addType(packageBuilder.build())
    }
}
