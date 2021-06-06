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

class ModelKotlin<T1, out T2, out T3, T4>(
        val f1: String,
        var f2: String,
        val f3: T1?,
        val f4: T1?,
        val f5: ModelKotlin<*, *, *, *>?
) where T1 : CharSequence, T1 : Comparable<T1> {
    var f6 = "test"
        private set

    fun simpleMethod() {

    }

    fun simpleMethod(p1: String) {

    }

    fun simpleMethod(p1: String, p2: Int): String = ""
}


object ModelObject {
    val f1: String = ""
    var f2: String = ""
    val f3: String? = null
    val f4: String? = null
    private var f6 = "test"

    fun getF6() = f6

    fun simpleMethod() {

    }

    fun simpleMethod(p1: String) {

    }

    fun simpleMethod(p1: String, p2: Int): String = ""
}
