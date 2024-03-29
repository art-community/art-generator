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

package io.art.generator.constants

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName.Companion.bestGuess
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.WildcardTypeName.Companion.producerOf
import io.art.core.property.LazyProperty
import io.art.meta.model.*
import java.util.function.Function

val KOTLIN_META_FIELD_CLASS_NAME = bestGuess(MetaField::class.java.name)
val KOTLIN_META_CLASS_CLASS_NAME = bestGuess(MetaClass::class.java.name)
val KOTLIN_META_TYPE_CLASS_NAME = bestGuess(MetaType::class.java.name)
val KOTLIN_META_PACKAGE_CLASS_NAME = bestGuess(MetaPackage::class.java.name)
val KOTLIN_META_LIBRARY_CLASS_NAME = bestGuess(MetaLibrary::class.java.name)
val KOTLIN_INSTANCE_META_METHOD_CLASS_NAME = bestGuess(InstanceMetaMethod::class.java.name)
val KOTLIN_STATIC_META_METHOD_CLASS_NAME = bestGuess(StaticMetaMethod::class.java.name)
val KOTLIN_META_PARAMETER_CLASS_NAME = bestGuess(MetaParameter::class.java.name)
val KOTLIN_META_CONSTRUCTOR_CLASS_NAME = bestGuess(MetaConstructor::class.java.name)
val KOTLIN_FUNCTION_TYPE_NAME = bestGuess(Function::class.java.name).parameterizedBy(ANY.copy(nullable = true), ANY.copy(nullable = true))
val KOTLIN_META_PROXY_CLASS_NAME = bestGuess(MetaProxy::class.java.name)
val KOTLIN_META_METHOD_PARAMETERIZED_NAME = bestGuess(MetaMethod::class.java.name)
        .parameterizedBy(KOTLIN_META_CLASS_CLASS_NAME.parameterizedBy(producerOf(ANY.copy(nullable = true))), producerOf(ANY.copy(nullable = true)))
val KOTLIN_MAP_META_METHOD_FUNCTION_TYPE_NAME = bestGuess(MutableMap::class.qualifiedName!!).parameterizedBy(
        KOTLIN_META_METHOD_PARAMETERIZED_NAME,
        KOTLIN_FUNCTION_TYPE_NAME
)
val KOTLIN_LAZY_CLASS_NAME = bestGuess(LazyProperty::class.java.name)
