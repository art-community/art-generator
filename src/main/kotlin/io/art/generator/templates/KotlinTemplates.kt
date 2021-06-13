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

import com.squareup.kotlinpoet.*
import io.art.core.combiner.SectionCombiner
import io.art.core.constants.CompilerSuppressingWarnings.WARNINGS
import io.art.core.constants.StringConstants.*
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.extension.asPoetType
import io.art.generator.extension.extractClass
import io.art.generator.extension.name
import io.art.generator.model.KotlinMetaClass
import io.art.generator.model.KotlinMetaParameter
import io.art.generator.model.KotlinMetaProperty
import io.art.generator.model.KotlinMetaType
import io.art.generator.model.KotlinMetaTypeKind.*
import org.jetbrains.kotlin.descriptors.DescriptorVisibility
import org.jetbrains.kotlin.synthetic.isVisibleOutside


fun kotlinMetaModuleClassName(packageName: String, name: String) =
        ClassName.bestGuess(SectionCombiner.combine(packageName, "Meta${capitalize(name)}"))

fun kotlinMetaPackageClassName(name: String) =
        ClassName.bestGuess("Meta${capitalize(name)}Package".name())

fun kotlinMetaClassClassName(name: String) =
        ClassName.bestGuess("Meta${capitalize(name)}Class".name())

fun kotlinMetaMethodClassName(name: String) =
        ClassName.bestGuess("Meta${capitalize(name)}Method")


fun kotlinSuppressAnnotation() = AnnotationSpec.builder(Suppress::class)
        .addMember("%S", WARNINGS)
        .build()

fun kotlinSuperStatement(label: String): CodeBlock = "%L".asCode(label)

fun kotlinNewStatement(type: TypeName): CodeBlock = "%T()".asCode(type)

fun kotlinReturnStatement(label: CodeBlock): CodeBlock = "return ".join(label)

fun kotlinReturnStatement(label: String): CodeBlock = "return %L".asCode(label)

fun kotlinRegisterNewStatement(type: TypeName): CodeBlock = "register(%T())".asCode(type)

fun kotlinNamedSuperStatement(name: String): CodeBlock = "%S".asCode(name)

fun kotlinReturnNullStatement() = "return null".asCode()


fun kotlinInvokeWithoutArgumentsInstanceStatement(method: String): CodeBlock {
    return "instance.$method()\n".asCode()
}

fun kotlinInvokeWithoutArgumentsStaticStatement(method: String, type: KotlinMetaType): CodeBlock {
    return "%T.$method()\n".asCode(type.extractClass())
}

fun kotlinInvokeOneArgumentInstanceStatement(method: String, parameter: KotlinMetaParameter): CodeBlock {
    return "instance.$method(".join(casted(parameter)).join(")\n")
}

fun kotlinInvokeOneArgumentStaticStatement(method: String, type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return "%T.$method(".asCode(type.extractClass()).join(casted(parameter)).join(")\n")
}

fun kotlinInvokeInstanceStatement(method: String, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "instance.$method(".join(casted(parameters)).join(")\n")
}

fun kotlinInvokeStaticStatement(method: String, type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "%T.$method(".asCode(type.extractClass()).join(casted(parameters)).join(")\n")
}


fun kotlinReturnInvokeWithoutArgumentsConstructorStatement(type: KotlinMetaType): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(")")
}

fun kotlinReturnInvokeOneArgumentConstructorStatement(type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(casted(parameter)).join(")")
}

fun kotlinReturnInvokeConstructorStatement(type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(casted(parameters)).join(")")
}


fun kotlinRegisterMetaFieldStatement(name: String, property: KotlinMetaProperty): CodeBlock =
        "register(MetaField(%S,"
                .asCode(name)
                .join(metaTypeStatement(property.type))
                .join("))")

fun kotlinRegisterMetaParameterStatement(index: Int, name: String, parameter: KotlinMetaParameter): CodeBlock =
        "register(MetaParameter($index, %S,"
                .asCode(name)
                .join(metaTypeStatement(parameter.type))
                .join("))")


fun kotlinMetaMethodSuperStatement(name: String, type: KotlinMetaType?, visibility: DescriptorVisibility): CodeBlock = "%S,"
        .asCode(name)
        .join(type?.let(::metaTypeStatement) ?: "%T".asCode(UNIT))
        .joinByComma(asPublicFlag(visibility))

