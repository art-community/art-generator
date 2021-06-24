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

@file:Suppress(JAVA_MODULE_SUPPRESSION)

package io.art.generator.service

import com.sun.tools.javac.code.Flags.Flag.SYNTHETIC
import com.sun.tools.javac.code.Flags.asFlagSet
import com.sun.tools.javac.code.Symbol.*
import com.sun.tools.javac.code.Type
import io.art.core.constants.StringConstants.DOT
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.CollectionExtensions.putIfAbsent
import io.art.generator.configuration.SourceConfiguration
import io.art.generator.constants.ANALYZE_COMPLETED
import io.art.generator.constants.ANALYZING_MESSAGE
import io.art.generator.constants.JAVA_LOGGER
import io.art.generator.constants.JAVA_MODULE_SUPPRESSION
import io.art.generator.model.*
import io.art.generator.model.JavaMetaTypeKind.*
import io.art.generator.provider.JavaCompilerConfiguration
import io.art.generator.provider.JavaCompilerProvider.useJavaCompiler
import java.nio.file.Path
import javax.lang.model.element.ElementKind.ENUM
import javax.lang.model.type.TypeMirror

fun analyzeJavaSources(source: SourceConfiguration, sources: Sequence<Path>, metaClassName: String) = JavaAnalyzingService()
        .analyzeJavaSources(source, sources, metaClassName)

private class JavaAnalyzingService {
    private val cache = mutableMapOf<TypeMirror, JavaMetaType>()

    fun analyzeJavaSources(source: SourceConfiguration, sources: Sequence<Path>, metaClassName: String): List<JavaMetaClass> {
        if (!source.root.toFile().exists()) return emptyList()
        JAVA_LOGGER.info(ANALYZING_MESSAGE(source.root))
        return useJavaCompiler(JavaCompilerConfiguration(source.root, sources, source.classpath)) { task ->
            task.analyze()
                    .asSequence()
                    .filter { input -> input.kind.isClass || input.kind.isInterface || input.kind == ENUM }
                    .map { element -> (element as ClassSymbol) }
                    .filter { symbol -> symbol.className() != metaClassName }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .map { symbol -> symbol.asMetaClass() }
                    .distinctBy { metaClass -> metaClass.type.typeName }
                    .toList()
                    .apply { JAVA_LOGGER.info(ANALYZE_COMPLETED(map { metaClass -> metaClass.type.typeName })) }
        }
    }

    private fun TypeMirror.asMetaType(): JavaMetaType = putIfAbsent(cache, this) {
        when (this) {
            is Type.ArrayType -> asMetaType()

            is Type.WildcardType -> asMetaType()

            is Type.ClassType -> asMetaType()

            is Type -> when {
                isPrimitiveOrVoid -> JavaMetaType(
                        javaOriginalType = this,
                        kind = PRIMITIVE_KIND,
                        typeName = tsym.qualifiedName.toString()
                )
                else -> JavaMetaType(
                        javaOriginalType = this,
                        kind = UNKNOWN_KIND,
                        typeName = tsym?.qualifiedName?.toString() ?: toString()
                )
            }

            else -> JavaMetaType(javaOriginalType = this, kind = UNKNOWN_KIND, typeName = toString())
        }
    }

    private fun Type.ClassType.asMetaType(): JavaMetaType {
        val type = putIfAbsent(cache, this) {
            JavaMetaType(
                    javaOriginalType = this,
                    classFullName = tsym.qualifiedName.toString(),
                    kind = when {
                        !asElement().isEnum -> CLASS_KIND
                        asElement().isEnum -> ENUM_KIND
                        else -> UNKNOWN_KIND
                    },
                    typeName = tsym.qualifiedName.toString(),
                    className = tsym.simpleName.toString(),
                    classPackageName = tsym.qualifiedName
                            .toString()
                            .takeIf { name -> name.contains(DOT) }?.substringBefore(".${tsym.simpleName}")
                            ?: EMPTY_STRING
            )
        }
        if (type.typeParameters.isNotEmpty()) return type
        val newArguments = typeArguments
                .asSequence()
                .map { argument -> argument.asMetaType() }
        type.typeParameters.addAll(newArguments)
        return type
    }

