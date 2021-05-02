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
import io.art.generator.meta.extension.path
import java.io.InputStream
import java.nio.file.Path
import java.time.Duration


data class Configuration(val sourcesRoot: Set<Path>,
                         val stubRoot: Path,
                         val sources: Set<Path>,
                         val classpath: Set<Path>,
                         val moduleName: String,
                         val watcherPeriod: Duration
)

lateinit var generatorConfiguration: Configuration

fun loadConfiguration(stream: InputStream) {
    with(YamlConfigurationSource(EMPTY_STRING, CUSTOM_FILE) { stream }) {
        generatorConfiguration = Configuration(
                sourcesRoot = getStringArray("paths.sources").map { file -> file.path }.toSet(),
                stubRoot = getString("paths.stubs").path,
                sources = getStringArray("sources").map { file -> file.path }.toSet(),
                classpath = getStringArray("classpath").map { file -> file.path }.toSet(),
                moduleName = getString("moduleName"),
                watcherPeriod = getDuration("watcher.period")
        )
    }
}
