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

package io.art.generator.loader

import io.art.generator.exception.MetaGeneratorException

class BinaryClassLoader(private val classes: Map<String, ByteArray>) : ClassLoader() {
    override fun loadClass(name: String): Class<*> = try {
        val bytes = classes[name] ?: throw ClassNotFoundException(name)
        defineClass(name, bytes, 0, bytes.size).apply(::resolveClass)
    } catch (throwable: Throwable) {
        throw MetaGeneratorException(throwable)
    }
}
