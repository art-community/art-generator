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

@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

package io.art.generator.meta

import io.art.core.constants.StringConstants.SEMICOLON
import io.art.core.constants.SystemProperties.JAVA_CLASS_PATH
import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.meta.extension.file
import io.art.generator.meta.extension.isJava
import io.art.launcher.ModuleLauncher.launch
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoot
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_RELATIVE_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.config.addJavaSourceRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.codegen.GeneratedClassLoader
import org.jetbrains.kotlin.config.CommonConfigurationKeys.DISABLE_INLINE
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CommonConfigurationKeys.REPORT_OUTPUT_FILES
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.config.JvmTarget.JVM_1_8
import org.jetbrains.kotlin.javac.JavacWrapper
import java.io.File
import java.lang.System.getProperty
import java.net.URL
import java.net.URLClassLoader
import kotlin.time.ExperimentalTime
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler as kotlinCompiler


object MetaGenerator {
    @ExperimentalTime
    @JvmStatic
    fun main(args: Array<String>) {
        launch()
        setIdeaIoUseFallback()
        val configuration = CompilerConfiguration()
        val collector = PrintingMessageCollector(System.err, PLAIN_RELATIVE_PATHS, false)
        val base = "D:/Development/Projects/art/art-environment/local/projects/sandbox".file
        val javaSources = base.walkTopDown().filter { file -> file.isJava }.toList()
        val output = base.resolve("output")
        configuration.put(OUTPUT_DIRECTORY, output)
        configuration.put(MESSAGE_COLLECTOR_KEY, collector)
        configuration.put(MODULE_NAME, "default")

        configuration.put(COMPILE_JAVA, true)
        configuration.put(USE_JAVAC, true)

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

        configuration.put(INCLUDE_RUNTIME, true)
        configuration.put(ALLOW_KOTLIN_PACKAGE, true)

        configuration.put(REPORT_OUTPUT_FILES, false)
        configuration.put(JVM_TARGET, JVM_1_8)

        val sourceRoot = base.resolve("src/main/java")
        configuration.addJavaSourceRoot(sourceRoot)
        configuration.addKotlinSourceRoot(sourceRoot.absolutePath)
        val bootClasspath = getProperty(JAVA_CLASS_PATH).split(SEMICOLON).map(::File)
        configuration.addJvmClasspathRoots(bootClasspath)

        val disposable = {}
        val kotlinCoreEnvironment = createForProduction(disposable, configuration, JVM_CONFIG_FILES)
        kotlinCoreEnvironment.registerJavac(javaFiles = javaSources, bootClasspath = bootClasspath, sourcePath = listOf(sourceRoot))

        val javaCompiler = JavacWrapper.getInstance(kotlinCoreEnvironment.project)

        val analysisResult = kotlinCompiler.analyze(kotlinCoreEnvironment)!!
        println("Analysis error: ${analysisResult.isError()}")

        val kotlinCompilationResult = kotlinCompiler.analyzeAndGenerate(kotlinCoreEnvironment)
        val javaCompilationResult = javaCompiler.compile(output)
        println("Kotlin compilation result: ${kotlinCompilationResult!!.files}")
        println("Java compilation result: $javaCompilationResult")


        val javaLoader = object : ClassLoader() {
            private val loader: URLClassLoader by lazy { createLoader() }
            private fun createLoader(): URLClassLoader = try {
                val urls: Array<URL> = arrayOf(output.toURI().toURL())
                URLClassLoader(urls, Thread.currentThread().contextClassLoader)
            } catch (throwable: Throwable) {
                throw RuntimeException(throwable)
            }

            override fun loadClass(name: String?): Class<*> = try {
                loader.loadClass(name)
            } catch (throwable: Throwable) {
                throw RuntimeException(throwable)
            }

            fun close() = try {
                loader.close()
            } catch (throwable: Throwable) {
                throw RuntimeException(throwable)
            }
        }

        val kotlinLoader = GeneratedClassLoader(kotlinCompilationResult.factory, Thread.currentThread().contextClassLoader)
        println("Kotlin class object:${kotlinLoader.loadClass("model.customer.KotelRequest").newInstance()}")
        println("Java class object:${javaLoader.loadClass("model.customer.Request").newInstance()}")

        block()
    }
}
