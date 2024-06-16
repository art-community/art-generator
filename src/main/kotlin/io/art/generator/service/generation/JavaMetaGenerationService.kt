/*
 * ART
 *
 * Copyright 2019-2022 ART
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

package io.art.generator.service.generation

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.*
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.metaPackage
import io.art.generator.extension.metaPath
import io.art.generator.factory.NameFactory
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaNode
import io.art.generator.model.asTree
import io.art.generator.producer.generateClass
import io.art.generator.service.common.metaModuleJavaFile
import io.art.generator.templates.*
import javax.lang.model.element.Modifier.*

fun generateJavaMetaClasses(configuration: SourceConfiguration, classes: Sequence<JavaMetaClass>, metaClassName: String) = JavaMetaGenerationService()
        .generateJavaMetaClasses(configuration, classes, metaClassName)

private class JavaMetaGenerationService(private val nameFactory: NameFactory = NameFactory()) {
    fun generateJavaMetaClasses(configuration: SourceConfiguration, classes: Sequence<JavaMetaClass>, metaClassName: String) {
        val root = configuration.root
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE(classes.javaClassNames()))
        root.toFile().parentFile.mkdirs()
        val metaModuleClassName = ClassName.get(configuration.metaPackage, metaClassName)
        classBuilder(metaModuleClassName)
                .addModifiers(PUBLIC)
                .addAnnotation(javaSuppressAnnotation())
                .superclass(JAVA_META_LIBRARY_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(ParameterSpec.builder(ArrayTypeName.of(JAVA_META_LIBRARY_CLASS_NAME), DEPENDENCIES_NAME).build())
                        .varargs()
                        .addCode(javaSuperStatement(DEPENDENCIES_NAME))
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(configuration.metaPackage, metaClass)
                            .addStaticImport(JAVA_META_TYPE_CLASS_NAME, *META_METHODS.toTypedArray())
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                    JAVA_LOGGER.info(GENERATED_MESSAGE(metaModuleJavaFile(root, configuration.metaPath, metaClassName).absolutePath))
                }
    }

    private fun TypeSpec.Builder.generateTree(classes: Sequence<JavaMetaClass>) {
        classes.asTree()
                .values
                .asSequence()
                .forEach { node -> generateTree(node.packageShortName, node) }
    }

    private fun TypeSpec.Builder.generateTree(packageName: String, node: JavaMetaNode) {
        if (packageName.isEmpty()) {
            return
        }
        val metaPackageName = metaPackageName(packageName)
        val packageClassName = javaMetaPackageClassName(packageName, nameFactory)
        FieldSpec.builder(packageClassName, metaPackageName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(javaRegisterPackage(packageClassName))
                .build()
                .apply(::addField)
        methodBuilder(metaPackageName)
                .addModifiers(PUBLIC)
                .returns(packageClassName)
                .addCode(javaReturnStatement(metaPackageName))
                .build()
                .let(::addMethod)
        val packageBuilder = classBuilder(packageClassName)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(JAVA_META_PACKAGE_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(javaNamedSuperStatement(packageName))
                        .build())
        node.classes
                .filter(JavaMetaClass::couldBeGenerated)
                .forEach { metaClass -> packageBuilder.generateClass(metaClass, nameFactory) }
        node.children.values.forEach { child ->
            packageBuilder.generateTree(child.packageShortName, child)
        }
        addType(packageBuilder.build())
    }
}
