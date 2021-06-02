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
import io.art.core.constants.CompilerSuppressingWarnings.*
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
        .addMember("value", "{\$S,\$S,\$S}", ALL, UNCHECKED, UNUSED)
        .build()

fun newStatement() = "new \$T()"

fun returnStatement() = "return \$L;"

fun returnStatement(block: CodeBlock): CodeBlock = "return".asCode().joinSpaced(block)

fun returnNullStatement() = "return null;"

fun registerNewStatement() = "register(new \$T())"

fun computeStatement() = "meta.compute(dependencies);"


fun invokeWithoutArgumentsInstanceStatement(method: String): CodeBlock {
    return "instance.$method();".asCode()
}

fun invokeWithoutArgumentsStaticStatement(method: String, type: JavaMetaType): CodeBlock {
    return "\$T.$method();".asCode(type.extractClass())
}


fun invokeOneArgumentInstanceStatement(method: String, parameter: JavaMetaParameter): CodeBlock {
    return "instance.$method(".join(casted(parameter)).join(");")
}

fun invokeOneArgumentStaticStatement(method: String, type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    return "\$T.$method(".asCode(type.extractClass()).join(casted(parameter)).join(");")
}


fun invokeInstanceStatement(method: String, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    return "instance.$method(".join(casted(parameters)).join(");")
}

fun invokeStaticStatement(method: String, type: JavaMetaType, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    return "\$T.$method(".asCode(type.extractClass()).join(casted(parameters)).join(");")
}


fun returnInvokeWithoutArgumentsConstructorStatement(type: JavaMetaType): CodeBlock {
    if (type.typeParameters.isNotEmpty()) return "return new \$T<>();".asCode(type.extractClass())
    return "return new \$T();".asCode(type.extractClass())
}

fun returnInvokeOneArgumentConstructorStatement(type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        return "return new \$T<>(".asCode(type.extractClass()).join(casted(parameter)).join(");")
    }
    return "return new \$T(".asCode(type.extractClass()).join(casted(parameter)).join(");")
}

fun returnInvokeConstructorStatement(type: JavaMetaType, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        return "return new \$T<>(".asCode(type.extractClass()).join(casted(parameters)).join(");")
    }
    return "return new \$T(".asCode(type.extractClass()).join(casted(parameters)).join(");")
}


fun registerMetaFieldStatement(name: String, type: JavaMetaType): CodeBlock {
    return "register(new MetaField<>(\$S,".asCode(name).join(metaTypeStatement(type)).join("))")
}

fun registerMetaParameterStatement(index: Int, name: String, type: JavaMetaType): CodeBlock {
    return "register(new MetaParameter<>($index, \$S,".asCode(name).join(metaTypeStatement(type)).join("))")
}


fun metaMethodSuperStatement(name: String, type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock {
    if (modifiers.isEmpty()) {
        return "super(\$S,".asCode(name).join(metaTypeStatement(type)).join(");")
    }
    return "super(\$S,".asCode(name).join(metaTypeStatement(type)).join(",").join(asString(modifiers)).join(");")
}

fun metaConstructorSuperStatement(type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock {
    if (modifiers.isEmpty()) {
        return "super(".join(metaTypeStatement(type)).join(");")
    }
    return "super(".join(metaTypeStatement(type)).join(",").join(asString(modifiers)).join(");")
}


fun metaTypeSuperStatement(type: JavaMetaType): CodeBlock {
    return "super(".join(metaTypeStatement(type)).join(");")
}

fun namedSuperStatement(name: String): CodeBlock {
    return "super(\$S);".asCode(name)
}


private const val metaVariablePattern = "metaVariable(\$S"
private const val metaTypePattern = "metaType(\$T.class, \$T[]::new"
private const val metaArrayTypePattern = "metaArray(\$T.class, \$T[]::new, "

private fun metaTypeBlock(className: TypeName, vararg parameters: JavaMetaType): CodeBlock {
    val builder = of(metaTypePattern, className, className).toBuilder()
    parameters.forEach { parameter ->
        builder.add(COMMA).add(metaTypeStatement(parameter))
    }
    return builder.add(CLOSING_BRACKET).build()
}

private fun metaTypeStatement(type: JavaMetaType): CodeBlock {
    val poetClass = type.extractClass()

    return when (type.kind) {
        PRIMITIVE_KIND, ENUM_KIND, UNKNOWN_KIND -> metaTypeBlock(poetClass)
        ARRAY_KIND -> {
            val componentType = type.arrayComponentType!!
            metaArrayTypePattern.asCode(poetClass, poetClass).join(metaTypeStatement(componentType)).join(")")
        }
        CLASS_KIND, INTERFACE_KIND -> {
            metaTypeBlock(poetClass, *type.typeParameters.toTypedArray())
        }
        VARIABLE_KIND -> metaVariablePattern.asCode(type.typeName).join(")")
        WILDCARD_KIND -> type.wildcardExtendsBound?.let(::metaTypeStatement)
                ?: type.wildcardSuperBound?.let(::metaTypeStatement)
                ?: metaTypeBlock(OBJECT_CLASS_NAME)
    }
}

private fun String.asCode(vararg arguments: Any): CodeBlock = of(this, *arguments)

private fun String.join(vararg blocks: CodeBlock): CodeBlock = asCode().join(*blocks)

private fun CodeBlock.joinBlocks(separator: String = EMPTY_STRING, vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), separator)

private fun CodeBlock.joinSpaced(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), SPACE)

private fun CodeBlock.joinCommas(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), COMMA)

private fun CodeBlock.join(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), EMPTY_STRING)

private fun CodeBlock.join(block: String): CodeBlock = join(listOf(this, block.asCode()), EMPTY_STRING)

private fun casted(parameter: JavaMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    if (!parameter.type.hasVariable()) {
        return "(\$T)(argument)".asCode(parameterClass)
    }
    return "\$T.cast(argument)".asCode(CASTER_CLASS_NAME)
}

private fun casted(parameters: Map<String, JavaMetaParameter>): CodeBlock = parameters.values
        .mapIndexed { index, parameter ->
            if (!parameter.type.hasVariable()) {
                return@mapIndexed "(\$T)(arguments[$index])".asCode(parameter.type.extractClass())
            }
            return@mapIndexed "\$T.cast(arguments[$index])".asCode(CASTER_CLASS_NAME)
        }
        .let { blocks -> join(blocks, COMMA) }

private fun asString(modifiers: Set<Modifier>): CodeBlock = join(modifiers.map { modifier -> "\$S".asCode(modifier) }, EMPTY_STRING)
