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

package io.art.generator.service

import io.art.core.constants.DateTimeConstants.DEFAULT_FORMATTER
import io.art.core.constants.StringConstants.SHARP
import io.art.core.extensions.FileExtensions.touchDirectory
import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorState
import io.art.generator.constants.GeneratorState.*
import io.art.generator.constants.LOCK_VALIDATION_DURATION
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.LocalDateTime.parse
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object ControllerService {
    fun controllerFileExists() = configuration.controller.exists()

    fun loadState(): GeneratorState {
        if (!controllerFileExists()) return AVAILABLE
        return GeneratorState.valueOf(configuration.controller.readText().split(SHARP)[0])
    }

    fun loadTimeStamp(): LocalDateTime? = loadState()
            .takeIf { state -> state != AVAILABLE }
            ?.let { parse(configuration.controller.readText().split(SHARP)[1], DEFAULT_FORMATTER) }

    fun isAvailable() = loadState() == AVAILABLE

    fun isStopping() = loadState() == STOPPING

    fun isLocked() = loadState() == LOCKED

    fun lockIsValid(): Boolean {
        if (isAvailable()) return true
        return loadTimeStamp()!!.isBefore(now().minus(LOCK_VALIDATION_DURATION))
    }

    fun updateLock() {
        if (!controllerFileExists()) touchDirectory(configuration.controller.parent)
        configuration.controller.writeText("$LOCKED$SHARP${now().format(DEFAULT_FORMATTER)}")
    }

    fun markAvailable() = configuration.controller.writeText("$AVAILABLE")
}
