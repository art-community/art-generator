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

package io.art.generator.meta

import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.service.JavaAnalyzingService.analyzeJavaSources
import io.art.generator.meta.service.generateJavaStubs
import io.art.generator.meta.service.generateMetaJavaSources
import io.art.generator.meta.service.initialize

object MetaGenerator {
    @JvmStatic
    fun main(arguments: Array<String>) {
        initialize(arguments)
        analyzeJavaSources().apply {
            //generateJavaStubs(this)
            generateMetaJavaSources(this)
        }
        block()
    }
}