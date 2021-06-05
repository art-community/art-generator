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

package io.art.generator.service.common

import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorLanguages.JAVA
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.SOURCES_CHANGED
import io.art.generator.detector.detectChanges
import io.art.generator.service.java.JavaMetaGenerationService.generateJavaMeta
import io.art.generator.service.java.analyzeJavaSources
import io.art.scheduler.manager.Scheduling.schedule


object SourceWatchingService {
    fun watchSources(asynchronous: Boolean = true) {
        configuration.sources[JAVA]?.forEach { path ->
            detectChanges(path, collectJavaSources(path)).changed {
                JAVA_LOGGER.info(SOURCES_CHANGED(path, modified, deleted))
                if (asynchronous) {
                    schedule {
                        val sources = analyzeJavaSources(path, existed.asSequence())
                        if (sources.isEmpty()) return@schedule
                        generateJavaMeta(path, sources.values.asSequence())
                    }
                    return@changed
                }
                val sources = analyzeJavaSources(path, existed.asSequence())
                if (sources.isEmpty()) return@changed
                generateJavaMeta(path, sources.values.asSequence())
            }
        }
    }
}
