package io.art.generator.parser

import io.art.core.extensions.CollectionExtensions
import io.art.core.factory.MapFactory.concurrentMap
import io.art.generator.model.*
import org.jetbrains.kotlin.backend.common.descriptors.isSuspend
import org.jetbrains.kotlin.codegen.coroutines.isSuspendLambdaOrLocalFunction
import org.jetbrains.kotlin.coroutines.isSuspendLambda
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.resolve.calls.tower.isSynthesized
import org.jetbrains.kotlin.resolve.constants.KClassValue
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassNotAny
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import java.util.*

open class KotlinDescriptorParser : KotlinTypeParser() {
    private val descriptorCache = concurrentMap<ClassDescriptor, KotlinMetaClass>()

    protected fun ClassDescriptor.asMetaClass(): KotlinMetaClass {
        val metaClass = CollectionExtensions.putIfAbsent(descriptorCache, this) {
            KotlinMetaClass(
                    type = defaultType.asMetaType(),

                    visibility = visibility,

                    modality = modality,

                    isObject = DescriptorUtils.isObject(this),

                    isInterface = DescriptorUtils.isInterface(this),

                    properties = DescriptorUtils.getAllDescriptors(defaultType.memberScope)
                            .asSequence()
                            .filterIsInstance<PropertyDescriptor>()
                            .filter { descriptor -> descriptor.type.resolved() }
                            .filter { descriptor -> !descriptor.isSynthesized }
                            .filter { descriptor -> !descriptor.isSuspend }
                            .filter { descriptor -> descriptor.valueParameters.none { parameter -> parameter.isSuspend || parameter.isSuspendLambda } }
                            .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                            .filter { descriptor -> Objects.isNull(descriptor.extensionReceiverParameter) }
                            .associate { descriptor -> descriptor.name.toString() to descriptor.asMetaProperty() },

                    constructors = constructors
                            .asSequence()
                            .filter { descriptor -> descriptor.returnType.resolved() && descriptor.valueParameters.all { parameter -> parameter.type.resolved() } }
                            .filter { descriptor -> !descriptor.isSuspendLambdaOrLocalFunction() }
                            .filter { descriptor -> !descriptor.isSynthesized }
                            .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                            .filter { descriptor -> descriptor.valueParameters.none { parameter -> parameter.isSuspend || parameter.isSuspendLambda } }
                            .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                            .map { descriptor -> descriptor.asMetaFunction() }
                            .toList(),

                    functions = DescriptorUtils.getAllDescriptors(defaultType.memberScope)
                            .asSequence()
                            .filterIsInstance<FunctionDescriptor>()
                            .filter { descriptor -> descriptor.returnType?.resolved() ?: true }
                            .filter { descriptor -> descriptor.valueParameters.all { parameter -> parameter.type.resolved() } }
                            .filter { descriptor -> !descriptor.isSuspendLambdaOrLocalFunction() }
                            .filter { descriptor -> !descriptor.isSynthesized }
                            .filter { descriptor -> !descriptor.isSuspend }
                            .filter { descriptor -> descriptor.valueParameters.none { parameter -> parameter.isSuspend || parameter.isSuspendLambda } }
                            .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                            .filter { descriptor -> Objects.isNull(descriptor.extensionReceiverParameter) }
                            .map { descriptor -> descriptor.asMetaFunction() }
                            .toList(),


                    parent = getSuperClassNotAny()
                            ?.takeIf { descriptor -> descriptor.defaultType.resolved() }
                            ?.takeIf { descriptor -> descriptor.kind != ClassKind.ENUM_ENTRY }
                            ?.takeIf { descriptor -> descriptor.kind != ClassKind.ANNOTATION_CLASS }
                            ?.takeIf { descriptor -> !descriptor.isInner }
                            ?.takeIf { descriptor -> !descriptor.classId!!.isLocal }
                            ?.takeIf { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                            ?.asMetaClass()
            )
        }

        if (metaClass.innerClasses.isEmpty()) {
            val innerClasses = DescriptorUtils.getAllDescriptors(defaultType.memberScope)
                    .asSequence()
                    .filterIsInstance<ClassDescriptor>()
                    .filter { descriptor -> descriptor.defaultType.resolved() }
                    .filter { descriptor -> descriptor.kind != ClassKind.ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ClassKind.ANNOTATION_CLASS }
                    .filter { descriptor -> !descriptor.isInner }
                    .filter { descriptor -> !descriptor.classId!!.isLocal }
                    .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                    .associate { descriptor -> descriptor.name.toString() to descriptorCache.getOrElse(descriptor) { descriptor.asMetaClass() } }
            metaClass.innerClasses.putAll(innerClasses)
        }

        if (metaClass.interfaces.isEmpty()) {
            val interfaces = getSuperInterfaces()
                    .asSequence()
                    .filter { descriptor -> descriptor.defaultType.resolved() }
                    .filter { descriptor -> descriptor.kind != ClassKind.ENUM_ENTRY }
                    .filter { descriptor -> descriptor.kind != ClassKind.ANNOTATION_CLASS }
                    .filter { descriptor -> !descriptor.isInner }
                    .filter { descriptor -> !descriptor.classId!!.isLocal }
                    .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                    .map { descriptor -> descriptorCache.getOrElse(descriptor) { descriptor.asMetaClass() } }
                    .toList()
            metaClass.interfaces.addAll(interfaces)
        }

        return metaClass
    }

    private fun FunctionDescriptor.asMetaFunction() = KotlinMetaFunction(
            name = name.toString(),
            returnType = returnType?.asMetaType(),
            parameters = valueParameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
            visibility = visibility,
            modality = modality,
            throws = extractThrows()
    )

    private fun FunctionDescriptor.extractThrows(): Set<KotlinMetaType> {
        val annotation = annotations.findAnnotation(FqName(Throws::class.qualifiedName!!)) ?: return emptySet()
        val values = annotation.allValueArguments.values
        if (values.isEmpty()) return emptySet()
        if (values.first() !is List<*>) return emptySet()
        val classes = values.first() as List<*>
        return classes
                .asSequence()
                .map { value -> value as? KClassValue }
                .filterNotNull()
                .map { classValue -> classValue.getArgumentType(this.module).asMetaType() }
                .toSet()
    }

    private fun PropertyDescriptor.asMetaProperty() = KotlinMetaProperty(
            name = name.toString(),
            type = type.asMetaType(),
            visibility = visibility,
            getter = getter?.let { function -> KotlinMetaPropertyFunction(kind = KotlinMetaPropertyFunctionKind.GETTER, visibility = function.visibility) },
            setter = setter?.let { function -> KotlinMetaPropertyFunction(kind = KotlinMetaPropertyFunctionKind.SETTER, visibility = function.visibility) },
    )

    private fun ValueParameterDescriptor.asMetaParameter() = KotlinMetaParameter(
            name = name.toString(),
            type = type.asMetaType(),
            visibility = visibility,
            varargs = Objects.nonNull(varargElementType)
    )
}
