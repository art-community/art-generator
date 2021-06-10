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
import io.art.generator.model.KotlinMetaTypeKind.*
import io.art.generator.model.KotlinTypeVariableVariance.IN
import io.art.generator.model.KotlinTypeVariableVariance.OUT
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isArray
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.ClassKind.ANNOTATION_CLASS
import org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
import org.jetbrains.kotlin.load.java.JavaClassesTracker
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassNotAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.SimpleType
import org.jetbrains.kotlin.types.TypeProjection
import org.jetbrains.kotlin.types.TypeUtils.isNullableType
import org.jetbrains.kotlin.types.UnwrappedType
import org.jetbrains.kotlin.types.Variance.*
import org.jetbrains.kotlin.types.typeUtil.isEnum
import java.nio.file.Path
import java.util.Objects.nonNull

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<KotlinType, KotlinMetaType>()

    private class JavaTracker : JavaClassesTracker {
        val classes = mutableListOf<JavaClassDescriptor>()

        override fun onCompletedAnalysis(module: ModuleDescriptor) = Unit

        override fun reportClass(classDescriptor: JavaClassDescriptor) {
            classes += classDescriptor
        }
    }

    fun analyzeKotlinSources(root: Path): List<KotlinMetaClass> {
        val javaTracker = JavaTracker()
        val analysisResult = useKotlinCompiler(KotlinCompilerConfiguration(root, javaTracker), KotlinToJVMBytecodeCompiler::analyze)
                ?.takeIf { result -> !result.isError() }
                ?: return emptyList()
        val kotlinClasses = root.toFile().listFiles()!!
                .map { file -> file.name }
                .flatMap { packageName -> collectClasses(analysisResult, packageName) }
                .asSequence()
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                .map { descriptor -> descriptor.asMetaClass() }
                .distinctBy { metaClass -> metaClass.type.typeName }
                .toList()
        val javaClasses = javaTracker.classes
                .toList()
                .asSequence()
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                .map { descriptor -> descriptor.asMetaClass() }
                .distinctBy { metaClass -> metaClass.type.typeName }
                .toList()
        return kotlinClasses + javaClasses
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
        when (val unwrapped: UnwrappedType = unwrap()) {
            is SimpleType -> unwrapped.asMetaType()
            else -> KotlinMetaType(originalType = unwrapped, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun SimpleType.asMetaType(): KotlinMetaType = when {
        isEnum() -> {
            val classId = constructor.declarationDescriptor?.classId
            KotlinMetaType(
                    originalType = this,
                    kind = ENUM_KIND,
                    nullable = isNullableType(this),
                    classFullName = classId!!.asSingleFqName().asString(),
                    className = classId.relativeClassName.asString(),
                    classPackageName = classId.packageFqName.asString(),
                    typeName = classId.asSingleFqName().asString()
            )
        }

        isArray(this) -> KotlinMetaType(
                originalType = this,
                kind = ARRAY_KIND,
                nullable = isNullableType(this),
                arrayComponentType = constructor.builtIns.getArrayElementType(this).asMetaType(),
                typeName = toString()
        )

        constructor.declarationDescriptor is FunctionClassDescriptor -> putIfAbsent(cache, this) {
            KotlinMetaType(
                    originalType = this,
                    kind = FUNCTION_KIND,
                    nullable = isNullableType(this),
                    typeName = toString(),
                    functionResultType = arguments
                            .takeLast(1)
                            .firstOrNull()
                            ?.asMetaType()
            )
        }.apply {
            val newArguments = arguments
                    .dropLast(1)
                    .map { type -> type.asMetaType() }
            functionArgumentTypes.clear()
            functionArgumentTypes.addAll(newArguments)
        }

        constructor.declarationDescriptor is ClassDescriptor -> {
            val classId = constructor.declarationDescriptor?.classId
            putIfAbsent(cache, this) {
                KotlinMetaType(
                        originalType = this,
                        kind = CLASS_KIND,
                        nullable = isNullableType(this),
                        classFullName = classId!!.asSingleFqName().asString(),
                        className = classId.relativeClassName.asString(),
                        classPackageName = classId.packageFqName.asString(),
                        typeName = classId.asSingleFqName().asString()
                )
            }.apply {
                val newArguments = arguments.map { projection -> projection.asMetaType() }
                typeParameters.clear()
                typeParameters.addAll(newArguments)
            }
        }

        constructor.declarationDescriptor is TypeParameterDescriptor -> {
            val typeParameterDescriptor = constructor.declarationDescriptor as TypeParameterDescriptor
            putIfAbsent(cache, this) {
                KotlinMetaType(
                        originalType = this,
                        kind = VARIABLE_KIND,
                        nullable = isNullableType(this),
                        typeName = toString(),
                        typeVariableVariance = when (typeParameterDescriptor.variance) {
                            INVARIANT -> KotlinTypeVariableVariance.INVARIANT
                            IN_VARIANCE -> IN
                            OUT_VARIANCE -> OUT
                        },
                )
            }.apply {
                val newBounds = (typeParameterDescriptor).upperBounds.map { type -> type.asMetaType() }
                typeVariableBounds.clear()
                typeVariableBounds.addAll(newBounds)
            }
        }

        else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun TypeProjection.asMetaType(): KotlinMetaType {
        val unwrapped = type.unwrap()
        if (isStarProjection) {
            return KotlinMetaType(
                    originalType = unwrapped,
                    kind = WILDCARD_KIND,
                    nullable = isNullableType(unwrapped),
                    typeName = toString()
            )
        }
        return unwrapped.asMetaType()
    }

    private fun ClassDescriptor.asMetaClass(): KotlinMetaClass = KotlinMetaClass(
            type = defaultType.asMetaType(),

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
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = getSuperClassNotAny()
                    ?.takeIf { descriptor -> descriptor.kind != ENUM_ENTRY }
                    ?.takeIf { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    ?.takeIf { descriptor -> descriptor != this }
                    ?.asMetaClass(),

            interfaces = getSuperInterfaces()
                    .asSequence()
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .filter { descriptor -> descriptor != this }
                    .map { interfaceType -> interfaceType.asMetaClass() }
                    .toList()
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
            type = type.asMetaType(),
            hasGetter = nonNull(getter),
            hasSetter = nonNull(setter),
    )

    private fun ValueParameterDescriptor.asMetaParameter() = KotlinMetaParameter(
            name = name.toString(),
            visibility = visibility,
            type = type.asMetaType(),
            varargs = varargElementType != null,
    )
}
