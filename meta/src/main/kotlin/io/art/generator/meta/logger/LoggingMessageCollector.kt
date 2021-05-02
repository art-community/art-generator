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

package io.art.generator.meta.logger

import io.art.logging.LoggingModule.logger
import org.apache.logging.log4j.Level.TRACE
import org.apache.logging.log4j.io.LoggerPrintStream
import org.apache.logging.log4j.spi.ExtendedLogger
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.WITHOUT_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import java.io.PrintStream
import java.nio.charset.Charset.defaultCharset

class LoggingMessageCollector(private val logger: ExtendedLogger = logger(LoggingMessageCollector::class.java),
                              private val stream: PrintStream = object : LoggerPrintStream(logger, false, defaultCharset(), null, TRACE, null) {},
                              private val printer: MessageCollector = PrintingMessageCollector(stream, WITHOUT_PATHS, true)
) : MessageCollector by printer

val loggingMessageCollector = LoggingMessageCollector()
