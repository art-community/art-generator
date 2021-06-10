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

package io.art.generator.service.kotlin

import com.squareup.javapoet.FieldSpec
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.VARARG
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.configuration.configuration
import io.art.generator.constants.*
import io.art.generator.extension.couldBeGenerated
import io.art.generator.model.*
import io.art.generator.producer.generateClass
import io.art.generator.templates.*
import java.nio.file.Path
import javax.lang.model.element.Modifier.*

object KotlinMetaGenerationService {
    fun generateKotlinMetaClasses(root: Path, classes: Sequence<KotlinMetaClass>) {
        KOTLIN_LOGGER.info(GENERATING_METAS_MESSAGE(classes.kotlinClassNames()))
        root.toFile().parentFile.mkdirs()
        val moduleName = configuration.moduleName
        val metaModuleClassName = kotlinMetaModuleClassName(META_NAME, moduleName)
        val reference = kotlinMetaModuleClassName(EMPTY_STRING, moduleName)
        classBuilder(metaModuleClassName)
                .addModifiers(KModifier.PUBLIC)
                .addAnnotation(kotlinSuppressAnnotation())
                .superclass(KOTLIN_META_MODULE_CLASS_NAME)
                .addFunction(FunSpec.constructorBuilder()
                        .addModifiers(KModifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(DEPENDENCIES_NAME, KOTLIN_META_MODULE_ARRAY_CLASS_NAME, VARARG).build())
                        .addCode(kotlinSuperStatement(DEPENDENCIES_NAME))
                        .build())
                .addProperty(PropertySpec.builder(META_NAME, reference)
                        .addModifiers(KModifier.PRIVATE, KModifier.FINAL)
                        .initializer(kotlinNewStatement(metaModuleClassName))
                        .build())
                .addFunction(FunSpec.builder(META_NAME)
                        .addModifiers(KModifier.PUBLIC)
                        .returns(reference)
                        .addCode(kotlinReturnStatement(META_NAME))
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    FileSpec.builder(META_NAME, metaClass.name!!)
                            .addType(metaClass)
                            .addImport(KOTLIN_META_TYPE_CLASS_NAME, *META_METHODS.toTypedArray())
                            .addImport(KOTLIN_CASTER_CLASS_NAME, CAST_NAME)
                            .addImport(KOTLIN_SET_FACTORY_CLASS_NAME, SET_OF_NAME)
                            .build()
                            .writeTo(root)
                    JAVA_LOGGER.info(GENERATED_MESSAGE(metaModuleClassFullName(moduleName)))
                }
    }

    private fun TypeSpec.Builder.generateTree(classes: Sequence<KotlinMetaClass>) {
        classes.asTree()
                .values
                .asSequence()
                .forEach { node -> generateTree(node.packageShortName, node) }
    }

    private fun TypeSpec.Builder.generateTree(packageName: String, node: KotlinMetaNode) {
        if (packageName.isEmpty()) {
            return
        }
        val metaPackageName = metaPackageName(packageName)
        val packageClassName = kotlinMetaPackageClassName(packageName)
        PropertySpec.builder(metaPackageName, packageClassName)
                .addModifiers(KModifier.PRIVATE, KModifier.FINAL)
                .initializer(kotlinRegisterNewStatement(packageClassName))
                .build()
                .apply(::addProperty)
        FunSpec.builder(metaPackageName)
                .addModifiers(KModifier.PUBLIC)
                .returns(packageClassName)
                .addCode(kotlinReturnStatement(metaPackageName))
                .build()
                .let(::addFunction)
        val packageBuilder = classBuilder(packageClassName)
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL)
                .superclass(KOTLIN_META_PACKAGE_CLASS_NAME)
                .addFunction(FunSpec.constructorBuilder()
                        .addModifiers(KModifier.PRIVATE)
                        .addCode(kotlinNamedSuperStatement(packageName))
                        .build())
        node.classes.filter(KotlinMetaClass::couldBeGenerated).forEach(packageBuilder::generateClass)
        node.children.values.forEach { child ->
            packageBuilder.generateTree(child.packageShortName, child)
        }
        addType(packageBuilder.build())
    }
}
