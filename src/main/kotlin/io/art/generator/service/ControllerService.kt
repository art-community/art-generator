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

private data class ControllerState(
        val state: GeneratorState,
        val timestamp: LocalDateTime? = null,
        val count: Int = 0,
)

object ControllerService {
    fun controllerFileExists() = configuration.controller.exists()

    fun isAvailable() = readState().state == AVAILABLE

    fun isStopping() = readState().state == STOPPING

    fun isLocked() = readState().state == LOCKED

    fun stoppingWaiters(): Int {
        if (!isStopping()) return 0
        return readState().count
    }

    fun lockIsValid(): Boolean {
        if (isAvailable()) return true
        return readState().timestamp!!.isBefore(now().minus(LOCK_VALIDATION_DURATION))
    }

    fun updateLock() = writeState(ControllerState(LOCKED, now()))

    fun incrementStopWaiters() = writeState(ControllerState(STOPPING, now(), 1))

    fun markAvailable() = writeState(ControllerState(AVAILABLE))

    private fun writeState(state: ControllerState) {
        if (!controllerFileExists()) touchDirectory(configuration.controller.parent)
        if (state.state == AVAILABLE) {
            configuration.controller.writeText(AVAILABLE.name)
            return
        }
        state.timestamp?.let { stamp ->
            configuration.controller.writeText("${state.state}$SHARP${stamp.format(DEFAULT_FORMATTER)}${SHARP}${state.count}")
        }
    }

    private fun readState(): ControllerState {
        if (!controllerFileExists()) return ControllerState(state = AVAILABLE)
        val value = configuration.controller.readText().split(SHARP)
        if (value.isEmpty()) return ControllerState(state = AVAILABLE)
        val state = GeneratorState.valueOf(value[0])
        if (state == AVAILABLE) return ControllerState(state = AVAILABLE)
        val timestamp = parse(value[1], DEFAULT_FORMATTER)
        if (value.size == 2) return ControllerState(state = state, timestamp = timestamp)
        return ControllerState(state = state, timestamp = timestamp, count = value[2].toInt())
    }
}
