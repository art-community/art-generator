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
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.*
import io.art.generator.meta.templates.*
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile()
        val moduleName = configuration.moduleName
        classBuilder(metaClassName(moduleName))
                .addModifiers(PUBLIC)
                .superclass(META_MODULE_CLASS_NAME)
                .addField(FieldSpec.builder(metaClassName(moduleName), META_NAME)
                        .addModifiers(PRIVATE, FINAL, STATIC)
                        .initializer(NEW_STATEMENT, metaClassName(moduleName), META_NAME)
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(metaClassName(moduleName))
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
        FieldSpec.builder(metaClassName(node.packageShortName), node.packageShortName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(REGISTER_NEW_STATEMENT, metaClassName(node.packageShortName))
                .build()
                .let(::addField)
        methodBuilder(node.packageShortName)
                .addModifiers(PUBLIC)
                .returns(metaClassName(node.packageShortName))
                .addCode(RETURN_STATEMENT, node.packageShortName)
                .build()
                .let(::addMethod)
        node.classes.forEach { metaClass ->
            generateClass(metaClass)
        }
    }

    private fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass) {
        val className = ClassName.get(metaClass.type.classPackageName, metaClass.type.className)
        val type = metaClass.type.asGenericPoetType()
        val constructorBuilder = CodeBlock.of("super(metaType(\$T.class, \$T[]::new)", className, className).toBuilder()
        if (metaClass.type.typeVariables.isNotEmpty()) {
            constructorBuilder.add(", ")
            metaClass.type.typeVariables.forEach { variable ->
                constructorBuilder.add("metaVariable(\$S, metaType(\$T.class, \$T[]::new))",
                        variable.key,
                        variable.value.asPoetType(),
                        variable.value.asPoetType()
                )
            }
        }
        val constructorBody = constructorBuilder.add(")").build()
        classBuilder(metaClassName(metaClass.type.className!!))
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(ParameterizedTypeName.get(META_CLASS_CLASS_NAME, type))
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(constructorBody)
                        .build())
                .apply { generateConstructors(metaClass.constructors, type, constructorBody) }
                .apply { generateFields(metaClass.fields) }
                .apply { generateMethods(metaClass.methods, type, constructorBody) }
                .build()
                .apply(::addType)
        metaClass.innerClasses.values.forEach { inner -> generateClass(inner) }
    }

    private fun TypeSpec.Builder.generateConstructors(constructors: List<JavaMetaMethod>, type: TypeName, typeConstructor: CodeBlock) {
        constructors.mapIndexed { index, constructor ->
            var name = "constructor"
            if (index > 0) name += index
            val constructorClassBuilder = classBuilder(name)
                    .addModifiers(PUBLIC, FINAL, STATIC)
                    .superclass(ParameterizedTypeName.get(META_CONSTRUCTOR_CLASS_NAME, type))
                    .addMethod(constructorBuilder()
                            .addModifiers(PRIVATE)
                            .addCode(typeConstructor)
                            .build())
            constructor.parameters.entries.forEachIndexed { parameterIndex, parameter ->
                val parameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameter.value.type.asGenericPoetType())
                FieldSpec.builder(parameterType, parameter.key)
                        .addModifiers(PRIVATE, FINAL)
                        .initializer(REGISTER_META_PARAMETER_STATEMENT(parameterIndex, parameter.key, parameterType))
                        .build()
                        .apply(constructorClassBuilder::addField)
                methodBuilder(parameter.key)
                        .addModifiers(PUBLIC)
                        .returns(parameterType)
                        .addCode(RETURN_STATEMENT, parameter.key)
                        .build()
                        .let(constructorClassBuilder::addMethod)

                addType(constructorClassBuilder.build())
            }
        }
    }

    private fun TypeSpec.Builder.generateMethods(methods: List<JavaMetaMethod>, type: TypeName, typeConstructor: CodeBlock) {
        methods.groupBy { method -> method.name }.map { grouped ->
            grouped.value.forEachIndexed { index, method ->
                var name = method.name
                if (index > 0) name += index
                val parent = when {
                    method.modifiers.contains(STATIC) -> ParameterizedTypeName.get(STATIC_META_METHOD_CLASS_NAME, type)
                    else -> ParameterizedTypeName.get(INSTANCE_META_METHOD_CLASS_NAME, type)
                }
                val methodClassBuilder = classBuilder(name)
                        .addModifiers(PUBLIC, FINAL, STATIC)
                        .superclass(parent)
                        .addMethod(constructorBuilder()
                                .addModifiers(PRIVATE)
                                .addCode(typeConstructor)
                                .build())
                method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
                    val parameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameter.value.type.asGenericPoetType())
                    FieldSpec.builder(parameterType, parameter.key)
                            .addModifiers(PRIVATE, FINAL)
                            .initializer(REGISTER_META_PARAMETER_STATEMENT(parameterIndex, parameter.key, parameterType))
                            .build()
                            .apply(methodClassBuilder::addField)
                    methodBuilder(parameter.key)
                            .addModifiers(PUBLIC)
                            .returns(parameterType)
                            .addCode(RETURN_STATEMENT, parameter.key)
                            .build()
                            .let(methodClassBuilder::addMethod)
                    addType(methodClassBuilder.build())
                }
            }

        }
    }

    private fun TypeSpec.Builder.generateFields(fields: Map<String, JavaMetaField>) {
        fields.entries.forEach { field ->
            val fieldType = ParameterizedTypeName.get(META_FIELD_CLASS_NAME, field.value.type.asGenericPoetType())
            FieldSpec.builder(fieldType, field.key)
                    .addModifiers(PRIVATE, FINAL)
                    .initializer(REGISTER_NEW_STATEMENT, field)
                    .build()
                    .apply(::addField)
        }
    }
}
