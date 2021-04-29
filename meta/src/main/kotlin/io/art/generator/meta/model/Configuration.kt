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

package io.art.generator.meta.model

import io.art.configuration.yaml.source.YamlConfigurationSource
import io.art.configurator.constants.ConfiguratorModuleConstants.ConfigurationSourceType.CUSTOM_FILE
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.FileExtensions.fileInputStream
import io.art.generator.meta.exception.cached
import io.art.generator.meta.extension.path
import java.nio.file.Path
import java.time.Duration

data class Configuration(val sourcesDirectory: Path,
                         val buildDirectory: Path,
                         val modulePackagePath: Path,
                         val compilationClasspath: String,
                         val watcherPeriod: Duration
)

fun parse(file: Path) = with(YamlConfigurationSource(EMPTY_STRING, CUSTOM_FILE) { fileInputStream(file) }) {
    cached("Configuration invalid") {
        Configuration(sourcesDirectory = requireNotNull("sourcesDirectory".let(::getString)?.path) { "sourcesDirectory" },
                buildDirectory = requireNotNull("sourcesDirectory".let(::getString)?.path) { "buildDirectory" },
                modulePackagePath = requireNotNull("sourcesDirectory".let(::getString)?.path) { "modulePackagePath" },
                compilationClasspath = getString("modulePackagePath"),
                watcherPeriod = getDuration("watcherPeriod"))
    }
}
