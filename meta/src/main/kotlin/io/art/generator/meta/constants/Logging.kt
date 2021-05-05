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

package io.art.generator.meta.constants

import io.art.logging.LoggingModule
import org.apache.logging.log4j.core.Logger

val JAVA_LOGGER: Logger = LoggingModule.logger(JAVA)
const val SOURCES_CHANGED = "Sources changed"
const val CLASSES_CHANGED = "Classes changed. Process generation"
const val CLASSES_NOT_CHANGED = "Classes not changed. Skip Generation"
const val ANALYZING_MESSAGE = "Analyzing sources"
const val GENERATING_METAS_MESSAGE = "Generating meta classes"
const val GENERATING_STUBS_MESSAGE = "Generating stub classes"
