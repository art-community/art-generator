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

package io.art.generator.service

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.constants.*
import io.art.generator.extension.couldBeGenerated
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaNode
import io.art.generator.model.asTree
import io.art.generator.producer.generateClass
import io.art.generator.templates.*
import java.nio.file.Path
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMetaClasses(root: Path, classes: Sequence<JavaMetaClass>, metaClassName: String) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE(classes.javaClassNames()))
        root.toFile().parentFile.mkdirs()
        val metaModuleClassName = ClassName.get(META_NAME, metaClassName)
        val reference = ClassName.get(EMPTY_STRING, metaClassName)
        classBuilder(metaModuleClassName)
                .addModifiers(PUBLIC)
                .addAnnotation(javaSuppressAnnotation())
                .superclass(JAVA_META_MODULE_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addParameter(ParameterSpec.builder(ArrayTypeName.of(JAVA_META_MODULE_CLASS_NAME), DEPENDENCIES_NAME).build())
                        .varargs()
                        .addCode(javaSuperStatement(DEPENDENCIES_NAME))
                        .build())
                .addField(FieldSpec.builder(reference, META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(javaNewStatement(metaModuleClassName))
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(reference)
                        .addCode(javaReturnStatement(META_NAME))
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .addStaticImport(JAVA_META_TYPE_CLASS_NAME, *META_METHODS.toTypedArray())
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                    JAVA_LOGGER.info(GENERATED_MESSAGE("$META_NAME.$metaClassName"))
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
        val packageClassName = javaMetaPackageClassName(packageName)
        FieldSpec.builder(packageClassName, metaPackageName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(javaRegisterNewStatement(packageClassName))
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
        node.classes.filter(JavaMetaClass::couldBeGenerated).forEach(packageBuilder::generateClass)
        node.children.values.forEach { child ->
            packageBuilder.generateTree(child.packageShortName, child)
        }
        addType(packageBuilder.build())
    }
}
