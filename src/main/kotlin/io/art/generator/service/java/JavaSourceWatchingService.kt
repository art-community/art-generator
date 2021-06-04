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

package io.art.generator.service.java

import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorLanguages
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.SOURCES_CHANGED
import io.art.generator.detector.JavaSourceChangesDetector.detectJavaChanges
import io.art.generator.service.java.JavaAnalyzingService.analyzeJavaSources
import io.art.generator.service.java.JavaMetaGenerationService.generateJavaMeta


object JavaSourceWatchingService {
    fun watchJavaSources() = configuration.sources[GeneratorLanguages.JAVA]?.forEach { path ->
        detectJavaChanges(path).changed {
            JAVA_LOGGER.info(SOURCES_CHANGED(path, modified, deleted))
            generateJavaMeta(path, analyzeJavaSources(path, existed.asSequence()).values.asSequence())
        }
    }
}
