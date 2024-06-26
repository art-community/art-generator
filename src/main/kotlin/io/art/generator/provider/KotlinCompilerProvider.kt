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

package io.art.generator.provider

import io.art.generator.constants.EMPTY_DISPOSABLE
import io.art.generator.constants.KOTLIN_ANALYZER_MODULE_NAME
import io.art.generator.logging.loggingMessageCollector
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoots
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CommonConfigurationKeys.DISABLE_INLINE
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CommonConfigurationKeys.REPORT_OUTPUT_FILES
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.config.JvmTarget.JVM_11
import org.jetbrains.kotlin.config.JvmTarget.JVM_21
import java.nio.file.Path


class KotlinCompilerConfiguration(
        val roots: Set<Path>,
        val classpath: Set<Path>,
        val destination: Path? = null,
)

object KotlinCompilerProvider {
    fun <T> useKotlinCompiler(kotlinCompilerConfiguration: KotlinCompilerConfiguration, action: KotlinCoreEnvironment.() -> T): T? {
        val compilerConfiguration = CompilerConfiguration()
        compilerConfiguration.put(MESSAGE_COLLECTOR_KEY, loggingMessageCollector)
        compilerConfiguration.put(MODULE_NAME, KOTLIN_ANALYZER_MODULE_NAME)
        compilerConfiguration.put(REPORT_OUTPUT_FILES, false)
        compilerConfiguration.put(JVM_TARGET, JVM_21)
        compilerConfiguration.put(RETAIN_OUTPUT_IN_MEMORY, true)
        compilerConfiguration.put(DISABLE_INLINE, true)
        compilerConfiguration.put(DISABLE_CALL_ASSERTIONS, true)
        compilerConfiguration.put(DISABLE_PARAM_ASSERTIONS, true)
        compilerConfiguration.put(DISABLE_OPTIMIZATION, true)
        compilerConfiguration.put(NO_RESET_JAR_TIMESTAMPS, true)
        compilerConfiguration.put(NO_OPTIMIZED_CALLABLE_REFERENCES, true)
        compilerConfiguration.put(PARAMETERS_METADATA, true)
        compilerConfiguration.put(EMIT_JVM_TYPE_ANNOTATIONS, true)
        compilerConfiguration.put(USE_FIR, false)
        compilerConfiguration.put(USE_FIR_EXTENDED_CHECKERS, false)
        compilerConfiguration.put(IR, false)

        kotlinCompilerConfiguration.destination?.let { destination ->
            compilerConfiguration.put(RETAIN_OUTPUT_IN_MEMORY, false)
            compilerConfiguration.put(OUTPUT_DIRECTORY, destination.toFile())
        }

        val classpath = kotlinCompilerConfiguration.classpath.map { path -> path.toFile() }

        compilerConfiguration.addKotlinSourceRoots(kotlinCompilerConfiguration.roots.map { root -> root.toFile().absolutePath })
        compilerConfiguration.addJvmClasspathRoots(classpath)

        return action(createForProduction(EMPTY_DISPOSABLE, compilerConfiguration, JVM_CONFIG_FILES))
    }
}
