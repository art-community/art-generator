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

data class SourceSet(
        val languages: Set<GeneratorLanguage>,
        val root: Path,
        val module: String,
)

data class Configuration(
        val sources: Set<SourceSet>,
        val classpath: Set<Path>,
        val watcherPeriod: Duration,
)

val configuration: Configuration by lazy {
    with(configuration()) {
        Configuration(
                sources = getNestedArray("sources") { source ->
                    SourceSet(
                            languages = source.getStringArray("language").map { language -> GeneratorLanguage.valueOf(language.uppercase()) }.toSet(),
                            root = get(source.getString("path")),
                            module = getString("module"),
                    )
                }.toSet(),
                classpath = getString("classpath").split(if (isWindows()) SEMICOLON else COLON).map { path -> get(path) }.toSet(),
                watcherPeriod = ofMillis(getLong("watcher.period"))
        )
    }
}
