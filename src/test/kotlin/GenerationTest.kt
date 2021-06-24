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

import io.art.core.extensions.FileExtensions.recursiveDelete
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.GeneratorLanguage.JAVA
import io.art.generator.constants.GeneratorLanguage.KOTLIN
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.extension.normalizeToClassSuffix
import io.art.generator.loader.PathClassLoader
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.collectJavaSources
import io.art.generator.service.initialize
import io.art.generator.templates.metaModuleClassFullName
import io.art.launcher.TestingActivator.testing
import io.art.logging.module.LoggingModule.logger
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyzeAndGenerate
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.name

@TestInstance(PER_CLASS)
class GenerationTest {
    @BeforeAll
    fun setup() = testing { activator ->
        initialize()
        activator.kit()
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
        val logger = logger("test")

        watchSources(asynchronous = false)

        logger.info("Meta sources generated")

        assertTrue { generatedFiles().filter { file -> file.exists() }.size == 4 }

        configuration.sources.forEach { source ->
            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }

            if (source.languages.contains(JAVA)) {
                val javaSources = collectJavaSources(source.root, emptySet()).asSequence()

                useJavaCompiler(JavaCompilerConfiguration(source.root, javaSources, source.classpath, tempDirectory)) { task -> assertTrue(task.call()) }
                logger.info("[${source.root.name}]: Java sources compiled")

                val generatedClassName = metaModuleClassFullName(source.module + source.root.toFile().name.normalizeToClassSuffix())
                assertNotNull(PathClassLoader(tempDirectory).loadClass(generatedClassName).apply {
                    logger.info("[${source.root.name}]: Loaded Java class: $name")
                })
            }

            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }

            if (source.languages.contains(KOTLIN)) {
                useKotlinCompiler(KotlinCompilerConfiguration(setOf(source.root.toFile()), source.classpath, destination = tempDirectory)) {
                    analyzeAndGenerate(this)
                }
                logger.info("[${source.root.name}]: Kotlin sources compiled")

                val generatedClassName = metaModuleClassFullName(source.module + source.root.toFile().name.normalizeToClassSuffix())
                assertNotNull(PathClassLoader(tempDirectory).loadClass(generatedClassName).apply {
                    logger.info("[${source.root.name}]: Loaded Kotlin class: $name")
                })
            }
        }
    }

    private fun generatedFiles() = configuration.sources
            .flatMap { source ->
                source.root
                        .resolve("meta")
                        .toFile()
                        .listFiles()
                        ?.filter { file -> file.name.startsWith("MetaExample") }
                        ?: emptyList()
            }
            .toSet()
}
