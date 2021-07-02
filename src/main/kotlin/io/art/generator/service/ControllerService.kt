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

data class ControllerState(
        val state: GeneratorState,
        val timestamp: LocalDateTime,
        val count: Int,
)

object ControllerService {
    fun writeState(state: ControllerState) = configuration.controller.writeText("${state.state}$SHARP${state.timestamp.format(DEFAULT_FORMATTER)}${SHARP}${state.count}")

    fun readState() = configuration.controller.readText()
}
