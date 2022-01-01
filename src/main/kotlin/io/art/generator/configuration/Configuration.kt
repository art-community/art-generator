/*
 * ART
 *
 * Copyright 2019-2022 ART
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

import io.art.configurator.Configuring.configuration
import io.art.core.constants.StringConstants.COLON
import io.art.core.constants.StringConstants.SEMICOLON
import io.art.core.determiner.SystemDeterminer.isWindows
import io.art.generator.constants.*
import java.nio.file.Path
import java.nio.file.Paths.get

data class SourceConfiguration(
        val languages: Set<GeneratorLanguage>,
        val root: Path,
        val sources: Set<Path>,
        val inclusions: Set<String>,
        val exclusions: Set<String>,
        val module: String,
        val `package`: String,
        val classpath: Set<Path>,
)

data class Configuration(
        val sources: Set<SourceConfiguration>,
        val controller: Path,
)

lateinit var configuration: Configuration
    private set

fun reconfigure() {
    configuration = load()
}

private fun load() = with(configuration().apply { refresh() }) {
    validate(SOURCES_KEY)
    Configuration(
            sources = getNestedArray(SOURCES_KEY) { source ->
                SourceConfiguration(
                        languages = source
                                .validate(LANGUAGES_KEY)
                                .getStringArray(LANGUAGES_KEY)
                                .map { language -> GeneratorLanguage.valueOf(language.uppercase()) }
                                .toSet(),
                        inclusions = source
                                .validate(LANGUAGES_KEY)
                                .getStringArray(INCLUSIONS_KEY).toSet(),
                        exclusions = source
                                .validate(EXCLUSIONS_KEY)
                                .getStringArray(EXCLUSIONS_KEY).toSet(),
                        classpath = source
                                .validate(CLASSPATH_KEY)
                                .getString(CLASSPATH_KEY)
                                .split(if (isWindows()) SEMICOLON else COLON)
                                .map { path -> get(path) }
                                .toSet(),
                        sources = source
                                .validate(SOURCES_KEY)
                                .getString(SOURCES_KEY)
                                .split(if (isWindows()) SEMICOLON else COLON)
                                .map { path -> get(path) }
                                .toSet(),
                        root = get(source.validate(ROOT_KEY).getString(ROOT_KEY)),
                        module = source.validate(MODULE_KEY).getString(MODULE_KEY),
                        `package` = source.validate(PACKAGE_KEY).getString(PACKAGE_KEY)
                )
            }.toSet(),
            controller = get(validate(CONTROLLER_KEY).getString(CONTROLLER_KEY))
    )
}
