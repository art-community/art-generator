/*
 * ART
 *
 * Copyright 2019-2022 ART
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
import io.art.core.constants.CompilerSuppressingWarnings.WARNINGS
import io.art.core.constants.StringConstants.*
import io.art.core.extensions.StringExtensions.capitalize
import io.art.generator.constants.*
import io.art.generator.exception.MetaGeneratorException
import io.art.generator.extension.asPoetType
import io.art.generator.extension.extractClass
import io.art.generator.factory.NameFactory
import io.art.generator.factory.name
import io.art.generator.model.*
import io.art.generator.model.KotlinMetaTypeKind.*


fun kotlinMetaPackageClassName(name: String, factory: NameFactory) =
        ClassName.bestGuess("Meta${capitalize(name)}Package".name(factory))

fun kotlinMetaClassClassName(name: String, factory: NameFactory) =
        ClassName.bestGuess("Meta${capitalize(name)}Class".name(factory))

fun kotlinMetaMethodClassName(name: String) =
        ClassName.bestGuess("Meta${capitalize(name)}Method")


fun kotlinSuppressAnnotation() = AnnotationSpec.builder(Suppress::class)
        .addMember("%S", WARNINGS)
        .build()

fun kotlinSuperStatement(label: String): CodeBlock = "%L".asCode(label)

fun kotlinReturnStatement(label: CodeBlock): CodeBlock = "return ".join(label)

fun kotlinReturnStatement(label: String): CodeBlock = "return %L".asCode(label)

fun kotlinReturnLazyGetStatement(label: String): CodeBlock = "return %L.get()".asCode(label)

fun kotlinRegisterNewStatement(type: TypeName): CodeBlock = "register(%T())".asCode(type)

fun kotlinRegisterOwnedNewStatement(type: TypeName): CodeBlock = "register(%T($OWNER_NAME))".asCode(type)

fun kotlinNamedSuperStatement(name: String): CodeBlock = "%S".asCode(name)

fun kotlinReturnNullStatement() = "return null".asCode()


fun kotlinInvokeInstanceStatement(method: MemberName): CodeBlock {
    return "$INSTANCE_NAME.%N()".asCode(method)
}

fun kotlinInvokeInstanceStatement(method: MemberName, parameter: KotlinMetaParameter): CodeBlock {
    return "$INSTANCE_NAME.%N(".asCode(method).join(casted(parameter)).join(")")
}

fun kotlinInvokeInstanceStatement(method: MemberName, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "$INSTANCE_NAME.%N(".asCode(method).join(casted(parameters)).join(")")
}


fun kotlinInvokeStaticStatement(method: MemberName, type: KotlinMetaType): CodeBlock {
    return "%T.%N()".asCode(type.extractClass(), method)
}

fun kotlinInvokeStaticStatement(method: MemberName, type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return "%T.%N(".asCode(type.extractClass(), method).join(casted(parameter)).join(")")
}

fun kotlinInvokeStaticStatement(method: MemberName, type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "%T.%N(".asCode(type.extractClass(), method).join(casted(parameters)).join(")")
}


fun kotlinInvokeConstructorStatement(type: KotlinMetaType): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(")")
}

fun kotlinInvokeConstructorStatement(type: KotlinMetaType, parameter: KotlinMetaParameter): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(casted(parameter)).join(")")
}

fun kotlinInvokeConstructorStatement(type: KotlinMetaType, parameters: Map<String, KotlinMetaParameter>): CodeBlock {
    return "return %T(".asCode(type.extractClass()).join(casted(parameters)).join(")")
}


fun kotlinRegisterMetaFieldStatement(property: KotlinMetaProperty, inherited: Boolean): CodeBlock =
        "register($META_FIELD_NAME(%S,"
                .asCode(property.name)
                .join(metaTypeStatement(property.type))
                .joinByComma(inheritedStatement(inherited))
                .joinByComma(THIS_NAME.asCode())
                .join("))")

fun kotlinRegisterMetaParameterStatement(index: Int, parameter: KotlinMetaParameter): CodeBlock =
        "register($META_PARAMETER_NAME($index, %S,"
                .asCode(parameter.name)
                .join(metaTypeStatement(parameter.type))
                .join("))")


fun kotlinMetaMethodSuperStatement(name: String, returnType: KotlinMetaType?): CodeBlock = "%S,"
        .asCode(name)
        .join(returnType?.let(::metaTypeStatement) ?: "%T".asCode(UNIT))
        .joinByComma(OWNER_NAME.asCode())

fun kotlinMetaConstructorSuperStatement(type: KotlinMetaType): CodeBlock = metaTypeStatement(type).joinByComma(OWNER_NAME.asCode())

fun kotlinMetaClassSuperStatement(metaClass: KotlinMetaClass): CodeBlock = metaTypeStatement(metaClass.type)


fun kotlinSetStatementBySingle(owner: String, property: KotlinMetaProperty, propertyName: MemberName) = "%L.%N = "
        .asCode(owner, propertyName)
        .join("$ARGUMENT_NAME as %T".asCode(property.type.asPoetType()))
        .joinLine(kotlinReturnNullStatement())

fun kotlinSetStatementByArray(owner: String, property: KotlinMetaProperty, propertyName: MemberName) = "%L.%N = "
        .asCode(owner, propertyName)
        .join("$ARGUMENTS_NAME[0] as %T".asCode(property.type.asPoetType()))
        .joinLine(kotlinReturnNullStatement())

fun kotlinReturnGetStatement(owner: String, property: MemberName) = "return %L.%N".asCode(owner, property)


fun kotlinReturnNewProxyStatement(proxyClass: TypeName) = "return %T($INVOCATIONS_NAME)".asCode(proxyClass)

fun kotlinGetInvocationStatement(methodName: String) = "%L = $INVOCATIONS_NAME[%L]!!\n"
        .asCode(metaProxyInvocationName(methodName), metaMethodName(methodName))

fun kotlinCallInvocationStatement(method: KotlinMetaFunction, invocationName: String): CodeBlock {
    if (method.parameters.isEmpty()) {
        val returnType = method.returnType?.asPoetType() ?: UNIT
        if (returnType == UNIT) {
            return "%L.apply(null)".asCode(invocationName)
        }
        return "return %L.apply(null) as %T".asCode(invocationName, returnType)
    }
    if (method.parameters.size == 1) {
        val returnType = method.returnType?.asPoetType() ?: UNIT
        if (returnType == UNIT) {
            return "%L.apply(%L)".asCode(invocationName, method.parameters.values.first().name)
        }
        return "return %L.apply(%L) as %T".asCode(invocationName, method.parameters.values.first().name, returnType)
    }
    val returnType = method.returnType?.asPoetType() ?: UNIT
    if (returnType == UNIT) {
        return "%L.apply(arrayOf("
                .asCode(invocationName)
                .join(joinByComma(*method.parameters.keys.map { name -> name.asCode() }.toTypedArray()))
                .join("));")
    }
    return "return (%L.apply(arrayOf("
            .asCode(invocationName)
            .join(joinByComma(*method.parameters.keys.map { name -> name.asCode() }.toTypedArray()))
            .join("))) as %T".asCode(returnType))
}

fun kotlinNotImplementedStatement() = "TODO(\"Not yet implemented\")"

fun kotlinMetaClassSelfMethodCall(target: TypeName): CodeBlock = "%T.self(%T::class.java)".asCode(KOTLIN_META_CLASS_CLASS_NAME, target)

fun FunSpec.Builder.addLines(vararg code: CodeBlock) = addCode(listOf(*code).joinToCode(NEW_LINE))


private fun metaEnumBlock(type: KotlinMetaType) = "$META_ENUM_METHOD_NAME(%T::class.java, %T::valueOf)"
        .asCode(type.extractClass(), type.extractClass())

private fun metaTypeBlock(type: KotlinMetaType, vararg parameters: KotlinMetaType): CodeBlock = "$META_TYPE_METHOD_NAME<%T>(%T::class.java"
        .asCode(type.asPoetType(), type.extractClass())
        .let { block ->
            if (parameters.isEmpty()) return@let block
            block.joinByComma(*parameters.map(::metaTypeStatement).toTypedArray())
        }
        .join(")")

private fun metaFunctionTypeBlock(type: KotlinMetaType): CodeBlock = "$META_TYPE_METHOD_NAME<%T>(%T::class.java"
        .asCode(type.asPoetType(), type.extractClass())
        .let { block ->
            val arguments = type.lambdaArgumentTypes.map(::metaTypeStatement) + (type.lambdaResultType?.let(::metaTypeStatement)
                    ?: "%T".asCode(UNIT))
            block.joinByComma(*arguments.toTypedArray())
        }
        .join(")")

private fun metaAnyTypeBlock(): CodeBlock = "$META_TYPE_METHOD_NAME<%T>(%T::class.java)".asCode(ANY, ANY)

private fun metaArrayBlock(type: KotlinMetaType): CodeBlock = "$META_ARRAY_METHOD_NAME<%T>(%T::class.java, { size: Int -> $ARRAY_OF_NULLS_METHOD<%T>(size) }, "
        .asCode(type.asPoetType(), type.extractClass(), type.arrayComponentType!!.asPoetType())
        .join(metaTypeStatement(type.arrayComponentType))
        .join(")")

private fun metaArrayBlock(type: TypeName, componentType: TypeName): CodeBlock = "$META_ARRAY_METHOD_NAME<%T>(%T::class.java, { size: Int -> $ARRAY_OF_NULLS_METHOD<%T>(size) }, "
        .asCode(type, type, componentType)
        .join("$META_TYPE_METHOD_NAME<%T>(%T::class.javaPrimitiveType)".asCode(componentType, componentType))
        .join(")")

private fun metaTypeStatement(type: KotlinMetaType): CodeBlock = when (type.kind) {
    ARRAY_KIND -> metaArrayBlock(type)
    CLASS_KIND -> when (type.typeName) {
        ByteArray::class.qualifiedName -> metaArrayBlock(ByteArray::class.asTypeName(), Byte::class.asTypeName())
        BooleanArray::class.qualifiedName -> metaArrayBlock(BooleanArray::class.asTypeName(), Boolean::class.asTypeName())
        IntArray::class.qualifiedName -> metaArrayBlock(IntArray::class.asTypeName(), Int::class.asTypeName())
        ShortArray::class.qualifiedName -> metaArrayBlock(ShortArray::class.asTypeName(), Short::class.asTypeName())
        DoubleArray::class.qualifiedName -> metaArrayBlock(DoubleArray::class.asTypeName(), Double::class.asTypeName())
        FloatArray::class.qualifiedName -> metaArrayBlock(FloatArray::class.asTypeName(), Float::class.asTypeName())
        LongArray::class.qualifiedName -> metaArrayBlock(LongArray::class.asTypeName(), Long::class.asTypeName())
        CharArray::class.qualifiedName -> metaArrayBlock(CharArray::class.asTypeName(), Char::class.asTypeName())
        else -> metaTypeBlock(type, *type.typeParameters.toTypedArray())
    }
    ENUM_KIND -> metaEnumBlock(type)
    WILDCARD_KIND -> metaAnyTypeBlock()
    FUNCTION_KIND -> metaFunctionTypeBlock(type)
    UNKNOWN_KIND -> throw MetaGeneratorException("$UNKNOWN_KIND: $type")
}


private fun String.asCode(vararg arguments: Any): CodeBlock = CodeBlock.of(this, *arguments)

private fun String.join(vararg blocks: CodeBlock): CodeBlock = asCode().join(*blocks)

private fun CodeBlock.join(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(EMPTY_STRING)

private fun CodeBlock.join(block: String): CodeBlock = listOf(this, block.asCode()).joinToCode(EMPTY_STRING)

private fun CodeBlock.joinLine(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(NEW_LINE)

private fun CodeBlock.joinByComma(vararg blocks: CodeBlock): CodeBlock = listOf(this, *blocks).joinToCode(COMMA)

private fun joinByComma(vararg blocks: CodeBlock): CodeBlock = listOf(*blocks).joinToCode(COMMA)

private fun casted(parameter: KotlinMetaParameter): CodeBlock {
    val parameterClass = parameter.type.asPoetType()
    val block = "$ARGUMENT_NAME as %T".asCode(parameterClass)
    if (parameter.varargs) {
        return "*(".join(block).join(")")
    }
    return block
}

private fun casted(parameters: Map<String, KotlinMetaParameter>): CodeBlock = parameters.values
        .mapIndexed { index, parameter ->
            var asInstruction = "as"
            if (parameter.type.nullable) {
                asInstruction += "?"
            }
            val block = "$ARGUMENTS_NAME[$index] $asInstruction %T".asCode(parameter.type.asPoetType())
            if (parameter.varargs) {
                return@mapIndexed "*(".join(block).join(")")
            }
            return@mapIndexed block
        }
        .joinToCode(COMMA)

private fun inheritedStatement(inherited: Boolean) = "%L".asCode(inherited)
