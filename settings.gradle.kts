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

include("core")
project(":core").projectDir = file("/Users/anton/Development/Projects/art/art-java/core")
include("value")
project(":value").projectDir = file("/Users/anton/Development/Projects/art/art-java/value")
include("logging")
project(":logging").projectDir = file("/Users/anton/Development/Projects/art/art-java/logging")
include("server")
project(":server").projectDir = file("/Users/anton/Development/Projects/art/art-java/server")
include("resilience")
project(":resilience").projectDir = file("/Users/anton/Development/Projects/art/art-java/resilience")
include("model")
project(":model").projectDir = file("/Users/anton/Development/Projects/art/art-java/model")
include("launcher")
project(":launcher").projectDir = file("/Users/anton/Development/Projects/art/art-java/launcher")
include("configurator")
project(":configurator").projectDir = file("/Users/anton/Development/Projects/art/art-java/configurator")
include("json")
project(":json").projectDir = file("/Users/anton/Development/Projects/art/art-java/json")
include("protobuf")
project(":protobuf").projectDir = file("/Users/anton/Development/Projects/art/art-java/protobuf")
include("rsocket")
project(":rsocket").projectDir = file("/Users/anton/Development/Projects/art/art-java/rsocket")
include("message-pack")
project(":message-pack").projectDir = file("/Users/anton/Development/Projects/art/art-java/message-pack")
include("communicator")
project(":communicator").projectDir = file("/Users/anton/Development/Projects/art/art-java/communicator")
include("yaml-configuration")
project(":yaml-configuration").projectDir = file("/Users/anton/Development/Projects/art/art-java/yaml-configuration")
include("xml")
project(":xml").projectDir = file("/Users/anton/Development/Projects/art/art-java/xml")
