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
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.ClassKind.ANNOTATION_CLASS
import com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY
import com.google.devtools.ksp.symbol.Modifier.*
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.art.core.matcher.PathMatcher.matches
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.ANALYZING_MESSAGE
import io.art.generator.constants.KOTLIN_LOGGER
import io.art.generator.extension.kotlinPath
import io.art.generator.model.KotlinMetaClass
import io.art.generator.parser.KotlinDescriptorParser
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.nio.file.Paths.get

data class KotlinAnalyzingRequest(
    val configuration: SourceConfiguration,
    val metaClassName: String,
)

object KotlinAnalyzerBuiltins {
    lateinit var builtins: KSBuiltIns
}

fun analyzeKotlinSources(request: KotlinAnalyzingRequest) = KotlinAnalyzingService().analyzeKotlinSources(request)

private class KotlinAnalyzingProcessor(private val processor: (files: List<KSFile>) -> Unit) : SymbolProcessor {
    var invoked = false
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()
        println("test")
        processor(resolver.getAllFiles().toList())
        invoked = true
        KotlinAnalyzerBuiltins.builtins = resolver.builtIns
        return emptyList()
    }
}

private class KotlinAnalyzingProcessorProvider(private val processor: (files: List<KSFile>) -> Unit) : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = KotlinAnalyzingProcessor(processor)
}

@OptIn(ExperimentalCompilerApi::class)
private class KotlinAnalyzingService : KotlinDescriptorParser() {
    fun analyzeKotlinSources(request: KotlinAnalyzingRequest): List<KotlinMetaClass> {
        KOTLIN_LOGGER.info(ANALYZING_MESSAGE(request.configuration.root))
        val roots = request.configuration.sources.toSet()
        val processed = mutableSetOf<KSFile>()
        KotlinCompilation().apply {
            sources = roots.map { path -> SourceFile.fromPath(path.toFile(), false) }
            symbolProcessorProviders = listOf(KotlinAnalyzingProcessorProvider(processed::addAll))
            compile()
        }
        return request.configuration.root.toFile().listFiles()!!
            .map { file -> file.name }
            .flatMap { packageName -> collectClasses(processed, packageName) }
            .asSequence()
            .filter { descriptor -> !descriptor.containingFile?.fileName?.kotlinPath.isNullOrBlank() }
            .filter { descriptor -> descriptor.included(request) }
            .filter { descriptor -> descriptor.qualifiedName?.asString() != request.metaClassName }
            .filter { descriptor -> descriptor.classKind != ENUM_ENTRY }
            .filter { descriptor -> descriptor.classKind != ANNOTATION_CLASS }
            .filter { descriptor -> !descriptor.modifiers.contains(INNER) }
            .filter { descriptor -> !descriptor.isLocal() }
            .filter { descriptor -> !descriptor.isInternal() }
            .filter { descriptor -> descriptor.parentDeclaration !is KSClassDeclaration }
            .filter { descriptor -> descriptor.primaryConstructor?.parameters?.isEmpty() == true }
            .map { descriptor -> descriptor.asMetaClass() }
            .distinctBy { metaClass -> metaClass.type.typeName }
            .toList()
    }

    private fun KSClassDeclaration.included(request: KotlinAnalyzingRequest) = get(containingFile!!.filePath)
        .let { path ->
            path.startsWith(request.configuration.root)
                    && request.configuration.exclusions.none { exclusion -> matches(exclusion.kotlinPath, path) }
                    && (request.configuration.inclusions.isEmpty() || request.configuration.inclusions.any { exclusion -> matches(exclusion.kotlinPath, path) })
        }

    private fun collectClasses(processed: Set<KSFile>, packageName: String): List<KSClassDeclaration> = processed
        .filter { declaration -> declaration.packageName.asString() == packageName }
        .flatMap { declaration -> declaration.declarations }
        .filterIsInstance<KSClassDeclaration>()
        .toList()
}
