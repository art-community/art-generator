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

package io.art.generator.producer

import com.squareup.javapoet.*
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import io.art.core.extensions.StringExtensions.decapitalize
import io.art.generator.constants.JAVA_LAZY_CLASS_NAME
import io.art.generator.constants.JAVA_META_CLASS_CLASS_NAME
import io.art.generator.constants.SELF_NAME
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.extension.extractOwnerClassName
import io.art.generator.factory.NameFactory
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind.PRIMITIVE_KIND
import io.art.generator.templates.*
import javax.lang.model.element.Modifier.*

fun TypeSpec.Builder.generateClass(metaClass: JavaMetaClass, nameFactory: NameFactory) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = javaMetaClassClassName(metaClass.type.className, nameFactory)
    val typeName = metaClass.type.asPoetType()
    val constructorStatement = javaMetaClassSuperStatement(metaClass)
    classBuilder(metaClassName)
            .addModifiers(PUBLIC, FINAL, STATIC)
            .superclass(ParameterizedTypeName.get(JAVA_META_CLASS_CLASS_NAME, typeName.box()))
            .addMethod(constructorBuilder()
                    .addModifiers(PRIVATE)
                    .addCode(constructorStatement)
                    .build())
            .apply { generateSelf(metaClassName, typeName, metaClass) }
            .apply { if (!metaClass.modifiers.contains(ABSTRACT)) generateConstructors(metaClass, typeName) }
            .apply { generateFields(metaClass) }
            .apply { generateMethods(metaClass) }
            .apply {
                if (metaClass.isInterface) {
                    generateProxy(metaClass)
                }
            }
            .apply {
                metaClass.innerClasses
                        .values
                        .filter(JavaMetaClass::couldBeGenerated)
                        .forEach { inner -> generateClass(inner, nameFactory) }
            }
            .build()
            .apply { qualifyImports(mutableSetOf(), metaClass) }
            .apply(::addType)
    FieldSpec.builder(metaClassName, className)
            .addModifiers(PRIVATE, FINAL)
            .initializer(javaRegisterNewStatement(metaClassName))
            .build()
            .apply(::addField)
    methodBuilder(className)
            .addModifiers(PUBLIC)
            .returns(metaClassName)
            .addCode(javaReturnStatement(className))
            .build()
            .let(::addMethod)
}

private fun TypeSpec.Builder.generateSelf(metaClassName: ClassName, typeName: TypeName, metaClass: JavaMetaClass) {
    val type = ParameterizedTypeName.get(JAVA_LAZY_CLASS_NAME, metaClassName)
    FieldSpec.builder(type, SELF_NAME, PRIVATE, STATIC, FINAL)
            .initializer(javaMetaClassSelfMethodCall(typeName))
            .build()
            .apply(::addField)
    methodBuilder(decapitalize(metaClass.type.className))
            .addModifiers(PUBLIC, STATIC)
            .returns(metaClassName)
            .addCode(javaReturnLazyGetStatement(SELF_NAME))
            .build()
            .let(::addMethod)
}

internal fun TypeSpec.Builder.qualifyImports(qualified: MutableSet<JavaMetaClass>, metaClass: JavaMetaClass) {
    qualified.add(metaClass)
    qualifyImports(metaClass.type)
    metaClass.constructors.forEach { constructor ->
        constructor.parameters.values.forEach { parameter ->
            qualifyImports(parameter.type)
        }
    }
    metaClass.methods.forEach { method ->
        qualifyImports(method.returnType)
        method.parameters.values.forEach { parameter ->
            qualifyImports(parameter.type)
        }
    }
    metaClass.fields.forEach { field ->
        qualifyImports(field.value.type)
    }
    metaClass.innerClasses.values.filter { value -> !qualified.contains(value) }.forEach { value -> qualifyImports(qualified, value) }
    metaClass.parent?.let { parent -> qualifyImports(qualified, parent) }
    metaClass.interfaces.filter { value -> !qualified.contains(value) }.forEach { value -> qualifyImports(qualified, value) }
}

internal fun TypeSpec.Builder.qualifyImports(metaType: JavaMetaType) {
    if (metaType.kind == PRIMITIVE_KIND) return
    metaType.className?.let { name ->
        alwaysQualify(metaType.extractOwnerClassName())
        alwaysQualify(name)
    }
    metaType.typeParameters.forEach(::qualifyImports)
    metaType.arrayComponentType?.let(::qualifyImports)
    metaType.wildcardExtendsBound?.let(::qualifyImports)
    metaType.wildcardSuperBound?.let(::qualifyImports)
}
