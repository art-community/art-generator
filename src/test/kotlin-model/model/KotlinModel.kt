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


typealias Alias = KotlinModel

open class KotlinModel(
        val iсp1: Boolean,
        val iсp2: String,
        val iсp6: List<String>,
        val iсp8: List<*>,
        val iсp9: List<String>,
        val iсp10: List<String>,
        val iсp18: BooleanArray,
        val iсp22: Array<List<String>>,
        val iсp23: Array<List<*>>,
        val iсp25: Array<List<String>>,
        val iсp26: Array<List<String>>,
        val iсp27: Array<BooleanArray>,
        val iсp29: Array<Array<List<String>>>,
        val iсp30: Array<Array<List<*>>>,
        val iсp32: Array<Array<List<String>>>,
        val iсp33: Array<Array<List<String>>>,
        val iсp34: List<BooleanArray>,
        val iсp36: Array<List<Array<String>>>,
        val iсp38: Array<List<Array<String>>>,
        val iсp39: Array<List<Array<String>>>,
        val iсp40: List<List<Boolean>>,
        val iсp42: List<List<String>>,
        val iсp43: List<List<*>>,
        val iсp45: List<List<String>>,
        val iсp46: List<List<String>>,
        val iсp47: List<List<BooleanArray>>,
        val iсp49: List<List<Array<String>>>,
        val iсp50: List<List<Array<String>>>,
        val iсp51: List<List<Array<String>>>,
        val iсp52: InnerModel,
        val iсp54: InnerModel,
        val iсp55: InnerModel,
        val iсp56: List<InnerModel>,
        val iсp60: KotlinModel?,
        val iсp61: Map<String, String>,
        val iсp62: Map<*, String>,
        val iсp63: Map<String, *>,
        val iсp64: Map<in String, String>,
        val iсp67: Enum,
        val iсp68: List<Enum>,
        val iсp69: Array<Enum>,
        val iсp70: Map<Enum, Enum>,
        val iсp71: Map<Enum, List<Enum>>,
        val iсp72: Map<Enum, List<Array<Enum>>>,
        val iсp73: Map<Enum, List<Array<Enum>>>,
        val icfp1: () -> Unit,
        val icfp2: () -> Nothing,
        val icfp3: () -> Any,
        val icfp4: (input1: String, input2: String, input3: String) -> KotlinModel,
        val icfp5: Map<Enum, List<Array<Enum>>>.(input1: String, input2: String, input3: String) -> KotlinModel,
) {
    var mp1 = false
    var mp2: String? = null
    var mp6: List<String>? = null
    var mp8: List<*>? = null
    var mp9: List<String>? = null
    var mp10: List<String>? = null
    var mp18: BooleanArray? = null
    var mp22: Array<List<String>>? = null
    var mp23: Array<List<*>>? = null
    var mp25: Array<List<String>>? = null
    var mp26: Array<List<String>>? = null
    var mp27: Array<BooleanArray>? = null
    var mp29: Array<Array<List<String>>>? = null
    var mp30: Array<Array<List<*>>>? = null
    var mp32: Array<Array<List<String>>>? = null
    var mp33: Array<Array<List<String>>>? = null
    var mp34: List<BooleanArray>? = null
    var mp36: Array<List<Array<String>>>? = null
    var mp38: Array<List<Array<String>>>? = null
    var mp39: Array<List<Array<String>>>? = null
    var mp40: List<List<Boolean>>? = null
    var mp42: List<List<String>>? = null
    var mp43: List<List<*>>? = null
    var mp45: List<List<String>>? = null
    var mp46: List<List<String>>? = null
    var mp47: List<List<BooleanArray>>? = null
    var mp49: List<List<Array<String>>>? = null
    var mp50: List<List<Array<String>>>? = null
    var mp51: List<List<Array<String>>>? = null
    var mp52: InnerModel? = null
    var mp54: InnerModel? = null
    var mp55: InnerModel? = null
    var mp56: List<InnerModel>? = null
    var mp60: KotlinModel? = null
    var mp61: Map<String, String>? = null
    var mp62: Map<*, String>? = null
    var mp63: Map<String, *>? = null
    var mp64: Map<in String, String>? = null
    var mp67: Enum? = null
    var mp68: List<Enum>? = null
    var mp69: Array<Enum>? = null
    var mp70: Map<Enum, Enum>? = null
    var mp71: Map<Enum, List<Enum>>? = null
    var mp72: Map<Enum, List<Array<Enum>>>? = null
    var mp73: Map<Enum, List<Array<Enum>>>? = null
    val ip1 = false
    val ip2: String? = null
    val ip6: List<String>? = null
    val ip8: List<*>? = null
    val ip9: List<String>? = null
    val ip10: List<String>? = null
    val ip18: BooleanArray? = null
    val ip22: Array<List<String>>? = null
    val ip23: Array<List<*>>? = null
    val ip25: Array<List<String>>? = null
    val ip26: Array<List<String>>? = null
    val ip27: Array<BooleanArray>? = null
    val ip29: Array<Array<List<String>>>? = null
    val ip30: Array<Array<List<*>>>? = null
    val ip32: Array<Array<List<String>>>? = null
    val ip33: Array<Array<List<String>>>? = null
    val ip34: List<BooleanArray>? = null
    val ip36: Array<List<Array<String>>>? = null
    val ip38: Array<List<Array<String>>>? = null
    val ip39: Array<List<Array<String>>>? = null
    val ip40: List<List<Boolean>>? = null
    val ip42: List<List<String>>? = null
    val ip43: List<List<*>>? = null
    val ip45: List<List<String>>? = null
    val ip46: List<List<String>>? = null
    val ip47: List<List<BooleanArray>>? = null
    val ip49: List<List<Array<String>>>? = null
    val ip50: List<List<Array<String>>>? = null
    val ip51: List<List<Array<String>>>? = null
    val ip52: InnerModel? = null
    val ip54: InnerModel? = null
    val ip55: InnerModel? = null
    val ip56: List<InnerModel>? = null
    val ip61: Map<String, String>? = null
    val ip62: Map<*, String>? = null
    val ip63: Map<String, *>? = null
    val ip64: Map<in String, String>? = null
    val ip67: Enum? = null
    val ip68: List<Enum>? = null
    val ip69: Array<Enum>? = null
    val ip70: Map<Enum, Enum>? = null
    val ip71: Map<Enum, List<Enum>>? = null
    val ip72: Map<Enum, List<Array<Enum>>>? = null
    val ip73: Map<Enum, List<Array<Enum>>>? = null
    lateinit var lp2: String
    lateinit var lp6: List<String>
    lateinit var lp8: List<*>
    lateinit var lp9: List<String>
    lateinit var lp10: List<String>
    lateinit var lp18: BooleanArray
    lateinit var lp22: Array<List<String>>
    lateinit var lp23: Array<List<*>>
    lateinit var lp25: Array<List<String>>
    lateinit var lp26: Array<List<String>>
    lateinit var lp27: Array<BooleanArray>
    lateinit var lp29: Array<Array<List<String>>>
    lateinit var lp30: Array<Array<List<*>>>
    lateinit var lp32: Array<Array<List<String>>>
    lateinit var lp33: Array<Array<List<String>>>
    lateinit var lp34: List<BooleanArray>
    lateinit var lp36: Array<List<Array<String>>>
    lateinit var lp38: Array<List<Array<String>>>
    lateinit var lp39: Array<List<Array<String>>>
    lateinit var lp40: List<List<Boolean>>
    lateinit var lp42: List<List<String>>
    lateinit var lp43: List<List<*>>
    lateinit var lp45: List<List<String>>
    lateinit var lp46: List<List<String>>
    lateinit var lp47: List<List<BooleanArray>>
    lateinit var lp49: List<List<Array<String>>>
    lateinit var lp50: List<List<Array<String>>>
    lateinit var lp51: List<List<Array<String>>>
    lateinit var lp52: InnerModel
    lateinit var lp54: InnerModel
    lateinit var lp55: InnerModel
    lateinit var lp56: List<InnerModel>
    lateinit var lp60: KotlinModel
    lateinit var lp61: Map<String, String>
    lateinit var lp62: Map<*, String>
    lateinit var lp63: Map<String, *>
    lateinit var lp64: Map<in String, String>
    lateinit var lp67: Enum
    lateinit var lp68: List<Enum>
    lateinit var lp69: Array<Enum>
    lateinit var lp70: Map<Enum, Enum>
    lateinit var lp71: Map<Enum, List<Enum>>
    lateinit var lp72: Map<Enum, List<Array<Enum>>>
    lateinit var lp73: Map<Enum, List<Array<Enum>>>

    fun simpleMethod(p1: Int): Int {
        return p1
    }

    fun simpleMethod(p1: IntArray): Int {
        return p1[0]
    }

    inline fun f(crossinline body: () -> Unit) {
        val f = Runnable { body() }
    }

    sealed class InnerModel {
        var f1 = false
        var f2: String? = null
        var f3 = 0

        fun simpleMethod(p1: Int): Int {
            return p1
        }

        companion object {
            fun simpleMethod(p1: Int): Int {
                return p1
            }
        }
    }

    enum class Enum {
        FIRST
    }

    companion object {
        fun simpleMethod(p1: Int): Int {
            return p1
        }

        fun simpleMethod(p1: String): String {
            return p1
        }

        fun simpleMethod(list: List<*>) {

        }

        fun simpleMethod(p1: String?, vararg p2: Int): Int {
            return p2[0]
        }
    }

    object KotlinObject {
        var iсp1: Boolean? = null
        var iсp2: String? = null
        var iсp6: List<String>? = null
        var iсp8: List<*>? = null
        var iсp9: List<String>? = null
        var iсp10: List<String>? = null
        var iсp18: BooleanArray? = null
        var iсp22: Array<List<String>>? = null
        var iсp23: Array<List<*>>? = null
        var iсp25: Array<List<String>>? = null
        var iсp26: Array<List<String>>? = null
        var iсp27: Array<BooleanArray>? = null
        var iсp29: Array<Array<List<String>>>? = null
        var iсp30: Array<Array<List<*>>>? = null
        var iсp32: Array<Array<List<String>>>? = null
        var iсp33: Array<Array<List<String>>>? = null
        var iсp34: List<BooleanArray>? = null
        var iсp36: Array<List<Array<String>>>? = null
        var iсp38: Array<List<Array<String>>>? = null
        var iсp39: Array<List<Array<String>>>? = null
        var iсp40: List<List<Boolean>>? = null
        var iсp42: List<List<String>>? = null

        fun simpleMethod(p1: Int): Int {
            return p1
        }

        fun simpleMethod(p1: String): String {
            return p1
        }

        fun simpleMethod(list: List<*>?) {

        }

        fun simpleMethod(p1: String?, vararg p2: Int): Int {
            return p2[0]
        }

        operator fun invoke(): String {
            return "test"
        }
    }
}

