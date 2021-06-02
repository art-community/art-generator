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

package io.art.generator.meta.templates

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.CodeBlock.join
import com.squareup.javapoet.CodeBlock.of
import com.squareup.javapoet.TypeName
import io.art.core.constants.CompilerSuppressingWarnings.ALL
import io.art.core.constants.StringConstants.*
import io.art.generator.meta.constants.CASTER_CLASS_NAME
import io.art.generator.meta.constants.OBJECT_CLASS_NAME
import io.art.generator.meta.model.JavaMetaParameter
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.service.extractClass
import io.art.generator.meta.service.hasVariable
import javax.lang.model.element.Modifier

fun suppressAnnotation(): AnnotationSpec = AnnotationSpec.builder(SuppressWarnings::class.java)
        .addMember("value", "\$S", ALL)
        .build()

fun newStatement() = "new \$T()"

fun returnStatement() = "return \$L;"

fun returnStatement(block: CodeBlock): CodeBlock = join(listOf(of("return"), block), SPACE)

fun returnNullStatement() = "return null;"

fun registerNewStatement() = "register(new \$T())"

fun computeStatement() = "meta.compute(dependencies);"


fun invokeWithoutArgumentsInstanceStatement(method: String): CodeBlock {
    return of("instance.$method();")
}

fun invokeWithoutArgumentsStaticStatement(method: String, type: JavaMetaType): CodeBlock {
    return of("\$T.$method();", type.extractClass())
}


fun invokeOneArgumentInstanceStatement(method: String, parameter: JavaMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    if (!parameter.type.hasVariable()) {
        return of("instance.$method((\$T)(argument));", parameterClass)
    }
    return of("instance.$method(\$T.cast(argument));", CASTER_CLASS_NAME)
}

fun invokeOneArgumentStaticStatement(method: String, type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    if (!parameter.type.hasVariable()) {
        return of("\$T.$method((\$T)(argument));", type.extractClass(), parameterClass)
    }
    return of("\$T.$method(\$T.cast(argument));", type.extractClass(), CASTER_CLASS_NAME)
}


fun invokeInstanceStatement(method: String, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    val parametersFormat = parameters.values.mapIndexed { index, parameter ->
        if (!parameter.type.hasVariable()) {
            return@mapIndexed "(\$T)(arguments[$index])"
        }
        return@mapIndexed "\$T.cast(arguments[$index])"
    }
    val format = "instance.$method(${parametersFormat.joinToString(COMMA)});"
    val parametersBlock = parameters.values.map { parameter ->
        if (!parameter.type.hasVariable()) {
            return@map parameter.type.extractClass()
        }
        return@map CASTER_CLASS_NAME
    }
    return of(format, *parametersBlock.toTypedArray())
}

fun invokeStaticStatement(method: String, type: JavaMetaType, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    val parametersFormat = parameters.values.mapIndexed { index, parameter ->
        if (!parameter.type.hasVariable()) {
            return@mapIndexed "(\$T)(arguments[$index])"
        }
        return@mapIndexed "\$T.cast(arguments[$index])"
    }
    val format = "\$T.$method(${parametersFormat.joinToString(COMMA)});"
    val parametersBlock = parameters.values.map { parameter ->
        if (!parameter.type.hasVariable()) {
            return@map parameter.type.extractClass()
        }
        return@map CASTER_CLASS_NAME
    }
    return of(format, type.extractClass(), *parametersBlock.toTypedArray())
}


fun returnInvokeWithoutArgumentsConstructorStatement(type: JavaMetaType): CodeBlock {
    if (type.typeParameters.isNotEmpty()) return of("return new \$T<>();", type.extractClass())
    return of("return new \$T();", type.extractClass())
}

fun returnInvokeOneArgumentConstructorStatement(type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        if (!parameter.type.hasVariable()) {
            return of("return new \$T<>(\$T.cast(argument));", type.extractClass(), CASTER_CLASS_NAME)
        }
        return of("return new \$T<>((\$T)(argument));", type.extractClass(), parameter.type.extractClass())
    }
    if (!parameter.type.hasVariable()) {
        return of("return new \$T(\$T.cast(argument));", type.extractClass(), CASTER_CLASS_NAME)
    }
    return of("return new \$T((\$T)(argument));", type.extractClass(), parameter.type.extractClass())
}

