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
import com.squareup.javapoet.TypeSpec.*
import io.art.core.exception.NotImplementedException
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.model.MetaJavaClass
import io.art.generator.meta.model.MetaJavaField
import io.art.generator.meta.model.MetaJavaMethod
import io.art.generator.meta.model.MetaJavaType
import io.art.generator.meta.model.MetaJavaTypeKind.*
import io.art.generator.meta.templates.STUB_METHOD_STRING
import io.art.generator.meta.templates.THROW_EXCEPTION_STATEMENT
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.type.WildcardType

fun generateJavaStubs(classes: Set<MetaJavaClass>) {
    val root = generatorConfiguration.stubRoot
    classes.forEach { metaJavaClass -> metaJavaClass.asPoetFile().writeTo(root) }
}

fun MetaJavaClass.asPoetFile(): JavaFile = JavaFile.builder(type.classPackageName!!, asPoetClass()).build()

private fun MetaJavaType.asPoetType(): TypeName = when (kind) {
    JDK_KIND, PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> originalType
            ?.let(TypeName::get)
            ?: TypeName.get(reflectionType!!)
    ARRAY_KIND -> ArrayTypeName.of(arrayComponentType!!.asPoetType())
    WILDCARD_KIND -> wildcardExtendsBound?.let { bound -> WildcardTypeName.subtypeOf(bound.asPoetType()) }
            ?: wildcardSuperBound?.asPoetType()?.let(WildcardTypeName::supertypeOf)
            ?: WildcardTypeName.get(originalType as? WildcardType ?: reflectionType as WildcardType)
    VARIABLE_KIND -> TypeVariableName.get(typeName, *typeVariableBounds.values.map { bound -> bound.asPoetType() }.toTypedArray())
    CLASS_KIND, INTERFACE_KIND -> when {
        classTypeParameters.isNotEmpty() -> ParameterizedTypeName.get(ClassName.get(classPackageName!!, className), *classTypeParameters.values.map { parameter -> parameter.asPoetType() }.toTypedArray())
        else -> ClassName.get(classPackageName!!, className)
    }
}

private fun MetaJavaClass.asPoetClass(): TypeSpec = when (type.kind) {
    INTERFACE_KIND -> interfaceBuilder(type.className)
            .apply {
                addModifiers(*this@asPoetClass.modifiers.toTypedArray())
                type.classSuperInterfaces.forEach { interfaceType -> addSuperinterface(interfaceType.asPoetType()) }
                writeTypeParameters(type.classTypeParameters.values.toList())
                writeMethods(methods)
                writeClasses(innerClasses.values.toList())
            }
            .build()
    CLASS_KIND -> classBuilder(type.className)
            .apply {
                addModifiers(*this@asPoetClass.modifiers.toTypedArray())
                type.classSuperClass?.let { superClass -> superclass(superClass.asPoetType()) }
                type.classSuperInterfaces.forEach { interfaceType -> addSuperinterface(interfaceType.asPoetType()) }
                writeTypeParameters(type.classTypeParameters.values.toList())
                writeFields(fields.values.toList())
                writeMethods(methods)
                writeClasses(innerClasses.values.toList())
            }
            .build()
    ENUM_KIND -> enumBuilder(type.className)
            .apply {
                addModifiers(*this@asPoetClass.modifiers.toTypedArray())
                writeClasses(innerClasses.values.toList())
                writeFields(fields.values.toList())
                writeMethods(methods)
            }
            .build()
    else -> TODO()
}

private fun TypeSpec.Builder.writeTypeParameters(parameters: List<MetaJavaType>) = parameters.filter { parameter -> parameter.kind == VARIABLE_KIND }.forEach { parameter ->
    addTypeVariable(parameter.asPoetType() as TypeVariableName)
}

private fun TypeSpec.Builder.writeFields(fields: List<MetaJavaField>) = fields.forEach { field ->
    addField(field.type.asPoetType(), field.name, *field.modifiers.filter { modifier -> modifier != FINAL }.toTypedArray())
}

private fun TypeSpec.Builder.writeClasses(classes: List<MetaJavaClass>) = classes.forEach { inner ->
    addType(inner.asPoetClass())
}

private fun TypeSpec.Builder.writeMethods(methods: List<MetaJavaMethod>) = methods.forEach { method ->
    addMethod(methodBuilder(method.name)
            .addModifiers(*method.modifiers.toTypedArray())
            .addTypeVariables(method.typeParameters
                    .filter { parameter -> parameter.value.kind == VARIABLE_KIND }
                    .map { parameter -> parameter.value.asPoetType() as TypeVariableName }
            )
            .addExceptions(method.exceptions.map { exception -> exception.asPoetType() })
            .returns(method.returnType.asPoetType())
            .addParameters(method.parameters.map { parameter -> ParameterSpec.builder(parameter.value.type.asPoetType(), parameter.key).build() })
            .apply { addCode(THROW_EXCEPTION_STATEMENT, NotImplementedException::class.java, STUB_METHOD_STRING) }
            .build())
}
