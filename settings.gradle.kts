import java.io.File.separator

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

val artDirectory: String? by settings
val artCommunityUrl: String by settings
var computedArtDirectory = artDirectory

if (file("local.properties").exists()) {
    file("local.properties").readLines().forEach { line ->
        val trimmed = line.trim()
        if (!trimmed.startsWith("#")) {
            val split = line.split("=")
            val name = split.getOrNull(0)
            val value = split.getOrNull(1)
            if (name == "artDirectory") computedArtDirectory = value
        }
    }
}

computedArtDirectory ?: error("Configuring error. 'artDirectory' not declared in gradle.properties or local.properties")

val artProjects = mapOf(
        "art-java" to "1.3.0",
        "art-kotlin" to "latest"
)

artProjects.forEach { (name, version) ->
    if (!file("$computedArtDirectory$separator$name").exists()) {
        ProcessBuilder("git", "clone", "$artCommunityUrl/$name", file("$computedArtDirectory$separator$name").absolutePath).start().waitFor()
        ProcessBuilder("git", "checkout", version).directory(file("$computedArtDirectory$separator$name")).start().waitFor()
    }
}

val artModules = mapOf(
        "art-java" to listOf(
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
                "graal",
                "rocks-db"
        ),

        "art-kotlin" to listOf(
                "kotlin-extensions"
        )
)

artModules.forEach { (name, modules) ->
    modules.forEach { module ->
        include(module)
        project(":$module").projectDir = file("$computedArtDirectory$separator$name$separator$module")
    }
}


rootProject.name = "art-generator"

include("language.kotlin")
include("language.java")
include("example.java")
include("example.kotlin")
