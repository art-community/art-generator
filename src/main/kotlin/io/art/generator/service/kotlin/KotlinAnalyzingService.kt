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
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isArray
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.*
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

    private fun KotlinType.asMetaType(): KotlinMetaType = putIfAbsent(cache, this) {
        when (val unwrapped: UnwrappedType = unwrap()) {
            is SimpleType -> unwrapped.asMetaType()
            else -> KotlinMetaType(originalType = unwrapped, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun SimpleType.asMetaType(): KotlinMetaType = when {
        isArray(this) -> KotlinMetaType(
                originalType = this,
                kind = ARRAY_KIND,
                nullable = isNullableType(this),

                arrayComponentType = constructor.builtIns.getArrayElementType(this).asMetaType(),

                typeName = toString()
        )
        constructor.declarationDescriptor is ClassDescriptor -> putIfAbsent(cache, this) {
            KotlinMetaType(
                    originalType = this,
                    kind = CLASS_KIND,
                    nullable = isNullableType(this),

                    classFullName = constructor.declarationDescriptor!!.classId!!.asSingleFqName().asString(),
                    className = constructor.declarationDescriptor!!.classId!!.relativeClassName.asString(),
                    classPackageName = constructor.declarationDescriptor!!.classId!!.packageFqName.asString(),

                    typeName = constructor.declarationDescriptor!!.classId!!.asSingleFqName().asString()
            )
        }.apply { typeParameters.addAll(arguments.map { projection -> projection.asMetaType() }) }

        else -> KotlinMetaType(originalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun TypeProjection.asMetaType(): KotlinMetaType {
        if (isStarProjection) {
            return KotlinMetaType(
                    originalType = type,
                    kind = WILDCARD_KIND,
                    nullable = isNullableType(type),
                    typeName = toString()
            )
        }
        val unwrap = type.unwrap()
        return when (val descriptor = unwrap.constructor.declarationDescriptor) {
            is TypeParameterDescriptor -> putIfAbsent(cache, unwrap) {
                KotlinMetaType(
                        originalType = unwrap,
                        kind = VARIABLE_KIND,
                        nullable = isNullableType(unwrap),
                        typeName = toString(),
                        typeVariableVariance = when (descriptor.variance) {
                            INVARIANT -> KotlinTypeVariableVariance.INVARIANT
                            IN_VARIANCE -> KotlinTypeVariableVariance.IN
                            OUT_VARIANCE -> KotlinTypeVariableVariance.OUT
                        },
                )
            }.apply { typeVariableBounds.addAll(descriptor.upperBounds.map { type -> type.asMetaType() }) }
            else -> KotlinMetaType(originalType = type, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun ClassDescriptor.asMetaType(): KotlinMetaType = defaultType.asMetaType()

    private fun ClassDescriptor.asMetaClass(): KotlinMetaClass = KotlinMetaClass(
            type = asMetaType(),

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

            interfaces = getSuperInterfaces().map { interfaceType -> interfaceType.asMetaClass() }
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

    private fun VariableDescriptor.asMetaParameter() = KotlinMetaParameter(
            name = name.toString(),
            visibility = visibility,
            type = type.asMetaType()
    )
}
