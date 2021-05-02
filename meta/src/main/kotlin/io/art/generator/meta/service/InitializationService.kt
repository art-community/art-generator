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

package io.art.generator.meta.service

import io.art.core.extensions.FileExtensions
import io.art.generator.meta.MetaGenerator
import io.art.generator.meta.configuration.loadConfiguration
import io.art.generator.meta.constants.CONFIGURATION_PATH_ARGUMENT
import io.art.generator.meta.constants.DEFAULT_CONFIGURATION_PATH
import io.art.generator.meta.constants.EXIT_CODE_ERROR
import io.art.generator.meta.extension.path
import net.sourceforge.argparse4j.ArgumentParsers
import net.sourceforge.argparse4j.inf.ArgumentParserException
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import kotlin.system.exitProcess

fun initialize(arguments: Array<String>) {
    setIdeaIoUseFallback()
    ArgumentParsers.newFor(MetaGenerator::class.simpleName)
            .cjkWidthHack(true)
            .build()
            .apply {
                addArgument(CONFIGURATION_PATH_ARGUMENT.short, CONFIGURATION_PATH_ARGUMENT.long)
                        .help(CONFIGURATION_PATH_ARGUMENT.help)
                        .dest(CONFIGURATION_PATH_ARGUMENT.name)
                try {
                    val stream = parseArgs(arguments).get<String>(CONFIGURATION_PATH_ARGUMENT.name)
                            ?.path
                            ?.let(FileExtensions::fileInputStream)
                            ?: MetaGenerator::class.java.classLoader.getResourceAsStream(DEFAULT_CONFIGURATION_PATH)!!
                    loadConfiguration(stream)
                } catch (exception: ArgumentParserException) {
                    handleError(exception)
                    exitProcess(EXIT_CODE_ERROR)
                }
            }

}
