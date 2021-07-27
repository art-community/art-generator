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

package io.art.generator.service.common

import io.art.generator.configuration.SourceConfiguration
import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorLanguage.JAVA
import io.art.generator.constants.GeneratorLanguage.KOTLIN
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.KOTLIN_LOGGER
import io.art.generator.constants.SOURCES_CHANGED
import io.art.generator.constants.SOURCES_NOT_FOUND
import io.art.generator.detector.SourcesChanges
import io.art.generator.detector.detectChanges
import io.art.generator.extension.metaPackage
import io.art.generator.extension.metaPath
import io.art.generator.extension.normalizeToClassSuffix
import io.art.generator.service.analyzing.JavaAnalyzingRequest
import io.art.generator.service.analyzing.KotlinAnalyzingRequest
import io.art.generator.service.analyzing.analyzeJavaSources
import io.art.generator.service.analyzing.analyzeKotlinSources
import io.art.generator.service.generation.generateJavaMetaClasses
import io.art.generator.service.generation.generateKotlinMetaClasses
import io.art.generator.templates.metaModuleClassFullName
import io.art.generator.templates.metaModuleClassName
import io.art.scheduler.Scheduling.schedule

object SourceWatchingService {
    private data class MetaModuleClassNames(val name: String, val fullName: String)

    fun watchSources(asynchronous: Boolean = true) {
        val sourcesByModule = configuration.sources.groupBy { source -> source.module }
        configuration.sources.forEach { source ->
            val metaModuleClassName = metaModuleClassName(source.module)
            val metaModuleClassFullName = metaModuleClassFullName(source.metaPackage, source.module)
            val metaModuleClassNames = source.languages.associate { language ->
                if (sourcesByModule[source.module]!!.size > 1) {
                    if (source.languages.size > 1) {
                        return@associate language to MetaModuleClassNames(
                                name = metaModuleClassName + source.root.toFile().name.normalizeToClassSuffix() + language.suffix,
                                fullName = metaModuleClassFullName + source.root.toFile().name.normalizeToClassSuffix() + language.suffix
                        )
                    }
                    return@associate language to MetaModuleClassNames(
                            name = metaModuleClassName + source.root.toFile().name.normalizeToClassSuffix(),
                            fullName = metaModuleClassFullName + source.root.toFile().name.normalizeToClassSuffix()
                    )
                }
                language to MetaModuleClassNames(metaModuleClassName, metaModuleClassFullName)
            }

            val excludedClassNames = metaModuleClassNames
                    .map { entry -> entry.value.name }
                    .toSet()

            if (source.languages.contains(JAVA)) {
                detectChanges(JAVA, source.root, collectJavaSources(source.root, source.metaPath, excludedClassNames)).changed {
                    JAVA_LOGGER.info(SOURCES_CHANGED(source.root, modified, deleted))
                    if (asynchronous) {
                        schedule { handleJavaSources(source, metaModuleClassNames[JAVA]!!) }
                        return@changed
                    }
                    handleJavaSources(source, metaModuleClassNames[JAVA]!!)
                }
            }

            if (source.languages.contains(KOTLIN)) {
                detectChanges(KOTLIN, source.root, collectKotlinSources(source.root, source.metaPath, excludedClassNames)).changed {
                    KOTLIN_LOGGER.info(SOURCES_CHANGED(source.root, modified, deleted))
                    if (asynchronous) {
                        schedule { handleKotlinSources(source, metaModuleClassNames[KOTLIN]!!) }
                        return@changed
                    }
                    handleKotlinSources(source, metaModuleClassNames[KOTLIN]!!)
                }
            }
        }
    }

    private fun SourcesChanges.handleJavaSources(sourceConfiguration: SourceConfiguration, metaClassName: MetaModuleClassNames) {
        val request = JavaAnalyzingRequest(sourceConfiguration, metaClassName.fullName)
        val sources = analyzeJavaSources(request)
        if (sources.isEmpty()) {
            JAVA_LOGGER.info(SOURCES_NOT_FOUND(root))
            return
        }
        generateJavaMetaClasses(sourceConfiguration, sources.asSequence(), metaClassName.name)
    }

    private fun SourcesChanges.handleKotlinSources(sourceConfiguration: SourceConfiguration, metaClassName: MetaModuleClassNames) {
        val request = KotlinAnalyzingRequest(sourceConfiguration, metaClassName.fullName)
        val sources = analyzeKotlinSources(request)
        if (sources.isEmpty()) {
            KOTLIN_LOGGER.info(SOURCES_NOT_FOUND(root))
            return
        }
        generateKotlinMetaClasses(sourceConfiguration, sources.asSequence(), metaClassName.name)
    }
}
