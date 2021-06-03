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

package io.art.generator.meta.constants

import io.art.generator.meta.configuration.configuration
import io.art.logging.logger.Logger
import io.art.logging.module.LoggingModule
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.relativeTo

val COMMON_LOGGER: Logger = LoggingModule.logger(GENERATOR_NAME)

fun Collection<Path>.names() = map { path -> path.toFile().relativeTo(configuration.sourcesRoot.toFile()) }.toList()

val JAVA_LOGGER: Logger = LoggingModule.logger(JAVA)

val SOURCES_CHANGED = { modified: List<Path>, deleted: List<Path> -> "Sources changed. Modified: ${modified.names()}. Deleted: ${deleted.names()}" }
val CLASSES_CHANGED = { changed: List<Path>, deleted: List<Path> -> "Classes changed. Modified: ${changed.names()}. Deleted: ${deleted.names()}. Process generation" }
val ANALYZING_MESSAGE = { root: Path -> "Analyzing sources inside $root" }
val ANALYZE_COMPLETED = { sources: List<Path> -> "Analyze completed. Sources: ${sources.names()}" }
val GENERATING_METAS_MESSAGE = { classes: List<String> -> "Generating meta classes for: $classes" }
val GENERATED_MESSAGE = { name: String -> "Generated meta class: $name" }

const val CLASSES_NOT_CHANGED = "Classes not changed. Skip Generation"
