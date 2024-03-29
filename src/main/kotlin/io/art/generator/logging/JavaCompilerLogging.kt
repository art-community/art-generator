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

import io.art.generator.constants.JAVA_LOGGER
import io.art.logging.logger.Logger
import io.art.logging.stream.LoggerOutputStream
import java.io.PrintWriter
import javax.tools.Diagnostic
import javax.tools.DiagnosticListener

object LoggingWriter : PrintWriter(LoggerOutputStream(JAVA_LOGGER, Logger::info))

object LoggingDiagnosticListener : DiagnosticListener<Any> {
    override fun report(diagnostic: Diagnostic<out Any>) = JAVA_LOGGER.info(diagnostic.toString())
}
