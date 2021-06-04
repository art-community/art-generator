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

package io.art.generator.service.common

import io.art.generator.configuration.configuration
import io.art.generator.constants.META_NAME
import io.art.generator.extension.isJava
import io.art.generator.extension.isKotlin
import io.art.generator.templates.metaModuleJavaFileName
import io.art.generator.templates.metaModuleKotlinFileName
import java.io.File

val metaModuleJavaFile: File
    get() = configuration.sourcesRoot.resolve(META_NAME)
            .resolve(metaModuleJavaFileName(configuration.moduleName))
            .toFile()

val metaModuleKotlinFile: File
    get() = configuration.sourcesRoot.resolve(META_NAME)
            .resolve(metaModuleKotlinFileName(configuration.moduleName))
            .toFile()

fun collectJavaSources() = configuration.sourcesRoot.toFile()
        .walkTopDown()
        .asSequence()
        .filter { file -> !metaModuleJavaFile.exists() || file != metaModuleJavaFile }
        .filter { file -> file.isJava }
        .map { file -> file.toPath() }

fun collectKotlinSources() = configuration.sourcesRoot.toFile()
        .walkTopDown()
        .asSequence()
        .filter { file -> !metaModuleKotlinFile.exists() || file != metaModuleKotlinFile }
        .filter { file -> file.isKotlin }
        .map { file -> file.toPath() }