fun kotlinMetaConstructorSuperStatement(type: KotlinMetaType, visibility: DescriptorVisibility): CodeBlock = metaTypeStatement(type)
        .joinByComma(asPublicFlag(visibility))

fun kotlinMetaClassSuperStatement(metaClass: KotlinMetaClass): CodeBlock = metaTypeStatement(metaClass.type)


fun kotlinSetStatementBySingle(owner: String, property: KotlinMetaProperty) = "%L.%L = "
        .asCode(owner, property.name)
        .join("argument as %T".asCode(property.type.asPoetType()))
        .join("\n return null")

fun kotlinSetStatementByArray(owner: String, property: KotlinMetaProperty) = "%L.%L = "
        .asCode(owner, property.name)
        .join("arguments[0] as %T".asCode(property.type.asPoetType()))
        .join("\n return null")

fun kotlinReturnGetStatement(owner: String, property: KotlinMetaProperty) = "return %L.%L".asCode(owner, property.name)

private fun metaEnumBlock(type: KotlinMetaType) = "metaEnum(%T::class.java, %T::valueOf)"
        .asCode(type.extractClass(), type.extractClass())

private fun metaTypeBlock(type: KotlinMetaType, vararg parameters: KotlinMetaType): CodeBlock = "metaType<%T>(%T::class.java"
        .asCode(type.asPoetType(), type.extractClass())
        .let { block ->
            if (parameters.isEmpty()) return@let block
            block.joinByComma(*parameters.map(::metaTypeStatement).toTypedArray())
        }
        .join(")")

private fun metaTypeBlock(name: TypeName, vararg parameters: KotlinMetaType): CodeBlock = "metaType<%T>(%T::class.java"
        .asCode(name, name)
        .let { block ->
            if (parameters.isEmpty()) return@let block
            block.joinByComma(*parameters.map(::metaTypeStatement).toTypedArray())
        }
        .join(")")

private fun metaArrayBlock(type: KotlinMetaType): CodeBlock = "metaArray<%T>(%T::class.java, { size: Int -> arrayOfNulls<%T>(size) }, "
        .asCode(type.asPoetType(), type.extractClass(), type.asPoetType())
        .join(metaTypeStatement(type.arrayComponentType!!))
        .join(")")

private fun metaTypeStatement(type: KotlinMetaType): CodeBlock {
    return when (type.kind) {
        ARRAY_KIND -> metaArrayBlock(type)
        CLASS_KIND -> metaTypeBlock(type, *type.typeParameters.toTypedArray())
        ENUM_KIND -> metaEnumBlock(type)
        WILDCARD_KIND -> metaTypeBlock(ANY)
        FUNCTION_KIND -> metaTypeBlock(ANY)
        UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $type")
    }
}


private fun String.asCode(vararg arguments: Any): CodeBlock = CodeBlock.of(this, *arguments)

private fun String.join(vararg blocks: CodeBlock): CodeBlock = asCode().join(*blocks)

private fun CodeBlock.join(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(EMPTY_STRING)

private fun CodeBlock.join(block: String): CodeBlock = listOf(this, block.asCode()).joinToCode(EMPTY_STRING)

private fun CodeBlock.joinBySpace(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(SPACE)

private fun CodeBlock.joinByComma(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(COMMA)

private fun casted(parameter: KotlinMetaParameter): CodeBlock {
    val parameterClass = parameter.type.asPoetType()
    val block = "argument as %T".asCode(parameterClass)
    if (parameter.varargs) {
        return "*(".join(block).join(")")
    }
    return block
}

private fun casted(parameters: Map<String, KotlinMetaParameter>): CodeBlock = parameters.values
        .mapIndexed { index, parameter ->
            val block = "arguments[$index] as %T".asCode(parameter.type.asPoetType())
            if (parameter.varargs) {
                return@mapIndexed "*(".join(block).join(")")
            }
            return@mapIndexed block
        }
        .joinToCode(COMMA)

private fun asPublicFlag(visibility: DescriptorVisibility): CodeBlock {
    if (visibility.isVisibleOutside()) {
        return "true".asCode()
    }
    return "false".asCode()
}
