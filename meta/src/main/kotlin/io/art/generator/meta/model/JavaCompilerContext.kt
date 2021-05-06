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

package io.art.generator.meta.model

import com.sun.tools.javac.util.Context
import io.art.core.constants.CompilerSuppressingWarnings.UNCHECKED_CAST

class JavaCompilerContext : Context() {
    private var values: MutableMap<Key<*>, Any> = mutableMapOf()
    private var factories: MutableMap<Key<*>, Factory<*>> = mutableMapOf()
    private var keys: MutableMap<Class<*>, Key<*>> = mutableMapOf()

    override fun <T : Any?> put(key: Key<T>, factory: Factory<T>) {
        this.values[key] = factory
        this.factories[key] = factory
    }

    override fun <T> put(key: Key<T>, value: T) {
        this.values[key] = value!!
    }

    override fun <T> put(var1: Class<T>, var2: T) {
        this.put(key(var1), var2)
    }

    override fun <T> put(key: Class<T>, factory: Factory<T>) {
        this.put(key(key), factory)
    }

    @Suppress(UNCHECKED_CAST)
    override operator fun <T> get(key: Key<T>): T {
        var value = this.values[key]
        if (value is Factory<*>) {
            value = value.make(this)
        }
        return value as T
    }

    override fun <T> get(var1: Class<T>): T {
        return this[key(var1)]
    }

    override fun dump() = values.values.forEach { value -> System.err.println(value.javaClass) }

    override fun clear() {
        values.clear()
        keys.clear()
        factories.clear()
    }

    @Suppress(UNCHECKED_CAST)
    private fun <T> key(keyClass: Class<T>): Key<T> {
        var current = this.keys[keyClass]
        if (current == null) {
            current = Key<Any>()
            this.keys[keyClass] = current
        }
        return current as Key<T>
    }

}
