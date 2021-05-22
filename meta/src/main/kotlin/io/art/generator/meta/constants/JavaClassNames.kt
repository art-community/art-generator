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
package io.art.generator.meta.constants

import com.squareup.javapoet.ClassName
import io.art.meta.MetaClass
import io.art.meta.MetaField
import io.art.meta.MetaMethod
import io.art.meta.MetaType

val CLASS_CLASS_NAME = ClassName.get(Class::class.java)!!
val OBJECT_CLASS_NAME = ClassName.get(Object::class.java)!!
val META_FIELD_CLASS_NAME = ClassName.get(MetaField::class.java)!!
val META_METHOD_CLASS_NAME = ClassName.get(MetaMethod::class.java)!!
val META_CLASS_CLASS_NAME = ClassName.get(MetaClass::class.java)!!
val META_TYPE_CLASS_NAME = ClassName.get(MetaType::class.java)!!
