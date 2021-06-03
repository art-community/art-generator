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

import com.sun.tools.javac.code.Flags.Flag.SYNTHETIC
import com.sun.tools.javac.code.Flags.asFlagSet
import com.sun.tools.javac.code.Symbol
import com.sun.tools.javac.code.Symbol.*
import com.sun.tools.javac.code.Type
import io.art.core.constants.CompilerSuppressingWarnings.UNCHECKED_CAST
import io.art.core.constants.StringConstants.DOT
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.*
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.provider.JavaCompilerProvider.useJavaCompiler
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

private val TYPE_CACHE = mutableMapOf<TypeMirror, JavaMetaType>()

private fun TypeMirror.asMetaType(): JavaMetaType = putIfAbsent(TYPE_CACHE, this) {
    when (this) {
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
}

private fun Type.ClassType.asMetaType(): JavaMetaType {
    val type = putIfAbsent(TYPE_CACHE, this) {
        JavaMetaType(
                originalType = this,
                classFullName = tsym.qualifiedName.toString(),
                kind = when {
                    !isInterface && !asElement().isEnum -> CLASS_KIND
                    isInterface -> INTERFACE_KIND
                    asElement().isEnum -> ENUM_KIND
                    else -> UNKNOWN_KIND
                },
                typeName = tsym.qualifiedName.toString(),
                className = tsym.simpleName.toString(),
                classPackageName = tsym.qualifiedName.toString().substringBeforeLast(DOT)
        )
    }
    typeArguments
            .asSequence()
            .map { argument -> argument.asMetaType() }
            .filter { argument -> argument.kind != UNKNOWN_KIND }
            .forEach(type.typeParameters::add)
    return type
}

private fun Type.TypeVar.asMetaType(): JavaMetaType {
    val type = putIfAbsent(TYPE_CACHE, this) {
        JavaMetaType(
                originalType = this,
                kind = VARIABLE_KIND,
                typeName = tsym.qualifiedName.toString()
        )
    }
    upperBound
            .let { bound ->
                when (bound) {
                    is IntersectionType -> bound.bounds
                    else -> listOf(bound)
                }
            }
            .asSequence()
            .map { argument -> argument.asMetaType() }
            .filter { argument -> argument.kind != UNKNOWN_KIND }
            .forEach(type.typeVariableBounds::add)
    return type
}

private fun Type.ArrayType.asMetaType(): JavaMetaType = JavaMetaType(
        originalType = this,
        kind = ARRAY_KIND,
        typeName = tsym.qualifiedName.toString(),
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
        source = Paths.get(sourcefile.name),
        modifiers = modifiers,

        fields = getMembers()
                .reversed()
                .asSequence()
                .filterIsInstance<VarSymbol>()
                .filter { field -> !asFlagSet(field.flags()).contains(SYNTHETIC) }
                .associate { symbol -> symbol.name.toString() to symbol.asMetaField() },

        constructors = getMembers()
                .asSequence()
                .filterIsInstance<MethodSymbol>()
                .filter { method -> method.isConstructor }
                .filter { method -> !method.isLambdaMethod }
                .filter { method -> !method.isDynamic }
                .filter { method -> !asFlagSet(method.flags()).contains(SYNTHETIC) }
                .map { method -> method.asMetaMethod() }
                .toList(),

        methods = getMembers()
                .asSequence()
                .filterIsInstance<MethodSymbol>()
                .filter { method -> !method.isConstructor }
                .filter { method -> !method.isLambdaMethod }
                .filter { method -> !method.isDynamic }
                .filter { method -> !asFlagSet(method.flags()).contains(SYNTHETIC) }
                .map { method -> method.asMetaMethod() }
                .toList(),

        innerClasses = getMembers()
                .asSequence()
                .filterIsInstance<ClassSymbol>()
                .filter { inner -> !asFlagSet(inner.flags()).contains(SYNTHETIC) }
                .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

        parent = superclass
                ?.let { superclass.tsym as? ClassSymbol }
                ?.takeIf { superclass.tsym.qualifiedName.toString() != Object::class.java.name }
                ?.asMetaClass(),

        interfaces = interfaces
                .map { interfaceType -> interfaceType.tsym }
                .filterIsInstance<ClassSymbol>()
                .filter { interfaceType -> !asFlagSet(interfaceType.flags()).contains(SYNTHETIC) }
                .map { interfaceField -> interfaceField.asMetaClass() }

)

private fun MethodSymbol.asMetaMethod() = JavaMetaMethod(
        name = name.toString(),
        modifiers = modifiers,
        returnType = returnType.asMetaType(),
        parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
        typeParameters = typeParameters.map { typeParameter -> typeParameter.type.asMetaType() },
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
