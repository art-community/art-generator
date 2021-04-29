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

@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

package io.art.generator.meta

import io.art.core.extensions.ThreadExtensions.block
import io.art.generator.meta.extension.file
import io.art.generator.meta.extension.isJava
import io.art.launcher.ModuleLauncher.launch
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.KOTLIN_HOME_PROPERTY
import org.jetbrains.kotlin.cli.common.config.ContentRoot
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor
import org.jetbrains.kotlin.name.FqName.ROOT
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
import org.jetbrains.kotlin.resolve.multiplatform.findActuals
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter.Companion.ALL
import org.jetbrains.kotlin.resolve.scopes.getDescriptorsFiltered
import org.jetbrains.kotlin.utils.KotlinPaths
import org.jetbrains.kotlin.utils.KotlinPathsFromHomeDir
import org.jetbrains.kotlin.utils.PathUtil.KOTLIN_JAVA_STDLIB_JAR
import org.jetbrains.kotlin.utils.PathUtil.getJdkClassesRootsFromCurrentJre
import org.jetbrains.kotlin.utils.PathUtil.kotlinPathsForCompiler
import java.lang.System.setProperty

object MetaGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        launch()
        setProperty("idea.io.use.nio2", "true")
        val configuration = CompilerConfiguration()
        val collector = object : MessageCollector {
            override fun clear() {
            }

            override fun hasErrors(): Boolean {
                return false
            }

            override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageSourceLocation?) {
                println(message)
            }

        }
        configuration.put(INCLUDE_RUNTIME, true)
        configuration.put(MESSAGE_COLLECTOR_KEY, collector)
        configuration.put(ALLOW_KOTLIN_PACKAGE, false)
        configuration.put(MODULE_NAME, "default")
        configuration.put(COMPILE_JAVA, true)
        configuration.put(USE_JAVAC, true)
        configuration.put(RETAIN_OUTPUT_IN_MEMORY, true)
        configuration.addJvmClasspathRoots(getJdkClassesRootsFromCurrentJre())


        val disposable = {}
        val kotlinCoreEnvironment = createForProduction(disposable, configuration, JVM_CONFIG_FILES)
        val root = "D:\\Development\\Projects\\art\\art-environment\\local\\projects\\sandbox\\src\\main\\java".file
        kotlinCoreEnvironment.registerJavac(
                javaFiles = root.walkTopDown().filter { file -> file.isJava }.toList(),
                sourcePath = listOf(root)
        )
        kotlinCoreEnvironment.addKotlinSourceRoots(listOf(root))

        val analysisResult = KotlinToJVMBytecodeCompiler.analyzeAndGenerate(kotlinCoreEnvironment)!!
//        val moduleDescriptor = analysisResult.moduleDescriptor
//        val packages = moduleDescriptor.getSubPackagesOf(ROOT) { true }.map(moduleDescriptor::getPackage)
//        packages.forEach { `package` ->
//            val descriptorsFiltered = `package`.memberScope.getDescriptorsFiltered(ALL)
//            descriptorsFiltered.forEach { descriptorFiltered ->
//                println(descriptorFiltered)
//                when {
//                    descriptorFiltered is LazyJavaClassDescriptor -> {
//                        val jClass = descriptorFiltered.jClass
//                    }
//                    descriptorFiltered is LazyClassDescriptor -> {
//                        descriptorFiltered.findActuals()
//                        println(descriptorFiltered)
//                    }
//                }
//            }
//        }
        block()
    }
}
