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

import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaTypeKind.UNKNOWN_KIND
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.VariableDescriptor
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassNotAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.types.KotlinType
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Objects.nonNull

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<KotlinType, KotlinMetaType>()

    fun analyzeKotlinSources(root: Path): Map<Path, KotlinMetaClass> {
        val analysisResult = useKotlinCompiler(KotlinCompilerConfiguration(root), KotlinToJVMBytecodeCompiler::analyze)
                ?.takeIf { result -> !result.isError() }
                ?: return emptyMap()
        return root.toFile().listFiles()!!
                .map { file -> file.name }
                .flatMap { packageName -> collectClasses(analysisResult, packageName) }
                .asSequence()
                .map { descriptor -> descriptor.asMetaClass() }
                .filter { metaClass -> nonNull(metaClass.source) }
                .associateBy { metaClass -> metaClass.source!! }
    }

    private fun collectClasses(analysisResult: AnalysisResult, packageName: String): List<ClassDescriptor> {
        val name = FqName(packageName)
        val packageView = analysisResult.moduleDescriptor.getPackage(name).memberScope
        val classes = getAllDescriptors(packageView)
                .filterIsInstance<ClassDescriptor>()
                .filter { descriptor -> descriptor !is JavaClassDescriptor }
        return classes + analysisResult.moduleDescriptor
                .getSubPackagesOf(name) { true }
                .flatMap { nested -> collectClasses(analysisResult, nested.asString()) }
    }

    private fun KotlinType.asMetaType(): KotlinMetaType = putIfAbsent(cache, this) {
        when (this) {
            else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
    }


    private fun ClassDescriptor.asMetaType(): KotlinMetaType = defaultType.asMetaType()

    private fun ClassDescriptor.asMetaClass(): KotlinMetaClass = KotlinMetaClass(
            type = asMetaType(),

            source = source.containingFile.name?.let { name -> Paths.get(name) },

            visibility = visibility,

            properties = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<VariableDescriptorWithAccessors>()
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaProperty() },

            constructors = constructors
                    .asSequence()
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            methods = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<FunctionDescriptor>()
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            innerClasses = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<ClassDescriptor>()
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = getSuperClassNotAny()?.asMetaClass(),

            interfaces = getSuperInterfaces().map { interfaceField -> interfaceField.asMetaClass() }
    )

    private fun FunctionDescriptor.asMetaMethod() = KotlinMetaMethod(
            name = name.toString(),
            visibility = visibility,
            returnType = returnType?.asMetaType(),
            parameters = valueParameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
            typeParameters = typeParameters.map { typeParameter -> typeParameter.defaultType.asMetaType() },
    )

    private fun VariableDescriptorWithAccessors.asMetaProperty() = KotlinMetaProperty(
            name = name.toString(),
            visibility = visibility,
            type = type.asMetaType()
    )

    private fun VariableDescriptor.asMetaParameter() = KotlinMetaParameter(
            name = name.toString(),
            visibility = visibility,
            type = type.asMetaType()
    )
}
