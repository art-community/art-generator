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

package io.art.generator.meta.extension

import io.art.core.constants.StringConstants.DOT
import io.art.core.extensions.FileExtensions.parseExtension
import io.art.generator.meta.constants.*
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

val String.path: Path
    get() = Paths.get(this)

val String.file: File
    get() = Paths.get(this).toFile()

val Path.isJava: Boolean
    get() = parseExtension(this.toString()) == JAVA

val Path.isClass: Boolean
    get() = parseExtension(this.toString()) == CLASS

val Path.isJar: Boolean
    get() = parseExtension(this.toString()) == JAR

val File.isJava: Boolean
    get() = parseExtension(this.toString()) == JAVA

val File.isKotlin: Boolean
    get() = parseExtension(this.toString()) == KOTLIN_EXTENSION || isKotlinScript

val File.isKotlinScript: Boolean
    get() = parseExtension(this.toString()) == KOTLIN_SCRIPT_EXTENSION

val File.isClass: Boolean
    get() = parseExtension(this.toString()) == CLASS

val File.isJar: Boolean
    get() = parseExtension(this.toString()) == JAR

val String.packages: List<String>
    get() = split(DOT)
