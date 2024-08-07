/*
 * ART
 *
 * Copyright 2019-2022 ART
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

package io.art.generator.logging

import io.art.generator.constants.KOTLIN_LOGGER
import io.art.logging.logger.Logger
import io.art.logging.stream.LoggerPrintStream
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.WITHOUT_PATHS
import java.io.PrintStream


class LoggingMessageCollector(
        private val logger: Logger = KOTLIN_LOGGER,
        private val stream: PrintStream = LoggerPrintStream(logger, Logger::trace),
        private val printer: MessageCollector = object : MessageCollector {
            override fun clear() {}

            // Important! This function used by Kotlin Compiler for checking analyzing status.
            // In case of hasErrors() == true analyze result will be null and we will not be able to continue generation
            override fun hasErrors(): Boolean = false

            override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageSourceLocation?) =
                    stream.println(WITHOUT_PATHS.render(severity, message, location))
        },
) : MessageCollector by printer

val loggingMessageCollector = LoggingMessageCollector()

