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

@file:Suppress(WARNINGS)

package model.model

import io.art.core.constants.CompilerSuppressingWarnings.WARNINGS
import java.time.Duration

// Must be ignored
const val outsideProperty = "test"

// Must be ignored
fun outsideMethod(argument: Int) {

}

// Must be ignored
private class PrivateClass {}

// Must be ignored
internal class InternalClass {}

class EmptyClass {}

object EmptyObject {}

sealed class KotlinModelParent(protected val protectedIcp: Boolean, val icp: Boolean) {
    protected val protectedIp: Boolean? = null
    val ip: Boolean? = null
    lateinit var protectedMp: String
    lateinit var publicMp: String
    lateinit var childP: KotlinModel
    lateinit var selfP: KotlinModelParent
    abstract var abstractProperty: String

    // Must be ignored
    protected fun protectedInstanceMethod(argument: Int): Int {
        return argument
    }

    open fun instanceOpenMethod(argument: Int): Int {
        return argument
    }

    fun instanceClosedMethod(argument: Int): Int {
        return argument
    }

    protected abstract fun protectedAbstractMethod(argument: Int): Int

    abstract fun abstractMethod(argument: Int): Int
}

data class KotlinModel(
        private val privateIcp: Boolean,
        internal val internalIcp: Boolean,
        val icp1: Boolean,
        val icp2: String,
        val icp3: BooleanArray,
        val icp4: Array<String>,
        val icp5: Array<*>,
        val icp6: Array<in String>,
        val icp7: Array<out String>,
        val icp8: List<Boolean>,
        val icp9: List<String>,
        val icp10: List<*>,
        val icp11: Map<Boolean, String>,
        val icp12: Map<*, String>,
        val icp13: Map<Boolean, *>,
        val icp14: Map<Boolean, BooleanArray>,
        val icp15: Map<Boolean, List<*>>,
        val icp16: Map<in Boolean, List<*>>,
        val icp17: Map<out Boolean, List<*>>,
        val icp18: KotlinModel?,
        val icp19: InnerModel,
        val icp20: NestedModel,
        val icp21: Enum,
        val icfp1: () -> Unit,
        val icfp2: () -> Nothing,
        val icfp3: () -> Any,
        val icfp4: (String, String, String) -> KotlinModel,
        val icfp5: Map<Enum, List<Array<Enum>>>.(String, String, String) -> KotlinModel, override var abstractProperty: String,
) : KotlinModelParent(icp1, icp1) {
    val ip1: Boolean? = null
    val ip2: String? = null
    val ip3: BooleanArray? = null
    val ip4: Array<String>? = null
    val ip5: Array<*>? = null
    val ip6: Array<in String>? = null
    val ip7: Array<out String>? = null
    val ip8: List<Boolean>? = null
    val ip9: List<String>? = null
    val ip10: List<*>? = null
    val ip11: Map<Boolean, String>? = null
    val ip12: Map<*, String>? = null
    val ip13: Map<Boolean, *>? = null
    val ip14: Map<Boolean, BooleanArray>? = null
    val ip15: Map<Boolean, List<*>>? = null
    val ip16: Map<in Boolean, List<*>>? = null
    val ip17: Map<out Boolean, List<*>>? = null
    val ip18: KotlinModel? = null
    val ip19: InnerModel? = null
    val ip20: NestedModel? = null
    val ip21: Enum? = null
    val lip1: Boolean? by lazy { null }
    val lip2: String? by lazy { null }
    val lip3: BooleanArray? by lazy { null }
    val lip4: Array<String>? by lazy { null }
    val lip5: Array<*>? by lazy { null }
    val lip6: Array<in String>? by lazy { null }
    val lip7: Array<out String>? by lazy { null }
    val lip8: List<Boolean>? by lazy { null }
    val lip9: List<String>? by lazy { null }
    val lip10: List<*>? by lazy { null }
    val lip11: Map<Boolean, String>? by lazy { null }
    val lip12: Map<*, String>? by lazy { null }
    val lip13: Map<Boolean, *>? by lazy { null }
    val lip14: Map<Boolean, BooleanArray>? by lazy { null }
    val lip15: Map<Boolean, List<*>>? by lazy { null }
    val lip16: Map<in Boolean, List<*>>? by lazy { null }
    val lip17: Map<out Boolean, List<*>>? by lazy { null }
    val lip18: KotlinModel? by lazy { null }
    val lip19: InnerModel? by lazy { null }
    val lip20: NestedModel? by lazy { null }
    val lip21: Enum? by lazy { null }
    lateinit var mp1: Duration
    lateinit var mp2: String
    lateinit var mp3: BooleanArray
    lateinit var mp4: Array<String>
    lateinit var mp5: Array<*>
    lateinit var mp6: Array<in String>
    lateinit var mp7: Array<out String>
    lateinit var mp8: List<Boolean>
    lateinit var mp9: List<String>
    lateinit var mp10: List<*>
    lateinit var mp11: Map<Boolean, String>
    lateinit var mp12: Map<*, String>
    lateinit var mp13: Map<Boolean, *>
    lateinit var mp14: Map<Boolean, BooleanArray>
    lateinit var mp15: Map<Boolean, List<*>>
    lateinit var mp16: Map<in Boolean, List<*>>
    lateinit var mp17: Map<out Boolean, List<*>>
    lateinit var mp18: KotlinModel
    lateinit var mp19: InnerModel
    lateinit var mp20: NestedModel
    lateinit var mp22: Enum
    lateinit var mp23: MixedKotlinModel

    // Must be ignored
    var specificProperty1: String
        get() = ""
        private set(value) = TODO()

    // Must be ignored
    var specificProperty2: String
        get() = ""
        internal set(value) = TODO()

    var specificProperty3: String
        get() = ""
        set(value) = TODO()

    override fun instanceOpenMethod(argument: Int): Int {
        return argument
    }

    override fun protectedAbstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    override fun abstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    fun instanceMethod(argument: IntArray): Int {
        return argument[0]
    }

    inline fun inlineMethod(crossinline body: () -> Unit) {
        val runnable = Runnable { body() }
    }

    inline fun inlineMethodWithCrossInline(crossinline body: () -> Unit) {
        val runnable = Runnable { body() }
    }

    inline fun inlineMethodWithNoInline(noinline body1: () -> Unit, body2: () -> Unit) {
        val runnable = Runnable { body1() }
    }

    infix fun infixMethod(argument: String): String {
        return argument
    }

    inline fun inlineMethod(argument1: List<String>?, argument2: (p: String) -> String) {
    }

    tailrec fun recursiveMethod(argument1: Long, argument2: Long = 1): Long {
        // Must be ignored
        class LocalClass {}

        val soFar = argument1 * argument2
        return if (argument1 <= 1) {
            soFar
        } else {
            recursiveMethod(argument1 - 1, soFar)
        }
    }

    operator fun invoke(): String {
        return "test"
    }

    class NestedModel {
        var p1 = false
        var p2: String? = null
        var p3 = 0

        fun instanceMethod(argument: Int): Int {
            return argument
        }

        companion object {
            fun staticMethod(argument: Int): Int {
                return argument
            }
        }
    }

    // Must be ignored as class (type will be available)
    inner class InnerModel {
        var p1 = false
        var p2: String? = null
        var p3 = 0

        fun instanceMethod(argument: Int): Int {
            return argument
        }
    }

    // Must be ignored
    private class PrivateClass {}

    // Must be ignored
    protected class ProtectedClass {}

    // Must be ignored
    internal class InternalClass {}

    enum class Enum {
        FIRST
    }

    companion object {
        fun staticMethod(argument: Int): Int {
            return argument
        }

        fun staticMethod(argument: String): String {
            return argument
        }

        fun staticMethod(argument: List<*>) {

        }

        fun staticMethod(argument1: String?, vararg argument2: Int): Int {
            return argument2[0]
        }

        inline fun inlineStaticMethod(crossinline body: () -> Unit) {
            val runnable = Runnable { body() }
        }

        infix fun infixStaticMethod(argument: String): String {
            return argument
        }

        inline fun inlineStaticMethod(argument1: List<String>?, argument2: (p: String) -> String) {
        }

        tailrec fun recursiveStaticMethod(argument1: Long, argument2: Long = 1): Long {
            val soFar = argument1 * argument2
            return if (argument1 <= 1) {
                soFar
            } else {
                recursiveStaticMethod(argument1 - 1, soFar)
            }
        }

        operator fun invoke(): String {
            return "test"
        }
    }
}

