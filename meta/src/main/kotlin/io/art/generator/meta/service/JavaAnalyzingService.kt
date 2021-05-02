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
import io.art.generator.meta.constants.NO_WARN_OPTION
import io.art.generator.meta.constants.PARAMETERS_OPTION
import io.art.generator.meta.extension.isJava
import io.art.generator.meta.logger.LoggingDiagnosticListener
import io.art.generator.meta.model.*
import org.jetbrains.kotlin.javac.JavacOptionsMapper.setUTF8Encoding
import java.io.StringWriter
import java.nio.charset.Charset.defaultCharset
import java.util.Locale.getDefault
import javax.lang.model.element.ElementKind.CLASS
import javax.lang.model.element.TypeElement
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

        val analysisResult = task.analyze().toList()
        if (analysisResult.isEmpty()) return emptySet()

        return analysisResult.filter { input -> input.kind == CLASS }.map { element -> (element as ClassSymbol).asMetaClass() }.toSet()
    }
}

private fun Type.asMetaType() = JavaMetaType(
        element = asElement() as TypeElement,
        fullName = asElement().qualifiedName.toString(),
        className = asElement().simpleName.toString(),
        packageName = asElement().qualifiedName.toString().substringBeforeLast(DOT)
)

private fun ClassSymbol.asMetaType() = type.asMetaType()

private fun ClassSymbol.asMetaClass(): MetaJavaClass = MetaJavaClass(
        symbol = this,
        type = asMetaType(),
        fields = members().symbols.filterIsInstance<VarSymbol>().associate { symbol -> symbol.name.toString() to symbol.asMetaField() },
        constructors = members().symbols.filterIsInstance<MethodSymbol>()
                .filter { method -> method.isConstructor }
                .map { method -> method.asMetaMethod() }
                .toSet(),
        methods = members().symbols.filterIsInstance<MethodSymbol>()
                .filter { method -> !method.isConstructor }
                .map { method -> method.asMetaMethod() }
                .toSet(),
        innerClasses = members().symbols.filterIsInstance<ClassSymbol>().associate { symbol -> symbol.name.toString() to symbol.asMetaClass() }
)

private fun MethodSymbol.asMetaMethod() = MetaJavaMethod(
        name = name.toString(),
        symbol = this,
        returnType = returnType.asMetaType(),
        parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() }
)

private fun VarSymbol.asMetaField() = MetaJavaField(
        name = name.toString(),
        symbol = this,
        type = type.asMetaType()
)

private fun VarSymbol.asMetaParameter() = MetaJavaParameter(
        name = name.toString(),
        symbol = this,
        type = type.asMetaType()
)
