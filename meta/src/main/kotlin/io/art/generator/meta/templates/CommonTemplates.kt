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
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.generator.meta.constants.META_NAME

fun metaModuleClassFullName(name: String): String = "$META_NAME.Meta$name"

fun metaModuleJavaFileName(name: String): String = "Meta$name.java"

fun metaModuleKotlinFileName(name: String): String = "Meta$name.kt"


fun metaClassName(name: String): String = "${name.decapitalize()}Class"

fun metaPackageName(name: String): String = "${name}Package"

fun metaFieldName(name: String): String = "${name}Field"

fun metaParameterName(name: String): String = "${name}Parameter"

fun metaMethodName(name: String): String = "${name}Method"


fun metaModuleClassName(packageName: String, name: String): ClassName =
        ClassName.get(packageName, "Meta${name.capitalize()}")

fun metaPackageClassName(name: String): ClassName =
        ClassName.get(EMPTY_STRING, "Meta${name.capitalize()}Package")

fun nestedMetaPackageClassName(name: String): ClassName =
        ClassName.get(EMPTY_STRING, "MetaNested${name.capitalize()}Package")

fun metaClassClassName(name: String): ClassName =
        ClassName.get(EMPTY_STRING, "Meta${name.capitalize()}Class")

fun metaMethodClassName(name: String): ClassName =
        ClassName.get(EMPTY_STRING, "Meta${name.capitalize()}Method")