data class KotlinOtherModel(
        val iсp1: Boolean,
        val iсp2: String,
        val iсp6: List<String>,
        val iсp8: List<*>,
        val iсp9: List<String>,
        val iсp10: List<String>,
        val iсp18: BooleanArray,
        val iсp22: Array<List<String>>,
        val iсp23: Array<List<*>>,
        val iсp25: Array<List<String>>,
        val iсp26: Array<List<String>>,
        val iсp27: Array<BooleanArray>,
        val iсp29: Array<Array<List<String>>>,
        val iсp30: Array<Array<List<*>>>,
        val iсp32: Array<Array<List<String>>>,
        val iсp33: Array<Array<List<String>>>,
        val iсp34: List<BooleanArray>,
        val iсp36: Array<List<Array<String>>>,
        val iсp38: Array<List<Array<String>>>,
        val iсp39: Array<List<Array<String>>>,
        val iсp40: List<List<Boolean>>,
        val iсp42: List<List<String>>,
        val iсp43: List<List<*>>,
        val iсp45: List<List<String>>,
        val iсp46: List<List<String>>,
        val iсp47: List<List<BooleanArray>>,
        val iсp49: List<List<Array<String>>>,
        val iсp50: List<List<Array<String>>>,
        val iсp51: List<List<Array<String>>>,
        val iсp52: KotlinModel.InnerModel,
        val iсp54: KotlinModel.InnerModel,
        val iсp55: KotlinModel.InnerModel,
        val iсp56: List<KotlinModel.InnerModel>,
        val iсp60: KotlinModel,
        val iсp61: Map<String, String>,
        val iсp62: Map<*, String>,
        val iсp63: Map<String, *>,
        val iсp64: Map<in String, String>,
        val iсp67: KotlinModel.Enum,
        val iсp68: List<KotlinModel.Enum>,
        val iсp69: Array<KotlinModel.Enum>,
        val iсp70: Map<KotlinModel.Enum, KotlinModel.Enum>,
        val iсp71: Map<KotlinModel.Enum, List<KotlinModel.Enum>>,
        val iсp72: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>,
        val iсp73: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>,
        val icfp1: () -> Unit,
        val icfp2: () -> Nothing,
        val icfp3: () -> Any,
        val icfp4: (input1: String, input2: String, input3: String) -> KotlinModel,
        val icfp5: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>.(input1: String, input2: String, input3: String) -> KotlinModel,
) {
    var mp1 = false
    var mp2: String? = null
    var mp6: List<String>? = null
    var mp8: List<*>? = null
    var mp9: List<String>? = null
    var mp10: List<String>? = null
    var mp18: BooleanArray? = null
    var mp22: Array<List<String>>? = null
    var mp23: Array<List<*>>? = null
    var mp25: Array<List<String>>? = null
    var mp26: Array<List<String>>? = null
    var mp27: Array<BooleanArray>? = null
    var mp29: Array<Array<List<String>>>? = null
    var mp30: Array<Array<List<*>>>? = null
    var mp32: Array<Array<List<String>>>? = null
    var mp33: Array<Array<List<String>>>? = null
    var mp34: List<BooleanArray>? = null
    var mp36: Array<List<Array<String>>>? = null
    var mp38: Array<List<Array<String>>>? = null
    var mp39: Array<List<Array<String>>>? = null
    var mp40: List<List<Boolean>>? = null
    var mp42: List<List<String>>? = null
    var mp43: List<List<*>>? = null
    var mp45: List<List<String>>? = null
    var mp46: List<List<String>>? = null
    var mp47: List<List<BooleanArray>>? = null
    var mp49: List<List<Array<String>>>? = null
    var mp50: List<List<Array<String>>>? = null
    var mp51: List<List<Array<String>>>? = null
    var mp52: KotlinModel.InnerModel? = null
    var mp54: KotlinModel.InnerModel? = null
    var mp55: KotlinModel.InnerModel? = null
    var mp56: List<KotlinModel.InnerModel>? = null
    var mp60: KotlinModel? = null
    var mp61: Map<String, String>? = null
    var mp62: Map<*, String>? = null
    var mp63: Map<String, *>? = null
    var mp64: Map<in String, String>? = null
    var mp67: KotlinModel.Enum? = null
    var mp68: List<KotlinModel.Enum>? = null
    var mp69: Array<KotlinModel.Enum>? = null
    var mp70: Map<KotlinModel.Enum, KotlinModel.Enum>? = null
    var mp71: Map<KotlinModel.Enum, List<KotlinModel.Enum>>? = null
    var mp72: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>? = null
    var mp73: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>? = null
    val ip1 = false
    val ip2: String? = null
    val ip6: List<String>? = null
    val ip8: List<*>? = null
    val ip9: List<String>? = null
    val ip10: List<String>? = null
    val ip18: BooleanArray? = null
    val ip22: Array<List<String>>? = null
    val ip23: Array<List<*>>? = null
    val ip25: Array<List<String>>? = null
    val ip26: Array<List<String>>? = null
    val ip27: Array<BooleanArray>? = null
    val ip29: Array<Array<List<String>>>? = null
    val ip30: Array<Array<List<*>>>? = null
    val ip32: Array<Array<List<String>>>? = null
    val ip33: Array<Array<List<String>>>? = null
    val ip34: List<BooleanArray>? = null
    val ip36: Array<List<Array<String>>>? = null
    val ip38: Array<List<Array<String>>>? = null
    val ip39: Array<List<Array<String>>>? = null
    val ip40: List<List<Boolean>>? = null
    val ip42: List<List<String>>? = null
    val ip43: List<List<*>>? = null
    val ip45: List<List<String>>? = null
    val ip46: List<List<String>>? = null
    val ip47: List<List<BooleanArray>>? = null
    val ip49: List<List<Array<String>>>? = null
    val ip50: List<List<Array<String>>>? = null
    val ip51: List<List<Array<String>>>? = null
    val ip52: KotlinModel.InnerModel? = null
    val ip54: KotlinModel.InnerModel? = null
    val ip55: KotlinModel.InnerModel? = null
    val ip56: List<KotlinModel.InnerModel>? = null
    val ip61: Map<String, String>? = null
    val ip62: Map<*, String>? = null
    val ip63: Map<String, *>? = null
    val ip64: Map<in String, String>? = null
    val ip67: KotlinModel.Enum? = null
    val ip68: List<KotlinModel.Enum>? = null
    val ip69: Array<KotlinModel.Enum>? = null
    val ip70: Map<KotlinModel.Enum, KotlinModel.Enum>? = null
    val ip71: Map<KotlinModel.Enum, List<KotlinModel.Enum>>? = null
    val ip72: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>? = null
    val ip73: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>? = null
    lateinit var lp2: String
    lateinit var lp6: List<String>
    lateinit var lp8: List<*>
    lateinit var lp9: List<String>
    lateinit var lp10: List<String>
    lateinit var lp18: BooleanArray
    lateinit var lp22: Array<List<String>>
    lateinit var lp23: Array<List<*>>
    lateinit var lp25: Array<List<String>>
    lateinit var lp26: Array<List<String>>
    lateinit var lp27: Array<BooleanArray>
    lateinit var lp29: Array<Array<List<String>>>
    lateinit var lp30: Array<Array<List<*>>>
    lateinit var lp32: Array<Array<List<String>>>
    lateinit var lp33: Array<Array<List<String>>>
    lateinit var lp34: List<BooleanArray>
    lateinit var lp36: Array<List<Array<String>>>
    lateinit var lp38: Array<List<Array<String>>>
    lateinit var lp39: Array<List<Array<String>>>
    lateinit var lp40: List<List<Boolean>>
    lateinit var lp42: List<List<String>>
    lateinit var lp43: List<List<*>>
    lateinit var lp45: List<List<String>>
    lateinit var lp46: List<List<String>>
    lateinit var lp47: List<List<BooleanArray>>
    lateinit var lp49: List<List<Array<String>>>
    lateinit var lp50: List<List<Array<String>>>
    lateinit var lp51: List<List<Array<String>>>
    lateinit var lp52: KotlinModel.InnerModel
    lateinit var lp54: KotlinModel.InnerModel
    lateinit var lp55: KotlinModel.InnerModel
    lateinit var lp56: List<KotlinModel.InnerModel>
    lateinit var lp60: KotlinModel
    lateinit var lp61: Map<String, String>
    lateinit var lp62: Map<*, String>
    lateinit var lp63: Map<String, *>
    lateinit var lp64: Map<in String, String>
    lateinit var lp67: KotlinModel.Enum
    lateinit var lp68: List<KotlinModel.Enum>
    lateinit var lp69: Array<KotlinModel.Enum>
    lateinit var lp70: Map<KotlinModel.Enum, KotlinModel.Enum>
    lateinit var lp71: Map<KotlinModel.Enum, List<KotlinModel.Enum>>
    lateinit var lp72: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>
    lateinit var lp73: Map<KotlinModel.Enum, List<Array<KotlinModel.Enum>>>

    fun simpleMethod(p1: Int): Int = p1

    fun simpleMethod(p1: IntArray): Int {
        fun simpleMethod(p1: IntArray) {

        }

        class LocalClass {

        }

        return p1[0]
    }

    inline fun f(crossinline body: () -> Unit) {
        val f = Runnable { body() }
    }

    sealed class InnerModel {
        var f1 = false
        var f2: String? = null
        var f3 = 0
        var f5: KotlinModel? = null
        var f6: KotlinModel? = null

        fun simpleMethod(p1: Int): Int = p1

        companion object {
            fun simpleMethod(p1: Int): Int = p1
        }
    }

    enum class Enum {
        FIRST
    }

    companion object {
        fun simpleMethod(p1: Int): Int {
            return p1
        }

        fun simpleMethod(p1: String): String {
            return p1
        }

        fun simpleMethod(list: List<*>) {

        }

        fun simpleMethod(p1: String?, vararg p2: Int): Int {
            return p2[0]
        }
    }

    object KotlinObject {
        fun simpleMethod(p1: Int): Int {
            return p1
        }

        fun simpleMethod(p1: String): String {
            return p1
        }

        fun simpleMethod(list: List<String>?) {

        }

        fun simpleMethod(p1: String?, vararg p2: Int): Int {
            return p2[0]
        }

        operator fun invoke(): String {
            return "test"
        }
    }
}