fun returnInvokeConstructorStatement(type: JavaMetaType, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    val parametersFormat = parameters.values.mapIndexed { index, parameter ->
        if (!parameter.type.hasVariable()) {
            return@mapIndexed "(\$T)(arguments[$index])"
        }
        return@mapIndexed "\$T.cast(arguments[$index])"
    }
    if (type.typeParameters.isNotEmpty()) {
        val format = "return new \$T<>(${parametersFormat.joinToString(COMMA)});"
        val parametersBlock = parameters.values.map { parameter ->
            if (!parameter.type.hasVariable()) {
                return@map parameter.type.extractClass()
            }
            return@map CASTER_CLASS_NAME
        }
        return of(format, type.extractClass(), *parametersBlock.toTypedArray())
    }
    val format = "return new \$T(${parametersFormat.joinToString(COMMA)});"
    val parametersBlock = parameters.values.map { parameter ->
        if (!parameter.type.hasVariable()) {
            return@map parameter.type.extractClass()
        }
        return@map CASTER_CLASS_NAME
    }
    return of(format, type.extractClass(), *parametersBlock.toTypedArray())
}


fun registerMetaFieldStatement(name: String, type: JavaMetaType): CodeBlock {
    return join(listOf(of("register(new MetaField<>(\$S,", name), metaTypeStatement(type), of("))")), EMPTY_STRING)
}

fun registerMetaParameterStatement(index: Int, name: String, type: JavaMetaType): CodeBlock {
    return join(listOf(of("register(new MetaParameter<>($index, \$S,", name), metaTypeStatement(type), of("))")), EMPTY_STRING)
}


fun metaMethodSuperStatement(name: String, type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock {
    return join(listOf(of("super(\$S,", name), metaTypeStatement(type), of(","), join(modifiers.map { modifier -> of("\$S", modifier) }, ","), of(");")), EMPTY_STRING)
}

fun metaConstructorSuperStatement(type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock {
    return join(listOf(of("super("), metaTypeStatement(type), of(","), join(modifiers.map { modifier -> of("\$S", modifier) }, ","), of(");")), EMPTY_STRING)
}

fun metaTypeSuperStatement(type: JavaMetaType): CodeBlock {
    return join(listOf(of("super("), metaTypeStatement(type), of(");")), EMPTY_STRING)
}

fun namedSuperStatement(name: String): CodeBlock {
    return of("super(\$S);", name)
}

private const val metaVariablePattern = "metaVariable(\$S"
private const val metaTypePattern = "metaType(\$T.class, \$T[]::new"
private const val metaArrayTypePattern = "metaArray(\$T.class, \$T[]::new, "

private fun metaTypeBlock(className: TypeName, vararg parameters: JavaMetaType): CodeBlock {
    val builder = of(metaTypePattern, className, className).toBuilder()
    parameters.forEach { parameter ->
        builder.add(", ").add(metaTypeStatement(parameter))
    }
    return builder.add(")").build()
}

private fun metaTypeStatement(type: JavaMetaType): CodeBlock {
    val poetClass = type.extractClass()

    return when (type.kind) {
        PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> metaTypeBlock(poetClass)
        ARRAY_KIND -> {
            val componentType = type.arrayComponentType!!
            join(listOf(of(metaArrayTypePattern, poetClass, poetClass), metaTypeStatement(componentType), of(")")), EMPTY_STRING)
        }
        CLASS_KIND, INTERFACE_KIND -> {
            metaTypeBlock(poetClass, *type.typeParameters.toTypedArray())
        }
        VARIABLE_KIND -> join(listOf(of(metaVariablePattern, type.typeName), of(")")), EMPTY_STRING)
        WILDCARD_KIND -> type.wildcardExtendsBound?.let(::metaTypeStatement)
                ?: type.wildcardSuperBound?.let(::metaTypeStatement)
                ?: metaTypeBlock(OBJECT_CLASS_NAME)
    }
}
