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

const val THROW_EXCEPTION_STATEMENT = "throw new \$T(\$S);"
const val STUB_METHOD_LITERAL = "stub method"
const val NEW_STATEMENT = "new \$T()"
const val RETURN_STATEMENT = "return \$L;"

const val COMPUTE_STATEMENT = "compute();"

const val META_FIELD_INITIALIZER = "register(\$T.metaField(\$S,\$T.class))"
const val META_GENERIC_FIELD_INITIALIZER = "register(\$T.metaField(\$S))"
const val META_METHOD_INITIALIZER = "register(\$T.metaMethod(\$S,\$T.class))"

const val META_FIELD_METHOD_REIFIED_PARAMETER = "type"
const val META_FIELD_METHOD_REIFIED_STATEMENT = "return \$L.reify(type);"
const val META_TYPE_REGISTER_STATEMENT = "\$T.register(provide());"
const val META_TYPE_OF_STATEMENT = "of(\$T.class)"

const val META_TYPE_OF_NAME = "of"
