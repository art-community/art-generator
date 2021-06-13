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

import io.art.generator.configuration.configuration
import io.art.generator.constants.GeneratorLanguages.JAVA
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.loader.PathClassLoader
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import io.art.generator.service.SourceWatchingService.watchSources
import io.art.generator.service.initialize
import io.art.launcher.Activator.activator
import io.art.logging.module.LoggingActivator.logging
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path


@TestInstance(PER_CLASS)
class JavaGeneratorTest {
    private val root: Path by lazy { configuration.sources[JAVA]!!.first() }
    private val generatedFile: Path by lazy { root.resolve("meta").resolve("MetaExample.java") }
    private val generatedClassName = "meta.MetaExample"

    @BeforeAll
    fun setup() {
        activator().mainModuleId(JavaGeneratorTest::class.simpleName).module(logging()).launch()
        initialize()
    }

    @BeforeEach
    fun prepare() {
        generatedFile.toFile().delete()
    }

    @AfterEach
    fun cleanup() {
        generatedFile.toFile().delete()
    }

    @Test
    fun testMetaGeneration(@TempDir tempDirectory: Path) {
        watchSources(asynchronous = false)

        assertTrue { generatedFile.toFile().exists() }

        val sources = sequenceOf(generatedFile)

        assertTrue(root.toFile().exists(), "sources for generation not found")

        useJavaCompiler(JavaCompilerConfiguration(root, sources, tempDirectory)) { task -> assertTrue(task.call()) }

        assertNotNull { PathClassLoader(tempDirectory).loadClass(generatedClassName) }
    }
}
