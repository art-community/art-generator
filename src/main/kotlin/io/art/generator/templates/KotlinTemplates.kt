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
import io.art.core.constants.CompilerSuppressingWarnings.*
import io.art.core.constants.StringConstants.*
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.constants.CASTER_CLASS_NAME
import io.art.generator.constants.KOTLIN_ANY_CLASS_NAME
import io.art.generator.constants.KOTLIN_CASTER_CLASS_NAME
import io.art.generator.extension.extractClass
import io.art.generator.extension.hasVariable
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
        .addMember("value", "{\$S,\$S,\$S}", ALL, UNCHECKED, UNUSED)
        .build()

fun kotlinSuperStatement(label: String): CodeBlock = "super(\$L);".asCode(label)

fun kotlinNewStatement(type: TypeName): CodeBlock = "new \$T()".asCode(type)

fun kotlinReturnStatement(label: String): CodeBlock = "return \$L;".asCode(label)

fun kotlinRegisterNewStatement(type: TypeName): CodeBlock = "register(new \$T())".asCode(type)

fun kotlinNamedSuperStatement(name: String): CodeBlock = "super(\$S);".asCode(name)

fun kotlinReturnNewStatement(type: KotlinMetaType): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        return "return new \$T<>(".asCode(type.extractClass())
    }
    return "return new \$T(".asCode(type.extractClass())
}

fun kotlinInvokeWithoutArgumentsInstanceStatement(method: String): CodeBlock {
    return "instance.$method();".asCode()
}

fun kotlinInvokeWithoutArgumentsStaticStatement(method: String, type: KotlinMetaType): CodeBlock {
    return "\$T.$method();".asCode(type.extractClass())
}


fun kotlinInvokeOneArgumentInstanceStatement(method: String, parameter: KotlinMetaParameter): CodeBlock {
    return "instance.$method(".join(casted(parameter)).join(");")
}

fun kotlinInvokeOneArgumentStaticStatement(method: String, type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return "\$T.$method(".asCode(type.extractClass()).join(casted(parameter)).join(");")
}


fun kotlinInvokeInstanceStatement(method: String, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "instance.$method(".join(casted(parameters)).join(");")
}

fun kotlinInvokeStaticStatement(method: String, type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "\$T.$method(".asCode(type.extractClass()).join(casted(parameters)).join(");")
}


fun kotlinReturnInvokeWithoutArgumentsConstructorStatement(type: KotlinMetaType): CodeBlock {
    return kotlinReturnNewStatement(type).join(");")
}

fun kotlinReturnInvokeOneArgumentConstructorStatement(type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return kotlinReturnNewStatement(type).join(casted(parameter)).join(");")
}

fun kotlinReturnInvokeConstructorStatement(type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return kotlinReturnNewStatement(type).join(casted(parameters)).join(");")
}

fun kotlinRegisterMetaFieldStatement(name: String, property: KotlinMetaProperty): CodeBlock = "register(new MetaField<>(\$S,"
        .asCode(name)
        .join(metaTypeStatement(property.type))
        .join("))")

fun kotlinRegisterMetaParameterStatement(index: Int, name: String, parameter: KotlinMetaParameter): CodeBlock = "register(new MetaParameter<>($index, \$S,"
        .asCode(name)
        .join(metaTypeStatement(parameter.type))
        .join("))")

fun kotlinMetaMethodSuperStatement(name: String, type: KotlinMetaType, visibility: DescriptorVisibility): CodeBlock = "super(\$S,"
        .asCode(name)
        .join(metaTypeStatement(type))
        .joinByComma(asPublicFlag(visibility))
        .join(");")

fun kotlinMetaConstructorSuperStatement(type: KotlinMetaType, visibility: DescriptorVisibility): CodeBlock = "super("
        .join(metaTypeStatement(type))
        .joinByComma(asPublicFlag(visibility))
        .join(");")

fun kotlinMetaClassSuperStatement(metaClass: KotlinMetaClass): CodeBlock = "super("
        .join(metaTypeStatement(metaClass.type))
        .join(");")


private fun metaVariableBlock(type: KotlinMetaType) = "metaVariable(\$S".asCode(type.typeName).join(")")

private fun metaEnumBlock(className: TypeName) = "metaEnum(\$T.class, \$T::valueOf)"
        .asCode(className, className)

private fun metaTypeBlock(className: TypeName, vararg parameters: KotlinMetaType): CodeBlock = "metaType(\$T.class"
        .asCode(className)
        .let { block ->
            if (parameters.isEmpty()) return@let block
            block.joinByComma(*parameters.map(::metaTypeStatement).toTypedArray())
        }
        .join(")")

private fun metaArrayBlock(type: KotlinMetaType, className: TypeName): CodeBlock = "metaArray(\$T.class, \$T[]::new, "
        .asCode(className, className)
        .join(metaTypeStatement(type.arrayComponentType!!))
        .join(")")

private fun metaTypeStatement(type: KotlinMetaType): CodeBlock {
    val poetClass = type.extractClass()
    return when (type.kind) {
        ARRAY_KIND -> metaArrayBlock(type, poetClass)
        CLASS_KIND -> metaTypeBlock(poetClass, *type.typeParameters.toTypedArray())
        ENUM_KIND -> metaEnumBlock(poetClass)
        VARIABLE_KIND -> metaVariableBlock(type)
        WILDCARD_KIND -> metaTypeBlock(KOTLIN_ANY_CLASS_NAME)
        FUNCTION_KIND -> metaTypeBlock(KOTLIN_ANY_CLASS_NAME)
        UNKNOWN_KIND -> metaTypeBlock(KOTLIN_ANY_CLASS_NAME)
    }
}


private fun String.asCode(vararg arguments: Any): CodeBlock = CodeBlock.of(this, *arguments)

private fun String.join(vararg blocks: CodeBlock): CodeBlock = asCode().join(*blocks)

private fun CodeBlock.join(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(EMPTY_STRING)

private fun CodeBlock.join(block: String): CodeBlock = listOf(this, block.asCode()).joinToCode(EMPTY_STRING)

private fun CodeBlock.joinBySpace(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(SPACE)

private fun CodeBlock.joinByComma(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(COMMA)


private fun casted(parameter: KotlinMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    if (!parameter.type.hasVariable()) {
        return "(\$T)(argument)".asCode(parameterClass)
    }
    return "\$T.cast(argument)".asCode(CASTER_CLASS_NAME)
}

private fun casted(parameters: Map<String, KotlinMetaParameter>): CodeBlock = parameters.values
        .mapIndexed { index, parameter ->
            if (!parameter.type.hasVariable()) {
                return@mapIndexed "(\$T)(arguments[$index])".asCode(parameter.type.extractClass())
            }
            return@mapIndexed "\$T.cast(arguments[$index])".asCode(KOTLIN_CASTER_CLASS_NAME)
        }.joinToCode(COMMA)

private fun asPublicFlag(visibility: DescriptorVisibility): CodeBlock {
    if (visibility.isVisibleOutside()) {
        return "true".asCode()
    }
    return "false".asCode()
}
