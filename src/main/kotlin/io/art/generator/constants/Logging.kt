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

package io.art.generator.constants

import io.art.core.constants.StringConstants.NEW_LINE
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.KotlinMetaClass
import io.art.logging.Logging.logger
import io.art.logging.logger.Logger
import java.nio.file.Path

fun Sequence<JavaMetaClass>.javaClassNames() = map { source -> source.type.classFullName!! }.toList()

fun Sequence<KotlinMetaClass>.kotlinClassNames() = map { source -> source.type.classFullName!! }.toList()

val JAVA_LOGGER: Logger = logger(JAVA)

val KOTLIN_LOGGER: Logger = logger(KOTLIN)

val SOURCES_CHANGED = { root: Path, modified: List<Path>, deleted: List<Path> ->
    var message = "Sources changed"
    if (modified.isNotEmpty()) {
        message += "\nModified:\n${modified.relativeNames(root)}"
    }
    if (deleted.isNotEmpty()) {
        message += "\nDeleted:\n${deleted.relativeNames(root)}"
    }
    message
}

val SOURCES_NOT_FOUND = { root: Path -> "Sources not found inside $root" }

val ANALYZING_MESSAGE = { root: Path -> "Analyzing sources inside $root" }

val ANALYZE_COMPLETED = { sources: List<String> -> "Analyze completed. Classes:\n${sources.joinToString(NEW_LINE)}" }

val GENERATING_METAS_MESSAGE = { classes: List<String> -> "Generating meta classes for:\n${classes.joinToString(NEW_LINE)}" }

val GENERATED_MESSAGE = { name: String -> "Generated meta module class: $name" }

private fun Collection<Path>.relativeNames(root: Path) = map { path -> path.toFile().relativeTo(root.toFile()) }.toList().joinToString(NEW_LINE)
