package io.art.generator.constants;

import io.art.configurator.custom.*;
import io.art.core.source.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import java.lang.reflect.*;

public interface ConfiguratorConstants {
    interface ConfiguratorMethods {
        Method CONFIGURE_METHOD = wrapException(() -> CustomConfigurationProxy.class.getDeclaredMethod("configure", ConfigurationSource.class));
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
        String GET_NESTED = "getNested";
        String GET_BOOL_LIST = "getBoolList";
        String GET_STRING_LIST = "getStringList";
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
        String GET_NESTED_LIST = "getNestedList";
        String GET_NESTED_MAP = "getNestedMap";
    }
}
