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

import io.art.generator.meta.service.JavaAnalyzingService
import io.art.logging.LoggingModule.logger
import java.util.Locale.getDefault
import javax.tools.Diagnostic
import javax.tools.DiagnosticListener

object LoggingDiagnosticListener : DiagnosticListener<Any> {
    private val logger = logger(JavaAnalyzingService::class.java)

    override fun report(diagnostic: Diagnostic<out Any>) = logger.trace(diagnostic.getMessage(getDefault()))
}
