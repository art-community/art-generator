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

package io.art.generator.service.analyzing

import io.art.core.matcher.PathMatcher.matches
import io.art.core.wrapper.ExceptionWrapper.ignoreException
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.ANALYZING_MESSAGE
import io.art.generator.constants.KOTLIN_LOGGER
import io.art.generator.extension.kotlinPath
import io.art.generator.model.KotlinMetaClass
import io.art.generator.parser.KotlinDescriptorParser
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassKind.ANNOTATION_CLASS
import org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
import org.jetbrains.kotlin.descriptors.SourceFile.NO_SOURCE_FILE
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement
import java.nio.file.Paths.get

data class KotlinAnalyzingRequest(
        val configuration: SourceConfiguration,
        val metaClassName: String,
)

fun analyzeKotlinSources(request: KotlinAnalyzingRequest) = KotlinAnalyzingService().analyzeKotlinSources(request)

private class KotlinAnalyzingService : KotlinDescriptorParser() {
    fun analyzeKotlinSources(request: KotlinAnalyzingRequest): List<KotlinMetaClass> {
        KOTLIN_LOGGER.info(ANALYZING_MESSAGE(request.configuration.root))

        val roots = request.configuration.sources.toSet()

        val analysisResult = useKotlinCompiler(KotlinCompilerConfiguration(roots, request.configuration.classpath), KotlinToJVMBytecodeCompiler::analyze)
                ?.apply { ignoreException(this::throwIfError, KOTLIN_LOGGER::error) }
                ?: return emptyList()

        return request.configuration.root.toFile().listFiles()!!
                .map { file -> file.name }
                .flatMap { packageName -> collectClasses(analysisResult, packageName) }
                .asSequence()
                .filter { descriptor -> descriptor.source.containingFile != NO_SOURCE_FILE }
                .filter { descriptor -> !descriptor.source.containingFile.name.isNullOrBlank() }
                .filter { descriptor -> descriptor.source is KotlinSourceElement }
                .filter { descriptor -> descriptor.included(request) }
                .filter { descriptor -> descriptor.defaultType.resolved() }
                .filter { descriptor -> descriptor.classId?.asSingleFqName()?.asString() != request.metaClassName }
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                .filter { descriptor -> !descriptor.isInner }
                .filter { descriptor -> !descriptor.classId!!.isNestedClass }
                .filter { descriptor -> !descriptor.classId!!.isLocal }
                .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                .map { descriptor -> descriptor.asMetaClass() }
                .distinctBy { metaClass -> metaClass.type.typeName }
                .toList()
    }

    private fun ClassDescriptor.included(request: KotlinAnalyzingRequest) = get((source as KotlinSourceElement).psi.containingKtFile.virtualFilePath)
            .let { path ->
                path.startsWith(request.configuration.root)
                        && request.configuration.exclusions.none { exclusion -> matches(exclusion.kotlinPath, path) }
                        && (request.configuration.inclusions.isEmpty() || request.configuration.inclusions.any { exclusion -> matches(exclusion.kotlinPath, path) })
            }

    private fun collectClasses(analysisResult: AnalysisResult, packageName: String): List<ClassDescriptor> {
        val name = FqName(packageName)
        val packageView = analysisResult.moduleDescriptor.getPackage(name).memberScope
        val classes = getAllDescriptors(packageView)
                .filterIsInstance<ClassDescriptor>()
                .filter { descriptor -> descriptor !is JavaClassDescriptor }
        return classes + analysisResult.moduleDescriptor
                .getSubPackagesOf(name) { true }
                .flatMap { nested -> collectClasses(analysisResult, nested.asString()) }
    }
}
