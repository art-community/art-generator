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
package io.art.generator.constants

import com.squareup.javapoet.ClassName
import io.art.meta.model.*


val JAVA_OBJECT_CLASS_NAME = ClassName.get(Object::class.java)!!
val JAVA_META_FIELD_CLASS_NAME = ClassName.get(MetaField::class.java)!!
val JAVA_META_CLASS_CLASS_NAME = ClassName.get(MetaClass::class.java)!!
val JAVA_META_TYPE_CLASS_NAME = ClassName.get(MetaType::class.java)!!
val JAVA_META_PACKAGE_CLASS_NAME = ClassName.get(MetaPackage::class.java)!!
val JAVA_META_MODULE_CLASS_NAME = ClassName.get(MetaLibrary::class.java)!!
val JAVA_INSTANCE_META_METHOD_CLASS_NAME = ClassName.get(InstanceMetaMethod::class.java)!!
val JAVA_STATIC_META_METHOD_CLASS_NAME = ClassName.get(StaticMetaMethod::class.java)!!
val JAVA_META_PARAMETER_CLASS_NAME = ClassName.get(MetaParameter::class.java)!!
val JAVA_META_CONSTRUCTOR_CLASS_NAME = ClassName.get(MetaConstructor::class.java)!!
val JAVA_THROWABLE_CLASS_NAME = ClassName.get(Throwable::class.java)!!
val JAVA_OVERRIDE_CLASS_NAME = ClassName.get(Override::class.java)!!
