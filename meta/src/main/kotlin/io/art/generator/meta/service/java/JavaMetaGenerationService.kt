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

package io.art.generator.meta.service.java

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.caster.Caster
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.factory.SetFactory
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
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE(classes))
        val root = configuration.sourcesRoot.toFile().apply { parentFile.mkdirs() }
        val moduleName = configuration.moduleName
        val metaModuleClassName = metaModuleClassName(META_NAME, moduleName)
        val reference = metaModuleClassName(EMPTY_STRING, moduleName)
        classBuilder(metaModuleClassName)
                .addModifiers(PUBLIC)
                .addAnnotation(suppressAnnotation())
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(reference, META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(newStatement(), metaModuleClassName)
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(reference)
                        .addCode(returnStatement(), META_NAME)
                        .build())
                .addMethod(methodBuilder(LOAD_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .addParameter(ParameterSpec.builder(ArrayTypeName.of(META_MODULE_CLASS_NAME), DEPENDENCIES_NAME).build())
                        .varargs()
                        .addCode(computeStatement())
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .addStaticImport(MetaType::class.java, *META_METHODS.toTypedArray())
                            .addStaticImport(Caster::class.java, CAST_NAME)
                            .addStaticImport(SetFactory::class.java, SET_OF_NAME)
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                    JAVA_LOGGER.info(GENERATED_MESSAGE(metaModuleClassFullName(moduleName)))
                }
    }

    private fun TypeSpec.Builder.generateTree(classes: Sequence<JavaMetaClass>) {
        classes.asTree()
                .values
                .asSequence()
                .forEach { node -> generateTree(node.packageShortName, metaPackageClassName(node.packageShortName), node) }
    }

    private fun TypeSpec.Builder.generateTree(packageName: String, packageClassName: ClassName, node: JavaMetaNode) {
        if (packageName.isEmpty()) {
            return
        }
        val metaPackageName = metaPackageName(packageName)
        FieldSpec.builder(packageClassName, metaPackageName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerNewStatement(), packageClassName)
                .build()
                .apply(::addField)
        methodBuilder(metaPackageName)
                .addModifiers(PUBLIC)
                .returns(packageClassName)
                .addCode(returnStatement(), metaPackageName)
                .build()
                .let(::addMethod)
        val packageBuilder = classBuilder(packageClassName)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(META_PACKAGE_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(namedSuperStatement(packageName))
                        .build())
        node.classes.filter(JavaMetaClass::couldBeGenerated).forEach(packageBuilder::generateClass)
        node.children.values.forEach { child ->
            if (packageName == child.packageShortName) {
                packageBuilder.generateTree(packageName, nestedMetaPackageClassName(packageName), child)
                return@forEach
            }
            packageBuilder.generateTree(child.packageShortName, metaPackageClassName(child.packageShortName), child)
        }
        addType(packageBuilder.build())
    }
}
