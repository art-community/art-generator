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

import io.art.configurator.module.ConfiguratorModule.configuration
import io.art.generator.meta.extension.path
import java.nio.file.Path
import java.time.Duration


data class Configuration(
        val configurationPath: String,
        val sourcesRoot: Path,
        val sources: Set<Path>,
        val classpath: Set<Path>,
        val moduleName: String,
        val watcherPeriod: Duration,
        val analyzerDelay: Duration,
)

val configuration: Configuration by lazy {
    with(configuration()) {
        Configuration(
                configurationPath = path,
                sourcesRoot = getString("paths.sources").path,
                sources = getStringArray("sources").map { file -> file.path }.toSet(),
                classpath = getStringArray("classpath").map { file -> file.path }.toSet(),
                moduleName = getString("module.name"),
                watcherPeriod = getDuration("watcher.period"),
                analyzerDelay = getDuration("analyzer.delay")
        )
    }
}
