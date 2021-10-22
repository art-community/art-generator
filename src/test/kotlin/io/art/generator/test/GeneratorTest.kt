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
@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.test

import io.art.configurator.kotlin.configurator
import io.art.core.extensions.FileExtensions.recursiveDelete
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.GeneratorLanguage.JAVA
import io.art.generator.constants.GeneratorLanguage.KOTLIN
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.META_NAME
import io.art.generator.extension.metaPackage
import io.art.generator.extension.metaPath
import io.art.generator.extension.normalizeToClassSuffix
import io.art.generator.loader.PathClassLoader
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import io.art.generator.service.common.SourceScanningService.scanSources
import io.art.generator.service.common.initialize
import io.art.generator.templates.metaModuleClassFullName
import io.art.launcher.kotlin.activator
import io.art.logging.Logging.logger
import io.art.logging.kotlin.logging
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyzeAndGenerate
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.name

@TestInstance(PER_CLASS)
class GeneratorTest {
    @BeforeAll
    fun setup() = activator {
        configurator()
        logging()
        launch()
    }

    @BeforeEach
    fun prepare() {
        reconfigure()
        generatedFiles().forEach { file -> file.delete() }
    }

    @AfterEach
    fun cleanup() {
        generatedFiles().forEach { file -> file.delete() }
    }

    @Test
    fun testMetaGeneration(@TempDir tempDirectory: Path) {
        initialize()

        val logger = logger("test")

        scanSources()

        logger.info("Meta sources generated")

        assertTrue { generatedFiles().filter { file -> file.exists() }.size == 4 }

        configuration.sources.forEach { source ->
            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }

            if (source.languages.contains(JAVA)) {
                val roots = configuration.sources
                        .filter { configuration -> configuration.languages.contains(JAVA) }
                        .map { configuration -> configuration.root }
                        .toSet()
                useJavaCompiler(JavaCompilerConfiguration(roots, source.classpath, tempDirectory)) { task -> assertTrue(task.call()) }
                logger.info("[${source.root.name}]: Java sources compiled")

                var name = source.module + source.root.toFile().name.normalizeToClassSuffix()
                if (source.languages.size > 1) {
                    name += JAVA.suffix
                }
                val generatedClassName = metaModuleClassFullName(source.metaPackage, name)
                assertNotNull(PathClassLoader(tempDirectory).loadClass(generatedClassName).apply {
                    logger.info("[${source.root.name}]: Loaded Java class: $generatedClassName")
                })
            }

            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }

            if (source.languages.contains(KOTLIN)) {
                val roots = configuration.sources
                        .filter { configuration -> configuration.languages.contains(KOTLIN) }
                        .map { configuration -> configuration.root }
                        .toSet()
                useKotlinCompiler(KotlinCompilerConfiguration(roots, source.classpath, tempDirectory)) {
                    analyzeAndGenerate(this)
                }
                logger.info("[${source.root.name}]: Kotlin sources compiled")

                var name = source.module + source.root.toFile().name.normalizeToClassSuffix()
                if (source.languages.size > 1) {
                    name += KOTLIN.suffix
                }
                val generatedClassName = metaModuleClassFullName(source.metaPackage, name)
                assertNotNull(PathClassLoader(tempDirectory).loadClass(generatedClassName).apply {
                    logger.info("[${source.root.name}]: Loaded Kotlin class: $generatedClassName")
                })
            }
        }
    }

    private fun generatedFiles() = configuration.sources
            .flatMap { source ->
                source.metaPath
                        .toFile()
                        .listFiles()
                        ?.filter { file -> file.name.startsWith(capitalize(META_NAME + source.module)) }
                        ?: emptyList()
            }
            .toSet()
}
