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

package io.art.generator.constants


const val META_NAME = "meta"
const val LOAD_NAME = "load"
const val DEPENDENCIES_NAME = "dependencies"
const val GENERATOR_NAME = "generator"
const val INVOKE_NAME = "invoke"
const val CONSTRUCTOR_NAME = "constructor"
const val INSTANCE_NAME = "instance"
const val ARGUMENT_NAME = "argument"
const val ARGUMENTS_NAME = "arguments"

const val META_TYPE_NAME = "metaType"
const val META_ARRAY_NAME = "metaArray"
const val META_ENUM_NAME = "metaEnum"

val META_METHODS = setOf(
        META_TYPE_NAME,
        META_ARRAY_NAME,
        META_ENUM_NAME
)


const val CAST_NAME = "cast"
const val SET_OF_NAME = "setOf"
