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

package io.art.generator.meta.configuration

import io.art.configuration.yaml.source.YamlConfigurationSource
import io.art.configurator.constants.ConfiguratorModuleConstants.ConfigurationSourceType.CUSTOM_FILE
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.FileExtensions.fileInputStream
import io.art.generator.meta.extension.path
import io.art.logging.LoggingModule.logger
import java.nio.file.Path
import java.time.Duration


data class Configuration(val sourcesRoot: Set<Path>,
                         val stubRoot: Set<Path>,
                         val sources: Set<Path>,
                         val classpath: Set<Path>,
                         val watcherPeriod: Duration
)

lateinit var generatorConfiguration: Configuration

fun loadConfiguration(file: Path) {
    logger(Configuration::class.java).info("Loading configuration from: $file")
    with(YamlConfigurationSource(EMPTY_STRING, CUSTOM_FILE) { fileInputStream(file) }) {
        generatorConfiguration = Configuration(
                sourcesRoot = getStringArray("paths.sources").map { file -> file.path }.toSet(),
                stubRoot = getStringArray("paths.stubs").map { file -> file.path }.toSet(),
                sources = getStringArray("sources").map { file -> file.path }.toSet(),
                classpath = getStringArray("classpath").map { file -> file.path }.toSet(),
                watcherPeriod = getDuration("watcher.period")
        )
    }
}
