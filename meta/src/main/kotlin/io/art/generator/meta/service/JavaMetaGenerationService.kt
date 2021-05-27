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

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.*
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaField
import io.art.generator.meta.model.JavaMetaMethod
import io.art.generator.meta.model.JavaMetaTypeKind.VARIABLE_KIND
import io.art.generator.meta.templates.*
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile()
        val moduleName = configuration.moduleName
        classBuilder(META_CLASS(moduleName))
                .addModifiers(PUBLIC)
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(ClassName.get(META_NAME, META_CLASS(moduleName)), META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(NEW_STATEMENT, ClassName.get(META_NAME, META_CLASS(moduleName)), META_NAME)
                        .build()
                )
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(ClassName.get(META_NAME, META_CLASS(moduleName)))
                        .addCode(RETURN_STATEMENT, META_NAME)
                        .build()
                )
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode("compute();")
                        .build()
                )
                .apply { generatePackageTree(classes) }
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
                .filter { metaClass -> META_CLASS(metaClass.type.className!!) != META_CLASS(moduleName) }
                .let(::MetaPackagesTree)
        tree.rootPackages.values
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
        tree.generate().forEach(::addType)
    }

    private fun TypeSpec.Builder.generateClass(javaClass: JavaMetaClass) {
        addType(classBuilder(META_CLASS(javaClass.type.className!!))
                .addModifiers(PUBLIC, STATIC)
                .apply {

                }
                .build()
        )
    }


    private fun TypeSpec.Builder.addMetaConstructor(method: JavaMetaMethod) {

    }

    private fun TypeSpec.Builder.addMetaField(owner: JavaMetaClass, field: JavaMetaField) {

    }


    private fun TypeSpec.Builder.addMetaMethod(method: JavaMetaMethod) {

    }


    private class MetaPackagesTree(classes: Sequence<JavaMetaClass>) {
        val rootPackages = mutableMapOf<String, Node>()

        init {
            classes.forEach { metaClass ->
                metaClass.type.classPackageName
                        ?.substringBefore(DOT)
                        ?.let { packageName ->
                            rootPackages[packageName]?.add(metaClass)?.let { return@forEach }
                            rootPackages[packageName] = Node(packageName).apply { add(metaClass) }
                        }
            }
        }

        fun generate(): List<TypeSpec> = rootPackages.values.map(Node::generate)

        data class Node(val packageFullName: String) {
            private val classes = mutableSetOf<JavaMetaClass>()
            private val children = mutableMapOf<String, Node>()
            val packageShortName = packageFullName.substringAfterLast(DOT)

            fun add(childClass: JavaMetaClass) {
                val childPackage = childClass.type.classPackageName!!
                if (childPackage == packageFullName) {
                    classes.add(childClass)
                    return
                }
                if (childPackage.isEmpty()) return
                val nextPackageShortName = childPackage.removePrefix(packageFullName + DOT).substringBefore(DOT)
                val nextPackageFullName = packageFullName + DOT + nextPackageShortName
                children[nextPackageShortName]?.add(childClass)?.let { return }
                children[nextPackageShortName] = Node(nextPackageFullName).apply { add(childClass) }
            }

            fun generate(): TypeSpec = classBuilder(packageShortName)
                    .superclass(META_PACKAGE_CLASS_NAME)
                    .addModifiers(PUBLIC, STATIC, FINAL)
                    .addMethod(constructorBuilder()
                            .addCode("super(\$L)", packageShortName)
                            .addModifiers(PRIVATE)
                            .build())
                    .apply {
                        classes.forEach { javaClass ->
                            val className = META_CLASS(javaClass.type.className!!)
                            addField(FieldSpec.builder(ClassName.get(javaClass.type.className.decapitalize(), className), className)
                                    .addModifiers(PRIVATE, FINAL, STATIC)
                                    .initializer(NEW_STATEMENT, ClassName.get(META_NAME, META_CLASS(moduleName)), META_NAME)
                                    .build()
                            )
                            addMethod(methodBuilder(META_NAME)
                                    .addModifiers(PUBLIC, STATIC)
                                    .returns(ClassName.get(META_NAME, META_CLASS(moduleName)))
                                    .addCode(RETURN_STATEMENT, META_NAME)
                                    .build()
                            )
                            generateClass(javaClass)
                        }
                        children.values.forEach { child ->
                            val name = child.packageShortName
                            addField()
                            addMethod()
                            addType(child.generate())
                        }
                    }
                    .build()
        }
    }
}
