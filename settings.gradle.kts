/*
 * ART Java
 *
 * Copyright 2019 ART
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

plugins {
    id("com.gradle.enterprise") version "3.0"
}

rootProject.name = "art-generator"
include("java-generator")
include("java-example")

val artDirectoryFromSettings: String? by settings
val artGitUrl: String by settings
var artDirectory = artDirectoryFromSettings

if (file("local.properties").exists()) {
    file("local.properties").readLines().forEach { line ->
        val trimmed = line.trim()
        if (!trimmed.startsWith("#")) {
            val name = line.split("=").getOrNull(0)
            val value = line.split("=").getOrNull(1)
            if (name == "artDirectory") artDirectory = value
        }
    }
}

artDirectory ?: error("Configuring error. 'artDirectory' not declared")
if (!file(artDirectory!!).exists()) {
    ProcessBuilder("git", "clone", artGitUrl, file(artDirectory!!).absolutePath).start().waitFor()
    ProcessBuilder("git", "checkout", "1.3.0").directory(file(artDirectory!!)).start().waitFor()
}

val artModules = listOf(
        "core",
        "configurator",
        "value",
        "scheduler",
        "logging",
        "server",
        "resilience",
        "model",
        "launcher",
        "configurator",
        "json",
        "protobuf",
        "rsocket",
        "message-pack",
        "communicator",
        "yaml-configuration",
        "xml",
        "yaml",
        "tarantool",
        "template-engine",
        "graal"
)

artModules.forEach { module ->
    include(module)
    project(":$module").projectDir = file("${artDirectory}/$module")
}
