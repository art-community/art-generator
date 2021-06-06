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
import io.art.core.caster.Caster
import io.art.core.factory.SetFactory
import io.art.meta.model.*


val SET_FACTORY_CLASS_NAME = ClassName.get(SetFactory::class.java)!!
val CASTER_CLASS_NAME = ClassName.get(Caster::class.java)!!
val OBJECT_CLASS_NAME = ClassName.get(Object::class.java)!!
val META_FIELD_CLASS_NAME = ClassName.get(MetaField::class.java)!!
val META_CLASS_CLASS_NAME = ClassName.get(MetaClass::class.java)!!
val META_TYPE_CLASS_NAME = ClassName.get(MetaType::class.java)!!
val META_PACKAGE_CLASS_NAME = ClassName.get(MetaPackage::class.java)!!
val META_MODULE_CLASS_NAME = ClassName.get(MetaModule::class.java)!!
val INSTANCE_META_METHOD_CLASS_NAME = ClassName.get(InstanceMetaMethod::class.java)!!
val STATIC_META_METHOD_CLASS_NAME = ClassName.get(StaticMetaMethod::class.java)!!
val META_PARAMETER_CLASS_NAME = ClassName.get(MetaParameter::class.java)!!
val META_CONSTRUCTOR_CLASS_NAME = ClassName.get(MetaConstructor::class.java)!!
val THROWABLE_CLASS_NAME = ClassName.get(Throwable::class.java)!!
val OVERRIDE_CLASS_NAME = ClassName.get(Override::class.java)!!
