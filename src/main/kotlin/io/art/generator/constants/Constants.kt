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

package io.art.generator.constants

import org.jetbrains.kotlin.com.intellij.openapi.Disposable
import java.time.Duration
import java.time.Duration.*

const val KOTLIN_EXTENSION = "kt"
const val KOTLIN_SCRIPT_EXTENSION = "kts"

const val JAVA = "java"
const val KOTLIN = "kotlin"
const val CLASS = "class"
const val JAR = "jar"

const val JAVA_MODULE_SUPPRESSION = "JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE"

val META_METHOD_EXCLUSIONS = setOf(
        "builder",
        "toString",
        "equals",
        "canEqual",
        "hashCode",
        "clone"
)

enum class GeneratorLanguage(val suffix: String) {
    JAVA("Java"),
    KOTLIN("Kotlin")
}

enum class GeneratorState {
    LOCKED,
    STOPPING,
    AVAILABLE
}

val EMPTY_DISPOSABLE = Disposable {}

const val KOTLIN_ANALYZER_MODULE_NAME = "kotlin-analyzer"

val LOCK_VALIDATION_DURATION: Duration = ofMinutes(1)

val LOCK_VALIDATION_PERIOD: Duration = ofMillis(500)

val STOPPING_TIMEOUT: Duration = ofSeconds(30)
