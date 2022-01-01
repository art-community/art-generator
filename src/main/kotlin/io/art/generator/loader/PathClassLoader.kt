/*
 * ART
 *
 * Copyright 2019-2022 ART
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
import java.lang.Thread.currentThread
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path

class PathClassLoader(private val path: Path) : ClassLoader() {
    private val loader: URLClassLoader by lazy { createLoader() }

    private fun createLoader(): URLClassLoader = try {
        val urls: Array<URL> = arrayOf(path.toFile().toURI().toURL())
        URLClassLoader(urls, currentThread().contextClassLoader)
    } catch (throwable: Throwable) {
        throw MetaGeneratorException(throwable)
    }

    override fun loadClass(name: String?): Class<*> = try {
        loader.loadClass(name)
    } catch (throwable: Throwable) {
        throw MetaGeneratorException(throwable)
    }

    fun close() = try {
        loader.close()
    } catch (throwable: Throwable) {
        throw MetaGeneratorException(throwable)
    }
}
