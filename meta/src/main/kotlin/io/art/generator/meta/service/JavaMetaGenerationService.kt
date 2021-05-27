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
import io.art.generator.meta.constants.*
import io.art.generator.meta.extension.packages
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaField
import io.art.generator.meta.model.JavaMetaMethod
import io.art.generator.meta.model.JavaMetaTypeKind.VARIABLE_KIND
import io.art.generator.meta.templates.*
import javax.lang.model.element.Modifier.*

object JavaMetaGenerationService {
    fun generateJavaMeta(classes: Sequence<JavaMetaClass>) {
        JAVA_LOGGER.info(GENERATING_METAS_MESSAGE)
        val root = configuration.sourcesRoot.toFile()
        val moduleName = configuration.moduleName
        classBuilder(META_CLASS(moduleName))
                .addModifiers(PUBLIC)
                .addField(metaPackageField(META_PACKAGE_REFERENCE, static = true))
                .apply { generatePackageTree(classes) }
                .addMethod(metaPackageMethod(META_PACKAGE_REFERENCE, static = true))
                .build()
                .let { metaClass ->
                    JavaFile.builder(META_PACKAGE, metaClass)
                            .skipJavaLangImports(true)
                            .build()
                            .writeTo(root)
                }
    }

    private fun TypeSpec.Builder.generatePackageTree(classes: Sequence<JavaMetaClass>) {
        val moduleName = configuration.moduleName
        val tree = classes
                .filter { metaClass -> META_CLASS(metaClass.type.className!!) != META_CLASS(moduleName) }
                .let(::MetaPackagesTree)
        tree.rootPackages.values
                .asSequence()
                .map { rootPackage -> rootPackage.packageShortName }
                .forEach { name ->
                    addField(metaPackageField(name, innerNames = listOf(name)))
                    addMethod(metaPackageMethod(name, innerNames = listOf(name)))
                }
        tree.generate().forEach(::addType)
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


    private fun TypeSpec.Builder.generateClass(javaClass: JavaMetaClass) {
        addType(classBuilder(META_CLASS(javaClass.type.className!!))
                .addModifiers(PUBLIC, STATIC)
                .apply {

                }
                .build()
        )
    }


    private fun TypeSpec.Builder.addMetaConstructor(method: JavaMetaMethod) {

    }

    private fun TypeSpec.Builder.addMetaField(owner: JavaMetaClass, field: JavaMetaField) {
        val ownerFieldType = field.type.asPoetType()
        val metaFieldType = when (field.type.kind) {
            VARIABLE_KIND -> ParameterizedTypeName.get(META_FIELD_CLASS_NAME, OBJECT_CLASS_NAME)
            else -> ParameterizedTypeName.get(META_FIELD_CLASS_NAME, ownerFieldType)
        }

        fun FieldSpec.Builder.metaFieldInitializer() = when (field.type.kind) {
            VARIABLE_KIND -> initializer(META_GENERIC_FIELD_INITIALIZER, META_FIELD_CLASS_NAME, field.name)
            else -> initializer(META_FIELD_INITIALIZER, META_FIELD_CLASS_NAME, field.name, ownerFieldType)
        }

        fun MethodSpec.Builder.metaMethodContent() = when (field.type.kind) {
            VARIABLE_KIND -> {
                val typeVariable = owner.type.classTypeParameters[field.type.typeName]!!.asPoetType() as TypeVariableName
                returns(ParameterizedTypeName.get(META_FIELD_CLASS_NAME, ownerFieldType))
                addTypeVariable(typeVariable)
                addParameter(ParameterizedTypeName.get(CLASS_CLASS_NAME, typeVariable), META_FIELD_METHOD_REIFIED_PARAMETER)
                addCode(META_FIELD_METHOD_REIFIED_STATEMENT, field.name)
            }
            else -> {
                returns(metaFieldType)
                addCode(RETURN_STATEMENT, field.name)
            }
        }

        addField(FieldSpec.builder(metaFieldType, field.name, PRIVATE, FINAL)
                .apply { metaFieldInitializer() }
                .build())

        addMethod(methodBuilder(field.name)
                .addModifiers(PUBLIC)
                .apply { metaMethodContent() }
                .build())
    }


    private fun TypeSpec.Builder.addMetaMethod(method: JavaMetaMethod) {
        val ownerMethodType = method.returnType.asPoetType()
        val metaMethodType = ParameterizedTypeName.get(META_METHOD_CLASS_NAME, ownerMethodType)
        addField(FieldSpec.builder(metaMethodType, method.name, PRIVATE, FINAL)
                .initializer(META_METHOD_INITIALIZER, META_METHOD_CLASS_NAME, method.name, ownerMethodType)
                .build())
        addMethod(methodBuilder(method.name)
                .addModifiers(PUBLIC)
                .returns(metaMethodType)
                .addCode(RETURN_STATEMENT, method.name)
                .build())
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
                    .addModifiers(PUBLIC, STATIC)
                    .apply {
                        classes.forEach { javaClass ->
                            val className = META_CLASS(javaClass.type.className!!)
                            val reference = javaClass.type.className.decapitalize()
                            addField(metaPackageField(reference, innerNames = packageFullName.packages + className))
                            addMethod(metaPackageMethod(reference, innerNames = packageFullName.packages + className))
                            generateClass(javaClass)
                        }
                        children.values.forEach { child ->
                            val name = child.packageShortName
                            addField(metaPackageField(name, innerNames = packageFullName.packages + name))
                            addMethod(metaPackageMethod(name, innerNames = packageFullName.packages + name))
                            addType(child.generate())
                        }
                    }
                    .build()
        }
    }
}
