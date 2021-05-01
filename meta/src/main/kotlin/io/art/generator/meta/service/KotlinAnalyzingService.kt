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

import io.art.generator.meta.configuration.generatorConfiguration
import io.art.generator.meta.constants.COMPILER_MODULE_NAME
import io.art.generator.meta.constants.EMPTY_DISPOSABLE
import io.art.generator.meta.extension.isJava
import io.art.generator.meta.logger.generatorCollector
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoots
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.addJavaSourceRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CommonConfigurationKeys.DISABLE_INLINE
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CommonConfigurationKeys.REPORT_OUTPUT_FILES
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.config.JvmTarget.JVM_1_8

object KotlinAnalyzingService {
    fun analyzeKotlinSources() {
        val configuration = CompilerConfiguration()
        configuration.put(MESSAGE_COLLECTOR_KEY, generatorCollector)
        configuration.put(INCLUDE_RUNTIME, true)
        configuration.put(ALLOW_KOTLIN_PACKAGE, true)
        configuration.put(MODULE_NAME, COMPILER_MODULE_NAME)
        configuration.put(REPORT_OUTPUT_FILES, false)
        configuration.put(JVM_TARGET, JVM_1_8)
        configuration.put(RETAIN_OUTPUT_IN_MEMORY, true)
        configuration.put(DISABLE_INLINE, true)
        configuration.put(DISABLE_OPTIMIZATION, true)
        configuration.put(NO_RESET_JAR_TIMESTAMPS, true)
        configuration.put(NO_OPTIMIZED_CALLABLE_REFERENCES, true)
        configuration.put(PARAMETERS_METADATA, true)
        configuration.put(EMIT_JVM_TYPE_ANNOTATIONS, true)
        configuration.put(USE_FIR, true)
        configuration.put(IR, true)
        configuration.put(USE_FIR_EXTENDED_CHECKERS, false)
        configuration.put(COMPILE_JAVA, true)
        configuration.put(USE_JAVAC, true)

        val sourcesRoot = generatorConfiguration.sourcesRoot.map { path -> path.toFile() }
        val classpath = generatorConfiguration.classpath.map { path -> path.toFile() }

        configuration.addKotlinSourceRoots(sourcesRoot.map { file -> file.absolutePath })
        configuration.addJvmClasspathRoots(classpath)
        sourcesRoot.forEach(configuration::addJavaSourceRoot)

        val kotlinCoreEnvironment = createForProduction(EMPTY_DISPOSABLE, configuration, JVM_CONFIG_FILES).apply {
            registerJavac(
                    javaFiles = generatorConfiguration.sources.filter { source -> source.isJava }.map { source -> source.toFile() },
                    bootClasspath = classpath,
                    sourcePath = sourcesRoot
            )
        }

        val analysisResult = KotlinToJVMBytecodeCompiler.analyze(kotlinCoreEnvironment)
        println("Kotlin: ${analysisResult?.isError()}")

    }
}
