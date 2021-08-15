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

rootProject.name = "art-generator"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://nexus.art-platform.io/repository/art-gradle-plugins/") }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.contains("art")) {
                useModule("io.art.gradle:art-gradle:${requested.module?.version}")
            }
        }
    }
    plugins {
        val kotlinVersion: String by settings
        val internalPluginVersion: String by settings
        kotlin("jvm") version kotlinVersion
        id("art-internal-jvm") version internalPluginVersion
    }
}
