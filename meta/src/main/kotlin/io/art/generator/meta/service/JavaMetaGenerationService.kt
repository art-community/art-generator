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
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.META_PACKAGE
import io.art.generator.meta.constants.SELF_FIELD
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.model.MetaJavaField
import io.art.generator.meta.model.MetaJavaMethod
import io.art.generator.meta.model.MetaJavaTypeKind.*
import io.art.generator.meta.templates.META_FIELD_METHOD
import io.art.generator.meta.templates.META_METHOD_METHOD
import io.art.meta.MetaField
import io.art.meta.MetaMethod
import javax.lang.model.element.Modifier.*

fun generateMetaJavaSources(classes: Set<MetaJavaClass>) {
    val javaClasses = classes.filter { javaClass ->
        javaClass.type.classPackageName != null && (javaClass.type.kind == CLASS_KIND || javaClass.type.kind == INTERFACE_KIND)
    }
    val root = generatorConfiguration.sourcesRoot.toFile()
    val moduleName = generatorConfiguration.moduleName
    classBuilder("Meta$moduleName")
            .addModifiers(PUBLIC)
            .addField(selfField())
            .apply { PackagesTree(javaClasses).generate().forEach(::addType) }
            .addMethod(selfMethod("meta$moduleName"))
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

private fun String.packages() = split(DOT)

private fun selfField(classes: List<String> = emptyList()): FieldSpec {
    val moduleName = generatorConfiguration.moduleName
    val classSelfType = ClassName.get(META_PACKAGE, "Meta$moduleName", *classes.toTypedArray())
    return FieldSpec.builder(classSelfType, SELF_FIELD, PUBLIC, FINAL, STATIC)
            .initializer("new \$T()", classSelfType)
            .build()
}

private fun selfMethod(name: String, classes: List<String> = emptyList()): MethodSpec? {
    val moduleName = generatorConfiguration.moduleName
    val classSelfType = ClassName.get(META_PACKAGE, "Meta$moduleName", *classes.toTypedArray())
    return methodBuilder(name)
            .addModifiers(PUBLIC, STATIC)
            .returns(classSelfType)
            .addCode("return \$L;", "self")
            .build()
}


private class PackagesTree(classes: List<MetaJavaClass>) {
    private val rootPackages = mutableMapOf<String, Node>()

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

    private data class Node(val packageFullName: String) {
        private val packageShortName = packageFullName.substringAfterLast(DOT)
        private val classes = mutableSetOf<MetaJavaClass>()
        private val children = mutableMapOf<String, Node>()

        fun add(childClass: MetaJavaClass) {
            val childPackage = childClass.type.classPackageName ?: return
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
                .addModifiers(PUBLIC, STATIC)
                .addField(selfField(packageFullName.packages()))
                .apply { classes.forEach { javaClass -> generate(javaClass) } }
                .apply { children.values.forEach { child -> addType(child.generate()) } }
                .addMethod(selfMethod(packageShortName, packageFullName.packages()))
                .build()

        private fun TypeSpec.Builder.generate(javaClass: MetaJavaClass) {
            addType(classBuilder(javaClass.type.className)
                    .addModifiers(PUBLIC, STATIC)
                    .addField(selfField(packageFullName.packages() + javaClass.type.className!!))
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
                                .filter { method -> javaClass.fields.none { field -> method.name == "get${field.key.capitalize()}" } }
                                .filter { method -> javaClass.fields.none { field -> method.name == "set${field.key.capitalize()}" } }
                                .filter { method -> javaClass.fields.none { field -> method.name == "is${field.key.capitalize()}" } }
                                .filter { method -> method.returnType.classTypeParameters.isEmpty() }
                                .filter { method -> method.name !in setOf("builder", "toString") }
                                .forEach { method -> addMetaMethod(method) }
                        addMethod(selfMethod(javaClass.type.className, packageFullName.packages() + javaClass.type.className))
                    }
                    .build()
            )
        }

        private fun TypeSpec.Builder.addMetaField(field: MetaJavaField) {
            val metaFieldType = ParameterizedTypeName.get(ClassName.get(MetaField::class.java), field.type.asPoetType())
            addField(FieldSpec.builder(metaFieldType, field.name, PRIVATE, FINAL)
                    .initializer("\$T.metaField(\$S,\$T.class)", ClassName.get(MetaField::class.java), field.name, field.type.asPoetType())
                    .build())
            addMethod(methodBuilder(field.name)
                    .addModifiers(PUBLIC)
                    .returns(metaFieldType)
                    .addCode("return \$L;", field.name)
                    .build())
        }

        private fun TypeSpec.Builder.addMetaMethod(method: MetaJavaMethod) {
            val metaMethodType = ParameterizedTypeName.get(ClassName.get(MetaMethod::class.java), method.returnType.asPoetType())
            addField(FieldSpec.builder(metaMethodType, method.name, PRIVATE, FINAL)
                    .initializer("\$T.metaMethod(\$S,\$T.class)", ClassName.get(MetaMethod::class.java), method.name, method.returnType.asPoetType())
                    .build())
            addMethod(methodBuilder(method.name)
                    .addModifiers(PUBLIC)
                    .returns(metaMethodType)
                    .addCode("return \$L;", method.name)
                    .build())
        }
    }
}
