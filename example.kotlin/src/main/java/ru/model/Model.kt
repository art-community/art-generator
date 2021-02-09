package ru.model

import io.art.core.collection.ImmutableArray
import io.art.core.collection.ImmutableMap
import io.art.core.collection.ImmutableSet
import io.art.core.property.LazyProperty
import io.art.value.immutable.Entity
import io.art.value.immutable.Value
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import java.util.stream.Stream

data class Model(
        val FInteger: Int,
        val FCharacter: Char,
        val FShort: Short,
        val FByte: Byte,
        val FBoolean: Boolean,
        val FLong: Long,
        val FDouble: Double,
        val FFloat: Float,

        val FBInteger: Int,
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
        val FDate: Date,

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

        val FCInteger: Collection<Int>,
        val FCCharacter: Collection<Char>,
        val FCShort: Collection<Short>,
        val FCByte: Collection<Byte>,
        val FCBoolean: Collection<Boolean>,
        val FCLong: Collection<Long>,
        val FCString: Collection<String>,
        val FCDouble: Collection<Double>,
        val FCFloat: Collection<Float>,

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

        val FMInteger: Map<Int, Int>,
        val FMCharacter: Map<Char, Char>,
        val FMShort: Map<Short, Short>,
        val FMByte: Map<Byte, Byte>,
        val FMBoolean: Map<Boolean, Boolean>,
        val FMLong: Map<Long, Long>,
        val FMString: Map<String, String>,
        val FMDouble: Map<Double, Double>,
        val pArgFMFloat: Map<Float, Float>,

        val FModel: Model,

        val FAModel: Array<Model>,

        val FLModel: List<Model>,
        val FLLModel: List<List<Model>>,
        val FLSModel: List<Set<Model>>,
        val FLCModel: List<Collection<Model>>,
        val FLMModel: List<Map<String, Model>>,
        val FLAModel: List<Array<Model>>,
        val FALModel: Array<List<Model>>,

        val FSModel: Set<Model>,
        val FSLModel: Set<List<Model>>,
        val FSSModel: Set<Set<Model>>,
        val FSCModel: Set<Collection<Model>>,
        val FSMModel: Set<Map<String, Model>>,
        val FSAModel: Set<Array<Model>>,
        val FASModel: Array<Set<Model>>,

        val FCModel: Collection<Model>,
        val FCLModel: Collection<List<Model>>,
        val FCSModel: Collection<Set<Model>>,
        val FCCModel: Collection<Collection<Model>>,
        val FCMModel: Collection<Map<String, Model>>,
        val FCAModel: Collection<Array<Model>>,
        val FACModel: Array<Collection<Model>>,

        val FMModel: Map<String, Model>,
        val FMAModel: Map<String, Array<Model>>,
        val FMLModel: Map<String, List<Model>>,
        val FMSModel: Map<String, Set<Model>>,
        val FMCModel: Map<String, Collection<Model>>,
        val FMMModel: Map<String, Map<String, Model>>,
        val FAMModel: Array<Map<String, Model>>,

        val FCCMModel: Collection<List<Map<String, Model>>>,
        val FCLMModel: Collection<Set<Map<String, Model>>>,
        val FCSMModel: Collection<Collection<Map<String, Model>>>,
        val FCMMModel: Collection<Map<String, Model>>,

        val FCLMAModel: Collection<Array<List<Map<String, Model>>>>,
        val FCSMAModel: Collection<Array<Set<Map<String, Model>>>>,
        val FCCMAModel: Collection<Array<Collection<Map<String, Model>>>>,
        val FCMAModel: Collection<Array<Map<String, Model>>>,

        val FGModel: GenericModel<String, GenericTypeParameterImplementation<String, Int>>,
        val FGSModel: GenericModel<Long, GenericTypeParameterImplementation<Long, Int>>,
        val FAGModel: Array<GenericModel<String, GenericTypeParameterImplementation<String, Int>>>,
        val FASAGModel: Array<GenericModel<Array<String>, GenericTypeParameterImplementation<Array<String>, Array<Int>>>>,
        val FLGModel: List<GenericModel<String, GenericTypeParameterImplementation<String, Int>>>,
        val FSGModel: Set<GenericModel<String, GenericTypeParameterImplementation<String, Int>>>,
        val FCGModel: Collection<GenericModel<String, GenericTypeParameterImplementation<String, Int>>>,
        val FMGMModel: Map<String, GenericModel<String, GenericTypeParameterImplementation<String, Int>>>,

        val FLazyString: LazyProperty<String>,
        val FLazyLong: LazyProperty<Long>,
        val FCLazyLong: Collection<LazyProperty<Long>>,
        val FLazyCLong: LazyProperty<Collection<Long>>,
        val FCLazyCLong: LazyProperty<Collection<LazyProperty<Long>>>,
        val FLazyLongA: Array<LazyProperty<Long>>,
        val FLazyALong: LazyProperty<Array<Long>>,
        val FLazyCLongA: LazyProperty<Array<Collection<Long>>>,
        val FLazyCALong: LazyProperty<Collection<Array<Long>>>,
        val FLazyCLAMultiA: Array<Array<LazyProperty<Collection<Array<Long>>>>>,
        val MSLazyMCMLongMultiA: Array<Map<String, Array<LazyProperty<Array<Collection<Array<Array<Long>>>>>>>>,

        val FOptionalString: Optional<String>,
        val FOptionalLong: Optional<Long>,
        val FCOptionalLong: Collection<Optional<Long>>,
        val FOptionalCLong: Optional<Collection<Long>>,
        val FCOptionalCLong: Optional<Collection<Optional<Long>>>,
        val FOptionalLongA: Array<Optional<Long>>,
        val FOptionalALong: Optional<Array<Long>>,
        val FOptionalCLongA: Optional<Array<Collection<Long>>>,
        val FOptionalCALong: Optional<Collection<Array<Long>>>,
        val FOptionalCLAMultiA: Array<Array<Optional<Collection<Array<Long>>>>>,
        val MSOptionalMCMLongMultiA: Array<Map<String, Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,

        val SASOptionalMCMLongMultiA: Array<Stream<Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,
        val QASOptionalMCMLongMultiA: Array<Queue<Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,
        val DSSOptionalMCMLongMultiA: Array<Deque<Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,
        val IASOptionalMCMLongMultiA: Array<ImmutableArray<Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,
        val ISSOptionalMCMLongMultiA: Array<ImmutableSet<Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,
        val IMSOptionalMCMLongMultiA: Array<ImmutableMap<String, Array<Optional<Array<Collection<Array<Array<Long>>>>>>>>,

        val FStringStream: Stream<String>,

        val FValue: Value,
        val FEntity: Entity,

        val FAValue: Array<Value>,
        val FAEntity: Array<Entity>,

        val FCValue: Collection<Value>,
        val FCAValue: Collection<Array<Value>>,
)
