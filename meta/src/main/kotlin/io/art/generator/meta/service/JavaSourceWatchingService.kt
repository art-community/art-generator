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

import io.art.core.constants.StringConstants.DOT
import io.art.core.extensions.HashExtensions.md5
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.*
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.service.JavaAnalyzingService.analyzeJavaSources
import io.art.generator.meta.service.JavaMetaGenerationService.generateJavaMeta
import io.art.generator.meta.service.JavaStubGenerationService.generateJavaStubs
import io.art.scheduler.manager.SchedulersManager.schedule
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime.now
import java.util.concurrent.ForkJoinTask


object JavaSourceWatchingService {
    @Volatile
    private var lastTask: ForkJoinTask<*>? = null

    private val cache = Cache(emptyMap(), emptySet())

    fun watchJavaSources() = collectChanges().changed {
        JAVA_LOGGER.info(SOURCES_CHANGED)
        val triggerTime = now().plusSeconds(configuration.analyzerDelay.seconds)
        lastTask?.cancel(false)
        lastTask = schedule(::handle, triggerTime)
    }

    private fun collectChanges(): SourcesChanges {
        val sources = collectJavaSources().filter { path -> path.parent.toFile().name != META_PACKAGE }
        val existed = cache.files.filterKeys(sources::contains).toMutableMap()
        val changed = mutableListOf<Path>()
        sources.forEach { source ->
            val currentModified = existed[source]
            val newModified = md5(source)
            if (!MessageDigest.isEqual(currentModified, newModified)) {
                changed.add(source)
            }
            existed[source] = newModified
        }
        cache.files = existed
        return SourcesChanges(existed.keys, changed)
    }

    private data class Cache(
            @Volatile var files: Map<Path, ByteArray>,
            @Volatile var classes: Set<JavaMetaClass>
    )

    private data class SourcesChanges(val existed: Set<Path>, val changed: List<Path>) {
        fun changed(action: SourcesChanges.() -> Unit) {
            if (changed.isNotEmpty()) action(this)
        }

        fun handle() = analyzeJavaSources(existed.asSequence()).apply {
            filterValues { javaClass ->
                javaClass.type.classPackageName?.substringAfterLast(DOT) != META_PACKAGE && !cache.classes.contains(javaClass)
            }.ifEmpty {
                JAVA_LOGGER.info(CLASSES_NOT_CHANGED)
                return@apply
            }

            JAVA_LOGGER.info(CLASSES_CHANGED)
            cache.classes = values.toSet()
            cache.classes
                    .asSequence()
                    .apply(::generateJavaMeta)
                    .filter { javaClass -> existed.contains(javaClass.source) }
                    .apply(::generateJavaStubs)
        }
    }
}
