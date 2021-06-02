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
import io.art.generator.meta.constants.META_MODULE_CLASS_NAME
import io.art.generator.meta.constants.OBJECT_CLASS_NAME
import io.art.generator.meta.model.JavaMetaParameter
import io.art.generator.meta.model.JavaMetaType
import io.art.generator.meta.model.JavaMetaTypeKind.*
import io.art.generator.meta.service.extractClass
import javax.lang.model.element.Modifier

fun suppressAnnotation(): AnnotationSpec = AnnotationSpec.builder(SuppressWarnings::class.java)
        .addMember("value", "\$S", ALL)
        .build()

fun newStatement() = "new \$T()"

fun returnStatement() = "return \$L;"

fun returnStatement(block: CodeBlock): CodeBlock = join(listOf(of("return"), block), SPACE)

fun returnNullStatement() = "return null;"

fun registerNewStatement() = "register(new \$T())"

fun computeStatement() = "compute(dependencies);"


fun invokeWithoutArgumentsInstanceStatement(method: String): CodeBlock {
    return of("instance.$method();")
}

fun invokeWithoutArgumentsStaticStatement(method: String, type: JavaMetaType): CodeBlock {
    return of("\$T.$method();", type.extractClass())
}


fun invokeOneArgumentInstanceStatement(method: String, parameter: JavaMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    return of("instance.$method((\$T)(argument));", parameterClass)
}

fun invokeOneArgumentStaticStatement(method: String, type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    val parameterClass = parameter.type.extractClass()
    return of("\$T.$method((\$T)(argument));", type.extractClass(), parameterClass)
}


fun invokeInstanceStatement(method: String, arguments: Map<String, JavaMetaParameter>): CodeBlock {
    val format = "instance.$method(${arguments.values.joinToString(COMMA) { index -> "(\$T)(arguments[$index])" }});"
    return of(format, *arguments.values.map { type -> type.type.extractClass() }.toTypedArray())
}

fun invokeStaticStatement(method: String, type: JavaMetaType, arguments: Map<String, JavaMetaParameter>): CodeBlock {
    val format = "\$T.$method(${arguments.values.joinToString(COMMA) { index -> "(\$T)(arguments[$index])" }});"
    return of(format, type, *arguments.values.map { parameter -> parameter.type.extractClass() }.toTypedArray())
}


fun returnInvokeWithoutArgumentsConstructorStatement(type: JavaMetaType): CodeBlock {
    if (type.typeParameters.isNotEmpty()) return of("return new \$T<>();", type.extractClass())
    return of("return new \$T();", type.extractClass())
}

fun returnInvokeOneArgumentConstructorStatement(type: JavaMetaType, parameter: JavaMetaParameter): CodeBlock {
    if (type.typeParameters.isNotEmpty()) return of("return new \$T<>((\$T)(argument));", type.extractClass(), parameter.type.extractClass())
    return of("return new \$T((\$T)(argument));", type.extractClass(), parameter.type.extractClass())
}

fun returnInvokeConstructorStatement(type: JavaMetaType, arguments: Map<String, JavaMetaParameter>): CodeBlock {
    if (type.typeParameters.isNotEmpty()) {
        val format = "return new \$T<>(${arguments.values.joinToString(",") { index -> "(\$T)(arguments[$index])" }});"
        return of(format, type.extractClass(), *arguments.values.map { parameter -> parameter.type.extractClass() }.toTypedArray())
    }
    val format = "return new \$T(${arguments.values.joinToString(",") { index -> "(\$T)(arguments[$index])" }});"
    return of(format, type.extractClass(), *arguments.values.map { parameter -> parameter.type.extractClass() }.toTypedArray())
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
