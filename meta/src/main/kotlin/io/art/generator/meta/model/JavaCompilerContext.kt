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

@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.meta.model

import com.sun.tools.javac.util.Context
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION

class JavaCompilerContext : Context() {
    override fun <T> put(key: Key<T>, factory: Factory<T>) {
        runCatching { super.put(key, factory) }.onFailure {
            if (it is AssertionError) return
            else throw it
        }
    }

    override fun <T> put(key: Key<T>, value: T) {
        runCatching { super.put(key, value) }.onFailure {
            if (it is AssertionError) return
            else throw it
        }
    }

    override fun <T> put(key: Class<T>, value: T) {
        runCatching { super.put(key, value) }.onFailure {
            if (it is AssertionError) return
            else throw it
        }
    }

    override fun <T> put(key: Class<T>, factory: Factory<T>) {
        runCatching { super.put(key, factory) }.onFailure {
            if (it is AssertionError) return
            else throw it
        }
    }

}