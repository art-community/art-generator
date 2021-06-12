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
import io.art.generator.model.JavaMetaTypeKind.*
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isArray
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.ClassKind.ANNOTATION_CLASS
import org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
import org.jetbrains.kotlin.load.java.JavaClassesTracker
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassOrAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.SimpleType
import org.jetbrains.kotlin.types.TypeProjection
import org.jetbrains.kotlin.types.UnwrappedType
import org.jetbrains.kotlin.types.typeUtil.isEnum
import java.nio.file.Path
import javax.lang.model.element.Modifier

fun analyzeKotlinSources(root: Path) = KotlinAnalyzingService().analyzeKotlinSources(root)

private class KotlinAnalyzingService {
    private val cache = mutableMapOf<KotlinType, JavaMetaType>()

    private class JavaTracker : JavaClassesTracker {
        val classes = mutableListOf<JavaClassDescriptor>()

        override fun onCompletedAnalysis(module: ModuleDescriptor) = Unit

        override fun reportClass(classDescriptor: JavaClassDescriptor) {
            classes += classDescriptor
        }
    }

    fun analyzeKotlinSources(root: Path): List<JavaMetaClass> {
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

    private fun KotlinType.asMetaType(): JavaMetaType = putIfAbsent(cache, this) {
        when (val unwrapped: UnwrappedType = unwrap()) {
            is SimpleType -> unwrapped.asMetaType()
            else -> JavaMetaType(kotlinOriginalType = unwrapped, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun SimpleType.asMetaType(): JavaMetaType = when {
        isEnum() -> {
            val classId = constructor.declarationDescriptor?.classId
            JavaMetaType(
                    kotlinOriginalType = this,
                    kind = ENUM_KIND,
                    classFullName = classId!!.asSingleFqName().asString(),
                    className = classId.relativeClassName.asString(),
                    classPackageName = classId.packageFqName.asString(),
                    typeName = classId.asSingleFqName().asString()
            )
        }

        isArray(this) -> JavaMetaType(
                kotlinOriginalType = this,
                kind = ARRAY_KIND,
                arrayComponentType = constructor.builtIns.getArrayElementType(this).asMetaType(),
                typeName = toString()
        )
//
//        constructor.declarationDescriptor is FunctionClassDescriptor -> putIfAbsent(cache, this) {
//            JavaMetaType(
//                    originalType = this,
//                    kind = FUNCTION_KIND,
//                    nullable = isNullableType(this),
//                    typeName = toString(),
//                    functionResultType = arguments
//                            .takeLast(1)
//                            .firstOrNull()
//                            ?.asMetaType()
//            )
//        }.apply {
//            val newArguments = arguments
//                    .dropLast(1)
//                    .map { type -> type.asMetaType() }
//            functionArgumentTypes.clear()
//            functionArgumentTypes.addAll(newArguments)
//        }

        constructor.declarationDescriptor is ClassDescriptor -> {
            val classId = constructor.declarationDescriptor?.classId
            putIfAbsent(cache, this) {
                JavaMetaType(
                        kotlinOriginalType = this,
                        kind = CLASS_KIND,
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
                JavaMetaType(
                        kotlinOriginalType = this,
                        kind = VARIABLE_KIND,
                        typeName = toString(),
//                        typeVariableVariance = when (typeParameterDescriptor.variance) {
//                            INVARIANT -> KotlinTypeVariableVariance.INVARIANT
//                            IN_VARIANCE -> IN
//                            OUT_VARIANCE -> OUT
//                        },
                )
            }.apply {
                val newBounds = (typeParameterDescriptor).upperBounds.map { type -> type.asMetaType() }
                typeVariableBounds.clear()
                typeVariableBounds.addAll(newBounds)
            }
        }

        else -> JavaMetaType(kotlinOriginalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun TypeProjection.asMetaType(): JavaMetaType {
        val unwrapped = type.unwrap()
        if (isStarProjection) {
            return JavaMetaType(
                    kotlinOriginalType = unwrapped,
                    kind = WILDCARD_KIND,
                    typeName = toString()
            )
        }
        return unwrapped.asMetaType()
    }

    private fun ClassDescriptor.asMetaClass(): JavaMetaClass = JavaMetaClass(
            type = defaultType.asMetaType(),

            modifiers = setOf(Modifier.PUBLIC),

            fields = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<VariableDescriptorWithAccessors>()
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaProperty() },

            constructors = constructors
                    .asSequence()
                    .map { method -> method.asMetaMethod(constructor = true) }
                    .toList(),

            methods = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<FunctionDescriptor>()
                    .map { method -> method.asMetaMethod(constructor = false) }
                    .toList(),

            innerClasses = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = getSuperClassOrAny()
                    .takeIf { descriptor -> descriptor.name.asString() != Any::class.qualifiedName!! }
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

    private fun FunctionDescriptor.asMetaMethod(constructor: Boolean) = JavaMetaMethod(
            name = name.toString(),
            returnType = returnType?.asMetaType() ?: JAVA_VOID_META_TYPE,
            parameters = valueParameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
            typeParameters = if (!constructor) typeParameters.map { typeParameter -> typeParameter.defaultType.asMetaType() } else emptyList(),
            modifiers = setOf(Modifier.PUBLIC)
    )

    private fun VariableDescriptorWithAccessors.asMetaProperty() = JavaMetaField(
            name = name.toString(),
            type = type.asMetaType(),
            modifiers = setOf(Modifier.PUBLIC)
    )

    private fun ValueParameterDescriptor.asMetaParameter() = JavaMetaParameter(
            name = name.toString(),
            type = type.asMetaType(),
            modifiers = setOf(Modifier.PUBLIC)
    )
}
