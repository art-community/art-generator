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

package model

class ModelKotlin<T1, out T2, out T3, T4>(
        val f1: T1?,
        val f2: T1?,
        val f3: T2,
        val f4: T3,
        val f5: T4,
        val f6: ModelKotlin<T1, *, *, String>?
) where T1 : CharSequence, T1 : Comparable<T1>

typealias Alias = ModelKotlin<*, *, *, *>

data class Nested(val f1: Alias)
