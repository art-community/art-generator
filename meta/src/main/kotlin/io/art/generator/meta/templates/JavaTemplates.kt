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

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

const val NEW_STATEMENT = "new \$T()"
const val RETURN_STATEMENT = "return \$L;"
const val REGISTER_NEW_STATEMENT = "register(new \$T());"
val REGISTER_META_PARAMETER_STATEMENT = { index: Int, name: String, type: TypeName ->
    CodeBlock.of("register(new MetaParameter<>($index, \$S, metaType(\$T.class, \$T[]::new)))", name, type, type)
}
const val COMPUTE_STATEMENT = "compute();"
