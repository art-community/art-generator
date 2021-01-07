package io.art.generator.constants;

import io.art.configurator.custom.*;
import io.art.core.collection.*;
import io.art.core.source.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.constants.ConfiguratorConstants.NestedConfigurationMethods.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

public interface ConfiguratorConstants {
    interface ConfiguratorMethods {
        Method CONFIGURE_METHOD = wrapException(() -> CustomConfigurator.class.getDeclaredMethod("configure", ConfigurationSource.class));
    }

    interface ConfigurationSourceMethods {
        String GET_NESTED = "getNested";
    }

    interface NestedConfigurationMethods {
        String AS_BOOL = "asBool";
        String AS_STRING = "asString";
        String AS_INT = "asInt";
        String AS_LONG = "asLong";
        String AS_DOUBLE = "asDouble";
        String AS_FLOAT = "asFloat";
        String AS_SHORT = "asShort";
        String AS_CHAR = "asChar";
        String AS_BYTE = "asByte";
        String AS_DURATION = "asDuration";
        String AS_UUID = "asUuid";
        String AS_LOCAL_DATE_TIME = "asLocalDateTime";
        String AS_ZONED_DATE_TIME = "asZonedDateTime";
        String AS_DATE = "asDate";

        String AS_ARRAY = "asArray";
        String AS_BOOL_ARRAY = "asBoolArray";
        String AS_STRING_ARRAY = "asStringArray";
        String AS_FLOAT_ARRAY = "asFloatArray";
        String AS_INT_ARRAY = "asIntArray";
        String AS_LONG_ARRAY = "asLongArray";
        String AS_DOUBLE_ARRAY = "asDoubleArray";
        String AS_SHORT_ARRAY = "asShortArray";
        String AS_CHAR_ARRAY = "asCharArray";
        String AS_BYTE_ARRAY = "asByteArray";
        String AS_DURATION_ARRAY = "asDurationArray";
        String AS_UUID_ARRAY = "asUuidArray";
        String AS_LOCAL_DATE_TIME_ARRAY = "asLocalDateTimeArray";
        String AS_ZONED_DATE_TIME_ARRAY = "asZonedDateTimeArray";
        String AS_DATE_ARRAY = "asDateArray";

        String AS_MAP = "asMap";
    }

    ImmutableMap<Type, String> CONFIGURATOR_PROPERTY_TYPE_METHODS = ImmutableMap.<Type, String>immutableMapBuilder()
            .put(Character.class, AS_CHAR)
            .put(Short.class, AS_SHORT)
            .put(Integer.class, AS_INT)
            .put(Long.class, AS_LONG)
            .put(Boolean.class, AS_BOOL)
            .put(Double.class, AS_DOUBLE)
            .put(Byte.class, AS_BYTE)
            .put(Float.class, AS_FLOAT)
            .put(String.class, AS_STRING)
            .put(UUID.class, AS_UUID)
            .put(LocalDateTime.class, AS_LOCAL_DATE_TIME)
            .put(ZonedDateTime.class, AS_ZONED_DATE_TIME)
            .put(Date.class, AS_DATE)
            .put(Duration.class, AS_DURATION)

            .put(parameterizedType(ImmutableArray.class, Character.class), AS_CHAR_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Short.class), AS_SHORT_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Integer.class), AS_INT_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Long.class), AS_LONG_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Boolean.class), AS_BOOL_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Double.class), AS_DOUBLE_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Byte.class), AS_BYTE_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Float.class), AS_FLOAT_ARRAY)
            .put(parameterizedType(ImmutableArray.class, String.class), AS_STRING_ARRAY)
            .put(parameterizedType(ImmutableArray.class, UUID.class), AS_UUID_ARRAY)
            .put(parameterizedType(ImmutableArray.class, LocalDateTime.class), AS_LOCAL_DATE_TIME_ARRAY)
            .put(parameterizedType(ImmutableArray.class, ZonedDateTime.class), AS_ZONED_DATE_TIME_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Date.class), AS_DATE_ARRAY)
            .put(parameterizedType(ImmutableArray.class, Duration.class), AS_DURATION_ARRAY)

            .put(ImmutableMap.class, AS_MAP)

            .build();
}
