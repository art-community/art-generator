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

import io.art.generator.model.JavaMetaClass
import io.art.logging.logger.Logger
import io.art.logging.module.LoggingModule
import java.nio.file.Path

val COMMON_LOGGER: Logger = LoggingModule.logger(GENERATOR_NAME)

fun Collection<Path>.relativeNames(root: Path) = map { path -> path.toFile().relativeTo(root.toFile()) }.toList()

fun Sequence<JavaMetaClass>.javaClassNames() = map { source -> source.type.classFullName!! }.toList()

val JAVA_LOGGER: Logger = LoggingModule.logger(JAVA)
val KOTLIN_LOGGER: Logger = LoggingModule.logger(KOTLIN)

val SOURCES_CHANGED = { root: Path, modified: List<Path>, deleted: List<Path> -> "Sources changed\nModified: ${modified.relativeNames(root)}\nDeleted: ${deleted.relativeNames(root)}" }
val SOURCES_NOT_FOUND = { root: Path -> "Sources not found inside $root" }
val ANALYZING_MESSAGE = { root: Path -> "Analyzing sources inside $root" }
val ANALYZE_COMPLETED = { root: Path, sources: List<Path> -> "Analyze completed\nSources: ${sources.relativeNames(root)}" }
val GENERATING_METAS_MESSAGE = { classes: Sequence<JavaMetaClass> -> "Generating meta classes\nSources: ${classes.javaClassNames()}" }
val GENERATED_MESSAGE = { name: String -> "Generated meta class: $name" }
