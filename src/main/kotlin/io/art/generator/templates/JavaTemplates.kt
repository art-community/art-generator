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

package io.art.generator.templates

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.CodeBlock.join
import com.squareup.javapoet.CodeBlock.of
import com.squareup.javapoet.TypeName
import io.art.core.constants.CompilerSuppressingWarnings.*
import io.art.core.constants.StringConstants.*
import io.art.generator.constants.CASTER_CLASS_NAME
import io.art.generator.constants.OBJECT_CLASS_NAME
import io.art.generator.constants.SET_FACTORY_CLASS_NAME
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.extension.extractClass
import io.art.generator.extension.hasVariable
import io.art.generator.model.JavaMetaClass
import io.art.generator.model.JavaMetaField
import io.art.generator.model.JavaMetaParameter
import io.art.generator.model.JavaMetaType
import io.art.generator.model.JavaMetaTypeKind.*
import javax.lang.model.element.Modifier


fun suppressAnnotation(): AnnotationSpec = AnnotationSpec.builder(SuppressWarnings::class.java)
        .addMember("value", "{\$S,\$S,\$S}", ALL, UNCHECKED, UNUSED)
        .build()

fun newStatement(type: TypeName): CodeBlock = "new \$T()".asCode(type)

fun returnStatement(label: String): CodeBlock = "return \$L;".asCode(label)

fun returnStatement(block: CodeBlock): CodeBlock = "return".asCode().joinBySpace(block)

fun returnNullStatement(): CodeBlock = "return null;".asCode()

fun registerNewStatement(type: TypeName): CodeBlock = "register(new \$T())".asCode(type)

fun superStatement(label: String): CodeBlock = "super(\$L);".asCode(label)

fun returnNewStatement(type: JavaMetaType): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        return "return new \$T<>(".asCode(type.extractClass())
    }
    return "return new \$T(".asCode(type.extractClass())
}


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
    return returnNewStatement(type).join(");")
}

fun returnInvokeOneArgumentConstructorStatement(type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    return returnNewStatement(type).join(casted(parameter)).join(");")
}

fun returnInvokeConstructorStatement(type: JavaMetaType, parameters: Map<String, JavaMetaParameter>): CodeBlock {
    return returnNewStatement(type).join(casted(parameters)).join(");")
}

fun registerMetaFieldStatement(name: String, field: JavaMetaField): CodeBlock = "register(new MetaField<>(\$S,"
        .asCode(name)
        .join(metaTypeStatement(field.type))
        .joinByComma(asString(field.modifiers))
        .join("))")

fun registerMetaParameterStatement(index: Int, name: String, parameter: JavaMetaParameter): CodeBlock = "register(new MetaParameter<>($index, \$S,"
        .asCode(name)
        .join(metaTypeStatement(parameter.type))
        .joinByComma(asString(parameter.modifiers))
        .join("))")

fun metaMethodSuperStatement(name: String, type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock = "super(\$S,"
        .asCode(name)
        .join(metaTypeStatement(type))
        .joinByComma(asString(modifiers))
        .join(");")

fun metaConstructorSuperStatement(type: JavaMetaType, modifiers: Set<Modifier>): CodeBlock = "super("
        .join(metaTypeStatement(type))
        .joinByComma(asString(modifiers))
        .join(");")

fun metaClassSuperStatement(metaClass: JavaMetaClass): CodeBlock = "super("
        .join(metaTypeStatement(metaClass.type))
        .joinByComma(asString(metaClass.modifiers))
        .join(");")

fun namedSuperStatement(name: String): CodeBlock = "super(\$S);".asCode(name)


private fun metaVariableBlock(type: JavaMetaType) = "metaVariable(\$S".asCode(type.typeName).join(")")

private fun metaEnumBlock(className: TypeName) = "metaEnum(\$T.class, \$T::valueOf)"
        .asCode(className, className)

private fun metaTypeBlock(className: TypeName, vararg parameters: JavaMetaType): CodeBlock = "metaType(\$T.class"
        .asCode(className)
        .let { block ->
            if (parameters.isEmpty()) return@let block
            block.joinByComma(*parameters.map(::metaTypeStatement).toTypedArray())
        }
        .join(")")

private fun metaArrayBlock(type: JavaMetaType, className: TypeName): CodeBlock = "metaArray(\$T.class, \$T[]::new, "
        .asCode(className, className)
        .join(metaTypeStatement(type.arrayComponentType!!))
        .join(")")

private fun metaTypeStatement(type: JavaMetaType): CodeBlock {
    val poetClass = type.extractClass()
    return when (type.kind) {
        PRIMITIVE_KIND -> metaTypeBlock(poetClass)
        ARRAY_KIND -> metaArrayBlock(type, poetClass)
        CLASS_KIND, INTERFACE_KIND -> metaTypeBlock(poetClass, *type.typeParameters.toTypedArray())
        ENUM_KIND -> metaEnumBlock(poetClass)
        VARIABLE_KIND -> metaVariableBlock(type)
        WILDCARD_KIND -> type.wildcardExtendsBound?.let(::metaTypeStatement)
                ?: type.wildcardSuperBound?.let(::metaTypeStatement)
                ?: metaTypeBlock(OBJECT_CLASS_NAME)
        UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $type")
    }
}


private fun String.asCode(vararg arguments: Any): CodeBlock = of(this, *arguments)

private fun String.join(vararg blocks: CodeBlock): CodeBlock = asCode().join(*blocks)

private fun CodeBlock.join(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), EMPTY_STRING)

private fun CodeBlock.join(block: String): CodeBlock = join(listOf(this, block.asCode()), EMPTY_STRING)

private fun CodeBlock.joinBySpace(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), SPACE)

private fun CodeBlock.joinByComma(vararg blocks: CodeBlock): CodeBlock = join(listOf(this, *blocks), COMMA)


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

private fun asString(modifiers: Set<Modifier>): CodeBlock = "\$T.setOf(".asCode(SET_FACTORY_CLASS_NAME)
        .join(join(modifiers.map { modifier -> "\$S".asCode(modifier) }, COMMA))
        .join(")")
