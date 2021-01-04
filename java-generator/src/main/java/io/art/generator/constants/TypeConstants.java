package io.art.generator.constants;

import io.art.core.builder.*;
import io.art.core.collection.*;
import io.art.core.lazy.*;
import io.art.generator.model.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import reactor.core.publisher.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfigurationSourceMethods.*;
import static io.art.generator.constants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.model.TypeMethodNames.*;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.BOOL;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.BYTE;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.DOUBLE;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.FLOAT;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.INT;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.LONG;
import static io.art.value.constants.ValueModuleConstants.ValueType.PrimitiveType.STRING;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

public interface TypeConstants {
    ImmutableSet<Class<?>> LIBRARY_BASED_TYPES = immutableSetOf(
            List.class,
            Collection.class,
            Set.class,
            Queue.class,
            Deque.class,
            Map.class,
            Flux.class,
            Mono.class,
            Date.class
    );

    ImmutableSet<Class<?>> LIBRARY_TYPES = immutableSetOf(
            void.class,
            Void.class,
            String.class,
            boolean.class,
            Boolean.class,
            short.class,
            Short.class,
            char.class,
            Character.class,
            int.class,
            Integer.class,
            long.class,
            Long.class,
            byte.class,
            Byte.class,
            float.class,
            Float.class,
            double.class,
            Double.class,
            UUID.class,
            Duration.class,
            LocalDateTime.class,
            ZonedDateTime.class,
            Object.class,
            LazyValue.class,
            Optional.class
    );

    Map<Type, TypeMethodNames> TYPE_MAPPING_METHODS = MapBuilder.<Type, TypeMethodNames>mapBuilder()
            .with(char[].class, typeMethods(FROM_CHAR_ARRAY, TO_CHAR_ARRAY))
            .with(short[].class, typeMethods(FROM_SHORT_ARRAY, TO_SHORT_ARRAY))
            .with(int[].class, typeMethods(FROM_INT_ARRAY, TO_INT_ARRAY))
            .with(long[].class, typeMethods(FROM_LONG_ARRAY, TO_LONG_ARRAY))
            .with(boolean[].class, typeMethods(FROM_BOOL_ARRAY, TO_BOOL_ARRAY))
            .with(double[].class, typeMethods(FROM_DOUBLE_ARRAY, TO_DOUBLE_ARRAY))
            .with(byte[].class, typeMethods(FROM_BYTE_ARRAY, TO_BYTE_ARRAY))
            .with(float[].class, typeMethods(FROM_FLOAT_ARRAY, TO_FLOAT_ARRAY))
            .with(char.class, typeMethods(FROM_CHAR, TO_CHAR, GET_CHAR))
            .with(short.class, typeMethods(FROM_SHORT, TO_SHORT, GET_SHORT))
            .with(int.class, typeMethods(FROM_INT, TO_INT, GET_INT))
            .with(long.class, typeMethods(FROM_LONG, TO_LONG, GET_LONG))
            .with(boolean.class, typeMethods(FROM_BOOL, TO_BOOL, GET_BOOL))
            .with(double.class, typeMethods(FROM_DOUBLE, TO_DOUBLE, GET_DOUBLE))
            .with(byte.class, typeMethods(FROM_BYTE, TO_BYTE, GET_BYTE))
            .with(float.class, typeMethods(FROM_FLOAT, TO_FLOAT, GET_FLOAT))
            .with(Character.class, typeMethods(FROM_CHAR, TO_CHAR, GET_CHAR))
            .with(Short.class, typeMethods(FROM_SHORT, TO_SHORT, GET_SHORT))
            .with(Integer.class, typeMethods(FROM_INT, TO_INT, GET_INT))
            .with(Long.class, typeMethods(FROM_LONG, TO_LONG, GET_LONG))
            .with(Boolean.class, typeMethods(FROM_BOOL, TO_BOOL, GET_BOOL))
            .with(Double.class, typeMethods(FROM_DOUBLE, TO_DOUBLE, GET_DOUBLE))
            .with(Byte.class, typeMethods(FROM_BYTE, TO_BYTE, GET_BYTE))
            .with(Float.class, typeMethods(FROM_FLOAT, TO_FLOAT, GET_FLOAT))
            .with(String.class, typeMethods(FROM_STRING, TO_STRING, GET_STRING))
            .with(UUID.class, typeMethods(FROM_UUID, TO_UUID, GET_UUID))
            .with(LocalDateTime.class, typeMethods(FROM_LOCAL_DATE_TIME, TO_LOCAL_DATE_TIME, GET_LOCAL_DATE_TIME))
            .with(ZonedDateTime.class, typeMethods(FROM_ZONED_DATE_TIME, TO_ZONED_DATE_TIME, GET_ZONED_DATE_TIME))
            .with(Date.class, typeMethods(FROM_DATE, TO_DATE, GET_DATE))
            .with(Duration.class, typeMethods(FROM_DURATION, TO_DURATION, GET_DURATION))
            .with(List.class, typeMethods(FROM_LIST, TO_LIST))
            .with(Queue.class, typeMethods(FROM_QUEUE, TO_QUEUE))
            .with(Deque.class, typeMethods(FROM_DEQUE, TO_DEQUE))
            .with(Set.class, typeMethods(FROM_SET, TO_SET))
            .with(Collection.class, typeMethods(FROM_COLLECTION, TO_COLLECTION))
            .build();

    Set<Type> PRIMITIVE_TYPES = setOf(
            String.class,
            char.class,
            int.class,
            short.class,
            long.class,
            boolean.class,
            double.class,
            byte.class,
            float.class,
            Character.class,
            Integer.class,
            Short.class,
            Long.class,
            Boolean.class,
            Double.class,
            Byte.class,
            Float.class,
            UUID.class,
            LocalDateTime.class,
            ZonedDateTime.class,
            Duration.class,
            Date.class
    );

    Set<Type> JAVA_PRIMITIVE_TYPES = setOf(
            char.class,
            int.class,
            short.class,
            long.class,
            boolean.class,
            double.class,
            byte.class,
            float.class
    );

    Set<Class<?>> COLLECTION_TYPES = setOf(
            List.class,
            Queue.class,
            Deque.class,
            Set.class,
            Collection.class
    );

    Map<Type, PrimitiveType> JAVA_TO_PRIMITIVE_TYPE = MapBuilder.<Type, PrimitiveType>mapBuilder()
            .with(char.class, STRING)
            .with(int.class, INT)
            .with(short.class, INT)
            .with(long.class, LONG)
            .with(boolean.class, BOOL)
            .with(double.class, DOUBLE)
            .with(byte.class, BYTE)
            .with(float.class, FLOAT)
            .with(char.class, STRING)
            .with(Integer.class, INT)
            .with(Short.class, INT)
            .with(Long.class, LONG)
            .with(Boolean.class, BOOL)
            .with(Double.class, DOUBLE)
            .with(Byte.class, BYTE)
            .with(Float.class, FLOAT)
            .build();
}
