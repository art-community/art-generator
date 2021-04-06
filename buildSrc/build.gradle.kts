/*
 * ART
 *
 * Copyright 2020 ART
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

repositories {
    jcenter()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
    `java-library`
}

gradlePlugin {
    plugins {
        register("java-generator") {
            id = "java-generator"
            implementationClass = "JavaGeneratorPlugin"
        }
        register("kotlin-generator") {
            id = "kotlin-generator"
            implementationClass = "KotlinGeneratorPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
