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

import io.art.core.extensions.StringExtensions.capitalize
import io.art.core.extensions.StringExtensions.decapitalize

fun metaModuleClassFullName(metaPackage: String, name: String): String = "$metaPackage.Meta$name"

fun metaModuleClassName(name: String): String = "Meta$name"

fun metaClassName(name: String): String = "${decapitalize(name)}Class"

fun metaPackageName(name: String): String = "${name}Package"

fun metaFieldName(name: String): String = "${name}Field"

fun metaParameterName(name: String): String = "${name}Parameter"

fun metaMethodName(name: String): String = "${name}Method"

fun metaConstructorClassName(name: String) = "Meta${capitalize(name)}Constructor"

fun metaProxyClassName(name: String) = "Meta${capitalize(name)}Proxy"

fun metaProxyInvocationName(name: String) = "${name}Invocation"
