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

package io.art.generator.service.analyzing

import com.sun.tools.javac.code.Symbol.ClassSymbol
import io.art.core.matcher.PathMatcher.matches
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.ANALYZE_COMPLETED
import io.art.generator.constants.ANALYZING_MESSAGE
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.model.JavaMetaClass
import io.art.generator.parser.JavaSymbolParser
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import java.nio.file.Paths.get
import javax.lang.model.element.ElementKind.ENUM
import javax.tools.JavaFileObject.Kind.SOURCE

data class JavaAnalyzingRequest(
        val configuration: SourceConfiguration,
        val metaClassName: String,
)

fun analyzeJavaSources(request: JavaAnalyzingRequest) = JavaAnalyzingService().analyzeJavaSources(request)

private class JavaAnalyzingService : JavaSymbolParser() {
    fun analyzeJavaSources(request: JavaAnalyzingRequest): List<JavaMetaClass> {
        if (!request.configuration.root.toFile().exists()) return emptyList()
        JAVA_LOGGER.info(ANALYZING_MESSAGE(request.configuration.root))
        return useJavaCompiler(JavaCompilerConfiguration(request.configuration.sources, request.configuration.classpath)) { task ->
            task.analyze()
                    .asSequence()
                    .filter { input -> input.kind.isClass || input.kind.isInterface || input.kind == ENUM }
                    .map { element -> (element as ClassSymbol) }
                    .filter { symbol -> symbol.included(request) }
                    .filter { symbol -> symbol.className() != request.metaClassName }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .map { symbol -> symbol.asMetaClass() }
                    .distinctBy { metaClass -> metaClass.type.typeName }
                    .toList()
                    .apply { JAVA_LOGGER.info(ANALYZE_COMPLETED(map { metaClass -> metaClass.type.typeName })) }
        }
    }

    private fun ClassSymbol.included(request: JavaAnalyzingRequest) =
            sourcefile.kind == SOURCE && get(sourcefile.name).let { path ->
                path.startsWith(request.configuration.root)
                        && request.configuration.exclusions.none { exclusion -> matches(exclusion, path) }
                        && (request.configuration.inclusions.isEmpty() || request.configuration.inclusions.any { exclusion -> matches(exclusion, path) })
            }
}
