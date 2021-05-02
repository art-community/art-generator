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

import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.code.Symbol.*
import com.sun.tools.javac.code.Type
import com.sun.tools.javac.util.Context
import com.sun.tools.javac.util.Options
import io.art.core.constants.StringConstants.DOT
import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.JAVA_PACKAGE_PREFIX
import io.art.generator.meta.constants.NO_WARN_OPTION
import io.art.generator.meta.constants.PARAMETERS_OPTION
import io.art.generator.meta.extension.isJava
import io.art.generator.meta.logger.LoggingDiagnosticListener
import io.art.generator.meta.model.*
import io.art.generator.meta.model.MetaJavaTypeKind.*
import org.jetbrains.kotlin.javac.JavacOptionsMapper.setUTF8Encoding
import java.io.StringWriter
import java.nio.charset.Charset.defaultCharset
import java.util.Locale.getDefault
import javax.lang.model.element.ElementKind.ENUM
import javax.lang.model.type.IntersectionType
import javax.lang.model.type.TypeMirror
import javax.tools.StandardLocation.CLASS_PATH
import javax.tools.StandardLocation.SOURCE_PATH

object JavaAnalyzingService {
    fun analyzeJavaSources(): Set<MetaJavaClass> {
        val compiler = JavacTool.create()
        val writer = StringWriter()
        val context = Context().apply { Options.instance(this).let(::setUTF8Encoding) }
        val sourceRoots = generatorConfiguration.sourcesRoot.map { path -> path.toFile() }
        val sources = generatorConfiguration.sourcesRoot.flatMap { root -> root.toFile().walkTopDown().filter { file -> file.isJava } }.toTypedArray()
        val classpath = generatorConfiguration.classpath.map { path -> path.toFile() }
        val fileManager = compiler.getStandardFileManager(LoggingDiagnosticListener, getDefault(), defaultCharset()).apply {
            setContext(context)
            setLocation(SOURCE_PATH, sourceRoots)
            setLocation(CLASS_PATH, classpath)
            isSymbolFileEnabled = false
        }
        val options = listOf(
                PARAMETERS_OPTION,
                NO_WARN_OPTION,
        )
        val files = fileManager.getJavaFileObjects(*sources)
        val task = compiler.getTask(
                writer,
                fileManager,
                LoggingDiagnosticListener,
                options,
                emptyList(),
                files,
                context
        )

        return task.analyze().toList()
                .filter { input -> input.kind.isClass || input.kind.isInterface || input.kind == ENUM }
                .map { element -> (element as ClassSymbol).asMetaClass() }
                .toSet()
    }
}

private fun TypeMirror.asMetaType(): MetaJavaType = when (this) {
    is Type.TypeVar -> asMetaType()
    is Type.ArrayType -> asMetaType()
    is Type.WildcardType -> asMetaType()
    is Type.ClassType -> when {
        tsym.qualifiedName.startsWith(JAVA_PACKAGE_PREFIX) -> MetaJavaType(originalType = this, kind = JDK_KIND, typeName = tsym.qualifiedName.toString())
        else -> asMetaType()
    }
    is Type -> when {
        isPrimitiveOrVoid -> MetaJavaType(originalType = this, kind = PRIMITIVE_KIND, typeName = tsym.qualifiedName.toString())
        else -> MetaJavaType(originalType = this, kind = UNKNOWN_KIND, typeName = tsym.qualifiedName.toString())
    }
    else -> MetaJavaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
}

private fun Type.ClassType.asMetaType(): MetaJavaType = MetaJavaType(
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
        classPackageName = tsym.qualifiedName.toString().substringBeforeLast(DOT),
        classTypeParameters = typeArguments
                .map { argument -> argument.asMetaType() }
                .filter { argument -> argument.kind != UNKNOWN_KIND }
                .associateBy { argument -> argument.typeName },
        classSuperClass = supertype_field?.asMetaType(),
        classSuperInterfaces = interfaces_field
                ?.map { interfaceField -> interfaceField.asMetaType() }
                ?: emptyList()
)

private fun Type.TypeVar.asMetaType(): MetaJavaType = MetaJavaType(
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
                .map { argument -> argument.asMetaType() }
                .filter { argument -> argument.kind != UNKNOWN_KIND }
                .associateBy { argument -> argument.typeName },
)

private fun Type.ArrayType.asMetaType(): MetaJavaType = MetaJavaType(
        originalType = this,
        kind = ARRAY_KIND,
        typeName = componentType.toString(),
        arrayComponentType = componentType.asMetaType().takeIf { type -> type.kind != UNKNOWN_KIND }
)

private fun Type.WildcardType.asMetaType(): MetaJavaType = MetaJavaType(
        originalType = this,
        kind = WILDCARD_KIND,
        typeName = type.toString(),
        wildcardSuperBound = superBound?.asMetaType()?.takeIf { type -> type.kind != UNKNOWN_KIND },
        wildcardExtendsBound = extendsBound?.asMetaType()?.takeIf { type -> type.kind != UNKNOWN_KIND }
)

private fun ClassSymbol.asMetaType(): MetaJavaType = type.asMetaType()

private fun ClassSymbol.asMetaClass(): MetaJavaClass = MetaJavaClass(
        type = asMetaType(),
        modifiers = modifiers,
        fields = members().symbols.filterIsInstance<VarSymbol>().associate { symbol -> symbol.name.toString() to symbol.asMetaField() },
        constructors = members().symbols.filterIsInstance<MethodSymbol>()
                .filter { method -> method.isConstructor }
                .map { method -> method.asMetaMethod() },
        methods = members().symbols.filterIsInstance<MethodSymbol>()
                .filter { method -> !method.isConstructor }
                .map { method -> method.asMetaMethod() },
        innerClasses = members().symbols.filterIsInstance<ClassSymbol>().associate { symbol -> symbol.name.toString() to symbol.asMetaClass() }
)

private fun MethodSymbol.asMetaMethod() = MetaJavaMethod(
        name = name.toString(),
        modifiers = modifiers,
        returnType = returnType.asMetaType(),
        parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
        typeParameters = typeParameters.map { typeParameter -> typeParameter.type.asMetaType() }.associateBy { type -> type.typeName },
        exceptions = thrownTypes.map { exception -> exception.asMetaType() }
)

private fun VarSymbol.asMetaField() = MetaJavaField(
        name = name.toString(),
        modifiers = modifiers,
        type = type.asMetaType()
)

private fun VarSymbol.asMetaParameter() = MetaJavaParameter(
        name = name.toString(),
        modifiers = modifiers,
        type = type.asMetaType()
)
