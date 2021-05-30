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

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import io.art.core.caster.Caster
import io.art.core.constants.StringConstants.SPACE
import io.art.generator.meta.model.JavaMetaClass
import io.art.generator.meta.service.asPoetType

fun newStatement() = "new \$T()"

fun returnStatement() = "return \$L;"

fun returnStatement(block: CodeBlock) = CodeBlock.join(listOf(CodeBlock.of("return"), block), SPACE)

fun returnNullStatement() = "return null;"

fun registerNewStatement() = "register(new \$T())"

fun computeStatement() = "compute();"

fun invokeInstanceStatement(method: String, argumentsCount: Int): CodeBlock {
    val format = "instance.$method(${(0 until argumentsCount).joinToString(",") { index -> "\$T.cast(arguments[$index])" }});"
    return CodeBlock.of(format, *(0 until argumentsCount).map { ClassName.get(Caster::class.java) }.toTypedArray())
}

fun invokeStaticStatement(method: String, type: TypeName, argumentsCount: Int): CodeBlock {
    val format = "\$T.$method(${(0 until argumentsCount).joinToString(",") { index -> "\$T.cast(arguments[$index])" }});"
    return CodeBlock.of(format, type, *(0 until argumentsCount).map { ClassName.get(Caster::class.java) }.toTypedArray())
}

fun returnInvokeConstructorStatement(type: TypeName, argumentsCount: Int): CodeBlock {
    val format = "return new \$T(${(0 until argumentsCount).joinToString(",") { index -> "\$T.cast(arguments[$index])" }});"
    return CodeBlock.of(format, type, *(0 until argumentsCount).map { ClassName.get(Caster::class.java) }.toTypedArray())
}

fun registerMetaFieldStatement(index: Int, name: String, type: TypeName): CodeBlock {
    return CodeBlock.of("register(new MetaField<>($index, \$S, metaType(\$T.class, \$T[]::new)))", name, type, type)
}

fun registerMetaParameterStatement(index: Int, name: String, type: TypeName): CodeBlock {
    return CodeBlock.of("register(new MetaParameter<>($index, \$S, metaType(\$T.class, \$T[]::new)))", name, type, type)
}

fun registerMetaMethodStatement(name: String, type: TypeName): CodeBlock {
    return CodeBlock.of("register(new MetaMethod<>(\$S, metaType(\$T.class, \$T[]::new)))", name, type, type)
}

fun registerMetaConstructorStatement(type: TypeName): CodeBlock {
    return CodeBlock.of("register(new MetaConstructor<>(metaType(\$T.class, \$T[]::new)))", type, type)
}

fun metaTypeSuperStatement(metaClass: JavaMetaClass, className: TypeName): CodeBlock {
    val constructorBuilder = CodeBlock.of("super(metaType(\$T.class, \$T[]::new)", className, className).toBuilder()
    if (metaClass.type.typeVariables.isNotEmpty()) {
        constructorBuilder.add(", ")
        metaClass.type.typeVariables.forEach { variable ->
            constructorBuilder.add("metaVariable(\$S, metaType(\$T.class, \$T[]::new))",
                    variable.key,
                    variable.value.asPoetType(),
                    variable.value.asPoetType()
            )
        }
    }
    return constructorBuilder.add(");").build()
}

fun metaNamedSuperStatement(name: String, className: TypeName): CodeBlock {
    return CodeBlock.of("super(\$S, metaType(\$T.class, \$T[]::new));", name, className, className)
}

fun metaSuperStatement(className: TypeName): CodeBlock {
    return CodeBlock.of("super(metaType(\$T.class, \$T[]::new));", className, className)
}

fun namedSuperStatement(name: String): CodeBlock {
    return CodeBlock.of("super(\$S);", name)
}
