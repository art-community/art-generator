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

package io.art.generator.service.analyzing

import com.google.devtools.ksp.isInternal
import com.google.devtools.ksp.isLocal
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind.ANNOTATION_CLASS
import com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.Modifier.INNER
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.art.core.constants.StringConstants.DOT
import io.art.core.matcher.PathMatcher.matches
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.ANALYZING_MESSAGE
import io.art.generator.constants.KOTLIN_LOGGER
import io.art.generator.extension.kotlinPath
import io.art.generator.model.KotlinMetaClass
import io.art.generator.parser.KotlinDescriptorParser
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.nio.file.Paths.get
import kotlin.io.path.exists

data class KotlinAnalyzingRequest(
    val configuration: SourceConfiguration,
    val metaClassName: String,
)

object KotlinAnalyzerBuiltins {
    lateinit var builtins: KSBuiltIns
}

fun analyzeKotlinSources(request: KotlinAnalyzingRequest) = KotlinAnalyzingService().analyzeKotlinSources(request)

private class KotlinAnalyzingProcessor(
    private val request: KotlinAnalyzingRequest,
    private val processor: (files: List<KotlinMetaClass>) -> Unit
) : SymbolProcessor, KotlinDescriptorParser() {
    var invoked = false
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()
        val resolved = resolver.getAllFiles().toSet()
        KotlinAnalyzerBuiltins.builtins = resolver.builtIns
        request.configuration.root.toFile().listFiles()!!
            .asSequence()
            .map { file -> file.name }
            .flatMap { packageName -> collectClasses(resolved, packageName) }
            .filter { descriptor -> !descriptor.containingFile?.fileName?.kotlinPath.isNullOrBlank() }
            .filter { descriptor -> descriptor.included(request) }
            .filter { descriptor -> descriptor.qualifiedName?.asString() != request.metaClassName }
            .filter { descriptor -> descriptor.classKind != ENUM_ENTRY }
            .filter { descriptor -> descriptor.classKind != ANNOTATION_CLASS }
            .filter { descriptor -> !descriptor.modifiers.contains(INNER) }
            .filter { descriptor -> !descriptor.isLocal() }
            .filter { descriptor -> !descriptor.isInternal() }
            .filter { descriptor -> descriptor.parentDeclaration !is KSClassDeclaration }
            .filter { descriptor -> descriptor.typeParameters.isEmpty() }
            .toList()
            .map { descriptor -> descriptor.asMetaClass() }
            .distinctBy { metaClass -> metaClass.type.typeName }
            .toList()
            .let(processor)
        invoked = true
        return emptyList()
    }

    private fun KSClassDeclaration.included(request: KotlinAnalyzingRequest) = get(containingFile!!.filePath)
        .let { path ->
            path.startsWith(request.configuration.root)
                    && request.configuration.exclusions.none { exclusion -> matches(exclusion.kotlinPath, path) }
                    && (request.configuration.inclusions.isEmpty() || request.configuration.inclusions.any { exclusion -> matches(exclusion.kotlinPath, path) })
        }

    private fun collectClasses(processed: Set<KSFile>, packageName: String): List<KSClassDeclaration> = processed
        .filter { declaration -> declaration.packageName.asString().substringBefore(DOT) == packageName }
        .flatMap { declaration -> declaration.declarations }
        .filterIsInstance<KSClassDeclaration>()
        .toList()
}

private class KotlinAnalyzingProcessorProvider(
    private val request: KotlinAnalyzingRequest,
    private val processor: (files: List<KotlinMetaClass>) -> Unit
) : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = KotlinAnalyzingProcessor(request, processor)
}

@OptIn(ExperimentalCompilerApi::class)
private class KotlinAnalyzingService {
    fun analyzeKotlinSources(request: KotlinAnalyzingRequest): List<KotlinMetaClass> {
        KOTLIN_LOGGER.info(ANALYZING_MESSAGE(request.configuration.root))
        val roots = request.configuration.sources.toSet()
        val processed = mutableListOf<KotlinMetaClass>()
        KotlinCompilation().apply {
            sources = roots.filter { path -> path.exists() }
                .flatMap { path -> path.toFile().walkTopDown().toList() }
                .filter { path -> path.isFile }
                .map { path -> SourceFile.fromPath(path, false) }
            symbolProcessorProviders = listOf(KotlinAnalyzingProcessorProvider(request, processed::addAll))
            compile()
        }
        return processed
    }
}
