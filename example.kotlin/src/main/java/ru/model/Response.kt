package ru.model

data class Response(
        var FBInteger: Int? = null,
        var FBShort: Short? = null,
        var FBByte: Byte? = null,
        var FBBoolean: Boolean? = null,
        var FBLong: Long? = null,
        var FBString: String? = null,
        var FBDouble: Double? = null,
        var FBFloat: Float? = null,
        var FAint: IntArray? = null,
        var FAchar: CharArray? = null,
        var FAshort: ShortArray? = null,
        var FAbyte: ByteArray? = null,
        var FAboolean: BooleanArray? = null,
        var FAlong: LongArray? = null,
        var FAdouble: DoubleArray? = null,
        var FAfloat: FloatArray? = null,
        var FABInteger: Array<Int>? = null,
        var FABCharacter: Array<Char>? = null,
        var FABShort: Array<Short>? = null,
        var FABByte: Array<Byte>? = null,
        var FABBoolean: Array<Boolean>? = null,
        var FABLong: Array<Long>? = null,
        var FABString: Array<String>? = null,
        var FABDouble: Array<Double>? = null,
        var FABFloat: Array<Float>? = null,
        var FCInteger: Collection<Int>? = null,
        var FCCharacter: Collection<Char>? = null,
        var FCShort: Collection<Short>? = null,
        var FCByte: Collection<Byte>? = null,
        var FCBoolean: Collection<Boolean>? = null,
        var FCLong: Collection<Long>? = null,
        var FCString: Collection<String>? = null,
        var FCDouble: Collection<Double>? = null,
        var FCFloat: Collection<Float>? = null,
        var FLInteger: List<Int>? = null,
        var FLCharacter: List<Char>? = null,
        var FLShort: List<Short>? = null,
        var FLByte: List<Byte>? = null,
        var FLBoolean: List<Boolean>? = null,
        var FLLong: List<Long>? = null,
        var FLString: List<String>? = null,
        var FLDouble: List<Double>? = null,
        var FLFloat: List<Float>? = null,
        var FSInteger: Set<Int>? = null,
        var FSCharacter: Set<Char>? = null,
        var FSShort: Set<Short>? = null,
        var FSByte: Set<Byte>? = null,
        var FSBoolean: Set<Boolean>? = null,
        var FSLong: Set<Long>? = null,
        var FSString: Set<String>? = null,
        var FSDouble: Set<Double>? = null,
        var FSFloat: Set<Float>? = null,
        var FMInteger: Map<Int, Int>? = null,
        var FMCharacter: Map<Char, Char>? = null,
        var FMShort: Map<Short, Short>? = null,
        var FMByte: Map<Byte, Byte>? = null,
        var FMBoolean: Map<Boolean, Boolean>? = null,
        var FMLong: Map<Long, Long>? = null,
        var FMString: Map<String, String>? = null,
        var FMDouble: Map<Double, Double>? = null,
        var FMFloat: Map<Float, Float>? = null,
        var FModel: Model? = null,
        var FAModel: Array<Model>? = null,
        var FALModel: Array<List<Model>>? = null,
        var FASModel: Array<Set<Model>>? = null,
        var FACModel: Array<Collection<Model>>? = null,
        var FAMModel: Array<Map<String, Model>>? = null,
        var FLModel: List<Model>? = null,
        var FLLModel: List<List<Model>>? = null,
        var FLSModel: List<Set<Model>>? = null,
        var FLCModel: List<Collection<Model>>? = null,
        var FLMModel: List<Map<String, Model>>? = null,
        var FLAModel: List<Array<Model>>? = null,
        var FSModel: Set<Model>? = null,
        var FSLModel: Set<List<Model>>? = null,
        var FSSModel: Set<Set<Model>>? = null,
        var FSCModel: Set<Collection<Model>>? = null,
        var FSMModel: Set<Map<String, Model>>? = null,
        var FSAModel: Set<Array<Model>>? = null,
        var FCModel: Collection<Model>? = null,
        var FCLModel: Collection<List<Model>>? = null,
        var FCSModel: Collection<Set<Model>>? = null,
        var FCCModel: Collection<Collection<Model>>? = null,
        var FCMModel: Collection<Map<String, Model>>? = null,
        var FCAModel: Collection<Array<Model>>? = null,
        var FMModel: Map<String, Model>? = null,
        var FMAModel: Map<String, Array<Model>>? = null,
        var FMLModel: Map<String, List<Model>>? = null,
        var FMSModel: Map<String, Set<Model>>? = null,
        var FMCModel: Map<String, Collection<Model>>? = null,
        var FMMModel: Map<String, Map<String, Model>>? = null,
        var FCCMModel: Collection<List<Map<String, Model>>>? = null,
        var FCLMModel: Collection<Set<Map<String, Model>>>? = null,
        var FCSMModel: Collection<Collection<Map<String, Model>>>? = null,
        var FCMMModel: Collection<Map<String, Model>>? = null,
        var FCLMAModel: Collection<Array<List<Map<String, Model>>>>? = null,
        var FCSMAModel: Collection<Array<Set<Map<String, Model>>>>? = null,
        var FCCMAModel: Collection<Array<Collection<Map<String, Model>>>>? = null,
        var FCMAModel: Collection<Array<Map<String, Model>>>? = null,
)