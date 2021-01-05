package io.art.generator.constants;

import io.art.configurator.custom.*;
import io.art.core.collection.*;
import io.art.core.source.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfigurationSourceMethods.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

public interface ConfiguratorConstants {
    interface ConfiguratorMethods {
        Method CONFIGURE_METHOD = wrapException(() -> CustomConfigurator.class.getDeclaredMethod("configure", ConfigurationSource.class));
        String CONFIGURE_METHOD_INPUT = CONFIGURE_METHOD.getParameters()[0].getName();
    }

    interface ConfigurationSourceMethods {
        String GET_BOOL = "getBool";
        String GET_STRING = "getString";
        String GET_INT = "getInt";
        String GET_LONG = "getLong";
        String GET_DOUBLE = "getDouble";
        String GET_FLOAT = "getFloat";
        String GET_SHORT = "getShort";
        String GET_CHAR = "getChar";
        String GET_BYTE = "getByte";
        String GET_DURATION = "getDuration";
        String GET_UUID = "getUuid";
        String GET_LOCAL_DATE_TIME = "getLocalDateTime";
        String GET_ZONED_DATE_TIME = "getZonedDateTime";
        String GET_DATE = "getDate";

        String GET_BOOL_LIST = "getBoolList";
        String GET_STRING_LIST = "getStringList";
        String GET_FLOAT_LIST = "getFloatList";
        String GET_INT_LIST = "getIntList";
        String GET_LONG_LIST = "getLongList";
        String GET_DOUBLE_LIST = "getDoubleList";
        String GET_SHORT_LIST = "getShortList";
        String GET_CHAR_LIST = "getCharList";
        String GET_BYTE_LIST = "getByteList";
        String GET_DURATION_LIST = "getDurationList";
        String GET_UUID_LIST = "getUuidList";
        String GET_LOCAL_DATE_TIME_LIST = "getLocalDateTimeList";
        String GET_ZONED_DATE_TIME_LIST = "getZonedDateTimeList";
        String GET_DATE_LIST = "getDateList";
        String GET_MAP = "getMap";
        String GET_NESTED = "getNested";
        String GET_NESTED_LIST = "getNestedList";
    }

    ImmutableMap<Type, String> CONFIGURATOR_PROPERTY_TYPE_METHODS = ImmutableMap.<Type, String>immutableMapBuilder()
            .put(Character.class, GET_CHAR)
            .put(Short.class, GET_SHORT)
            .put(Integer.class, GET_INT)
            .put(Long.class, GET_LONG)
            .put(Boolean.class, GET_BOOL)
            .put(Double.class, GET_DOUBLE)
            .put(Byte.class, GET_BYTE)
            .put(Float.class, GET_FLOAT)
            .put(String.class, GET_STRING)
            .put(UUID.class, GET_UUID)
            .put(LocalDateTime.class, GET_LOCAL_DATE_TIME)
            .put(ZonedDateTime.class, GET_ZONED_DATE_TIME)
            .put(Date.class, GET_DATE)
            .put(Duration.class, GET_DURATION)

            .put(parameterizedType(ImmutableArray.class, Character.class), GET_CHAR_LIST)
            .put(parameterizedType(ImmutableArray.class, Short.class), GET_SHORT_LIST)
            .put(parameterizedType(ImmutableArray.class, Integer.class), GET_INT_LIST)
            .put(parameterizedType(ImmutableArray.class, Long.class), GET_LONG_LIST)
            .put(parameterizedType(ImmutableArray.class, Boolean.class), GET_BOOL_LIST)
            .put(parameterizedType(ImmutableArray.class, Double.class), GET_DOUBLE_LIST)
            .put(parameterizedType(ImmutableArray.class, Byte.class), GET_BYTE_LIST)
            .put(parameterizedType(ImmutableArray.class, Float.class), GET_FLOAT_LIST)
            .put(parameterizedType(ImmutableArray.class, String.class), GET_STRING_LIST)
            .put(parameterizedType(ImmutableArray.class, UUID.class), GET_UUID_LIST)
            .put(parameterizedType(ImmutableArray.class, LocalDateTime.class), GET_LOCAL_DATE_TIME_LIST)
            .put(parameterizedType(ImmutableArray.class, ZonedDateTime.class), GET_ZONED_DATE_TIME_LIST)
            .put(parameterizedType(ImmutableArray.class, Date.class), GET_DATE_LIST)
            .put(parameterizedType(ImmutableArray.class, Duration.class), GET_DURATION_LIST)

            .put(ImmutableMap.class, GET_MAP)

            .build();
}