interface KotlinInterface {
    val interfaceProperty: String

    // Must be ignored
    private fun privateMethod() {

    }

    fun publicMethod() {

    }

    fun implementableMethod()
}

object KotlinObject : KotlinModelParent(false, true), KotlinInterface {
    const val constantProperty = "test"

    // Must be ignored
    init {

    }

    override var abstractProperty: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun protectedAbstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    override fun abstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    override val interfaceProperty: String
        get() = TODO("Not yet implemented")

    var ownProperty: String
        get() = TODO("Not yet implemented")
        set(value) = TODO("Not yet implemented")

    @Throws(NullPointerException::class)
    override fun implementableMethod() {
        TODO("Not yet implemented")
    }

    // Must be ignored
    private fun privateMethod() {

    }

    fun publicSelfMethod() {

    }
}

open class KotlinClass(override var abstractProperty: String) : KotlinModelParent(false, true), KotlinInterface {
    // Must be ignored
    val KotlinObject.extensionProperty: String
        get() {
            TODO()
        }

    constructor(input: Int) : this("") {

    }

    // Must be ignored
    fun KotlinObject.extensionFunction() = "test"

    val genericProperty: KotlinGeneric<*>? = null

    override fun protectedAbstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    final override fun abstractMethod(argument: Int): Int {
        TODO("Not yet implemented")
    }

    override val interfaceProperty: String
        get() = TODO("Not yet implemented")

    var ownProperty: String
        get() = TODO("Not yet implemented")
        set(value) = TODO("Not yet implemented")

    @Throws(NullPointerException::class)
    override fun implementableMethod() {
        TODO("Not yet implemented")
    }

    // Must be ignored
    private fun privateMethod() {

    }

    fun publicSelfMethod() {

    }

    // Must be ignored
    suspend fun suspendFunction() {

    }

    // Must be ignored
    suspend fun suspendFunctionParameter(parameter: suspend String.() -> Unit) {

    }

    // Must be ignored
    fun <T> genericMethod(): T? = null

    // Must be ignored
    fun <T> genericMethod(parameter: T.() -> Unit): Unit? = null

    // Must be ignored
    fun <T> genericMethod(parameter: T): Unit? = null

    // Must be ignored as class
    class KotlinGeneric<T>
}

// Must be ignored as class
class KotlinGeneric<T>
