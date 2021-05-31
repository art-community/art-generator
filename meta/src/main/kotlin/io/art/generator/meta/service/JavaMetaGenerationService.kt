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
import io.art.core.caster.Caster
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.*
import io.art.generator.meta.templates.*
import io.art.meta.model.MetaType
import javax.lang.model.element.Modifier.*
import javax.lang.model.type.TypeKind.VOID

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
                        .initializer(newStatement(), metaModuleName)
                        .build())
                .addMethod(methodBuilder(META_NAME)
                        .addModifiers(PUBLIC, STATIC)
                        .returns(metaModuleNameReference)
                        .addCode(returnStatement(), META_NAME)
                        .build())
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(computeStatement())
                        .build())
                .apply { generateTree(classes) }
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_NAME, metaClass)
                            .addStaticImport(MetaType::class.java, META_TYPE_NAME, META_ARRAY_NAME, META_VARIABLE_NAME)
                            .addStaticImport(Caster::class.java, CAST_NAME)
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
                .initializer(registerNewStatement(), metaPackageNameReference)
                .build()
                .apply(::addField)
        methodBuilder(node.packageShortName)
                .addModifiers(PUBLIC)
                .returns(metaPackageNameReference)
                .addCode(returnStatement(), node.packageShortName)
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
        val metaClassName = metaClassName(META_NAME, metaClass.type.className!!)
        val metaName = metaName(metaClass.type.className)
        val metaClassNameReference = metaClassName(EMPTY_STRING, metaClass.type.className)
        val className = ClassName.get(metaClass.type.classPackageName, metaClass.type.className)
        val type = metaClass.type.asGenericPoetType()
        val constructorStatement = metaTypeSuperStatement(metaClass, className)
        val apply = classBuilder(metaClassName)
                .addModifiers(PUBLIC, FINAL, STATIC)
                .superclass(ParameterizedTypeName.get(META_CLASS_CLASS_NAME, type))
                .addMethod(constructorBuilder()
                        .addModifiers(PRIVATE)
                        .addCode(constructorStatement)
                        .build())
                .apply { generateConstructors(metaClass.constructors, type) }
                .apply { generateFields(metaClass.fields) }
                .apply { generateMethods(metaClass.methods, type) }
                .apply { metaClass.innerClasses.values.forEach { inner -> generateClass(inner) } }
                .build()
                .apply(::addType)
        FieldSpec.builder(metaClassNameReference, metaName)
                .addModifiers(PRIVATE, FINAL)
                .initializer(registerNewStatement(), metaClassNameReference)
                .build()
                .apply(::addField)
        methodBuilder(metaName)
                .addModifiers(PUBLIC)
                .returns(metaClassNameReference)
                .addCode(returnStatement(), metaName)
                .build()
                .let(::addMethod)
    }

    private fun TypeSpec.Builder.generateFields(fields: Map<String, JavaMetaField>) {
        fields.entries.forEach { field ->
            val fieldType = field.value.type.asPoetType()
            val fieldMetaType = ParameterizedTypeName.get(META_FIELD_CLASS_NAME, fieldType.box())
            FieldSpec.builder(fieldMetaType, field.key)
                    .addModifiers(PRIVATE, FINAL)
                    .initializer(registerMetaFieldStatement(field.key, fieldType))
                    .build()
                    .apply(::addField)
        }
    }


    private fun TypeSpec.Builder.generateConstructors(constructors: List<JavaMetaMethod>, type: TypeName) {
        constructors.mapIndexed { index, constructor ->
            var name = CONSTRUCTOR_NAME
            if (index > 0) name += index
            classBuilder(name)
                    .addModifiers(PUBLIC, FINAL, STATIC)
                    .superclass(ParameterizedTypeName.get(META_CONSTRUCTOR_CLASS_NAME, type))
                    .addMethod(constructorBuilder()
                            .addModifiers(PRIVATE)
                            .addCode(metaSuperStatement(type))
                            .build())
                    .apply { generateConstructorInvocations(type, constructor) }
                    .apply { generateParameters(constructor) }
                    .build()
                    .apply(::addType)
            val constructorClassName = ClassName.get(EMPTY_STRING, name)
            FieldSpec.builder(constructorClassName, name)
                    .addModifiers(PRIVATE, FINAL)
                    .initializer(registerNewStatement(), constructorClassName)
                    .build()
                    .apply(::addField)
            methodBuilder(name)
                    .addModifiers(PUBLIC)
                    .returns(constructorClassName)
                    .addCode(returnStatement(), name)
                    .build()
                    .let(::addMethod)
        }
    }

    private fun TypeSpec.Builder.generateConstructorInvocations(type: TypeName, constructor: JavaMetaMethod) {
        val template = methodBuilder(INVOKE_NAME)
                .addModifiers(PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(type)
                .build()
        template.toBuilder()
                .addParameter(ArrayTypeName.of(Object::class.java), ARGUMENTS_NAME)
                .addCode(returnInvokeConstructorStatement(type, constructor.parameters.size))
                .build()
                .apply(::addMethod)
        when (constructor.parameters.size) {
            0 -> {
                template.toBuilder()
                        .addCode(returnInvokeWithoutArgumentsConstructorStatement(type))
                        .build()
                        .apply(::addMethod)
            }
            1 -> {
                template.toBuilder()
                        .addParameter(ArrayTypeName.of(Object::class.java), ARGUMENT_NAME)
                        .addCode(returnInvokeOneArgumentConstructorStatement(type))
                        .build()
                        .apply(::addMethod)
            }
        }
    }


    private fun TypeSpec.Builder.generateMethods(methods: List<JavaMetaMethod>, type: TypeName) {
        methods.filter { method -> !configuration.metaMethodExclusions.contains(method.name) }
                .groupBy { method -> method.name }
                .map { grouped ->
                    grouped.value.forEachIndexed { methodIndex, method ->
                        var name = method.name
                        if (methodIndex > 0) name += methodIndex
                        val returnType = method.returnType.asPoetType()
                        val static = method.modifiers.contains(STATIC)
                        val parent = when {
                            static -> {
                                ParameterizedTypeName.get(STATIC_META_METHOD_CLASS_NAME, returnType.box())
                            }
                            else -> {
                                ParameterizedTypeName.get(INSTANCE_META_METHOD_CLASS_NAME, type, returnType.box())
                            }
                        }
                        classBuilder(name)
                                .addModifiers(PUBLIC, FINAL, STATIC)
                                .superclass(parent)
                                .addMethod(constructorBuilder()
                                        .addModifiers(PRIVATE)
                                        .addCode(metaNamedSuperStatement(name, returnType))
                                        .build())
                                .apply { generateMethodInvocations(type, name, method) }
                                .apply { generateParameters(method) }
                                .build()
                                .apply(::addType)
                        val methodClassName = ClassName.get(EMPTY_STRING, name)
                        FieldSpec.builder(methodClassName, name)
                                .addModifiers(PRIVATE, FINAL)
                                .initializer(registerNewStatement(), methodClassName)
                                .build()
                                .apply(::addField)
                        methodBuilder(name)
                                .addModifiers(PUBLIC)
                                .returns(methodClassName)
                                .addCode(returnStatement(), name)
                                .build()
                                .let(::addMethod)
                    }

                }
    }

    private fun TypeSpec.Builder.generateMethodInvocations(type: TypeName, name: String, method: JavaMetaMethod) {
        val returnType = method.returnType.asPoetType()
        val static = method.modifiers.contains(STATIC)
        val template = methodBuilder(INVOKE_NAME)
                .addModifiers(PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(returnType.box())
                .apply { if (!static) addParameter(type, INSTANCE_NAME) }
                .build()
        template.toBuilder().apply {
            val invoke = when {
                static -> invokeStaticStatement(name, type, method.parameters.size)
                else -> invokeInstanceStatement(name, method.parameters.size)
            }
            when (method.returnType.originalType.kind) {
                VOID -> addCode(invoke).addCode(returnNullStatement())
                else -> addCode(returnStatement(invoke))
            }
            addParameter(ArrayTypeName.of(Object::class.java), ARGUMENTS_NAME)
            addMethod(build())
        }
        when (method.parameters.size) {
            0 -> template.toBuilder().apply {
                val invoke = when {
                    static -> invokeWithoutArgumentsStaticStatement(name, type)
                    else -> invokeWithoutArgumentsInstanceStatement(name)
                }
                when (method.returnType.originalType.kind) {
                    VOID -> addCode(invoke).addCode(returnNullStatement())
                    else -> addCode(returnStatement(invoke))
                }
                addMethod(build())
            }
            1 -> template.toBuilder().apply {
                addParameter(ClassName.get(Object::class.java), ARGUMENT_NAME)
                val invoke = when {
                    static -> invokeOneArgumentStaticStatement(name, type)
                    else -> invokeOneArgumentInstanceStatement(name)
                }
                when (method.returnType.originalType.kind) {
                    VOID -> addCode(invoke).addCode(returnNullStatement())
                    else -> addCode(returnStatement(invoke))
                }
                addMethod(build())
            }
        }
    }


    private fun TypeSpec.Builder.generateParameters(method: JavaMetaMethod) {
        method.parameters.entries.forEachIndexed { parameterIndex, parameter ->
            val parameterType = parameter.value.type.asPoetType()
            val metaParameterType = ParameterizedTypeName.get(META_PARAMETER_CLASS_NAME, parameterType.box())
            FieldSpec.builder(metaParameterType, parameter.key)
                    .addModifiers(PRIVATE, FINAL)
                    .initializer(registerMetaParameterStatement(parameterIndex, parameter.key, parameterType))
                    .build()
                    .apply(::addField)
            methodBuilder(parameter.key)
                    .addModifiers(PUBLIC)
                    .returns(metaParameterType)
                    .addCode(returnStatement(), parameter.key)
                    .build()
                    .let(::addMethod)
        }
    }
}
