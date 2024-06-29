package io.art.generator.parser

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.isInternal
import com.google.devtools.ksp.isLocal
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.ClassKind.*
import com.google.devtools.ksp.symbol.Modifier.SUSPEND
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaPropertyFunctionKind.*
import java.util.*

open class KotlinDescriptorParser : KotlinTypeParser() {
    private val cache = mutableMapOf<KSClassDeclaration, KotlinMetaClass>()

    protected fun KSClassDeclaration.asMetaClass(): KotlinMetaClass {
        val metaClass = putIfAbsent(cache, this) {
            KotlinMetaClass(
                type = asStarProjectedType().asMetaType(),

                modifiers = modifiers,

                isObject = classKind == OBJECT,

                isInterface = classKind == INTERFACE,

                properties = getAllProperties()
                    .filter { declaration -> declaration.type.resolve().resolved() }
                    .filter { declaration -> !declaration.type.resolve().isSuspend() }
                    .filter { declaration -> declaration.type.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { declaration -> declaration.typeParameters.isEmpty() }
                    .filter { declaration -> Objects.isNull(declaration.extensionReceiver) }
                    .associate { declaration -> declaration.type.resolve().declaration.simpleName.asString() to declaration.asMetaProperty() },

                constructors = getConstructors()
                    .filter { declaration -> declaration.returnType!!.resolve().resolved() }
                    .filter { declaration -> !declaration.returnType!!.resolve().isSuspend() }
                    .filter { declaration -> declaration.returnType!!.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { declaration -> declaration.typeParameters.isEmpty() }
                    .filter { declaration -> Objects.isNull(declaration.extensionReceiver) }
                    .filter { declaration -> declaration.parameters.none { parameter -> parameter.type.resolve().isSuspend() } }
                    .filter { declaration -> declaration.typeParameters.isEmpty() }
                    .map { declaration -> declaration.asMetaFunction() }
                    .toList(),

                functions = getAllFunctions()
                    .filter { declaration -> declaration.returnType!!.resolve().resolved() }
                    .filter { declaration -> !declaration.returnType!!.resolve().isSuspend() }
                    .filter { declaration -> declaration.returnType!!.resolve().declaration.typeParameters.none { parameter -> parameter.modifiers.contains(SUSPEND) } }
                    .filter { declaration -> declaration.parameters.none { parameter -> parameter.type.resolve().isSuspend() } }
                    .filter { declaration -> declaration.typeParameters.isEmpty() }
                    .filter { declaration -> Objects.isNull(declaration.extensionReceiver) }
                    .map { declaration -> declaration.asMetaFunction() }
                    .toList(),


                parent = getAllSuperTypes()
                    .firstOrNull()
                    ?.takeIf { declaration -> declaration.resolved() }
                    ?.takeIf { declaration -> declaration.declaration is KSClassDeclaration }
                    ?.let { declaration -> (declaration.declaration as KSClassDeclaration) }
                    ?.takeIf { declaration -> declaration.classKind != ENUM_ENTRY }
                    ?.takeIf { declaration -> declaration.classKind != ANNOTATION_CLASS }
                    ?.takeIf { declaration -> declaration.isInternal() }
                    ?.takeIf { declaration -> declaration.isLocal() }
                    ?.takeIf { declaration -> declaration.primaryConstructor?.parameters?.isEmpty() == true }
                    ?.asMetaClass()
            )
        }

        if (metaClass.innerClasses.isEmpty()) {
            val innerClasses = declarations
                .filterIsInstance<KSClassDeclaration>()
                .filter { declaration -> declaration.asStarProjectedType().resolved() }
                .filter { declaration -> declaration.classKind != ENUM_ENTRY }
                .filter { declaration -> declaration.classKind != ANNOTATION_CLASS }
                .filter { declaration -> !declaration.isInternal() }
                .filter { declaration -> !declaration.isLocal() }
                .filter { declaration -> declaration.primaryConstructor?.parameters?.isEmpty() == true }
                .associate { declaration -> declaration.simpleName.asString() to cache.getOrElse(declaration) { declaration.asMetaClass() } }
            metaClass.innerClasses.putAll(innerClasses)
        }

        if (metaClass.interfaces.isEmpty()) {
            val interfaces = getAllSuperTypes()
                .filterIsInstance<KSClassDeclaration>()
                .filter { declaration -> declaration.asStarProjectedType().resolved() }
                .filter { declaration -> declaration.classKind != ENUM_ENTRY }
                .filter { declaration -> declaration.classKind != ANNOTATION_CLASS }
                .filter { declaration -> !declaration.isInternal() }
                .filter { declaration -> !declaration.isLocal() }
                .filter { declaration -> declaration.primaryConstructor?.parameters?.isEmpty() == true }
                .map { declaration -> cache.getOrElse(declaration) { declaration.asMetaClass() } }
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
        val annotation = annotations.find { annotation -> annotation.shortName.asString() == Throws::class.qualifiedName!! }
        val values = annotation?.arguments ?: emptyList()
        if (values.isEmpty()) return emptySet()
        val classes = values.first()
        return emptySet()
    }

    private fun KSPropertyDeclaration.asMetaProperty() = KotlinMetaProperty(
        name = simpleName.asString(),
        type = type.asMetaType(),
        modifiers = modifiers,
        getter = getter?.let { function -> KotlinMetaPropertyFunction(kind = GETTER, modifiers = function.modifiers) },
        setter = setter?.let { function -> KotlinMetaPropertyFunction(kind = SETTER, modifiers = function.modifiers) },
    )

    private fun KSValueParameter.asMetaParameter() = KotlinMetaParameter(
        name = name.toString(),
        type = type.asMetaType(),
        modifiers = setOf(),
        varargs = isVararg,
    )
}

fun KSType.isSuspend() = isSuspendFunctionType || declaration.modifiers.contains(SUSPEND)