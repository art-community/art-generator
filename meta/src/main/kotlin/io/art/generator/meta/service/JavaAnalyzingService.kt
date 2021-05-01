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
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.addJavaSourceRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CommonConfigurationKeys.REPORT_OUTPUT_FILES
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.name.FqName

object JavaAnalyzingService {
    fun analyzeJavaSources() {
        val configuration = CompilerConfiguration()
        configuration.put(MESSAGE_COLLECTOR_KEY, generatorCollector)
        configuration.put(MODULE_NAME, COMPILER_MODULE_NAME)

        configuration.put(COMPILE_JAVA, true)
        configuration.put(USE_JAVAC, true)

        configuration.put(REPORT_OUTPUT_FILES, false)
        configuration.put(JVM_TARGET, JvmTarget.JVM_1_8)

        val sourcesRoot = generatorConfiguration.sourcesRoot.map { path -> path.toFile() }
        val classpath = generatorConfiguration.classpath.map { path -> path.toFile() }

        sourcesRoot.forEach(configuration::addJavaSourceRoot)
        configuration.addJvmClasspathRoots(classpath)

        val kotlinCoreEnvironment = createForProduction(EMPTY_DISPOSABLE, configuration, JVM_CONFIG_FILES).apply {
            registerJavac(
                    javaFiles = generatorConfiguration.sources.filter { source -> source.isJava }.map { source -> source.toFile() },
                    bootClasspath = classpath,
                    sourcePath = sourcesRoot
            )
        }

        val analysisResult = KotlinToJVMBytecodeCompiler.analyze(kotlinCoreEnvironment) ?: return
        if (analysisResult.isError()) return
        println(analysisResult.moduleDescriptor.getSubPackagesOf(FqName.ROOT, { true }))
    }
}
