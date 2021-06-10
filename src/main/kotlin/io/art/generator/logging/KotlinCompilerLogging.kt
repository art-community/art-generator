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

package io.art.generator.logging

import io.art.logging.logger.Logger
import io.art.logging.module.LoggingModule.logger
import io.art.logging.stream.LoggerPrintStream
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.WITHOUT_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import java.io.PrintStream


class EmptyMessageCollector(private val printer: MessageCollector = MessageCollector.NONE) : MessageCollector by printer

val emptyMessageCollector = EmptyMessageCollector()


class LoggingMessageCollector(
        private val logger: Logger = logger(LoggingMessageCollector::class.java),
        private val stream: PrintStream = LoggerPrintStream(logger, Logger::info),
        private val printer: MessageCollector = PrintingMessageCollector(stream, WITHOUT_PATHS, true),
) : MessageCollector by printer

val loggingMessageCollector = LoggingMessageCollector()

