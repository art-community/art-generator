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

package io.art.generator.detector

import io.art.core.extensions.FileExtensions.readFileBytes
import io.art.core.extensions.HashExtensions.xx64
import io.art.generator.service.common.collectJavaSources
import java.nio.file.Path


object JavaSourceChangesDetector {
    private data class Cache(@Volatile var hashes: Map<Path, Long>)

    private val cache = Cache(emptyMap())

    fun detectJavaChanges(root: Path): JavaSourcesChanges {
        val sources = collectJavaSources(root)
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
                root = root,
                existed = existed.keys,
                modified = modified,
                deleted = deleted
        )
    }

    data class JavaSourcesChanges(val root: Path, val existed: Set<Path>, val modified: List<Path>, val deleted: List<Path>) {
        fun changed(action: JavaSourcesChanges.() -> Unit) {
            if (modified.isNotEmpty() || deleted.isNotEmpty()) action(this)
        }
    }
}
