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
import io.art.core.extensions.FileExtensions.readFileBytes
import io.art.core.extensions.HashExtensions.xx64
import io.art.generator.meta.constants.CLASSES_CHANGED
import io.art.generator.meta.constants.CLASSES_NOT_CHANGED
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.constants.META_NAME
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.service.JavaAnalyzingService.analyzeJavaSources
import java.nio.file.Path


object JavaSourceChangesDetector {
    private data class Cache(
            @Volatile var hashes: Map<Path, Long>,
            @Volatile var classes: Set<JavaMetaClass>
    )

    private val cache = Cache(emptyMap(), emptySet())

    fun detectJavaChanges(): JavaSourcesChanges {
        val sources = collectJavaSources()
        val existed = cache.hashes.filterKeys(sources::contains).toMutableMap()
        val changed = mutableListOf<Path>()
        sources.forEach { source ->
            val currentModified = existed[source]
            val newModified = xx64(readFileBytes(source))
            if (currentModified != newModified) {
                changed.add(source)
            }
            existed[source] = newModified
        }
        cache.hashes = existed
        return JavaSourcesChanges(existed.keys, changed)
    }

    data class JavaSourcesChanges(val existed: Set<Path>, val changed: List<Path>) {
        fun changed(action: JavaSourcesChanges.() -> Unit) {
            if (changed.isNotEmpty()) action(this)
        }

        fun handle(handler: (Set<JavaMetaClass>) -> Unit) = analyzeJavaSources(existed.asSequence()).apply {
            filterValues { javaClass -> javaClass.type.classPackageName?.substringAfterLast(DOT) != META_NAME }
            filterValues { javaClass -> !cache.classes.contains(javaClass) }.ifEmpty {
                JAVA_LOGGER.info(CLASSES_NOT_CHANGED)
                return@apply
            }
            JAVA_LOGGER.info(CLASSES_CHANGED)
            cache.classes = values.toSet().apply(handler)
        }
    }
}
