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

package io.art.generator.configuration

import io.art.configurator.module.ConfiguratorModule.configuration
import io.art.core.constants.StringConstants.COLON
import io.art.core.constants.StringConstants.SEMICOLON
import io.art.core.determiner.SystemDeterminer.isWindows
import io.art.generator.constants.GeneratorLanguage
import java.nio.file.Path
import java.nio.file.Paths.get
import java.time.Duration
import java.time.Duration.ofMillis

data class SourceConfiguration(
        val languages: Set<GeneratorLanguage>,
        val root: Path,
        val module: String,
        val classpath: Set<Path>,
)

data class Configuration(
        val sources: Set<SourceConfiguration>,
        val watcherPeriod: Duration,
        val lock: Path,
)

lateinit var configuration: Configuration
    private set

fun reconfigure() {
    configuration = load()
}

private fun load() = with(configuration().apply { refresh() }) {
    Configuration(
            sources = getNestedArray("sources") { source ->
                SourceConfiguration(
                        languages = source
                                .getStringArray("languages")
                                .map { language -> GeneratorLanguage.valueOf(language.uppercase()) }
                                .toSet(),
                        classpath = source.getString("classpath")
                                .split(if (isWindows()) SEMICOLON else COLON)
                                .map { path -> get(path) }
                                .toSet(),
                        root = get(source.getString("root")),
                        module = source.getString("module")
                )
            }.toSet(),
            lock = get(getString("lock")),
            watcherPeriod = ofMillis(getLong("watcher.period"))
    )
}
