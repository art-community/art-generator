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

package io.art.generator.service

import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorLanguage.JAVA
import io.art.generator.constants.GeneratorLanguage.KOTLIN
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.KOTLIN_LOGGER
import io.art.generator.constants.SOURCES_CHANGED
import io.art.generator.constants.SOURCES_NOT_FOUND
import io.art.generator.detector.SourcesChanges
import io.art.generator.detector.detectChanges
import io.art.generator.templates.metaModuleClassFullName
import io.art.generator.templates.metaModuleClassName
import io.art.scheduler.manager.Scheduling.schedule
import java.nio.file.Path

object SourceWatchingService {
    private data class MetaModuleClassNames(val name: String, val fullName: String)

    fun watchSources(asynchronous: Boolean = true) {
        configuration.sources.forEach { source ->
            val metaModuleClassName = metaModuleClassName(source.module)
            val metaModuleClassFullName = metaModuleClassFullName(source.module)
            val metaModuleClassNames = source
                    .languages
                    .map { language ->
                        if (source.languages.size > 1) {
                            language to MetaModuleClassNames(
                                    name = metaModuleClassName + language.suffix,
                                    fullName = metaModuleClassFullName + language.suffix
                            )
                            return@forEach
                        }
                        language to MetaModuleClassNames(metaModuleClassName, metaModuleClassFullName)
                    }
                    .toMap()
            val excludedClassNames = metaModuleClassNames
                    .map { entry -> entry.value.name }
                    .toSet()

            if (source.languages.contains(JAVA)) {
                detectChanges(source.root, collectJavaSources(source.root, excludedClassNames)).changed {
                    JAVA_LOGGER.info(SOURCES_CHANGED(source.root, modified, deleted))
                    if (asynchronous) {
                        schedule { handleJavaSources(source.root, metaModuleClassNames[JAVA]!!.fullName) }
                        return@changed
                    }
                    handleJavaSources(source.root, metaModuleClassNames[JAVA]!!.name)
                }
            }

            if (source.languages.contains(KOTLIN)) {
                detectChanges(source.root, collectKotlinSources(source.root, excludedClassNames)).changed {
                    KOTLIN_LOGGER.info(SOURCES_CHANGED(source.root, modified, deleted))
                    if (asynchronous) {
                        schedule { handleKotlinSources(source.root, metaModuleClassNames[KOTLIN]!!.fullName) }
                        return@changed
                    }
                    handleKotlinSources(source.root, metaModuleClassNames[KOTLIN]!!.name)
                }
            }
        }
    }

    private fun SourcesChanges.handleJavaSources(path: Path, metaClassName: String) {
        val sources = analyzeJavaSources(path, existed.asSequence(), metaClassName)
        if (sources.isEmpty()) {
            JAVA_LOGGER.info(SOURCES_NOT_FOUND(root))
            return
        }
        generateJavaMetaClasses(path, sources.asSequence(), metaClassName)
    }

    private fun SourcesChanges.handleKotlinSources(path: Path, metaClassName: String) {
        val sources = analyzeKotlinSources(path, metaClassName)
        if (sources.isEmpty()) {
            KOTLIN_LOGGER.info(SOURCES_NOT_FOUND(root))
            return
        }
        generateKotlinMetaClasses(path, sources.asSequence(), metaClassName)
    }
}
