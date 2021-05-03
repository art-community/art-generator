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
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.META_PACKAGE
import io.art.generator.meta.constants.SELF_FIELD
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.model.MetaJavaTypeKind.CLASS_KIND
import io.art.generator.meta.model.MetaJavaTypeKind.INTERFACE_KIND
import io.art.generator.meta.templates.META_FIELD_METHOD
import io.art.generator.meta.templates.META_METHOD_METHOD
import io.art.meta.MetaField
import io.art.meta.MetaMethod
import javax.lang.model.element.Modifier.*


fun generateMetaJavaSources(classes: Set<MetaJavaClass>) {
    val javaClasses = classes.filter { javaClass -> javaClass.type.kind == CLASS_KIND || javaClass.type.kind == INTERFACE_KIND }
    val root = generatorConfiguration.sourcesRoot.toFile()
    val moduleName = generatorConfiguration.moduleName
    JavaFile.builder(META_PACKAGE, classBuilder("Meta$moduleName")
            .addModifiers(PUBLIC)
            .generateMetaClasses(javaClasses)
            .build())
            .addStaticImport(ClassName.get(MetaField::class.java), META_FIELD_METHOD)
            .addStaticImport(ClassName.get(MetaMethod::class.java), META_METHOD_METHOD)
            .skipJavaLangImports(true)
            .build()
            .writeTo(root)
}

private fun TypeSpec.Builder.generateMetaClasses(javaClasses: List<MetaJavaClass>) = apply {
    val tree = mutableMapOf<String, TypeSpec>()
    javaClasses
            .groupBy { javaClass -> javaClass.type.classPackageName ?: EMPTY_STRING }
            .asSequence()
            .forEach { packagedClass -> generatePackageTree(packagedClass.key, tree) }
}

private fun TypeSpec.Builder.generatePackageTree(classesPackage: String, tree: MutableMap<String, TypeSpec>) {
    val moduleName = generatorConfiguration.moduleName
    val packageParts = classesPackage.split(DOT)
    var currentPackage: String = EMPTY_STRING
    val passedParts = mutableListOf<String>()
    packageParts
            .filter { packagePart -> packagePart.isNotEmpty() }
            .forEach { packagePart ->
                val previousPackage = currentPackage
                currentPackage += if (currentPackage.isEmpty()) packagePart else DOT + packagePart
                passedParts += packagePart
                if (tree.containsKey(currentPackage)) {
                    return@forEach
                }
                val selfType = ClassName.get(META_PACKAGE, "Meta$moduleName", *passedParts.toTypedArray())
                println(passedParts)
                val newScope = classBuilder(packagePart)
                        .addModifiers(PUBLIC, STATIC)
                        .addField(FieldSpec.builder(selfType, SELF_FIELD, PUBLIC, FINAL, STATIC)
                                .initializer("new \$T()", selfType)
                                .build())
                        .build()
                if (tree.containsKey(previousPackage)) {
                    tree[previousPackage] = tree[previousPackage]!!.toBuilder().addType(newScope).build()
                    tree[currentPackage] = newScope
                    return@forEach
                }
                tree[previousPackage] = addType(newScope).build()
                tree[currentPackage] = newScope
            }
}

private fun TypeSpec.Builder.generatePackageTree(packageTree: List<String>, javaClass: MetaJavaClass) {
    val moduleName = generatorConfiguration.moduleName
    val self = ClassName.get(META_PACKAGE, "Meta$moduleName", *packageTree.toTypedArray())
    classBuilder(javaClass.type.className)
            .addModifiers(PUBLIC, STATIC)
            .addField(FieldSpec.builder(self, SELF_FIELD, PUBLIC, FINAL, STATIC)
                    .initializer("new \$T()", self)
                    .build())
            .apply {
                javaClass.fields.forEach { field ->
                    val metaFieldType = ParameterizedTypeName.get(ClassName.get(MetaField::class.java), field.value.type.asPoetType())
                    addField(FieldSpec.builder(metaFieldType, field.key, PRIVATE, FINAL)
                            .initializer("\$T.metaField(\$S,\$T.class)", ClassName.get(MetaField::class.java), field.key, field.value.type.asPoetType())
                            .build())
                    addMethod(methodBuilder(field.key)
                            .returns(metaFieldType)
                            .addCode("return \$L;", field.key)
                            .build())
                }
                javaClass.methods.forEach { method ->
                    val metaMethodType = ParameterizedTypeName.get(ClassName.get(MetaMethod::class.java), method.returnType.asPoetType())
                    addField(FieldSpec.builder(metaMethodType, method.name, PRIVATE, FINAL)
                            .initializer("\$T.metaMethod(\$S,\$T.class)", ClassName.get(MetaMethod::class.java), method.name, method.returnType.asPoetType())
                            .build())
                    addMethod(methodBuilder(method.name)
                            .returns(metaMethodType)
                            .addCode("return \$L;", method.name)
                            .build())
                }
            }
            .build()
            .let(::addType)
}