object KotlinObject {
    fun simpleMethod(p1: Alias): Int {
        return 0
    }

    infix fun simpleMethod(p1: String): String {
        return p1
    }

    inline fun simpleMethod(list: List<String>?, lambda: (p: String) -> String) {
    }

    private tailrec fun factorial(n: Long, accum: Long = 1): Long {
        val soFar = n * accum
        return if (n <= 1) {
            soFar
        } else {
            factorial(n - 1, soFar)
        }
    }

    suspend fun simpleMethod(p1: String?, vararg p2: Int) = p2[0]
}

fun simpleMethod(p1: Alias): Int {
    return 0
}

inline fun simpleMethod(p1: String, noinline lambda: (p: String) -> String, inline: (p: String) -> String) = p1

inline fun simpleMethod(list: List<String>?, lambda: (p: String) -> String) {

}

private tailrec fun factorial(n: Long, accum: Long = 1): Long {
    val soFar = n * accum
    return if (n <= 1) {
        soFar
    } else {
        factorial(n - 1, soFar)
    }
}

suspend fun simpleMethod(p1: String?, vararg p2: Int): Int {
    return p2[0]
}

class KotlinBase

var KotlinBase.mp1: Boolean
    get() = false
    set(value) = TODO()

var KotlinBase.mp2: KotlinOtherModel.Enum?
    get() = null
    set(value) = TODO()

var KotlinBase.mp3: List<KotlinOtherModel.Enum>?
    get() = null
    set(value) = TODO()

var KotlinBase.mp4: Array<KotlinOtherModel.Enum>?
    get() = null
    set(value) = TODO()

var KotlinBase.mp5: Map<KotlinOtherModel.Enum, KotlinOtherModel.Enum>?
    get() = null
    set(value) = TODO()

var KotlinBase.mp6: Map<KotlinOtherModel.Enum, List<KotlinOtherModel.Enum>>?
    get() = null
    set(value) = TODO()

var KotlinBase.mp7: Map<KotlinOtherModel.Enum, List<Array<KotlinOtherModel.Enum>>>?
    get() = null
    set(value) = TODO()

var KotlinBase.mp8: Map<KotlinOtherModel.Enum, List<Array<KotlinOtherModel.Enum>>>?
    get() = null
    set(value) = TODO()

fun KotlinBase.simpleMethod(p1: Alias): Int {
    return 0
}

inline fun KotlinBase.simpleMethod(p1: String, noinline lambda: (p: String) -> String, inline: (p: String) -> String) = p1
