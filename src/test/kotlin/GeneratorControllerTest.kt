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

import io.art.core.constants.DateTimeConstants.DEFAULT_FORMATTER
import io.art.core.constants.StringConstants.SHARP
import io.art.core.context.Context.context
import io.art.core.determiner.SystemDeterminer.isWindows
import io.art.core.waiter.Waiter.waitCondition
import io.art.core.waiter.Waiter.waitTime
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.generator.constants.META_NAME
import io.art.launcher.Activator
import io.art.launcher.TestingActivator.testing
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.lang.Runtime.getRuntime
import java.lang.System.getProperty
import java.time.Duration.ofSeconds
import java.time.LocalDateTime.parse
import kotlin.io.path.readText
import kotlin.io.path.writeText

@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
class GeneratorControllerTest {
    @BeforeAll
    fun setup() {
        testing(Activator::kit)
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
    @Order(0)
    fun testGeneratorControllerLocked() {
        runGenerator()
        waitTime(ofSeconds(5))
        val controllerContent = configuration.controller.readText()
        assertTrue(controllerContent.split(SHARP)[0] == "LOCKED")
        val timestamp = parse(controllerContent.split(SHARP)[1], DEFAULT_FORMATTER)
        waitTime(ofSeconds(3))
        assertTrue(parse(configuration.controller.readText().split(SHARP)[1], DEFAULT_FORMATTER).isAfter(timestamp))
    }

    @RepeatedTest(5)
    @Order(1)
    fun testGeneratorControllerSingleton() {
        val second = runGenerator()
        var exited = false
        second.onExit().thenRun { exited = true }
        waitCondition { exited }
        assertTrue(exited)
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

        return getRuntime().exec(
                arrayOf(
                        executable.toFile().absolutePath,
                        "-Dconfiguration=${getProperty("configuration")}",
                        "-jar", getProperty("jar")
                )
        )
    }
}
