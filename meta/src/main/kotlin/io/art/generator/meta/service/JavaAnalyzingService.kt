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

import com.sun.tools.javac.code.Symbol
import com.sun.tools.javac.code.Symbol.*
import com.sun.tools.javac.code.Type
import io.art.core.constants.CompilerSuppressingWarnings.UNCHECKED_CAST
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.*
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.service.JavaCompilerProvider.useJavaCompiler
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.lang.model.SourceVersion.RELEASE_8
import javax.lang.model.SourceVersion.latest
import javax.lang.model.element.ElementKind.ENUM
import javax.lang.model.type.IntersectionType
import javax.lang.model.type.TypeMirror

object JavaAnalyzingService {
    fun analyzeJavaSources(sources: Sequence<Path>): Map<Path, JavaMetaClass> {
        JAVA_LOGGER.info(ANALYZING_MESSAGE)

        val sourceRoots = configuration.sourcesRoot.toFile()
                .listFiles()
                ?.asSequence()
                ?.map(File::toPath)
                ?.toList()
                ?: emptyList()

        return useJavaCompiler(sources, sourceRoots) { task ->
            task.analyze()
                    .asSequence()
                    .filter { input -> input.kind.isClass || input.kind.isInterface || input.kind == ENUM }
                    .map { element -> (element as ClassSymbol).asMetaClass() }
                    .filter { input -> input.type.classPackageName?.split(DOT)?.firstOrNull() != META_NAME }
                    .associateBy { metaClass -> metaClass.source }
        }
    }
}

private fun TypeMirror.asMetaType(): JavaMetaType = when (this) {
    is Type.TypeVar -> asMetaType()

    is Type.ArrayType -> asMetaType()

    is Type.WildcardType -> asMetaType()

    is Type.ClassType -> asMetaType()

    is Type -> when {
        isPrimitiveOrVoid -> JavaMetaType(
                originalType = this,
                kind = PRIMITIVE_KIND,
                typeName = tsym.qualifiedName.toString()
        )
        else -> JavaMetaType(
                originalType = this,
                kind = UNKNOWN_KIND,
                typeName = tsym?.qualifiedName?.toString() ?: toString()
        )
    }

    else -> JavaMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
}

private fun Type.ClassType.asMetaType(): JavaMetaType = JavaMetaType(
        originalType = this,
        classFullName = tsym.qualifiedName.toString(),
        kind = when {
            !isInterface && !asElement().isEnum -> CLASS_KIND
            isInterface -> INTERFACE_KIND
            asElement().isEnum -> ENUM_KIND
            else -> UNKNOWN_KIND
        },
        typeName = tsym.name.toString(),
        className = tsym.simpleName.toString(),
        classPackageName = tsym.qualifiedName.toString().substringBeforeLast(DOT),
        typeParameters = typeArguments
                .asSequence()
                .map { argument -> argument.asMetaType() }
                .filter { argument -> argument.kind != UNKNOWN_KIND }
                .associateBy { argument -> argument.typeName },
        classSuperClass = supertype_field?.asMetaType(),
        classSuperInterfaces = interfaces_field
                ?.map { interfaceField -> interfaceField.asMetaType() }
                ?: emptyList()
)

private fun Type.TypeVar.asMetaType(): JavaMetaType = JavaMetaType(
        originalType = this,
        kind = VARIABLE_KIND,
        typeName = tsym.name.toString(),
        typeVariableBounds = upperBound
                .let { bound ->
                    when (bound) {
                        is IntersectionType -> bound.bounds
                        else -> listOf(bound)
                    }
                }
                .asSequence()
                .map { argument -> argument.asMetaType() }
                .filter { argument -> argument.kind != UNKNOWN_KIND }
                .toList(),
)

private fun Type.ArrayType.asMetaType(): JavaMetaType = JavaMetaType(
        originalType = this,
        kind = ARRAY_KIND,
        typeName = tsym.name.toString(),
        arrayComponentType = componentType.asMetaType().takeIf { type -> type.kind != UNKNOWN_KIND }
)

private fun Type.WildcardType.asMetaType(): JavaMetaType = JavaMetaType(
        originalType = this,
        kind = WILDCARD_KIND,
        typeName = type.toString(),
        wildcardSuperBound = superBound?.asMetaType()?.takeIf { type -> type.kind != UNKNOWN_KIND },
        wildcardExtendsBound = extendsBound?.asMetaType()?.takeIf { type -> type.kind != UNKNOWN_KIND }
)

private fun ClassSymbol.asMetaType(): JavaMetaType = type.asMetaType()

private fun ClassSymbol.asMetaClass(): JavaMetaClass = JavaMetaClass(
        type = asMetaType(),
        source = Paths.get(sourcefile.toUri()),
        modifiers = modifiers,

        fields = getMembers()
                .reversed()
                .asSequence()
                .filterIsInstance<VarSymbol>()
                .associate { symbol -> symbol.name.toString() to symbol.asMetaField() },

        constructors = getMembers()
                .asSequence()
                .filterIsInstance<MethodSymbol>()
                .filter { method -> method.isConstructor }
                .map { method -> method.asMetaMethod() }
                .toList(),

        methods = getMembers()
                .asSequence()
                .filterIsInstance<MethodSymbol>()
                .filter { method -> !method.isConstructor }
                .map { method -> method.asMetaMethod() }
                .toList(),

        innerClasses = getMembers()
                .asSequence()
                .filterIsInstance<ClassSymbol>()
                .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() }
)

private fun MethodSymbol.asMetaMethod() = JavaMetaMethod(
        name = name.toString(),
        modifiers = modifiers,
        returnType = returnType.asMetaType(),
        parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
        typeParameters = typeParameters
                .asSequence()
                .map { typeParameter -> typeParameter.type.asMetaType() }
                .associateBy { type -> type.typeName },
        exceptions = thrownTypes.map { exception -> exception.asMetaType() }
)

private fun VarSymbol.asMetaField() = JavaMetaField(
        name = name.toString(),
        modifiers = modifiers,
        type = type.asMetaType()
)

private fun VarSymbol.asMetaParameter() = JavaMetaParameter(
        name = name.toString(),
        modifiers = modifiers,
        type = type.asMetaType()
)

@Suppress(UNCHECKED_CAST)
private fun Symbol.getMembers(): Iterable<Symbol> = when (latest()) {
    RELEASE_8 -> members().javaClass.getMethod(GET_ELEMENTS_NAME).invoke(members()) as Iterable<Symbol>
    else -> members().javaClass.getMethod(GET_SYMBOLS_NAME).invoke(members()) as Iterable<Symbol>
}
