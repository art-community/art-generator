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

package io.art.generator.meta.service

import io.art.core.extensions.HashExtensions.md5
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.GENERATION_TRIGGERED
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.service.JavaAnalyzingService.analyzeJavaSources
import io.art.generator.meta.service.JavaMetaGenerationService.generateJavaMeta
import io.art.scheduler.manager.SchedulersManager.schedule
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime.now

object SourceWatchingService {
    private var state = mapOf<Path, ByteArray>()

    fun watchJavaSources() = collectJavaChanges().changed {
        JAVA_LOGGER.info(GENERATION_TRIGGERED)
        val triggerTime = now().plusSeconds(configuration.analyzerDelay.toSeconds())
        schedule(::handle, triggerTime)
    }

    private fun collectJavaChanges(): JavaSourcesChanges {
        val sources = collectJavaSources()
        val existed = state.filterKeys(sources::contains).toMutableMap()
        val changed = mutableListOf<Path>()
        sources.forEach { source ->
            val currentModified = existed[source]
            val newModified = md5(source)
            if (!MessageDigest.isEqual(currentModified, newModified)) {
                changed.add(source)
            }
            existed[source] = newModified
        }
        state = existed
        return JavaSourcesChanges(existed.keys, changed)
    }

    private data class JavaSourcesChanges(val existed: Set<Path>, val changed: List<Path>) {
        fun changed(action: JavaSourcesChanges.() -> Unit) {
            if (changed.isNotEmpty()) action(this)
        }

        fun handle() = analyzeJavaSources(existed.asSequence())
                .success { changes ->
                    changes.classes
                            .values
                            .asSequence()
                            .let(::generateJavaMeta)
                }
                .success { changes ->
                    changes.classes
                            .asSequence()
                            .filter { entry -> existed.contains(entry.key) }
                            .map { entry -> entry.value }
                            .let(::generateJavaStubs)
                }
    }
}
