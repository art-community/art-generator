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
import io.art.generator.constants.GeneratorLanguage.JAVA
import io.art.generator.constants.GeneratorLanguage.KOTLIN
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.loader.PathClassLoader
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.collectJavaSources
import io.art.logging.module.LoggingModule.logger
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyzeAndGenerate
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path


@TestInstance(PER_CLASS)
class GeneratorTest {
    @BeforeAll
    fun setup() = setupTest()

    @BeforeEach
    fun prepare() {
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
        logger.info("Meta sources Generated")

        assertTrue { generatedFiles().filter { file -> file.exists() }.size == 4 }

        configuration.sources.forEach { source ->
            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }
            if (source.languages.contains(JAVA)) {
                val javaSources = collectJavaSources(source.root, emptySet()).asSequence()

                useJavaCompiler(JavaCompilerConfiguration(source.root, javaSources, tempDirectory)) { task -> assertTrue(task.call()) }
                logger.info("Java sources compiled")

                var generatedClassName = "meta.MetaExample"
                if (source.languages.size > 1) generatedClassName += "Java"

                assertNotNull { PathClassLoader(tempDirectory).loadClass(generatedClassName).apply { logger.info("Loaded Java class: $name") } }
            }

            if (tempDirectory.toFile().exists()) {
                recursiveDelete(tempDirectory)
                tempDirectory.toFile().mkdirs()
            }
            if (source.languages.contains(KOTLIN)) {
                useKotlinCompiler(KotlinCompilerConfiguration(source.root, destination = tempDirectory)) { analyzeAndGenerate(this) }
                logger.info("Kotlin sources compiled")

                var generatedClassName = "meta.MetaExample"
                if (source.languages.size > 1) generatedClassName += "Kotlin"

                assertNotNull { PathClassLoader(tempDirectory).loadClass(generatedClassName).apply { logger.info("Loaded Kotlin class: $name") } }
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
