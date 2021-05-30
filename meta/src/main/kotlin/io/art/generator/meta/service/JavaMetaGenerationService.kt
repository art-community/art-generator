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
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.*
import io.art.generator.meta.templates.*
import io.art.meta.model.MetaType
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile().apply { parentFile.mkdirs() }
        val moduleName = configuration.moduleName
        val metaModuleName = metaName(META_NAME, moduleName)
        val metaModuleNameReference = metaName(EMPTY_STRING, moduleName)
        classBuilder(metaModuleName)
                .addModifiers(PUBLIC)
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(metaModuleNameReference, META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(NEW_STATEMENT, metaModuleName)
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(metaModuleNameReference)
                        .addCode(RETURN_STATEMENT, META_NAME)
                        .build())
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(COMPUTE_STATEMENT)
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .addStaticImport(MetaType::class.java, "metaType", "metaArray", "metaVariable")
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                }
    }

    private fun TypeSpec.Builder.generateTree(classes: Sequence<JavaMetaClass>) {
        classes.asTree()
                .values
                .asSequence()
                .forEach { node -> generateTree(node) }
    }

    private fun TypeSpec.Builder.generateTree(node: JavaMetaNode) {
        val metaPackageName = metaPackageName(META_NAME, node.packageShortName)
        val metaPackageNameReference = metaPackageName(EMPTY_STRING, node.packageShortName)
        FieldSpec.builder(metaPackageNameReference, node.packageShortName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(REGISTER_NEW_STATEMENT, metaPackageNameReference)
                .build()
                .let(::addField)
        methodBuilder(node.packageShortName)
                .addModifiers(PUBLIC)
                .returns(metaPackageNameReference)
                .addCode(RETURN_STATEMENT, node.packageShortName)
                .build()
                .let(::addMethod)

        val packageBuilder = classBuilder(metaPackageName)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(META_PACKAGE_CLASS_NAME)
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(namedSuperStatement(node.packageShortName))
                        .build())
        node.classes.forEach { metaClass -> packageBuilder.generateClass(metaClass) }
        node.children.values.forEach { child -> packageBuilder.generateTree(child) }
        addType(packageBuilder.build())
    }

    private fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass) {
        val className = ClassName.get(metaClass.type.classPackageName, metaClass.type.className)
        val type = metaClass.type.asGenericPoetType()
        val constructorStatement = metaTypeSuperStatement(metaClass, className)
        classBuilder(metaClassName(META_NAME, metaClass.type.className!!))
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(ParameterizedTypeName.get(META_CLASS_CLASS_NAME, type))
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(constructorStatement)
                        .build())
                .apply { generateConstructors(metaClass.constructors, type) }
                .apply { generateFields(metaClass.fields) }
                .apply { generateMethods(metaClass.methods, type) }
                .build()
                .apply(::addType)
        metaClass.innerClasses.values.forEach { inner -> generateClass(inner) }
    }

    private fun TypeSpec.Builder.generateConstructors(constructors: List<JavaMetaMethod>, type: TypeName) {
        constructors.mapIndexed { index, constructor ->
            var name = "constructor"
            if (index > 0) name += index
            val constructorClassBuilder = classBuilder(name)
                    .addModifiers(PUBLIC, FINAL, STATIC)
                    .superclass(ParameterizedTypeName.get(META_CONSTRUCTOR_CLASS_NAME, type))
                    .addMethod(constructorBuilder()
                            .addModifiers(PRIVATE)
                            .addCode(metaSuperStatement(type))
                            .build())
            constructor.parameters.entries.forEachIndexed { parameterIndex, parameter ->
                val parameterType = parameter.value.type.asGenericPoetType()
                val metaParameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameterType)
                FieldSpec.builder(metaParameterType, parameter.key)
                        .addModifiers(PRIVATE, FINAL)
                        .initializer(registerMetaParameterStatement(parameterIndex, parameter.key, parameterType))
                        .build()
                        .apply(constructorClassBuilder::addField)
                methodBuilder(parameter.key)
                        .addModifiers(PUBLIC)
                        .returns(metaParameterType)
                        .addCode(RETURN_STATEMENT, parameter.key)
                        .build()
                        .let(constructorClassBuilder::addMethod)

                addType(constructorClassBuilder.build())
            }
        }
    }

    private fun TypeSpec.Builder.generateMethods(methods: List<JavaMetaMethod>, type: TypeName) {
        methods.groupBy { method -> method.name }.map { grouped ->
            grouped.value.forEachIndexed { index, method ->
                var name = method.name
                if (index > 0) name += index
                val returnType = method.returnType.asGenericPoetType()
                val parent = when {
                    method.modifiers.contains(STATIC) -> {
                        ParameterizedTypeName.get(STATIC_META_METHOD_CLASS_NAME, returnType)
                    }
                    else -> {
                        ParameterizedTypeName.get(INSTANCE_META_METHOD_CLASS_NAME, type, returnType)
                    }
                }
                val methodClassBuilder = classBuilder(name)
                        .addModifiers(PUBLIC, FINAL, STATIC)
                        .superclass(parent)
                        .addMethod(constructorBuilder()
                                .addModifiers(PRIVATE)
                                .addCode(metaNamedSuperStatement(name, type))
                                .build())
                method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
                    val parameterType = parameter.value.type.asGenericPoetType()
                    val metaParameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameterType)
                    FieldSpec.builder(metaParameterType, parameter.key)
                            .addModifiers(PRIVATE, FINAL)
                            .initializer(registerMetaParameterStatement(parameterIndex, parameter.key, parameterType))
                            .build()
                            .apply(methodClassBuilder::addField)
                    methodBuilder(parameter.key)
                            .addModifiers(PUBLIC)
                            .returns(metaParameterType)
                            .addCode(RETURN_STATEMENT, parameter.key)
                            .build()
                            .let(methodClassBuilder::addMethod)
                    addType(methodClassBuilder.build())
                }
            }

        }
    }

    private fun TypeSpec.Builder.generateFields(fields: Map<String, JavaMetaField>) {
        fields.entries.forEachIndexed { index, field ->
            val fieldType = field.value.type.asGenericPoetType()
            val fieldMetaType = ParameterizedTypeName.get(META_FIELD_CLASS_NAME, fieldType)
            FieldSpec.builder(fieldMetaType, field.key)
                    .addModifiers(PRIVATE, FINAL)
                    .initializer(registerMetaFieldStatement(index, field.key, fieldType))
                    .build()
                    .apply(::addField)
        }
    }
}
