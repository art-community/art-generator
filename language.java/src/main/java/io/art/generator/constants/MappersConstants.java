package io.art.generator.constants;

public interface MappersConstants {
    String TO_MODEL_NAME = "toModel";
    String FROM_MODEL_NAME = "fromModel";
    String MAPPING_METHOD_NAME = "mapping";
    String MAP_OR_DEFAULT_NAME = "mapOrDefault";
    String MAP_OPTIONAL = "mapOptional";
    String MAPPING_INTERFACE_NAME = "Mapping";
    String ENTITY_NAME = "entity";
    String ENTITY_BUILDER_NAME = "entityBuilder";
    String LAZY_PUT_NAME = "lazyPut";
    String MAP_NAME = "map";

    interface PrimitiveMappingMethods {
        String TO_UUID = "toUuid";
        String TO_STRING = "toString";
        String TO_CHAR = "toChar";
        String TO_INT = "toInt";
        String TO_SHORT = "toShort";
        String TO_BOOL = "toBool";
        String TO_LONG = "toLong";
        String TO_DOUBLE = "toDouble";
        String TO_BYTE = "toByte";
        String TO_FLOAT = "toFloat";
        String TO_LOCAL_DATE_TIME = "toLocalDateTime";
        String TO_ZONED_DATE_TIME = "toZonedDateTime";
        String TO_DATE = "toDate";
        String TO_DURATION = "toDuration";

        String FROM_UUID = "fromUuid";
        String FROM_STRING = "fromString";
        String FROM_CHAR = "fromChar";
        String FROM_INT = "fromInt";
        String FROM_SHORT = "fromShort";
        String FROM_BOOL = "fromBool";
        String FROM_LONG = "fromLong";
        String FROM_DOUBLE = "fromDouble";
        String FROM_BYTE = "fromByte";
        String FROM_FLOAT = "fromFloat";
        String FROM_LOCAL_DATE_TIME = "fromLocalDateTime";
        String FROM_ZONED_DATE_TIME = "fromZonedDateTime";
        String FROM_DATE = "fromDate";
        String FROM_DURATION = "fromDuration";
    }

    interface ArrayMappingMethods {
        String FROM_COLLECTION = "fromCollection";
        String FROM_ARRAY = "fromArray";
        String FROM_LIST = "fromList";
        String FROM_SET = "fromSet";
        String FROM_QUEUE = "fromQueue";
        String FROM_DEQUE = "fromDeque";
        String FROM_STREAM = "fromStream";

        String FROM_IMMUTABLE_ARRAY = "fromImmutableArray";
        String FROM_IMMUTABLE_SET = "fromImmutableSet";
        String TO_IMMUTABLE_ARRAY = "toImmutableArray";
        String TO_IMMUTABLE_SET = "toImmutableSet";

        String TO_ARRAY = "toArray";
        String TO_STREAM = "toStream";
        String TO_MUTABLE_COLLECTION = "toMutableCollection";
        String TO_MUTABLE_LIST = "toMutableList";
        String TO_MUTABLE_SET = "toMutableSet";
        String TO_MUTABLE_QUEUE = "toMutableQueue";
        String TO_MUTABLE_DEQUE = "toMutableDeque";

        String TO_INT_ARRAY = "toIntArray";
        String TO_LONG_ARRAY = "toLongArray";
        String TO_SHORT_ARRAY = "toShortArray";
        String TO_DOUBLE_ARRAY = "toDoubleArray";
        String TO_FLOAT_ARRAY = "toFloatArray";
        String TO_BYTE_ARRAY = "toByteArray";
        String TO_CHAR_ARRAY = "toCharArray";
        String TO_BOOL_ARRAY = "toBoolArray";


        String FROM_INT_ARRAY = "fromIntArray";
        String FROM_LONG_ARRAY = "fromLongArray";
        String FROM_SHORT_ARRAY = "fromShortArray";
        String FROM_DOUBLE_ARRAY = "fromDoubleArray";
        String FROM_FLOAT_ARRAY = "fromFloatArray";
        String FROM_BYTE_ARRAY = "fromByteArray";
        String FROM_CHAR_ARRAY = "fromCharArray";
        String FROM_BOOL_ARRAY = "fromBoolArray";
    }

    interface EntityMappingMethods {
        String TO_MAP = "toMap";
        String FROM_MAP = "fromMap";

        String TO_IMMUTABLE_MAP = "toImmutableMap";
        String FROM_IMMUTABLE_MAP = "fromImmutableMap";

        String TO_MUTABLE_MAP = "toMutableMap";
    }

    interface BinaryMappingMethods {
        String FROM_BINARY = "fromBinary";
        String TO_BINARY = "toBinary";
    }

    interface LazyValueMappingMethods {
        String TO_LAZY = "toLazy";
        String FROM_LAZY = "fromLazy";
    }

    interface OptionalMappingMethods {
        String TO_OPTIONAL = "toOptional";
        String FROM_OPTIONAL = "fromOptional";
    }

    interface ValueCustomizerMethods {
        String REGISTER_TO_MODEL = "registerToModel";
        String REGISTER_FROM_MODEL = "registerFromModel";
    }
}
