import io.art.core.constants.DateTimeConstants.DEFAULT_FORMATTER
import io.art.core.context.Context.context
import io.art.core.waiter.Waiter.waitCondition
import io.art.core.waiter.Waiter.waitTime
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import io.art.launcher.TestingActivator.testing
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.lang.Runtime.getRuntime
import java.lang.System.getProperty
import java.time.Duration.ofSeconds
import java.time.LocalDateTime.now
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

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

@TestInstance(PER_CLASS)
class GeneratorControllerTest {
    @BeforeAll
    fun setup() {
        testing { activator -> activator.kit() }
        reconfigure()
        configuration.controller.toFile().apply { if (exists()) delete() }
    }

    @AfterAll
    fun cleanup() {
        configuration.controller.toFile().apply { if (exists()) delete() }
    }

    @Test
    fun testGeneratorController() {
        assertTrue { runGenerator().isAlive }
        waitTime(ofSeconds(10))
        assertTrue { configuration.controller.readText().split(" ")[0] == "LOCKED" }
        waitTime(ofSeconds(10))
        configuration.controller.writeText("STOPPING")
        waitTime(ofSeconds(10))
        assertTrue { configuration.controller.readText().split(" ")[0] == "AVAILABLE" }
    }

    private fun runGenerator(): Process {
        val executable = context().configuration().javaHomeDirectory.resolve("bin").resolve("java")
        return getRuntime().exec(
                arrayOf(
                        executable.toFile().absolutePath,
                        "-Dconfiguration=${getProperty("configuration")}",
                        "-jar", getProperty("jarPath")
                )
        )
    }
}
