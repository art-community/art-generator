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

package io.art.generator.meta.templates

import com.squareup.javapoet.ClassName
import io.art.generator.meta.constants.META_NAME

val GETTER = { field: String -> "get${field.capitalize()}" }
val GETTER_BOOLEAN = { field: String -> "is${field.capitalize()}" }
val SETTER = { field: String -> "set${field.capitalize()}" }

fun metaClassName(name: String, vararg nested: String): ClassName = ClassName.get(META_NAME, "Meta$name", *nested)
const val META_PACKAGE_REFERENCE = "meta"
