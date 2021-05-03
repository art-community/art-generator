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
    PackageTree().addAll(javaClasses).build().forEach(::addType)
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

            }
            .build()
            .let(::addType)
}


private class PackageTree {
    val rootPackages = mutableMapOf<String, PackageTreeNode>()

    fun addAll(childClasses: Collection<MetaJavaClass>): PackageTree {
        childClasses.forEach(this::addClass)
        return this
    }

    fun addClass(metaClass: MetaJavaClass): PackageTree {
        val rootPackageShortName = metaClass.type.classPackageName?.substringBefore(DOT)
                ?: throw IllegalArgumentException("Null package")

        if (!rootPackages.contains(rootPackageShortName)) {
            rootPackages[rootPackageShortName] = PackageTreeNode(rootPackageShortName)
        }
        rootPackages[rootPackageShortName]!!.addClass(metaClass);

        return this
    }

    fun build(): Set<TypeSpec> {
        val rootTypes = mutableSetOf<TypeSpec>()
        rootPackages.values.forEach { item ->
            rootTypes.add(item.build())
        }
        return rootTypes
    }

    private data class PackageTreeNode(val packageFullName: String) {
        val packageShortName = packageFullName.substringAfterLast(DOT)
        val classes = mutableSetOf<MetaJavaClass>()
        val childPackages = mutableMapOf<String, PackageTreeNode>()

        fun addClass(childClass: MetaJavaClass) {
            val childPackage = childClass.type.classPackageName;
            if (childPackage == packageFullName) {
                classes.add(childClass)
                return
            }
            if (childPackage == null || !childPackage.startsWith(packageFullName))
                throw IllegalArgumentException("Tree insertion into wrong package")

            val nextPackageShortName = childPackage.removePrefix(packageFullName + DOT).substringBefore(DOT)
            val nextPackageFullName = packageFullName + DOT + nextPackageShortName
            childPackages.putIfAbsent(nextPackageShortName, PackageTreeNode(nextPackageFullName))
            childPackages[nextPackageShortName]!!.addClass(childClass)
        }

        fun build(): TypeSpec {
            val moduleName = generatorConfiguration.moduleName
            val selfType = ClassName.get(META_PACKAGE, "Meta$moduleName", *packageFullName.split(DOT).toTypedArray())
            println(packageFullName)
            val newScope = classBuilder(packageShortName)
                    .addModifiers(PUBLIC, STATIC)
                    .addField(FieldSpec.builder(selfType, SELF_FIELD, PUBLIC, FINAL, STATIC)
                            .initializer("new \$T()", selfType)
                            .build())
                    .apply {
                        classes.forEach { javaClass ->
                            val classSelfType = ClassName.get(META_PACKAGE, "Meta$moduleName", *(packageFullName.split(DOT) + javaClass.type.className!!).toTypedArray())
                            addType(classBuilder(javaClass.type.className)
                                    .addModifiers(PUBLIC, STATIC)
                                    .addField(FieldSpec.builder(classSelfType, SELF_FIELD, PUBLIC, FINAL, STATIC)
                                            .initializer("new \$T()", classSelfType)
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
                            )
                        }
                    }
            childPackages.values.forEach { child -> newScope.addType(child.build()) }
            return newScope.build()
        }
    }
}


