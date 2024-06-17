package io.art.generator.parser

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.ClassKind.*
import com.google.devtools.ksp.symbol.Modifier.SUSPEND
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.*
import java.util.*

open class KotlinDescriptorParser : KotlinTypeParser() {
    private val descriptorCache = mutableMapOf<KSClassDeclaration, KotlinMetaClass>()

    protected fun KSClassDeclaration.asMetaClass(): KotlinMetaClass {
        val metaClass = putIfAbsent(descriptorCache, this) {
            KotlinMetaClass(
                type = asStarProjectedType().asMetaType(),

                modifiers = modifiers,

                isObject = classKind == OBJECT,

                isInterface = classKind == INTERFACE,

                properties = getAllProperties()
                    .filter { descriptor -> descriptor.type.resolve().resolved() }
                    .filter { descriptor -> !descriptor.type.resolve().isSuspendFunctionType }
                    .filter { descriptor -> !descriptor.type.resolve().declaration.modifiers.contains(SUSPEND) }
                    .filter { descriptor -> descriptor.type.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> Objects.isNull(descriptor.extensionReceiver) }
                    .associate { descriptor -> descriptor.type.resolve().declaration.simpleName.asString() to descriptor.asMetaProperty() },

                constructors = getConstructors()
                    .filter { descriptor -> descriptor.returnType!!.resolve().resolved() }
                    .filter { descriptor -> !descriptor.returnType!!.resolve().isSuspendFunctionType }
                    .filter { descriptor -> !descriptor.returnType!!.resolve().declaration.modifiers.contains(SUSPEND) }
                    .filter { descriptor -> descriptor.returnType!!.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> descriptor.parameters.none { parameter -> parameter.type.resolve().isSuspendFunctionType || parameter.type.resolve().declaration.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .map { descriptor -> descriptor.asMetaFunction() }
                    .toList(),

                functions = getAllFunctions()
                    .filter { descriptor -> descriptor.returnType!!.resolve().resolved() }
                    .filter { descriptor -> !descriptor.returnType!!.resolve().isSuspendFunctionType }
                    .filter { descriptor -> !descriptor.returnType!!.resolve().declaration.modifiers.contains(SUSPEND) }
                    .filter { descriptor -> descriptor.returnType!!.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> descriptor.parameters.none { parameter -> parameter.type.resolve().isSuspendFunctionType || parameter.type.resolve().declaration.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> Objects.isNull(descriptor.extensionReceiver) }
                    .filter { descriptor -> descriptor.parameters.none { parameter -> parameter.type.resolve().isSuspendFunctionType || parameter.type.resolve().declaration.modifiers.contains(SUSPEND) } }
                    .filter { descriptor -> descriptor.typeParameters.isEmpty() }
                    .filter { descriptor -> Objects.isNull(descriptor.extensionReceiver) }
                    .map { descriptor -> descriptor.asMetaFunction() }
                    .toList(),


                parent = getAllSuperTypes()
                    .firstOrNull()
                    ?.takeIf { descriptor -> descriptor.resolved() }
                    ?.takeIf { descriptor -> (descriptor.declaration as KSClassDeclaration).classKind != ENUM_ENTRY }
                    ?.takeIf { descriptor -> (descriptor.declaration as KSClassDeclaration).classKind != ANNOTATION_CLASS }
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
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
                .filter { descriptor -> descriptor.kind != ClassKind.ANNOTATION_CLASS }
                .filter { descriptor -> !descriptor.isInner }
                .filter { descriptor -> !descriptor.classId!!.isLocal }
                .filter { descriptor -> descriptor.defaultType.constructor.parameters.isEmpty() }
                .associate { descriptor -> descriptor.name.toString() to descriptorCache.getOrElse(descriptor) { descriptor.asMetaClass() } }
            metaClass.innerClasses.putAll(innerClasses)
        }

        if (metaClass.interfaces.isEmpty()) {
            val interfaces = getAllSuperTypes()
                .filter { descriptor -> descriptor.defaultType.resolved() }
                .filter { descriptor -> descriptor.kind != ENUM_ENTRY }
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

    private fun KSFunctionDeclaration.asMetaFunction() = KotlinMetaFunction(
        name = simpleName.asString(),
        returnType = returnType?.asMetaType(),
        parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
        modifiers = modifiers,
        throws = extractThrows()
    )

    private fun KSFunctionDeclaration.extractThrows(): Set<KotlinMetaType> {
        this.extensionReceiver
        val annotation = annotations.find { annotation -> annotation.shortName.asString() == Throws::class.qualifiedName!! }
        val values = annotation?.arguments ?: emptyList()
        if (values.isEmpty()) return emptySet()
        if (values.first() !is List<*>) return emptySet()
        val classes = values.first() as List<*>
        return classes
            .asSequence()
            .map { value -> value as? KSClassDeclaration }
            .filterNotNull()
            .map { classValue -> classValue.getArgumentType(this.module).asMetaType() }
            .toSet()
    }

    private fun KSPropertyDeclaration.asMetaProperty() = KotlinMetaProperty(
        name = simpleName.asString(),
        type = type.asMetaType(),
        modifiers = modifiers,
        getter = getter?.let { function -> KotlinMetaPropertyFunction(kind = KotlinMetaPropertyFunctionKind.GETTER, modifiers = function.modifiers) },
        setter = setter?.let { function -> KotlinMetaPropertyFunction(kind = KotlinMetaPropertyFunctionKind.SETTER, modifiers = function.modifiers) },
    )

    private fun KSValueParameter.asMetaParameter() = KotlinMetaParameter(
        name = name.toString(),
        type = type.asMetaType(),
        modifiers = setOf(),
        varargs = isVararg,
    )
}
