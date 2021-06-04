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

package io.art.generator.meta.detector

import io.art.core.extensions.FileExtensions.readFileBytes
import io.art.core.extensions.HashExtensions.xx64
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.CLASSES_CHANGED
import io.art.generator.meta.constants.CLASSES_NOT_CHANGED
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.service.common.collectJavaSources
import io.art.generator.meta.service.java.JavaAnalyzingService.analyzeJavaSources
import io.art.generator.meta.templates.metaModuleClassFullName
import java.nio.file.Path


object JavaSourceChangesDetector {
    private data class Cache(
            @Volatile var hashes: Map<Path, Long>,
            @Volatile var classes: Map<Path, JavaMetaClass>
    )

    private val cache = Cache(emptyMap(), emptyMap())

    fun detectJavaChanges(): JavaSourcesChanges {
        val sources = collectJavaSources()
        val deleted = cache.hashes.keys.filter { source -> !sources.contains(source) }
        val existed = cache.hashes.filterKeys(sources::contains).toMutableMap()
        val modified = mutableListOf<Path>()
        sources.forEach { source ->
            val currentModified = existed[source]
            val newModified = xx64(readFileBytes(source))
            if (currentModified != newModified) modified.add(source)
            existed[source] = newModified
        }
        cache.hashes = existed
        return JavaSourcesChanges(
                existed = existed.keys,
                modified = modified,
                deleted = deleted
        )
    }

    data class JavaSourcesChanges(val existed: Set<Path>, val modified: List<Path>, val deleted: List<Path>) {
        fun changed(action: JavaSourcesChanges.() -> Unit) {
            if (modified.isNotEmpty() || deleted.isNotEmpty()) action(this)
        }

        fun handle(handler: (Set<JavaMetaClass>) -> Unit) {
            val sources = analyzeJavaSources(existed.asSequence()).filter { source ->
                source.value.type.className != metaModuleClassFullName(configuration.moduleName)
            }
            if (deleted.isEmpty() && sources.isEmpty()) {
                JAVA_LOGGER.info(CLASSES_NOT_CHANGED)
                return
            }
            val changed = sources.filterValues { source -> !cache.classes.filterKeys { path -> !deleted.contains(path) }.containsValue(source) }
            if (deleted.isEmpty() && changed.isEmpty()) {
                JAVA_LOGGER.info(CLASSES_NOT_CHANGED)
                return
            }
            JAVA_LOGGER.info(CLASSES_CHANGED(changed.keys.toList(), deleted))
            handler(sources.values.toSet())
            cache.classes = changed
        }
    }
}
