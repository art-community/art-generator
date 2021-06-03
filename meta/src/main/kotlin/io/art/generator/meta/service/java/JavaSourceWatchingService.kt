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

package io.art.generator.meta.service.java

import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.JAVA_LOGGER
import io.art.generator.meta.constants.SOURCES_CHANGED
import io.art.generator.meta.detector.JavaSourceChangesDetector.detectJavaChanges
import io.art.generator.meta.service.java.JavaMetaGenerationService.generateJavaMeta
import io.art.scheduler.manager.Scheduling.schedule
import java.time.LocalDateTime.now
import java.util.concurrent.Future


object JavaSourceWatchingService {
    @Volatile
    private var generate: Future<*>? = null

    fun watchJavaSources() = detectJavaChanges().changed {
        JAVA_LOGGER.info(SOURCES_CHANGED(modified, deleted))
        generate = schedule(now().plusSeconds(configuration.analyzerDelay.seconds)) {
            generate?.cancel(false)
            handle { classes -> generateJavaMeta(classes.asSequence()) }
        }
    }
}
