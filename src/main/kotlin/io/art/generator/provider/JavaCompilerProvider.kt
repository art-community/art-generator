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

package io.art.generator.provider

import com.sun.source.util.JavacTask
import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.comp.CompileStates.CompileState.GENERATE
import com.sun.tools.javac.main.JavaCompiler
import com.sun.tools.javac.util.Log.WriterKind.ERROR
import com.sun.tools.javac.util.Options
import io.art.generator.constants.JAVA
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.constants.PARAMETERS_OPTION
import io.art.generator.logging.LoggingDiagnosticListener
import io.art.generator.logging.LoggingWriter
import io.art.generator.model.JavaCompilerContext
import org.jetbrains.kotlin.javac.JavacOptionsMapper.setUTF8Encoding
import java.nio.charset.Charset.defaultCharset
import java.nio.file.Path
import java.util.*
import javax.tools.StandardLocation.*

class JavaCompilerConfiguration(
        val roots: Set<Path>,
        val classpath: Set<Path>,
        val destination: Path? = null,
)

object JavaCompilerProvider {
    fun <T> useJavaCompiler(compilerConfiguration: JavaCompilerConfiguration, action: JavaCompiler.(task: JavacTask) -> T): T {
        val tool = JavacTool.create()
        val context = JavaCompilerContext().apply {
            Options.instance(this).apply { setUTF8Encoding(this) }
        }
        val fileManager = tool.getStandardFileManager(LoggingDiagnosticListener, Locale.getDefault(), defaultCharset()).apply {
            setLocation(SOURCE_PATH, compilerConfiguration.roots.map { root -> root.toFile() })
            setLocation(CLASS_PATH, compilerConfiguration.classpath.map { path -> path.toFile() })
            compilerConfiguration.destination?.let { destination -> setLocation(CLASS_OUTPUT, listOf(destination.toFile())) }
        }
        val options = listOf(PARAMETERS_OPTION)
        val sources = compilerConfiguration
                .roots
                .flatMap { root -> root.toFile().walkTopDown().filter { file -> file.extension == JAVA } }
                .toTypedArray()
        val files = fileManager.getJavaFileObjects(*sources)
        val compilerInstance = JavaCompiler.instance(context).apply {
            log.setWriter(ERROR, LoggingWriter)
            shouldStopPolicyIfError = GENERATE
        }
        try {
            val javacTask = tool.getTask(
                    LoggingWriter,
                    fileManager,
                    LoggingDiagnosticListener,
                    options,
                    emptyList(),
                    files,
                    context
            )
            return javacTask.let { task -> compilerInstance.action(task) }
        } finally {
            fileManager.close()
            compilerInstance.close()
        }
    }
}