    private fun Type.ArrayType.asMetaType(): JavaMetaType = putIfAbsent(cache, this) {
        JavaMetaType(
                javaOriginalType = this,
                kind = ARRAY_KIND,
                typeName = tsym.qualifiedName.toString(),
                arrayComponentType = componentType.asMetaType()
        )
    }

    private fun Type.WildcardType.asMetaType(): JavaMetaType = putIfAbsent(cache, this) {
        JavaMetaType(
                javaOriginalType = this,
                kind = WILDCARD_KIND,
                typeName = type.toString(),
                wildcardSuperBound = superBound?.asMetaType(),
                wildcardExtendsBound = extendsBound?.asMetaType()
        )
    }

    private fun ClassSymbol.asMetaClass(): JavaMetaClass = JavaMetaClass(
            type = type.asMetaType(),

            modifiers = modifiers,

            fields = members()
                    .symbols
                    .reversed()
                    .asSequence()
                    .filterIsInstance<VarSymbol>()
                    .filter { field -> !asFlagSet(field.flags()).contains(SYNTHETIC) }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaField() },

            constructors = members()
                    .symbols
                    .reversed()
                    .asSequence()
                    .filterIsInstance<MethodSymbol>()
                    .filter { method -> method.isConstructor }
                    .filter { method -> !method.isLambdaMethod }
                    .filter { method -> !method.isDynamic }
                    .filter { method -> !asFlagSet(method.flags()).contains(SYNTHETIC) }
                    .filter { method -> method.typeParameters.isEmpty() }
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            methods = members()
                    .symbols
                    .reversed()
                    .asSequence()
                    .filterIsInstance<MethodSymbol>()
                    .filter { method -> !method.isConstructor }
                    .filter { method -> !method.isLambdaMethod }
                    .filter { method -> !method.isDynamic }
                    .filter { method -> !asFlagSet(method.flags()).contains(SYNTHETIC) }
                    .filter { method -> method.typeParameters.isEmpty() }
                    .map { method -> method.asMetaMethod() }
                    .toList(),

            innerClasses = members()
                    .symbols
                    .asSequence()
                    .filterIsInstance<ClassSymbol>()
                    .filter { inner -> !asFlagSet(inner.flags()).contains(SYNTHETIC) }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .associate { symbol -> symbol.name.toString() to symbol.asMetaClass() },

            parent = superclass
                    ?.let { superclass.tsym as? ClassSymbol }
                    ?.takeIf { superclass.tsym?.qualifiedName.toString() != Object::class.java.name }
                    ?.takeIf { symbol -> symbol.typeParameters.isEmpty() }
                    ?.asMetaClass(),

            interfaces = interfaces
                    .asSequence()
                    .map { interfaceType -> interfaceType.tsym }
                    .filterIsInstance<ClassSymbol>()
                    .filter { interfaceType -> !asFlagSet(interfaceType.flags()).contains(SYNTHETIC) }
                    .filter { symbol -> symbol.typeParameters.isEmpty() }
                    .map { interfaceField -> interfaceField.asMetaClass() }
                    .toList()

    )

    private fun MethodSymbol.asMetaMethod() = JavaMetaMethod(
            name = name.toString(),
            modifiers = modifiers,
            returnType = returnType.asMetaType(),
            parameters = parameters.associate { parameter -> parameter.name.toString() to parameter.asMetaParameter() },
    )

    private fun VarSymbol.asMetaField() = JavaMetaField(
            name = name.toString(),
            modifiers = modifiers,
            type = type.asMetaType()
    )

    private fun VarSymbol.asMetaParameter() = JavaMetaParameter(
            name = name.toString(),
            modifiers = modifiers,
            type = type.asMetaType()
    )
}
