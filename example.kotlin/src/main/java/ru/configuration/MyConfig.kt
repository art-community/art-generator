package ru.configuration

import io.art.core.collection.ImmutableArray
import io.art.core.source.NestedConfiguration
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*


data class MyConfig(
        val FBInteger: Int?,
        val FBShort: Short,
        val FBByte: Byte,
        val FBBoolean: Boolean,
        val FBLong: Long,
        val FBString: String,
        val FBDouble: Double,
        val FBFloat: Float,
        val FUUID: UUID,
        val FlocalDateTime: LocalDateTime,
        val FzonedDateTime: ZonedDateTime,
        val Fdate: Date,


        val FAint: IntArray,
        val FAchar: CharArray,
        val FAshort: ShortArray,
        val FAbyte: ByteArray,
        val FAboolean: BooleanArray,
        val FAlong: LongArray,
        val FAdouble: DoubleArray,
        val FAfloat: FloatArray,

        val FABInteger: Array<Int>,
        val FABCharacter: Array<Char>,
        val FABShort: Array<Short>,
        val FABByte: Array<Byte>,
        val FABBoolean: Array<Boolean>,
        val FABLong: Array<Long>,
        val FABString: Array<String>,
        val FABDouble: Array<Double>,
        val FABFloat: Array<Float>,


        val FLInteger: List<Int>,
        val FLCharacter: List<Char>,
        val FLShort: List<Short>,
        val FLByte: List<Byte>,
        val FLBoolean: List<Boolean>,
        val FLLong: List<Long>,
        val FLString: List<String>,
        val FLDouble: List<Double>,
        val FLFloat: List<Float>,


        val FSInteger: Set<Int>,
        val FSCharacter: Set<Char>,
        val FSShort: Set<Short>,
        val FSByte: Set<Byte>,
        val FSBoolean: Set<Boolean>,
        val FSLong: Set<Long>,
        val FSString: Set<String>,
        val FSDouble: Set<Double>,
        val FSFloat: Set<Float>,
        val FModelL: List<MyConfig>,
        val FModelS: Set<MyConfig>,
        val FModelA: Array<MyConfig>,


        val FModelLA: List<Array<MyConfig>>,
        val FModelAL: Array<List<MyConfig>>,
        val FModeALA: kotlin.Array<List<Array<MyConfig>>>,


        val FModelSA: Set<Array<MyConfig>>,
        val FModelAS: kotlin.Array<Set<MyConfig>>,
        val FModelSAA: kotlin.Array<Set<Array<MyConfig>>>,


        val FModelAA: ImmutableArray<Array<MyConfig>>,
        val FModelAAA: kotlin.Array<ImmutableArray<ru.configuration.MyConfig>>,
        val FModelAAAA: kotlin.Array<ImmutableArray<Array<MyConfig>>>,


        val FModelSAAAAA: io.art.core.collection.ImmutableSet<Array<MyConfig>>,
        val FModelSAAAAAA: kotlin.Array<io.art.core.collection.ImmutableSet<ru.configuration.MyConfig>>,
        val FModelLSAA: kotlin.Array<io.art.core.collection.ImmutableSet<Array<MyConfig>>>,

        val FModelLAAAAA: List<ImmutableArray<ru.configuration.MyConfig>>,
        val FModelLAA: List<kotlin.Array<ImmutableArray<ru.configuration.MyConfig>>>,
        val FModelLS: List<io.art.core.collection.ImmutableSet<ru.configuration.MyConfig>>,
        val FModelLSA: List<kotlin.Array<io.art.core.collection.ImmutableSet<ru.configuration.MyConfig>>>,
        val FModelLL: ImmutableArray<List<MyConfig>>,
        val FModelLLLA: ImmutableArray<Array<List<MyConfig>>>,


        val FModelLSL: io.art.core.collection.ImmutableSet<List<MyConfig>>,
        val FModelLSLA: io.art.core.collection.ImmutableSet<Array<List<MyConfig>>>,
        val FPModelLSLA: io.art.core.collection.ImmutableSet<kotlin.Array<List<ru.configuration.MyConfigParent>>>,


        val FPModelLSLAIM: io.art.core.collection.ImmutableMap<kotlin.String, kotlin.Array<List<ru.configuration.MyConfigParent>>>,
        val FPModelLSLAM: Map<kotlin.String, kotlin.Array<List<ru.configuration.MyConfigParent>>>,
        val FPModelLSLAMM: Map<String, Array<Map<String, List<MyConfigParent>>>>,
        val FPModelLSLAMOM: Map<String, Array<Map<String, Optional<List<MyConfigParent>>>>>,


        val FSIM: java.util.Optional<io.art.core.collection.ImmutableMap<kotlin.String, Set<kotlin.String>>>,
        val FSM: java.util.Optional<Map<String, String>>,
        val FSMA: java.util.Optional<kotlin.Array<Map<String, String>>>,


        val FNested: NestedConfiguration,
        val FNestedMap: Map<String, NestedConfiguration>,
        val FNestedIMap: io.art.core.collection.ImmutableMap<kotlin.String, NestedConfiguration>,
        val FNestedList: List<NestedConfiguration>,
        val FNestedIList: ImmutableArray<NestedConfiguration>,
        val FNestedSet: Set<NestedConfiguration>,
        val FNestedISet: io.art.core.collection.ImmutableSet<NestedConfiguration>?,
)
