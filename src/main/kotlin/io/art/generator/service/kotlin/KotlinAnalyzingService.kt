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

object KotlinAnalyzingService {
    fun analyzeKotlinSources() {
//        val configuration = CompilerConfiguration()
//        configuration.put(MESSAGE_COLLECTOR_KEY, generatorCollector)
//        configuration.put(MODULE_NAME, COMPILER_MODULE_NAME)
//        configuration.put(REPORT_OUTPUT_FILES, false)
//        configuration.put(JVM_TARGET, JVM_1_8)
//        configuration.put(RETAIN_OUTPUT_IN_MEMORY, true)
//        configuration.put(DISABLE_INLINE, true)
//        configuration.put(DISABLE_OPTIMIZATION, true)
//        configuration.put(NO_RESET_JAR_TIMESTAMPS, true)
//        configuration.put(NO_OPTIMIZED_CALLABLE_REFERENCES, true)
//        configuration.put(PARAMETERS_METADATA, true)
//        configuration.put(EMIT_JVM_TYPE_ANNOTATIONS, true)
//        configuration.put(USE_FIR, true)
//        configuration.put(IR, true)
//        configuration.put(USE_FIR_EXTENDED_CHECKERS, false)
//        configuration.put(COMPILE_JAVA, true)
//        configuration.put(USE_JAVAC, true)
//
//        val sourcesRoot = generatorConfiguration.sourcesRoot.map { path -> path.toFile() }
//        val classpath = generatorConfiguration.classpath.map { path -> path.toFile() }
//
//        configuration.addKotlinSourceRoots(sourcesRoot.map { file -> file.absolutePath })
//        configuration.addJvmClasspathRoots(classpath)
//        sourcesRoot.forEach(configuration::addJavaSourceRoot)
//
//        val kotlinCoreEnvironment = createForProduction(EMPTY_DISPOSABLE, configuration, JVM_CONFIG_FILES).apply {
//            registerJavac(
//                    javaFiles = generatorConfiguration.sources.filter { source -> source.isJava }.map { source -> source.toFile() },
//                    bootClasspath = classpath,
//                    sourcePath = sourcesRoot
//            )
//        }
//
//        val analysisResult = KotlinToJVMBytecodeCompiler.analyze(kotlinCoreEnvironment) ?: return
//        if (analysisResult.isError()) return
//
//        val kotlinClasses = sourcesRoot
//                .flatMap { path -> path.listFiles()?.map { file -> file.name } ?: emptyList() }
//                .flatMap { packageName ->
//                    analysisResult.moduleDescriptor.getSubPackagesOf(FqName(packageName)) { true }.flatMap { name ->
//                        val packageView = analysisResult.moduleDescriptor.getPackage(name).memberScope
//                        getAllDescriptors(packageView).filterIsInstance<ClassDescriptor>().filter { descriptor -> descriptor !is JavaClassDescriptor }
//                    }
//                }
//
//        println("Kotlin classes")
//
////        kotlinClasses.forEach { classDescriptor ->
////            println(classDescriptor.fqNameSafe)
////            println("Fields:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<VariableDescriptorWithAccessors>())
////            println("Methods:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<FunctionDescriptor>())
////            println("Constructors:" + classDescriptor.constructors)
////        }
//
//        println()
    }
}
