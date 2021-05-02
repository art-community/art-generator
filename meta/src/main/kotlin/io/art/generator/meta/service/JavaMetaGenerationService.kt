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
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.model.MetaJavaTypeKind.CLASS_KIND
import io.art.generator.meta.model.MetaJavaTypeKind.INTERFACE_KIND
import io.art.meta.MetaField
import io.art.meta.MetaMethod
import javax.lang.model.element.Modifier.*

fun generateMetaJavaSources(classes: Set<MetaJavaClass>) {
    val javaClasses = classes.filter { javaClass -> javaClass.type.kind == CLASS_KIND || javaClass.type.kind == INTERFACE_KIND }
    val root = generatorConfiguration.sourcesRoot.first().toFile()
    val moduleName = generatorConfiguration.moduleName
    JavaFile.builder("meta", classBuilder("Meta$moduleName")
            .addModifiers(PUBLIC)
            .apply {
                javaClasses
                        .filter { javaClass -> javaClass.type.classPackageName?.contains(DOT) == false }
                        .map { javaClass -> javaClass.type.classPackageName }
                        .toSet()
                        .forEach { basePackage ->
                            basePackage
                                    ?.let { baseNotEmptyPackage -> generateMetaPackage(emptySet(), baseNotEmptyPackage, javaClasses) }
                                    ?: generateMetaPackage(emptySet(), "", javaClasses)
                        }
            }
            .build())
            .addStaticImport(ClassName.get(MetaField::class.java), "metaField")
            .addStaticImport(ClassName.get(MetaMethod::class.java), "metaMethod")
            .skipJavaLangImports(true)
            .build()
            .writeTo(root)
}

private fun TypeSpec.Builder.generateMetaPackage(parentPackages: Set<String>, packageName: String, javaClasses: List<MetaJavaClass>) {
    val moduleName = generatorConfiguration.moduleName
    val currentPackageTree = if (packageName.isEmpty()) parentPackages else (parentPackages + packageName).filter { packageName -> packageName.isNotEmpty() }.toSet()
    val currentPackage = if (currentPackageTree.isEmpty()) packageName else currentPackageTree.joinToString { "." }
    println(currentPackage)
    val self = ClassName.get("meta", "Meta$moduleName", *currentPackageTree.toTypedArray())
    addType(classBuilder(packageName)
            .addModifiers(PUBLIC, STATIC)
            .addField(FieldSpec.builder(self, "self", PUBLIC, FINAL, STATIC).initializer("new \$T()", self).build())
            .apply {
                javaClasses
                        .filter { javaClass -> currentPackage == javaClass.type.classPackageName }
                        .forEach { javaClass -> addType(javaClass.generateMetaClass(currentPackageTree)) }
                javaClasses
                        .filter { javaClass -> javaClass.type.classPackageName?.contains(DOT) == true && javaClass.type.classPackageName.startsWith(currentPackage) }
                        .forEach { javaClass ->
                            javaClass.type.classPackageName!!.split(DOT).forEach { nestedPackage ->
                                println(nestedPackage)
                                generateMetaPackage(parentPackages + packageName, nestedPackage, javaClasses)
                            }
                        }
            }
            .build())
}

private fun MetaJavaClass.generateMetaClass(packageTree: Set<String>): TypeSpec? {
    val self = ClassName.get("meta", "Meta${generatorConfiguration.moduleName}", *(packageTree + type.className).toTypedArray())
    return classBuilder(type.className)
            .addModifiers(PUBLIC, STATIC)
            .addField(FieldSpec.builder(self, "self", PUBLIC, FINAL, STATIC)
                    .initializer("new \$T()", self)
                    .build())
            .apply {
                fields.forEach { field ->
                    val metaFieldType = ParameterizedTypeName.get(ClassName.get(MetaField::class.java), field.value.type.asPoetType())
                    addField(FieldSpec.builder(metaFieldType, field.key, PRIVATE, FINAL)
                            .initializer("\$T.metaField(\$S,\$T.class)", ClassName.get(MetaField::class.java), field.key, field.value.type.asPoetType())
                            .build())
                    addMethod(methodBuilder(field.key)
                            .returns(metaFieldType)
                            .addCode("return \$L;", field.key)
                            .build())
                }
                methods.forEach { method ->
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
}
