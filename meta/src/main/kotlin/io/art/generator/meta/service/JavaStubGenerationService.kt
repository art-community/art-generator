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

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeSpec.*
import com.squareup.javapoet.TypeVariableName
import io.art.core.exception.NotImplementedException
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.GENERATING_STUBS_MESSAGE
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.throwInvalidTypeException
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.model.JavaMetaField
import io.art.generator.meta.model.JavaMetaMethod
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.templates.STUB_METHOD_LITERAL
import io.art.generator.meta.templates.THROW_EXCEPTION_STATEMENT
import javax.lang.model.element.Modifier.FINAL

fun generateJavaStubs(classes: Sequence<JavaMetaClass>) {
    JAVA_LOGGER.info(GENERATING_STUBS_MESSAGE)
    val root = configuration.stubRoot
    classes.forEach { metaJavaClass -> metaJavaClass.asPoetFile().writeTo(root) }
}

private fun JavaMetaClass.asPoetFile(): JavaFile = JavaFile.builder(type.classPackageName!!, asPoetClass()).build()

private fun JavaMetaClass.asPoetClass(): TypeSpec = when (type.kind) {
    INTERFACE_KIND -> with(interfaceBuilder(type.className)) {
        addModifiers(*this@asPoetClass.modifiers.toTypedArray())
        type.classSuperInterfaces.forEach { interfaceType -> addSuperinterface(interfaceType.asPoetType()) }
        writeTypeParameters(type.classTypeParameters.values.asSequence())
        writeMethods(methods.asSequence())
        writeClasses(innerClasses.values.asSequence())
        build()
    }

    CLASS_KIND -> with(classBuilder(type.className)) {
        addModifiers(*this@asPoetClass.modifiers.toTypedArray())
        type.classSuperClass?.let { superClass -> superclass(superClass.asPoetType()) }
        type.classSuperInterfaces.forEach { interfaceType -> addSuperinterface(interfaceType.asPoetType()) }
        writeTypeParameters(type.classTypeParameters.values.asSequence())
        writeFields(fields.values.asSequence())
        writeMethods(methods.asSequence())
        writeClasses(innerClasses.values.asSequence())
        build()
    }

    ENUM_KIND -> with(enumBuilder(type.className)) {
        addModifiers(*this@asPoetClass.modifiers.toTypedArray())
        writeClasses(innerClasses.values.asSequence())
        writeFields(fields.values.asSequence())
        writeMethods(methods.asSequence())
        build()
    }

    else -> throwInvalidTypeException()
}

private fun Builder.writeTypeParameters(parameters: Sequence<JavaMetaType>) = parameters
        .filter { parameter -> parameter.kind == VARIABLE_KIND }
        .forEach { parameter ->
            addTypeVariable(parameter.asPoetType() as TypeVariableName)
        }

private fun Builder.writeFields(fields: Sequence<JavaMetaField>) = fields
        .forEach { field ->
            addField(field.type.asPoetType(), field.name, *field.modifiers.filter { modifier -> modifier != FINAL }.toTypedArray())
        }

private fun Builder.writeClasses(classes: Sequence<JavaMetaClass>) = classes.forEach { inner ->
    addType(inner.asPoetClass())
}

private fun Builder.writeMethods(methods: Sequence<JavaMetaMethod>) = methods.forEach { method ->
    addMethod(methodBuilder(method.name)
            .addModifiers(*method.modifiers.toTypedArray())
            .addTypeVariables(method.typeParameters
                    .filter { parameter -> parameter.value.kind == VARIABLE_KIND }
                    .map { parameter -> parameter.value.asPoetType() as TypeVariableName }
            )
            .addExceptions(method.exceptions.map { exception -> exception.asPoetType() })
            .returns(method.returnType.asPoetType())
            .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
            .apply { addCode(THROW_EXCEPTION_STATEMENT, NotImplementedException::class.java, STUB_METHOD_LITERAL) }
            .build())
}
