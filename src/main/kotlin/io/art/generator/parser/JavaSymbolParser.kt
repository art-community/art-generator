@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.parser

import com.sun.tools.javac.code.Flags
import com.sun.tools.javac.code.Symbol
import io.art.core.extensions.CollectionExtensions
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaField
import io.art.generator.model.JavaMetaMethod
import io.art.generator.model.JavaMetaParameter

open class JavaSymbolParser : JavaTypeParser() {
    private val symbolCache = mutableMapOf<Symbol.ClassSymbol, JavaMetaClass>()

    internal fun Symbol.ClassSymbol.asMetaClass(): JavaMetaClass {
        val metaClass = CollectionExtensions.putIfAbsent(symbolCache, this) {
            JavaMetaClass(
                    type = type.asMetaType(),

                    modifiers = modifiers,

                    isInterface = isInterface,

                    fields = members()
                            .symbols
                            .reversed()
                            .asSequence()
                            .filterIsInstance<Symbol.VarSymbol>()
                            .filter { field -> !Flags.asFlagSet(field.flags()).contains(Flags.Flag.SYNTHETIC) }
                            .associate { symbol -> symbol.name.toString() to symbol.asMetaField() },

                    constructors = members()
                            .symbols
                            .reversed()
                            .asSequence()
                            .filterIsInstance<Symbol.MethodSymbol>()
                            .filter { method -> method.isConstructor }
                            .filter { method -> !method.isLambdaMethod }
                            .filter { method -> !method.isDynamic }
                            .filter { method -> !Flags.asFlagSet(method.flags()).contains(Flags.Flag.SYNTHETIC) }
                            .filter { method -> method.typeParameters.isEmpty() }
                            .map { method -> method.asMetaMethod() }
                            .toList(),

                    methods = members()
                            .symbols
                            .reversed()
                            .asSequence()
                            .filterIsInstance<Symbol.MethodSymbol>()
                            .filter { method -> !method.isConstructor }
                            .filter { method -> !method.isLambdaMethod }
                            .filter { method -> !method.isDynamic }
                            .filter { method -> !Flags.asFlagSet(method.flags()).contains(Flags.Flag.SYNTHETIC) }
                            .filter { method -> method.typeParameters.isEmpty() }
                            .map { method -> method.asMetaMethod() }
                            .toList(),

                    parent = superclass
                            ?.let { superclass.tsym as? Symbol.ClassSymbol }
                            ?.takeIf { superclass.tsym?.qualifiedName.toString() != Object::class.java.name }
                            ?.takeIf { symbol -> symbol.typeParameters.isEmpty() }
                            ?.asMetaClass()
            )
        }

        if (metaClass.innerClasses.isEmpty()) {
            val innerClasses = members()
                    .symbols
                    .asSequence()
                    .filterIsInstance<Symbol.ClassSymbol>()
                    .filter { inner -> !Flags.asFlagSet(inner.flags()).contains(Flags.Flag.SYNTHETIC) }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .associate { symbol -> symbol.name.toString() to symbolCache.getOrElse(symbol) { symbol.asMetaClass() } }

            metaClass.innerClasses.putAll(innerClasses)
        }

        if (metaClass.interfaces.isEmpty()) {
            val interfaces = interfaces
                    .asSequence()
                    .map { interfaceType -> interfaceType.tsym }
                    .filterIsInstance<Symbol.ClassSymbol>()
                    .filter { symbol -> !Flags.asFlagSet(symbol.flags()).contains(Flags.Flag.SYNTHETIC) }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .map { symbol -> symbolCache.getOrElse(symbol) { symbol.asMetaClass() } }
                    .toList()
            metaClass.interfaces.addAll(interfaces)
        }

        return metaClass
    }

    private fun Symbol.MethodSymbol.asMetaMethod() = JavaMetaMethod(
            name = name.toString(),
            modifiers = modifiers,
            returnType = returnType.asMetaType(),
            throws = thrownTypes.map { type -> type.asMetaType() }.toSet(),
            parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
    )

    private fun Symbol.VarSymbol.asMetaField() = JavaMetaField(
            name = name.toString(),
            modifiers = modifiers,
            type = type.asMetaType()
    )

    private fun Symbol.VarSymbol.asMetaParameter() = JavaMetaParameter(
            name = name.toString(),
            modifiers = modifiers,
            type = type.asMetaType()
    )
}
