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

import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaType
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.classValueType
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import java.nio.file.Path
import javax.lang.model.type.TypeMirror

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<TypeMirror, KotlinMetaType>()

    fun analyzeKotlinSources(root: Path): Map<Path, KotlinMetaClass> {
        val analysisResult = useKotlinCompiler(KotlinCompilerConfiguration(root), KotlinToJVMBytecodeCompiler::analyze)
                ?.takeIf { result -> !result.isError() }
                ?: return emptyMap()

        val rootPackages = root.toFile().listFiles()!!.map { file -> file.name }

        val kotlinClasses = rootPackages.flatMap { packageName ->
            val packageView = analysisResult.moduleDescriptor.getPackage(FqName(packageName)).memberScope
            getAllDescriptors(packageView)
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor !is JavaClassDescriptor }
        }

        println("Kotlin classes")

        kotlinClasses.forEach { classDescriptor ->
            classDescriptor.classValueType
            println(classDescriptor.fqNameSafe)

            println("Fields:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<VariableDescriptorWithAccessors>())
            println("Methods:" + getAllDescriptors(classDescriptor.defaultType.memberScope).filterIsInstance<FunctionDescriptor>())
            println("Constructors:" + classDescriptor.constructors)
        }

        println()

        return emptyMap()
    }
}
