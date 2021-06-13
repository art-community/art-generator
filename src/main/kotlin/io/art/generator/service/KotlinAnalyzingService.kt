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

package io.art.generator.service

import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaTypeKind.*
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.backend.common.descriptors.isSuspend
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isArray
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isNumber
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.coroutines.isSuspendLambda
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.ClassKind.ANNOTATION_CLASS
import org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.DescriptorUtils.isObject
import org.jetbrains.kotlin.resolve.calls.tower.isSynthesized
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassOrAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.TypeUtils.isNullableType
import org.jetbrains.kotlin.types.typeUtil.isEnum
import java.nio.file.Path
import java.util.Objects.nonNull

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<KotlinType, KotlinMetaType>()

    fun analyzeKotlinSources(root: Path): List<KotlinMetaClass> {
        val analysisResult = useKotlinCompiler(KotlinCompilerConfiguration(root), KotlinToJVMBytecodeCompiler::analyze)
                ?.takeIf { result -> !result.isError() }
                ?: return emptyList()
        return root.toFile().listFiles()!!
                .map { file -> file.name }
                .flatMap { packageName -> collectClasses(analysisResult, packageName) }
                .asSequence()
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                .filter { descriptor -> !descriptor.isInner }
                .filter { descriptor -> !descriptor.classId!!.isNestedClass }
                .filter { descriptor -> !descriptor.isSealed() }
                .filter { descriptor -> !descriptor.classId!!.isLocal }
                .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                .map { descriptor -> descriptor.asMetaClass() }
                .distinctBy { metaClass -> metaClass.type.typeName }
                .toList()
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

    private fun KotlinType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = putIfAbsent(cache, this) {
        when (this) {
            is SimpleType -> asMetaType(variance)
            is FlexibleType -> asMetaType(variance)
            is DeferredType -> asMetaType(variance)
            else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun SimpleType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType = when {
        isEnum() -> {
            val classId = constructor.declarationDescriptor?.classId!!
            KotlinMetaType(
                    originalType = this,
                    kind = ENUM_KIND,
                    classFullName = classId.asSingleFqName().asString(),
                    className = classId.relativeClassName.pathSegments().last().asString(),
                    classPackageName = classId.packageFqName.asString(),
                    typeName = classId.asSingleFqName().asString()
            )
        }

        isArray(this) -> KotlinMetaType(
                originalType = this,
                kind = ARRAY_KIND,
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
                            ?.asMetaType(),
                    typeVariance = variance
            )
        }.apply {
            val newArguments = arguments
                    .dropLast(1)
                    .map { type -> type.asMetaType() }
            functionArgumentTypes.clear()
            functionArgumentTypes.addAll(newArguments)
        }

        constructor.declarationDescriptor is ClassDescriptor -> {
            val classId = constructor.declarationDescriptor?.classId!!
            putIfAbsent(cache, this) {
                KotlinMetaType(
                        originalType = this,
                        kind = CLASS_KIND,
                        classFullName = classId.asSingleFqName().asString(),
                        className = classId.relativeClassName.pathSegments().last().asString(),
                        classPackageName = classId.packageFqName.asString(),
                        typeName = classId.asSingleFqName().asString(),
                        typeVariance = variance,
                )
            }.apply {
                if (typeParameters.isNotEmpty()) return@apply
                arguments
                        .map { projection -> projection.asMetaType() }
                        .forEach(typeParameters::add)
            }
        }

        else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun FlexibleType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType {
        return delegate.asMetaType(variance)
    }

    private fun DeferredType.asMetaType(variance: KotlinTypeVariance? = null): KotlinMetaType {
        return delegate.asMetaType(variance)
    }

    private fun TypeProjection.asMetaType(): KotlinMetaType {
        if (isStarProjection) {
            return KotlinMetaType(
                    originalType = type,
                    kind = WILDCARD_KIND,
                    typeName = toString()
            )
        }
        return type.asMetaType(when (projectionKind) {
            Variance.INVARIANT -> KotlinTypeVariance.INVARIANT
            Variance.IN_VARIANCE -> KotlinTypeVariance.IN
            Variance.OUT_VARIANCE -> KotlinTypeVariance.OUT
        })
    }

    private fun ClassDescriptor.asMetaClass(): KotlinMetaClass = KotlinMetaClass(
            type = defaultType.asMetaType(),

            visibility = visibility,

            modality = modality,

            isObject = isObject(this),

            properties = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<PropertyDescriptor>()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaProperty() },

            constructors = constructors
                    .asSequence()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> descriptor.valueParameters.none { parameter -> parameter.isSuspend || parameter.isSuspendLambda } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            methods = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<FunctionDescriptor>()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .filter { descriptor -> !descriptor.isSuspend }
                    .filter { descriptor -> descriptor.valueParameters.none { parameter -> parameter.isSuspend || parameter.isSuspendLambda } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            innerClasses = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .filter { descriptor -> !descriptor.isInner }
                    .filter { descriptor -> !descriptor.isSealed() }
                    .filter { descriptor -> !descriptor.classId!!.isLocal }
                    .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = getSuperClassOrAny()
                    .takeIf { descriptor -> descriptor.name.asString() != Any::class.qualifiedName!! }
                    ?.takeIf { descriptor -> !isNumber(descriptor.defaultType) }
                    ?.takeIf { descriptor -> descriptor.kind != ENUM_ENTRY }
                    ?.takeIf { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    ?.takeIf { descriptor -> descriptor != this }
                    ?.takeIf { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                    ?.asMetaClass(),

            interfaces = getSuperInterfaces()
                    .asSequence()
                    .filter { descriptor -> !isNumber(descriptor.defaultType) }
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .filter { descriptor -> descriptor != this }
                    .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                    .map { interfaceType -> interfaceType.asMetaClass() }
                    .toList()
    )

    private fun FunctionDescriptor.asMetaMethod() = KotlinMetaMethod(
            name = name.toString(),
            returnType = returnType?.asMetaType(),
            parameters = valueParameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
            visibility = visibility,
            modality = modality,
    )

    private fun PropertyDescriptor.asMetaProperty() = KotlinMetaProperty(
            name = name.toString(),
            type = type.asMetaType(),
            visibility = visibility,
            hasGetter = nonNull(getter),
            hasSetter = nonNull(setter)
    )

    private fun ValueParameterDescriptor.asMetaParameter() = KotlinMetaParameter(
            name = name.toString(),
            type = type.asMetaType(),
            visibility = visibility,
            varargs = nonNull(varargElementType)
    )
}
