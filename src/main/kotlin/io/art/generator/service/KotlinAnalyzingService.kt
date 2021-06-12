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

import io.art.core.constants.StringConstants.DOT
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.*
import io.art.generator.model.JavaMetaTypeKind.*
import io.art.generator.provider.KotlinCompilerConfiguration
import io.art.generator.provider.KotlinCompilerProvider.useKotlinCompiler
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.backend.jvm.lower.isPrivate
import org.jetbrains.kotlin.backend.jvm.lower.isProtected
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.builtins.KotlinBuiltIns.*
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.ClassKind.ANNOTATION_CLASS
import org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
import org.jetbrains.kotlin.load.java.JavaClassesTracker
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils.getAllDescriptors
import org.jetbrains.kotlin.resolve.calls.tower.isSynthesized
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassOrAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.Variance.OUT_VARIANCE
import org.jetbrains.kotlin.types.typeUtil.isEnum
import org.jetbrains.kotlin.types.typeUtil.makeNotNullable
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
                .filter { descriptor -> !descriptor.isInner }
                .filter { descriptor -> !descriptor.classId!!.isNestedClass }
                .filter { descriptor -> !descriptor.isSealed() }
                .filter { descriptor -> !descriptor.classId!!.isLocal }
                .map { descriptor -> descriptor.asMetaClass() }
                .distinctBy { metaClass -> metaClass.type.typeName }
                .toList()
        val javaClasses = javaTracker.classes
                .toList()
                .asSequence()
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                .filter { descriptor -> !descriptor.classId!!.isNestedClass }
                .filter { descriptor -> !descriptor.isSealed() }
                .filter { descriptor -> !descriptor.classId!!.isLocal }
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

    private fun KotlinType.asMetaType(): JavaMetaType {
        var unwrapped = unwrap()
        if (unwrapped.isMarkedNullable) {
            unwrapped = unwrapped.makeNotNullable().unwrap()
        }
        val builtIns = unwrapped.constructor.builtIns
        return putIfAbsent(cache, unwrapped) {

            if (isPrimitiveArray(unwrapped)) {
                val elementType = builtIns.getArrayElementType(unwrapped).unwrap()
                return@putIfAbsent JavaMetaType(
                        kotlinOriginalType = unwrapped,
                        kind = ARRAY_KIND,
                        typeName = unwrapped.constructor.declarationDescriptor!!.fqNameSafe.asString(),
                        arrayComponentType = elementType.asPrimitiveMetaType(builtIns)
                )
            }

            if (isPrimitiveTypeOrNullablePrimitiveType(unwrapped)) {
                return@putIfAbsent unwrapped.asPrimitiveMetaType(builtIns)
            }

            if (isStringOrNullableString(unwrapped)) {
                return@putIfAbsent JavaMetaType(
                        kind = CLASS_KIND,
                        kotlinOriginalType = unwrapped,
                        typeName = String::class.java.typeName,
                        className = String::class.java.simpleName,
                        classPackageName = String::class.java.packageName,
                        classFullName = String::class.java.name
                )
            }

            if (isNumber(unwrapped)) {
                return@putIfAbsent JavaMetaType(
                        kind = CLASS_KIND,
                        kotlinOriginalType = unwrapped,
                        typeName = Number::class.java.typeName,
                        className = Number::class.java.simpleName,
                        classPackageName = Number::class.java.packageName,
                        classFullName = Number::class.java.name
                )
            }

            when (unwrapped) {
                is SimpleType -> unwrapped.asMetaType()
                is FlexibleType -> unwrapped.asMetaType()
                else -> JavaMetaType(kotlinOriginalType = unwrapped, kind = UNKNOWN_KIND, typeName = toString())
            }
        }
    }

    private fun UnwrappedType.asPrimitiveMetaType(builtIns: KotlinBuiltIns) = JavaMetaType(
            kotlinOriginalType = this,
            kind = PRIMITIVE_KIND,
            typeName = when (this) {
                builtIns.unitType -> Void.TYPE.typeName
                builtIns.booleanType -> Boolean::class.java.typeName
                builtIns.intType -> Int::class.java.typeName
                builtIns.shortType -> Short::class.java.typeName
                builtIns.charType -> Char::class.java.typeName
                builtIns.doubleType -> Double::class.java.typeName
                builtIns.floatType -> Float::class.java.typeName
                builtIns.longType -> Long::class.java.typeName
                else -> Byte::class.java.typeName
            }
    )

    private fun SimpleType.asMetaType(): JavaMetaType = when {
        isEnum() -> {
            val classId = constructor.declarationDescriptor?.classId!!
            JavaMetaType(
                    kotlinOriginalType = this,
                    kind = ENUM_KIND,
                    classFullName = classId.asSingleFqName().asString(),
                    className = classId.relativeClassName.pathSegments().last().asString(),
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

        /*
        constructor.declarationDescriptor is FunctionClassDescriptor -> putIfAbsent(cache, this) {
            JavaMetaType(
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
*/

        constructor.declarationDescriptor is ClassDescriptor -> {
            val classId = constructor.declarationDescriptor?.classId!!
            putIfAbsent(cache, this) {
                JavaMetaType(
                        kotlinOriginalType = this,
                        kind = CLASS_KIND,
                        classFullName = classId.asSingleFqName().asString(),
                        className = classId.relativeClassName.pathSegments().last().asString(),
                        classPackageName = classId.packageFqName.asString(),
                        typeName = classId.asSingleFqName().asString()
                )
            }.apply {
//                if (typeParameters.isNotEmpty()) return@apply
//                arguments
//                        .map { projection -> projection.asMetaType() }
//                        .forEach(typeParameters::add)
            }
        }

        constructor.declarationDescriptor is TypeParameterDescriptor -> {
            val typeParameterDescriptor = constructor.declarationDescriptor as TypeParameterDescriptor
            putIfAbsent(cache, this) {
                JavaMetaType(
                        kotlinOriginalType = this,
                        kind = VARIABLE_KIND,
                        typeName = toString()
                )
            }.apply {
                if (typeVariableBounds.isNotEmpty()) return@apply
                if (typeParameterDescriptor.variance != OUT_VARIANCE) {
                    typeParameterDescriptor
                            .upperBounds
                            .map { bound -> bound.asMetaType() }
                            .forEach(typeVariableBounds::add)
                }
            }
        }

        else -> JavaMetaType(kotlinOriginalType = this, kind = UNKNOWN_KIND, typeName = toString())
    }

    private fun FlexibleType.asMetaType(): JavaMetaType {
        return delegate.asMetaType()
    }

    private fun TypeProjection.asMetaType(): JavaMetaType {
        if (isStarProjection) {
            return JavaMetaType(
                    kotlinOriginalType = type,
                    kind = WILDCARD_KIND,
                    typeName = toString()
            )
        }
        return type.asMetaType()
    }

    private fun ClassDescriptor.asMetaClass(): JavaMetaClass = JavaMetaClass(
            type = defaultType.asMetaType(),

            modifiers = setOf(Modifier.PUBLIC),

            fields = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<VariableDescriptorWithAccessors>()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaProperty() },

            constructors = constructors
                    .asSequence()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .map { method -> method.asMetaMethod(constructor = true) }
                    .toList(),

            methods = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<FunctionDescriptor>()
                    .filter { descriptor -> !descriptor.isSynthesized }
                    .map { method -> method.asMetaMethod(constructor = false) }
                    .toList(),

            innerClasses = getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .filter { descriptor -> !descriptor.isInner }
                    .filter { descriptor -> !descriptor.isSealed() }
                    .filter { descriptor -> !descriptor.classId!!.isLocal }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = getSuperClassOrAny()
                    .takeIf { descriptor -> descriptor.name.asString() != Any::class.qualifiedName!! }
                    ?.takeIf { descriptor -> !isNumber(descriptor.defaultType) }
                    ?.takeIf { descriptor -> descriptor.kind != ENUM_ENTRY }
                    ?.takeIf { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    ?.takeIf { descriptor -> descriptor != this }
                    ?.asMetaClass(),

            interfaces = getSuperInterfaces()
                    .asSequence()
                    .filter { descriptor -> !isNumber(descriptor.defaultType) }
                    .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ANNOTATION_CLASS }
                    .filter { descriptor -> descriptor != this }
                    .map { interfaceType -> interfaceType.asMetaClass() }
                    .toList()
    )

    private fun FunctionDescriptor.asMetaMethod(constructor: Boolean) = JavaMetaMethod(
            name = name.toString(),
            returnType = returnType?.takeIf(::isUnit)
                    ?.let { JAVA_VOID_META_TYPE }
                    ?: returnType?.asMetaType()
                    ?: JAVA_VOID_META_TYPE,
            parameters = valueParameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
            typeParameters = if (!constructor) typeParameters.map { typeParameter -> typeParameter.defaultType.asMetaType() } else emptyList(),
            modifiers = if (!visibility.isPrivate && !visibility.isProtected && visibility.isPublicAPI) setOf(Modifier.PUBLIC) else setOf()
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
