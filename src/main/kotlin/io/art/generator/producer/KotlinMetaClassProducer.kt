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

import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.FunSpec.Companion.constructorBuilder
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import io.art.core.extensions.StringExtensions.decapitalize
import io.art.generator.constants.KOTLIN_LAZY_CLASS_NAME
import io.art.generator.constants.KOTLIN_META_CLASS_CLASS_NAME
import io.art.generator.constants.SELF_NAME
import io.art.generator.extension.asPoetType
import io.art.generator.extension.couldBeGenerated
import io.art.generator.factory.NameFactory
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaClassName
import io.art.generator.templates.*
import org.jetbrains.kotlin.descriptors.Modality.ABSTRACT

fun TypeSpec.Builder.generateClass(metaClass: KotlinMetaClass, nameFactory: NameFactory) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = KotlinMetaClassName(
        metaName = kotlinMetaClassClassName(metaClass.type.className, nameFactory),
        type = metaClass,
        typeName = metaClass.type.asPoetType()
    )
    classBuilder(metaClassName.metaName)
        .superclass(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(metaClassName.typeName))
        .addFunction(
            constructorBuilder()
                .addModifiers(INTERNAL)
                .callSuperConstructor(kotlinMetaClassSuperStatement(metaClass))
                .build()
        )
        .apply { generateSelf(metaClassName) }
        .apply { if (!metaClass.modifiers.contains(Modifier.ABSTRACT)) generateConstructors(metaClassName) }
        .apply { generateProperties(metaClassName) }
        .apply { generateFunctions(metaClassName) }
        .apply {
            if (metaClass.isInterface) {
                generateProxy(metaClass)
            }
        }
        .apply {
            metaClass.innerClasses
                .values
                .filter(KotlinMetaClass::couldBeGenerated)
                .forEach { inner -> generateClass(inner, nameFactory) }
        }
        .build()
        .apply(::addType)
    PropertySpec.builder(className, metaClassName.metaName)
        .addModifiers(PRIVATE)
        .initializer(kotlinRegisterClass(metaClassName.metaName))
        .build()
        .apply(::addProperty)
    FunSpec.builder(className)
        .returns(metaClassName.metaName)
        .addCode(kotlinReturnStatement(className))
        .build()
        .let(::addFunction)
}

private fun TypeSpec.Builder.generateSelf(metaClassName: KotlinMetaClassName) {
    val type = KOTLIN_LAZY_CLASS_NAME.parameterizedBy(metaClassName.metaName)
    TypeSpec.companionObjectBuilder().apply {
        PropertySpec.builder(SELF_NAME, type, PRIVATE, FINAL)
            .initializer(kotlinMetaClassSelfMethodCall(metaClassName.typeName))
            .build()
            .apply(::addProperty)
        FunSpec.builder(decapitalize(metaClassName.type.type.className))
            .addModifiers(PUBLIC)
            .returns(metaClassName.metaName)
            .addCode(kotlinReturnLazyGetStatement(SELF_NAME))
            .build()
            .let(::addFunction)
    }.build().apply(::addType)
}
