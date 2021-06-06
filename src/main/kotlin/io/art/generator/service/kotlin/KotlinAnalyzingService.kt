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

package io.art.generator.service.kotlin

import io.art.generator.configuration.configuration
import io.art.generator.constants.EMPTY_DISPOSABLE
import io.art.generator.constants.KOTLIN_COMPILER_MODULE_NAME
import io.art.generator.logging.loggingMessageCollector
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaType
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.common.config.addKotlinSourceRoots
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles.JVM_CONFIG_FILES
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.Companion.createForProduction
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.addJavaSourceRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CommonConfigurationKeys.DISABLE_INLINE
import org.jetbrains.kotlin.config.CommonConfigurationKeys.MODULE_NAME
import org.jetbrains.kotlin.config.CommonConfigurationKeys.REPORT_OUTPUT_FILES
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR
import org.jetbrains.kotlin.config.CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys.*
import org.jetbrains.kotlin.config.JvmTarget.JVM_11
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import java.nio.file.Path
import javax.lang.model.type.TypeMirror

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<TypeMirror, KotlinMetaType>()

    fun analyzeKotlinSources(root: Path): Map<Path, KotlinMetaClass> {
        val compilerConfiguration = CompilerConfiguration()
        compilerConfiguration.put(MESSAGE_COLLECTOR_KEY, loggingMessageCollector)
        compilerConfiguration.put(MODULE_NAME, KOTLIN_COMPILER_MODULE_NAME)
        compilerConfiguration.put(REPORT_OUTPUT_FILES, true)
        compilerConfiguration.put(JVM_TARGET, JVM_11)
        compilerConfiguration.put(RETAIN_OUTPUT_IN_MEMORY, true)
        compilerConfiguration.put(DISABLE_INLINE, true)
        compilerConfiguration.put(DISABLE_OPTIMIZATION, true)
        compilerConfiguration.put(NO_RESET_JAR_TIMESTAMPS, true)
        compilerConfiguration.put(NO_OPTIMIZED_CALLABLE_REFERENCES, true)
        compilerConfiguration.put(PARAMETERS_METADATA, true)
        compilerConfiguration.put(EMIT_JVM_TYPE_ANNOTATIONS, true)
        compilerConfiguration.put(USE_FIR, true)
        compilerConfiguration.put(IR, true)
        compilerConfiguration.put(USE_FIR_EXTENDED_CHECKERS, false)
        val classpath = configuration.classpath.map { path -> path.toFile() }

        compilerConfiguration.addKotlinSourceRoots(listOf(root.toFile().absolutePath))
        compilerConfiguration.addJvmClasspathRoots(classpath)
        compilerConfiguration.addJavaSourceRoot(root.toFile())

        val kotlinCoreEnvironment = createForProduction(EMPTY_DISPOSABLE, compilerConfiguration, JVM_CONFIG_FILES)

        val analysisResult = KotlinToJVMBytecodeCompiler.analyze(kotlinCoreEnvironment) ?: return emptyMap()
        if (analysisResult.isError()) return emptyMap()

        val rootPackages = root.toFile().listFiles()!!.map { file -> file.name }

        val kotlinClasses = rootPackages.flatMap { packageName ->
            val packageView = analysisResult.moduleDescriptor.getPackage(FqName(packageName)).memberScope
            getAllDescriptors(packageView)
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor !is JavaClassDescriptor }
        }


        println("Kotlin classes")

        kotlinClasses.forEach { classDescriptor ->
            println(classDescriptor.fqNameSafe)
            println("Fields:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<VariableDescriptorWithAccessors>())
            println("Methods:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<FunctionDescriptor>())
            println("Constructors:" + classDescriptor.constructors)
        }

        println()

        return emptyMap()
    }
}
