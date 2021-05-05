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
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.GENERATING_METAS_MESSAGE
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.META_PACKAGE
import io.art.generator.meta.extension.packages
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaField
import io.art.generator.meta.model.JavaMetaMethod
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.templates.*
import io.art.meta.MetaField
import io.art.meta.MetaMethod
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile()
        val moduleName = configuration.moduleName
        classBuilder(META_CLASS(moduleName))
                .addModifiers(PUBLIC)
                .addField(metaPackageField(META_PACKAGE_REFERENCE, static = true))
                .apply {
                    MetaPackagesTree(classes).apply {
                        rootPackages.values
                                .asSequence()
                                .map { rootPackage -> rootPackage.packageShortName }
                                .forEach { name ->
                                    addField(metaPackageField(name, innerNames = listOf(name)))
                                    addMethod(metaPackageMethod(name, innerNames = listOf(name)))
                                }
                        generate().forEach(::addType)
                    }
                }
                .addMethod(metaPackageMethod(META_PACKAGE_REFERENCE, static = true))
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_PACKAGE, metaClass)
                            .addStaticImport(ClassName.get(MetaField::class.java), META_FIELD_METHOD)
                            .addStaticImport(ClassName.get(MetaMethod::class.java), META_METHOD_METHOD)
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                }
    }

    private fun metaPackageField(name: String, static: Boolean = false, innerNames: List<String> = emptyList()): FieldSpec {
        val moduleName = configuration.moduleName
        val type = ClassName.get(META_PACKAGE, META_CLASS(moduleName), *innerNames.toTypedArray())
        return FieldSpec.builder(type, name, PRIVATE, FINAL).apply { if (static) addModifiers(STATIC) }
                .initializer(NEW_STATEMENT, type)
                .build()
    }

    private fun metaPackageMethod(name: String, static: Boolean = false, innerNames: List<String> = emptyList()): MethodSpec {
        val moduleName = configuration.moduleName
        val type = ClassName.get(META_PACKAGE, META_CLASS(moduleName), *innerNames.toTypedArray())
        return methodBuilder(name)
                .addModifiers(PUBLIC).apply { if (static) addModifiers(STATIC) }
                .returns(type)
                .addCode(RETURN_STATEMENT, name)
                .build()
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
                    .addModifiers(PUBLIC)
                    .apply {
                        classes.forEach { javaClass ->
                            val className = javaClass.type.className!!
                            val reference = javaClass.type.className.decapitalize()
                            addField(metaPackageField(reference, innerNames = packageFullName.packages + className))
                            addMethod(metaPackageMethod(reference, innerNames = packageFullName.packages + className))
                            generate(javaClass)
                        }
                        children.values.forEach { child ->
                            val name = child.packageShortName
                            addField(metaPackageField(name, innerNames = packageFullName.packages + name))
                            addMethod(metaPackageMethod(name, innerNames = packageFullName.packages + name))
                            addType(child.generate())
                        }
                    }
                    .build()

            private fun TypeSpec.Builder.generate(javaClass: JavaMetaClass) {
                addType(classBuilder(javaClass.type.className)
                        .addModifiers(PUBLIC)
                        .apply {
                            javaClass.fields
                                    .asSequence()
                                    .filter { field -> field.value.type.kind !in setOf(WILDCARD_KIND, UNKNOWN_KIND, VARIABLE_KIND) }
                                    .filter { field -> field.value.type.classTypeParameters.isEmpty() }
                                    .forEach { field -> addMetaField(field.value) }

                            javaClass.methods
                                    .asSequence()
                                    .filter { method -> method.returnType.kind !in setOf(WILDCARD_KIND, UNKNOWN_KIND, VARIABLE_KIND) }
                                    .filter { method -> method.parameters.values.none { parameter -> parameter.type.kind in setOf(WILDCARD_KIND, UNKNOWN_KIND, VARIABLE_KIND) } }
                                    .filter { method -> javaClass.fields.none { field -> method.name == GETTER(field.key) } }
                                    .filter { method -> javaClass.fields.none { field -> method.name == GETTER_BOOLEAN(field.key) } }
                                    .filter { method -> javaClass.fields.none { field -> method.name == SETTER(field.key) } }
                                    .filter { method -> method.returnType.classTypeParameters.isEmpty() }
                                    .filter { method ->
                                        method.name !in setOf(
                                                "builder",
                                                "toString",
                                                "equals",
                                                "canEqual",
                                                "hashCode",
                                                "clone"
                                        )
                                    }
                                    .forEach { method -> addMetaMethod(method) }
                        }
                        .build()
                )
            }

            private fun TypeSpec.Builder.addMetaField(field: JavaMetaField) {
                val ownerFieldType = field.type.asPoetType()
                val metaFieldType = ParameterizedTypeName.get(ClassName.get(MetaField::class.java), ownerFieldType)
                addField(FieldSpec.builder(metaFieldType, field.name, PRIVATE, FINAL)
                        .initializer(META_FIELD_INITIALIZER, ClassName.get(MetaField::class.java), field.name, ownerFieldType)
                        .build())
                addMethod(methodBuilder(field.name)
                        .addModifiers(PUBLIC)
                        .returns(metaFieldType)
                        .addCode(RETURN_STATEMENT, field.name)
                        .build())
            }

            private fun TypeSpec.Builder.addMetaMethod(method: JavaMetaMethod) {
                val ownerMethodType = method.returnType.asPoetType()
                val metaMethodType = ParameterizedTypeName.get(ClassName.get(MetaMethod::class.java), ownerMethodType)
                addField(FieldSpec.builder(metaMethodType, method.name, PRIVATE, FINAL)
                        .initializer(META_METHOD_INITIALIZER, ClassName.get(MetaMethod::class.java), method.name, ownerMethodType)
                        .build())
                addMethod(methodBuilder(method.name)
                        .addModifiers(PUBLIC)
                        .returns(metaMethodType)
                        .addCode(RETURN_STATEMENT, method.name)
                        .build())
            }
        }
    }
}
