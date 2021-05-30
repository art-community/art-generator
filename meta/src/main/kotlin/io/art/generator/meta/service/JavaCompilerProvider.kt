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

package io.art.generator.meta.service

import com.sun.source.util.JavacTask
import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.comp.CompileStates
import com.sun.tools.javac.main.JavaCompiler
import com.sun.tools.javac.util.Log.WriterKind.ERROR
import com.sun.tools.javac.util.Options
import io.art.generator.meta.configuration.configuration
import io.art.generator.meta.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.meta.constants.PARAMETERS_OPTION
import io.art.generator.meta.logger.CollectingDiagnosticListener
import io.art.generator.meta.model.JavaCompilerContext
import org.jetbrains.kotlin.javac.JavacOptionsMapper.setUTF8Encoding
import java.io.PrintWriter
import java.io.Writer
import java.nio.charset.Charset.defaultCharset
import java.nio.file.Path
import java.util.*
import javax.tools.StandardLocation.CLASS_PATH
import javax.tools.StandardLocation.SOURCE_PATH

object JavaCompilerProvider {
    fun <T> useJavaCompiler(sources: Sequence<Path>, sourceRoots: List<Path>, action: JavaCompiler.(task: JavacTask) -> T): T {
        val tool = JavacTool.create()
        val listener = CollectingDiagnosticListener()
        val classpath = configuration.classpath.map { path -> path.toFile() }
        val emptyWriter = PrintWriter(object : Writer() {
            override fun close() {

            }

            override fun flush() {
            }

            override fun write(cbuf: CharArray, off: Int, len: Int) {
            }
        })

        val context = JavaCompilerContext().apply {
            Options.instance(this).apply {
                setUTF8Encoding(this)
            }
        }

        val fileManager = tool.getStandardFileManager(listener, Locale.getDefault(), defaultCharset()).apply {
            setLocation(SOURCE_PATH, sourceRoots.map(Path::toFile))
            setLocation(CLASS_PATH, classpath)
        }

        val options = listOf(
                PARAMETERS_OPTION,
        )

        val files = fileManager.getJavaFileObjects(*sources.map { path -> path.toFile() }.toList().toTypedArray())

        val compilerInstance = JavaCompiler.instance(context).apply { log.setWriter(ERROR, emptyWriter) }
        compilerInstance.shouldStopPolicyIfError = CompileStates.CompileState.GENERATE
        try {
            val javacTask = tool.getTask(
                    emptyWriter,
                    fileManager,
                    listener,
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
