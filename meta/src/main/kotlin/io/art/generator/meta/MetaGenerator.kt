@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

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

package io.art.generator.meta

import com.sun.tools.javac.api.JavacTool.create
import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.meta.extension.file
import io.art.launcher.ModuleLauncher.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.resolve.multiplatform.findActuals
import java.io.StringWriter
import java.nio.charset.Charset
import java.util.*
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.JavaFileObject.Kind.SOURCE
import javax.tools.StandardLocation.CLASS_PATH
import javax.tools.StandardLocation.SOURCE_PATH

object MetaGenerator {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        launch()
        //        val configuration = parse("".path)
        //        val sources = configuration.sourcesDirectory.toFile().walkTopDown().filter { file -> file.isFile && file.toPath().isJava }
        //        val classpath = configuration.compilationClasspath


        val stringWriter = StringWriter()
        val javac = create()
        val diagnosticCollector = DiagnosticCollector<JavaFileObject>()
        val standardFileManager = javac.getStandardFileManager(diagnosticCollector, Locale.getDefault(), Charset.defaultCharset())
        standardFileManager.setLocation(SOURCE_PATH, listOf("D:\\Development\\Projects\\art\\art-environment\\local\\projects\\sandbox\\src\\main\\java".file))
        standardFileManager.setLocation(CLASS_PATH, listOf("D:\\Development\\Gradle\\caches\\modules-2\\files-2.1\\org.projectlombok\\lombok\\1.18.20\\18bcea7d5df4d49227b4a0743a536208ce4825bb\\lombok-1.18.20.jar".file))
        val file = standardFileManager.getJavaFileForInput(SOURCE_PATH, "model\\Request", SOURCE)
        val task = javac.getTask(
                stringWriter,
                standardFileManager,
                diagnosticCollector,
                emptyList(),
                listOf("model.Request"),
                listOf(file)
        )
        val message = task.generate()
        println(message)

        val configuration = CompilerConfiguration()
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
        configuration.put(MODULE_NAME, "default")
        val kotlinCoreEnvironment = KotlinCoreEnvironment.createForProduction({ }, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES)
        kotlinCoreEnvironment.addKotlinSourceRoots(listOf("D:\\Development\\Projects\\art\\art-environment\\local\\projects\\art-generator\\meta\\src\\main\\kotlin".file))
        kotlinCoreEnvironment.projectEnvironment.addSourcesToClasspath(LightVirtualFile())
        val analyze = KotlinToJVMBytecodeCompiler.analyze(kotlinCoreEnvironment)!!
        block()
    }
}
