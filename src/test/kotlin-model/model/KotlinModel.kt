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


typealias Alias = KotlinModel.IntersectionType

open class KotlinModel<V1, V2 : String?, V3, V4 : V2?>(
        val iсp1: Boolean,
        val iсp2: String,
        val iсp3: V1,
        val iсp4: V2,
        val iсp5: V3,
        val iсp6: List<String>,
        val iсp7: List<V1>,
        val iсp8: List<*>,
        val iсp9: List<String>,
        val iсp10: List<String>,
        val iсp11: List<V1>,
        val iсp12: List<V2>,
        val iсp13: List<V2>,
        val iсp14: List<V3>,
        val iсp15: List<V3>,
        val iсp16: List<V4>,
        val iсp17: List<V4>,
        val iсp18: BooleanArray,
        val iсp19: Array<V1>,
        val iсp20: Array<V2>,
        val iсp21: Array<V3>,
        val iсp22: Array<List<String>>,
        val iсp23: Array<List<*>>,
        val iсp24: Array<List<V1>>,
        val iсp25: Array<List<String>>,
        val iсp26: Array<List<String>>,
        val iсp27: Array<BooleanArray>,
        val iсp28: Array<Array<V1>>,
        val iсp29: Array<Array<List<String>>>,
        val iсp30: Array<Array<List<*>>>,
        val iсp31: Array<Array<List<V1>>>,
        val iсp32: Array<Array<List<String>>>,
        val iсp33: Array<Array<List<String>>>,
        val iсp34: List<BooleanArray>,
        val iсp35: List<Array<V1>>,
        val iсp36: Array<List<Array<String>>>,
        val iсp37: Array<List<Array<V1>>>,
        val iсp38: Array<List<Array<String>>>,
        val iсp39: Array<List<Array<String>>>,
        val iсp40: List<List<Boolean>>,
        val iсp41: List<V1>,
        val iсp42: List<List<String>>,
        val iсp43: List<List<*>>,
        val iсp44: List<List<V1>>,
        val iсp45: List<List<String>>,
        val iсp46: List<List<String>>,
        val iсp47: List<List<BooleanArray>>,
        val iсp48: List<List<Array<V1>>>,
        val iсp49: List<List<Array<String>>>,
        val iсp50: List<List<Array<String>>>,
        val iсp51: List<List<Array<String>>>,
        val iсp52: InnerModel<String>,
        val iсp53: InnerModel<V1>,
        val iсp54: InnerModel<out String>,
        val iсp55: InnerModel<in String>,
        val iсp56: List<InnerModel<String>>,
        val iсp57: List<MutableList<out Array<V1>>?>,
        val iсp58: List<Array<MutableList<out Array<V1>>?>?>,
        val iсp59: Array<List<Array<MutableList<out Array<V1>>?>?>>,
        val iсp60: KotlinModel<String, V2, IntersectionType, V4>,
        val iсp61: Map<String, String>,
        val iсp62: Map<*, String>,
        val iсp63: Map<String, *>,
        val iсp64: Map<in String, String>,
        val iсp65: Map<in V1, V1>,
        val iсp66: Map<V1, V1>,
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
        val icfp4: (input1: String, input2: String, input3: String) -> KotlinModel<*, *, *, *>,
        val icfp5: Map<Enum, List<Array<Enum>>>.(input1: String, input2: String, input3: String) -> KotlinModel<*, *, *, *>,
) where V3 : Number?, V3 : Comparator<String> {
    var mp1 = false
    var mp2: String? = null
    var mp3: V1? = null
    var mp4: V2? = null
    var mp5: V3? = null
    var mp6: List<String>? = null
    var mp7: List<V1>? = null
    var mp8: List<*>? = null
    var mp9: List<String>? = null
    var mp10: List<String>? = null
    var mp11: List<V1>? = null
    var mp12: List<V2>? = null
    var mp13: List<V2>? = null
    var mp14: List<V3>? = null
    var mp15: List<V3>? = null
    var mp16: List<V4>? = null
    var mp17: List<V4>? = null
    var mp18: BooleanArray? = null
    var mp19: Array<V1>? = null
    var mp20: Array<V2>? = null
    var mp21: Array<V3>? = null
    var mp22: Array<List<String>>? = null
    var mp23: Array<List<*>>? = null
    var mp24: Array<List<V1>>? = null
    var mp25: Array<List<String>>? = null
    var mp26: Array<List<String>>? = null
    var mp27: Array<BooleanArray>? = null
    var mp28: Array<Array<V1>>? = null
    var mp29: Array<Array<List<String>>>? = null
    var mp30: Array<Array<List<*>>>? = null
    var mp31: Array<Array<List<V1>>>? = null
    var mp32: Array<Array<List<String>>>? = null
    var mp33: Array<Array<List<String>>>? = null
    var mp34: List<BooleanArray>? = null
    var mp35: List<Array<V1>>? = null
    var mp36: Array<List<Array<String>>>? = null
    var mp37: Array<List<Array<V1>>>? = null
    var mp38: Array<List<Array<String>>>? = null
    var mp39: Array<List<Array<String>>>? = null
    var mp40: List<List<Boolean>>? = null
    var mp41: List<V1>? = null
    var mp42: List<List<String>>? = null
    var mp43: List<List<*>>? = null
    var mp44: List<List<V1>>? = null
    var mp45: List<List<String>>? = null
    var mp46: List<List<String>>? = null
    var mp47: List<List<BooleanArray>>? = null
    var mp48: List<List<Array<V1>>>? = null
    var mp49: List<List<Array<String>>>? = null
    var mp50: List<List<Array<String>>>? = null
    var mp51: List<List<Array<String>>>? = null
    var mp52: InnerModel<String>? = null
    var mp53: InnerModel<V1>? = null
    var mp54: InnerModel<out String>? = null
    var mp55: InnerModel<in String>? = null
    var mp56: List<InnerModel<String>>? = null
    var mp57: List<MutableList<out Array<V1>>?>? = null
    var mp58: List<Array<MutableList<out Array<V1>>?>?>? = null
    var mp59: Array<List<Array<MutableList<out Array<V1>>?>?>>? = null
    var mp60: KotlinModel<String, V2, IntersectionType, V4>? = null
    var mp61: Map<String, String>? = null
    var mp62: Map<*, String>? = null
    var mp63: Map<String, *>? = null
    var mp64: Map<in String, String>? = null
    var mp65: Map<in V1, V1>? = null
    var mp66: Map<V1, V1>? = null
    var mp67: Enum? = null
    var mp68: List<Enum>? = null
    var mp69: Array<Enum>? = null
    var mp70: Map<Enum, Enum>? = null
    var mp71: Map<Enum, List<Enum>>? = null
    var mp72: Map<Enum, List<Array<Enum>>>? = null
    var mp73: Map<Enum, List<Array<Enum>>>? = null
    val ip1 = false
    val ip2: String? = null
    val ip3: V1? = null
    val ip4: V2? = null
    val ip5: V3? = null
    val ip6: List<String>? = null
    val ip7: List<V1>? = null
    val ip8: List<*>? = null
    val ip9: List<String>? = null
    val ip10: List<String>? = null
    val ip11: List<V1>? = null
    val ip12: List<V2>? = null
    val ip13: List<V2>? = null
    val ip14: List<V3>? = null
    val ip15: List<V3>? = null
    val ip16: List<V4>? = null
    val ip17: List<V4>? = null
    val ip18: BooleanArray? = null
    val ip19: Array<V1>? = null
    val ip20: Array<V2>? = null
    val ip21: Array<V3>? = null
    val ip22: Array<List<String>>? = null
    val ip23: Array<List<*>>? = null
    val ip24: Array<List<V1>>? = null
    val ip25: Array<List<String>>? = null
    val ip26: Array<List<String>>? = null
    val ip27: Array<BooleanArray>? = null
    val ip28: Array<Array<V1>>? = null
    val ip29: Array<Array<List<String>>>? = null
    val ip30: Array<Array<List<*>>>? = null
    val ip31: Array<Array<List<V1>>>? = null
    val ip32: Array<Array<List<String>>>? = null
    val ip33: Array<Array<List<String>>>? = null
    val ip34: List<BooleanArray>? = null
    val ip35: List<Array<V1>>? = null
    val ip36: Array<List<Array<String>>>? = null
    val ip37: Array<List<Array<V1>>>? = null
    val ip38: Array<List<Array<String>>>? = null
    val ip39: Array<List<Array<String>>>? = null
    val ip40: List<List<Boolean>>? = null
    val ip41: List<V1>? = null
    val ip42: List<List<String>>? = null
    val ip43: List<List<*>>? = null
    val ip44: List<List<V1>>? = null
    val ip45: List<List<String>>? = null
    val ip46: List<List<String>>? = null
    val ip47: List<List<BooleanArray>>? = null
    val ip48: List<List<Array<V1>>>? = null
    val ip49: List<List<Array<String>>>? = null
    val ip50: List<List<Array<String>>>? = null
    val ip51: List<List<Array<String>>>? = null
    val ip52: InnerModel<String>? = null
    val ip53: InnerModel<V1>? = null
    val ip54: InnerModel<out String>? = null
    val ip55: InnerModel<in String>? = null
    val ip56: List<InnerModel<String>>? = null
    val ip57: List<MutableList<out Array<V1>>?>? = null
    val ip58: List<Array<MutableList<out Array<V1>>?>?>? = null
    val ip59: Array<List<Array<MutableList<out Array<V1>>?>?>>? = null
    val ip60: KotlinModel<String, V2, IntersectionType, V4>? = null
    val ip61: Map<String, String>? = null
    val ip62: Map<*, String>? = null
    val ip63: Map<String, *>? = null
    val ip64: Map<in String, String>? = null
    val ip65: Map<in V1, V1>? = null
    val ip66: Map<V1, V1>? = null
    val ip67: Enum? = null
    val ip68: List<Enum>? = null
    val ip69: Array<Enum>? = null
    val ip70: Map<Enum, Enum>? = null
    val ip71: Map<Enum, List<Enum>>? = null
    val ip72: Map<Enum, List<Array<Enum>>>? = null
    val ip73: Map<Enum, List<Array<Enum>>>? = null
    lateinit var lp2: String
    lateinit var lp6: List<String>
    lateinit var lp7: List<V1>
    lateinit var lp8: List<*>
    lateinit var lp9: List<String>
    lateinit var lp10: List<String>
    lateinit var lp11: List<V1>
    lateinit var lp12: List<V2>
    lateinit var lp13: List<V2>
    lateinit var lp14: List<V3>
    lateinit var lp15: List<V3>
    lateinit var lp16: List<V4>
    lateinit var lp17: List<V4>
    lateinit var lp18: BooleanArray
    lateinit var lp19: Array<V1>
    lateinit var lp20: Array<V2>
    lateinit var lp21: Array<V3>
    lateinit var lp22: Array<List<String>>
    lateinit var lp23: Array<List<*>>
    lateinit var lp24: Array<List<V1>>
    lateinit var lp25: Array<List<String>>
    lateinit var lp26: Array<List<String>>
    lateinit var lp27: Array<BooleanArray>
    lateinit var lp28: Array<Array<V1>>
    lateinit var lp29: Array<Array<List<String>>>
    lateinit var lp30: Array<Array<List<*>>>
    lateinit var lp31: Array<Array<List<V1>>>
    lateinit var lp32: Array<Array<List<String>>>
    lateinit var lp33: Array<Array<List<String>>>
    lateinit var lp34: List<BooleanArray>
    lateinit var lp35: List<Array<V1>>
    lateinit var lp36: Array<List<Array<String>>>
    lateinit var lp37: Array<List<Array<V1>>>
    lateinit var lp38: Array<List<Array<String>>>
    lateinit var lp39: Array<List<Array<String>>>
    lateinit var lp40: List<List<Boolean>>
    lateinit var lp41: List<V1>
    lateinit var lp42: List<List<String>>
    lateinit var lp43: List<List<*>>
    lateinit var lp44: List<List<V1>>
    lateinit var lp45: List<List<String>>
    lateinit var lp46: List<List<String>>
    lateinit var lp47: List<List<BooleanArray>>
    lateinit var lp48: List<List<Array<V1>>>
    lateinit var lp49: List<List<Array<String>>>
    lateinit var lp50: List<List<Array<String>>>
    lateinit var lp51: List<List<Array<String>>>
    lateinit var lp52: InnerModel<String>
    lateinit var lp53: InnerModel<V1>
    lateinit var lp54: InnerModel<out String>
    lateinit var lp55: InnerModel<in String>
    lateinit var lp56: List<InnerModel<String>>
    lateinit var lp57: List<MutableList<out Array<V1>>?>
    lateinit var lp58: List<Array<MutableList<out Array<V1>>?>?>
    lateinit var lp59: Array<List<Array<MutableList<out Array<V1>>?>?>>
    lateinit var lp60: KotlinModel<String, V2, IntersectionType, V4>
    lateinit var lp61: Map<String, String>
    lateinit var lp62: Map<*, String>
    lateinit var lp63: Map<String, *>
    lateinit var lp64: Map<in String, String>
    lateinit var lp65: Map<in V1, V1>
    lateinit var lp66: Map<V1, V1>
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

    sealed class InnerModel<T> {
        var f1 = false
        var f2: String? = null
        var f3 = 0
        var f4: T? = null
        var f5: KotlinModel<*, *, *, *>? = null
        var f6: KotlinModel<*, *, *, *>? = null

        fun simpleMethod(p1: Int): Int {
            return p1
        }

        companion object {
            fun <M : String?> simpleMethod(p1: Int, p2: M): Int {
                return p1
            }
        }
    }

    inline fun <reified T> membersOf() = T::class.members

    inner class IntersectionInnerType : Number(), Comparator<String> {
        override fun toByte(): Byte {
            TODO("Not yet implemented")
        }

        override fun toChar(): Char {
            TODO("Not yet implemented")
        }

        override fun toDouble(): Double {
            TODO("Not yet implemented")
        }

        override fun toFloat(): Float {
            TODO("Not yet implemented")
        }

        override fun toInt(): Int {
            TODO("Not yet implemented")
        }

        override fun toLong(): Long {
            TODO("Not yet implemented")
        }

        override fun toShort(): Short {
            TODO("Not yet implemented")
        }

        override fun compare(o1: String?, o2: String?): Int {
            TODO("Not yet implemented")
        }
    }

    class IntersectionType : Number(), Comparator<String> {
        override fun toByte(): Byte {
            TODO("Not yet implemented")
        }

        override fun toChar(): Char {
            TODO("Not yet implemented")
        }

        override fun toDouble(): Double {
            TODO("Not yet implemented")
        }

        override fun toFloat(): Float {
            TODO("Not yet implemented")
        }

        override fun toInt(): Int {
            TODO("Not yet implemented")
        }

        override fun toLong(): Long {
            TODO("Not yet implemented")
        }

        override fun toShort(): Short {
            TODO("Not yet implemented")
        }

        override fun compare(o1: String?, o2: String?): Int {
            TODO("Not yet implemented")
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

        fun <T> simpleMethod(list: List<T>) {

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

        fun <T> simpleMethod(list: List<T>?) {

        }

        fun simpleMethod(p1: String?, vararg p2: Int): Int {
            return p2[0]
        }

        operator fun invoke(): String {
            return "test"
        }
    }
}

data class KotlinOtherModel<V1, V2 : String?, V3, V4 : V2?>(
        val iсp1: Boolean,
        val iсp2: String,
        val iсp3: V1,
        val iсp4: V2,
        val iсp5: V3,
        val iсp6: List<String>,
        val iсp7: List<V1>,
        val iсp8: List<*>,
        val iсp9: List<String>,
        val iсp10: List<String>,
        val iсp11: List<V1>,
        val iсp12: List<V2>,
        val iсp13: List<V2>,
        val iсp14: List<V3>,
        val iсp15: List<V3>,
        val iсp16: List<V4>,
        val iсp17: List<V4>,
        val iсp18: BooleanArray,
        val iсp19: Array<V1>,
        val iсp20: Array<V2>,
        val iсp21: Array<V3>,
        val iсp22: Array<List<String>>,
        val iсp23: Array<List<*>>,
        val iсp24: Array<List<V1>>,
        val iсp25: Array<List<String>>,
        val iсp26: Array<List<String>>,
        val iсp27: Array<BooleanArray>,
        val iсp28: Array<Array<V1>>,
        val iсp29: Array<Array<List<String>>>,
        val iсp30: Array<Array<List<*>>>,
        val iсp31: Array<Array<List<V1>>>,
        val iсp32: Array<Array<List<String>>>,
        val iсp33: Array<Array<List<String>>>,
        val iсp34: List<BooleanArray>,
        val iсp35: List<Array<V1>>,
        val iсp36: Array<List<Array<String>>>,
        val iсp37: Array<List<Array<V1>>>,
        val iсp38: Array<List<Array<String>>>,
        val iсp39: Array<List<Array<String>>>,
        val iсp40: List<List<Boolean>>,
        val iсp41: List<V1>,
        val iсp42: List<List<String>>,
        val iсp43: List<List<*>>,
        val iсp44: List<List<V1>>,
        val iсp45: List<List<String>>,
        val iсp46: List<List<String>>,
        val iсp47: List<List<BooleanArray>>,
        val iсp48: List<List<Array<V1>>>,
        val iсp49: List<List<Array<String>>>,
        val iсp50: List<List<Array<String>>>,
        val iсp51: List<List<Array<String>>>,
        val iсp52: InnerModel<String>,
        val iсp53: InnerModel<V1>,
        val iсp54: InnerModel<out String>,
        val iсp55: InnerModel<in String>,
        val iсp56: List<InnerModel<String>>,
        val iсp57: List<MutableList<out Array<V1>>?>,
        val iсp58: List<Array<MutableList<out Array<V1>>?>?>,
        val iсp59: Array<List<Array<MutableList<out Array<V1>>?>?>>,
        val iсp60: KotlinModel<String, V2, IntersectionType, V4>,
        val iсp61: Map<String, String>,
        val iсp62: Map<*, String>,
        val iсp63: Map<String, *>,
        val iсp64: Map<in String, String>,
        val iсp65: Map<in V1, V1>,
        val iсp66: Map<V1, V1>,
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
        val icfp4: (input1: String, input2: String, input3: String) -> KotlinModel<*, *, *, *>,
        val icfp5: Map<Enum, List<Array<Enum>>>.(input1: String, input2: String, input3: String) -> KotlinModel<*, *, *, *>,
) where V3 : Number?, V3 : Comparator<String> {
    var mp1 = false
    var mp2: String? = null
    var mp3: V1? = null
    var mp4: V2? = null
    var mp5: V3? = null
    var mp6: List<String>? = null
    var mp7: List<V1>? = null
    var mp8: List<*>? = null
    var mp9: List<String>? = null
    var mp10: List<String>? = null
    var mp11: List<V1>? = null
    var mp12: List<V2>? = null
    var mp13: List<V2>? = null
    var mp14: List<V3>? = null
    var mp15: List<V3>? = null
    var mp16: List<V4>? = null
    var mp17: List<V4>? = null
    var mp18: BooleanArray? = null
    var mp19: Array<V1>? = null
    var mp20: Array<V2>? = null
    var mp21: Array<V3>? = null
    var mp22: Array<List<String>>? = null
    var mp23: Array<List<*>>? = null
    var mp24: Array<List<V1>>? = null
    var mp25: Array<List<String>>? = null
    var mp26: Array<List<String>>? = null
    var mp27: Array<BooleanArray>? = null
    var mp28: Array<Array<V1>>? = null
    var mp29: Array<Array<List<String>>>? = null
    var mp30: Array<Array<List<*>>>? = null
    var mp31: Array<Array<List<V1>>>? = null
    var mp32: Array<Array<List<String>>>? = null
    var mp33: Array<Array<List<String>>>? = null
    var mp34: List<BooleanArray>? = null
    var mp35: List<Array<V1>>? = null
    var mp36: Array<List<Array<String>>>? = null
    var mp37: Array<List<Array<V1>>>? = null
    var mp38: Array<List<Array<String>>>? = null
    var mp39: Array<List<Array<String>>>? = null
    var mp40: List<List<Boolean>>? = null
    var mp41: List<V1>? = null
    var mp42: List<List<String>>? = null
    var mp43: List<List<*>>? = null
    var mp44: List<List<V1>>? = null
    var mp45: List<List<String>>? = null
    var mp46: List<List<String>>? = null
    var mp47: List<List<BooleanArray>>? = null
    var mp48: List<List<Array<V1>>>? = null
    var mp49: List<List<Array<String>>>? = null
    var mp50: List<List<Array<String>>>? = null
    var mp51: List<List<Array<String>>>? = null
    var mp52: InnerModel<String>? = null
    var mp53: InnerModel<V1>? = null
    var mp54: InnerModel<out String>? = null
    var mp55: InnerModel<in String>? = null
    var mp56: List<InnerModel<String>>? = null
    var mp57: List<MutableList<out Array<V1>>?>? = null
    var mp58: List<Array<MutableList<out Array<V1>>?>?>? = null
    var mp59: Array<List<Array<MutableList<out Array<V1>>?>?>>? = null
    var mp60: KotlinModel<String, V2, IntersectionType, V4>? = null
    var mp61: Map<String, String>? = null
    var mp62: Map<*, String>? = null
    var mp63: Map<String, *>? = null
    var mp64: Map<in String, String>? = null
    var mp65: Map<in V1, V1>? = null
    var mp66: Map<V1, V1>? = null
    var mp67: Enum? = null
    var mp68: List<Enum>? = null
    var mp69: Array<Enum>? = null
    var mp70: Map<Enum, Enum>? = null
    var mp71: Map<Enum, List<Enum>>? = null
    var mp72: Map<Enum, List<Array<Enum>>>? = null
    var mp73: Map<Enum, List<Array<Enum>>>? = null
    val ip1 = false
    val ip2: String? = null
    val ip3: V1? = null
    val ip4: V2? = null
    val ip5: V3? = null
    val ip6: List<String>? = null
    val ip7: List<V1>? = null
    val ip8: List<*>? = null
    val ip9: List<String>? = null
    val ip10: List<String>? = null
    val ip11: List<V1>? = null
    val ip12: List<V2>? = null
    val ip13: List<V2>? = null
    val ip14: List<V3>? = null
    val ip15: List<V3>? = null
    val ip16: List<V4>? = null
    val ip17: List<V4>? = null
    val ip18: BooleanArray? = null
    val ip19: Array<V1>? = null
    val ip20: Array<V2>? = null
    val ip21: Array<V3>? = null
    val ip22: Array<List<String>>? = null
    val ip23: Array<List<*>>? = null
    val ip24: Array<List<V1>>? = null
    val ip25: Array<List<String>>? = null
    val ip26: Array<List<String>>? = null
    val ip27: Array<BooleanArray>? = null
    val ip28: Array<Array<V1>>? = null
    val ip29: Array<Array<List<String>>>? = null
    val ip30: Array<Array<List<*>>>? = null
    val ip31: Array<Array<List<V1>>>? = null
    val ip32: Array<Array<List<String>>>? = null
    val ip33: Array<Array<List<String>>>? = null
    val ip34: List<BooleanArray>? = null
    val ip35: List<Array<V1>>? = null
    val ip36: Array<List<Array<String>>>? = null
    val ip37: Array<List<Array<V1>>>? = null
    val ip38: Array<List<Array<String>>>? = null
    val ip39: Array<List<Array<String>>>? = null
    val ip40: List<List<Boolean>>? = null
    val ip41: List<V1>? = null
    val ip42: List<List<String>>? = null
    val ip43: List<List<*>>? = null
    val ip44: List<List<V1>>? = null
    val ip45: List<List<String>>? = null
    val ip46: List<List<String>>? = null
    val ip47: List<List<BooleanArray>>? = null
    val ip48: List<List<Array<V1>>>? = null
    val ip49: List<List<Array<String>>>? = null
    val ip50: List<List<Array<String>>>? = null
    val ip51: List<List<Array<String>>>? = null
    val ip52: InnerModel<String>? = null
    val ip53: InnerModel<V1>? = null
    val ip54: InnerModel<out String>? = null
    val ip55: InnerModel<in String>? = null
    val ip56: List<InnerModel<String>>? = null
    val ip57: List<MutableList<out Array<V1>>?>? = null
    val ip58: List<Array<MutableList<out Array<V1>>?>?>? = null
    val ip59: Array<List<Array<MutableList<out Array<V1>>?>?>>? = null
    val ip60: KotlinModel<String, V2, IntersectionType, V4>? = null
    val ip61: Map<String, String>? = null
    val ip62: Map<*, String>? = null
    val ip63: Map<String, *>? = null
    val ip64: Map<in String, String>? = null
    val ip65: Map<in V1, V1>? = null
    val ip66: Map<V1, V1>? = null
    val ip67: Enum? = null
    val ip68: List<Enum>? = null
    val ip69: Array<Enum>? = null
    val ip70: Map<Enum, Enum>? = null
    val ip71: Map<Enum, List<Enum>>? = null
    val ip72: Map<Enum, List<Array<Enum>>>? = null
    val ip73: Map<Enum, List<Array<Enum>>>? = null
    lateinit var lp2: String
    lateinit var lp6: List<String>
    lateinit var lp7: List<V1>
    lateinit var lp8: List<*>
    lateinit var lp9: List<String>
    lateinit var lp10: List<String>
    lateinit var lp11: List<V1>
    lateinit var lp12: List<V2>
    lateinit var lp13: List<V2>
    lateinit var lp14: List<V3>
    lateinit var lp15: List<V3>
    lateinit var lp16: List<V4>
    lateinit var lp17: List<V4>
    lateinit var lp18: BooleanArray
    lateinit var lp19: Array<V1>
    lateinit var lp20: Array<V2>
    lateinit var lp21: Array<V3>
    lateinit var lp22: Array<List<String>>
    lateinit var lp23: Array<List<*>>
    lateinit var lp24: Array<List<V1>>
    lateinit var lp25: Array<List<String>>
    lateinit var lp26: Array<List<String>>
    lateinit var lp27: Array<BooleanArray>
    lateinit var lp28: Array<Array<V1>>
    lateinit var lp29: Array<Array<List<String>>>
    lateinit var lp30: Array<Array<List<*>>>
    lateinit var lp31: Array<Array<List<V1>>>
    lateinit var lp32: Array<Array<List<String>>>
    lateinit var lp33: Array<Array<List<String>>>
    lateinit var lp34: List<BooleanArray>
    lateinit var lp35: List<Array<V1>>
    lateinit var lp36: Array<List<Array<String>>>
    lateinit var lp37: Array<List<Array<V1>>>
    lateinit var lp38: Array<List<Array<String>>>
    lateinit var lp39: Array<List<Array<String>>>
    lateinit var lp40: List<List<Boolean>>
    lateinit var lp41: List<V1>
    lateinit var lp42: List<List<String>>
    lateinit var lp43: List<List<*>>
    lateinit var lp44: List<List<V1>>
    lateinit var lp45: List<List<String>>
    lateinit var lp46: List<List<String>>
    lateinit var lp47: List<List<BooleanArray>>
    lateinit var lp48: List<List<Array<V1>>>
    lateinit var lp49: List<List<Array<String>>>
    lateinit var lp50: List<List<Array<String>>>
    lateinit var lp51: List<List<Array<String>>>
    lateinit var lp52: InnerModel<String>
    lateinit var lp53: InnerModel<V1>
    lateinit var lp54: InnerModel<out String>
    lateinit var lp55: InnerModel<in String>
    lateinit var lp56: List<InnerModel<String>>
    lateinit var lp57: List<MutableList<out Array<V1>>?>
    lateinit var lp58: List<Array<MutableList<out Array<V1>>?>?>
    lateinit var lp59: Array<List<Array<MutableList<out Array<V1>>?>?>>
    lateinit var lp60: KotlinModel<String, V2, IntersectionType, V4>
    lateinit var lp61: Map<String, String>
    lateinit var lp62: Map<*, String>
    lateinit var lp63: Map<String, *>
    lateinit var lp64: Map<in String, String>
    lateinit var lp65: Map<in V1, V1>
    lateinit var lp66: Map<V1, V1>
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
        fun simpleMethod(p1: IntArray) {

        }

        class LocalClass {}

        return p1[0]
    }

    inline fun f(crossinline body: () -> Unit) {
        val f = Runnable { body() }

    }

    sealed class InnerModel<T> {
        var f1 = false
        var f2: String? = null
        var f3 = 0
        var f4: T? = null
        var f5: KotlinModel<*, *, *, *>? = null
        var f6: KotlinModel<*, *, *, *>? = null

        fun simpleMethod(p1: Int): Int {
            return p1
        }

        companion object {
            fun <M : String?> simpleMethod(p1: Int, p2: M): Int {
                return p1
            }
        }
    }

    inline fun <reified T> membersOf() = T::class.members

    inner class IntersectionInnerType : Number(), Comparator<String> {
        override fun toByte(): Byte {
            TODO("Not yet implemented")
        }

        override fun toChar(): Char {
            TODO("Not yet implemented")
        }

        override fun toDouble(): Double {
            TODO("Not yet implemented")
        }

        override fun toFloat(): Float {
            TODO("Not yet implemented")
        }

        override fun toInt(): Int {
            TODO("Not yet implemented")
        }

        override fun toLong(): Long {
            TODO("Not yet implemented")
        }

        override fun toShort(): Short {
            TODO("Not yet implemented")
        }

        override fun compare(o1: String?, o2: String?): Int {
            TODO("Not yet implemented")
        }
    }

    class IntersectionType : Number(), Comparator<String> {
        override fun toByte(): Byte {
            TODO("Not yet implemented")
        }

        override fun toChar(): Char {
            TODO("Not yet implemented")
        }

        override fun toDouble(): Double {
            TODO("Not yet implemented")
        }

        override fun toFloat(): Float {
            TODO("Not yet implemented")
        }

        override fun toInt(): Int {
            TODO("Not yet implemented")
        }

        override fun toLong(): Long {
            TODO("Not yet implemented")
        }

        override fun toShort(): Short {
            TODO("Not yet implemented")
        }

        override fun compare(o1: String?, o2: String?): Int {
            TODO("Not yet implemented")
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

        fun <T> simpleMethod(list: List<T>) {

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

        fun <T> simpleMethod(list: List<T>?) {

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

    inline fun <T> simpleMethod(list: List<T>?, lambda: (p: String) -> String) {

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

inline fun <T> simpleMethod(list: List<T>?, lambda: (p: String) -> String) {

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
