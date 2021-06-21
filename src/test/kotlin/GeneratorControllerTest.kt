import io.art.core.context.Context.context
import io.art.core.waiter.Waiter.waitCondition
import io.art.generator.configuration.configuration
import io.art.generator.configuration.reconfigure
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.lang.Runtime.getRuntime
import java.lang.System.getProperty
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
        reconfigure()
        initializeTest()
    }

    @Test
    fun testGeneratorController() {
        assertTrue { !configuration.controller.exists() }
        runGenerator()
        assertTrue { configuration.controller.exists() }
        assertTrue { configuration.controller.readText().split(" ")[0] == "LOCKED" }
        configuration.controller.writeText("STOPPING")
        assertTrue { waitCondition { configuration.controller.readText() == "AVAILABLE" } }
        runGenerator()
        assertTrue { configuration.controller.readText().split(" ")[0] == "LOCKED" }
        configuration.controller.writeText("STOPPING")
        assertTrue { waitCondition { configuration.controller.readText() == "AVAILABLE" } }
    }

    private fun runGenerator() {
        val executable = context().configuration().javaHomeDirectory.resolve("bin").resolve("java")
        getRuntime().exec(
                arrayOf(
                        executable.toFile().absolutePath,
                        "-Dconfiguration=${getProperty("configuration")}",
                        "-jar", getProperty("jarPath")
                )
        )
    }
}
