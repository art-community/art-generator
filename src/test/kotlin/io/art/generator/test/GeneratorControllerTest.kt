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
package io.art.generator.test

import io.art.configurator.kotlin.configurator
import io.art.core.context.Context.context
import io.art.core.determiner.SystemDeterminer.isWindows
import io.art.core.waiter.Waiter.waitCondition
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.META_NAME
import io.art.launcher.kotlin.activator
import io.art.logging.kotlin.logging
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.lang.Runtime.getRuntime
import java.lang.System.getProperty
import java.time.Duration.ofMinutes
import kotlin.io.path.readText
import kotlin.io.path.writeText

@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
class GeneratorControllerTest {
    @BeforeAll
    fun setup() {
        activator {
            configurator()
            logging()
            launch()
        }
        reconfigure()
        configuration.sources
            .map { source -> source.root.resolve(META_NAME).toFile() }
            .forEach { path -> if (path.exists()) path.deleteRecursively() }
        configuration.controller.toFile().apply { if (exists()) delete() }
    }

    @AfterEach
    fun cleanup() {
        configuration.sources
            .map { source -> source.root.resolve(META_NAME).toFile() }
            .forEach { path -> if (path.exists()) path.deleteRecursively() }
    }

    @AfterAll
    fun finalize() {
        configuration.controller.writeText("STOPPING")
    }

    @Test
    fun testGeneratorControllerSingleton() {
        val process = runGenerator()
        var exited = false
        process.onExit().thenRun { exited = true }
        waitCondition(ofMinutes(10)) { exited }
        assertTrue(exited)
        assertEquals("AVAILABLE", configuration.controller.readText())
    }

    private fun runGenerator(): Process {
        val executable = context().configuration()
            .javaHomeDirectory
            .resolve("bin")
            .resolve(buildString {
                append("java")
                if (isWindows()) {
                    append(".exe")
                }
            })

        return ProcessBuilder().command(
            arrayOf(
                executable.toFile().absolutePath,
                "-Xms2g", "-Xmx2g",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
                "-Dconfiguration=${getProperty("configuration")}",
                "-jar", getProperty("jar")
            ).toList()
        ).inheritIO().start()
    }
}
