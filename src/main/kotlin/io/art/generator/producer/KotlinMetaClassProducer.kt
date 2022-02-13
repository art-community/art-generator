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
import io.art.generator.templates.*
import org.jetbrains.kotlin.descriptors.Modality.ABSTRACT

fun TypeSpec.Builder.generateClass(metaClass: KotlinMetaClass, nameFactory: NameFactory) {
    val className = metaClassName(metaClass.type.className!!)
    val metaClassName = kotlinMetaClassClassName(metaClass.type.className, nameFactory)
    val typeName = metaClass.type.asPoetType()
    classBuilder(metaClassName)
            .superclass(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(typeName))
            .addFunction(constructorBuilder()
                    .addModifiers(INTERNAL)
                    .callSuperConstructor(kotlinMetaClassSuperStatement(metaClass))
                    .build())
            .apply { generateSelf(metaClassName, typeName, metaClass) }
            .apply { if (metaClass.modality != ABSTRACT) generateConstructors(metaClass, typeName) }
            .apply { generateProperties(metaClass) }
            .apply { generateFunctions(metaClass) }
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
    PropertySpec.builder(className, metaClassName)
            .addModifiers(PRIVATE)
            .initializer(kotlinRegisterNewStatement(metaClassName))
            .build()
            .apply(::addProperty)
    FunSpec.builder(className)
            .returns(metaClassName)
            .addCode(kotlinReturnStatement(className))
            .build()
            .let(::addFunction)
}

private fun TypeSpec.Builder.generateSelf(metaClassName: ClassName, typeName: TypeName, metaClass: KotlinMetaClass) {
    val type = KOTLIN_LAZY_CLASS_NAME.parameterizedBy(metaClassName)
    TypeSpec.companionObjectBuilder().apply {
        PropertySpec.builder(SELF_NAME, type, PRIVATE, FINAL)
                .initializer(kotlinMetaClassSelfMethodCall(typeName))
                .build()
                .apply(::addProperty)
        FunSpec.builder(decapitalize(metaClass.type.className))
                .addModifiers(PUBLIC)
                .returns(metaClassName)
                .addCode(kotlinReturnLazyGetStatement(SELF_NAME))
                .build()
                .let(::addFunction)
    }.build().apply(::addType)
}
