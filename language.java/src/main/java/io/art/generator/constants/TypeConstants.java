package io.art.generator.constants;

import io.art.core.collection.*;
import io.art.core.property.*;
import io.art.generator.model.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import reactor.core.publisher.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.model.TypeMappingNames.*;
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
import java.util.stream.*;

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
            Date.class,
            Stream.class,
            ImmutableArray.class,
            ImmutableSet.class,
            ImmutableMap.class
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
            LazyProperty.class,
            Optional.class
    );

    ImmutableMap<Type, TypeMappingNames> TYPE_MAPPING_METHODS = ImmutableMap.<Type, TypeMappingNames>immutableMapBuilder()
            .put(char[].class, typeMappings(FROM_CHAR_ARRAY, TO_CHAR_ARRAY))
            .put(short[].class, typeMappings(FROM_SHORT_ARRAY, TO_SHORT_ARRAY))
            .put(int[].class, typeMappings(FROM_INT_ARRAY, TO_INT_ARRAY))
            .put(long[].class, typeMappings(FROM_LONG_ARRAY, TO_LONG_ARRAY))
            .put(boolean[].class, typeMappings(FROM_BOOL_ARRAY, TO_BOOL_ARRAY))
            .put(double[].class, typeMappings(FROM_DOUBLE_ARRAY, TO_DOUBLE_ARRAY))
            .put(byte[].class, typeMappings(FROM_BYTE_ARRAY, TO_BYTE_ARRAY))
            .put(float[].class, typeMappings(FROM_FLOAT_ARRAY, TO_FLOAT_ARRAY))
            .put(char.class, typeMappings(FROM_CHAR, TO_CHAR))
            .put(short.class, typeMappings(FROM_SHORT, TO_SHORT))
            .put(int.class, typeMappings(FROM_INT, TO_INT))
            .put(long.class, typeMappings(FROM_LONG, TO_LONG))
            .put(boolean.class, typeMappings(FROM_BOOL, TO_BOOL))
            .put(double.class, typeMappings(FROM_DOUBLE, TO_DOUBLE))
            .put(byte.class, typeMappings(FROM_BYTE, TO_BYTE))
            .put(float.class, typeMappings(FROM_FLOAT, TO_FLOAT))
            .put(Character.class, typeMappings(FROM_CHAR, TO_CHAR))
            .put(Short.class, typeMappings(FROM_SHORT, TO_SHORT))
            .put(Integer.class, typeMappings(FROM_INT, TO_INT))
            .put(Long.class, typeMappings(FROM_LONG, TO_LONG))
            .put(Boolean.class, typeMappings(FROM_BOOL, TO_BOOL))
            .put(Double.class, typeMappings(FROM_DOUBLE, TO_DOUBLE))
            .put(Byte.class, typeMappings(FROM_BYTE, TO_BYTE))
            .put(Float.class, typeMappings(FROM_FLOAT, TO_FLOAT))
            .put(String.class, typeMappings(FROM_STRING, TO_STRING))
            .put(UUID.class, typeMappings(FROM_UUID, TO_UUID))
            .put(LocalDateTime.class, typeMappings(FROM_LOCAL_DATE_TIME, TO_LOCAL_DATE_TIME))
            .put(ZonedDateTime.class, typeMappings(FROM_ZONED_DATE_TIME, TO_ZONED_DATE_TIME))
            .put(Date.class, typeMappings(FROM_DATE, TO_DATE))
            .put(Duration.class, typeMappings(FROM_DURATION, TO_DURATION))
            .put(List.class, typeMappings(FROM_LIST, TO_MUTABLE_LIST))
            .put(Set.class, typeMappings(FROM_SET, TO_MUTABLE_SET))
            .put(Queue.class, typeMappings(FROM_QUEUE, TO_MUTABLE_QUEUE))
            .put(Deque.class, typeMappings(FROM_DEQUE, TO_MUTABLE_DEQUE))
            .put(Collection.class, typeMappings(FROM_COLLECTION, TO_MUTABLE_COLLECTION))
            .put(Stream.class, typeMappings(FROM_STREAM, TO_STREAM))
            .put(ImmutableArray.class, typeMappings(FROM_IMMUTABLE_ARRAY, TO_IMMUTABLE_ARRAY))
            .put(ImmutableSet.class, typeMappings(FROM_IMMUTABLE_SET, TO_IMMUTABLE_SET))
            .build();

    ImmutableSet<Type> PRIMITIVE_TYPES = immutableSetOf(
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

    ImmutableSet<Type> JAVA_PRIMITIVE_TYPES = immutableSetOf(
            char.class,
            int.class,
            short.class,
            long.class,
            boolean.class,
            double.class,
            byte.class,
            float.class
    );

    ImmutableMap<Class<?>, Class<?>> JAVA_PRIMITIVE_MAPPINGS = ImmutableMap.<Class<?>, Class<?>>immutableMapBuilder()
            .put(char.class, Character.class)
            .put(int.class, Integer.class)
            .put(short.class, Short.class)
            .put(long.class, Long.class)
            .put(boolean.class, Boolean.class)
            .put(double.class, Double.class)
            .put(byte.class, Byte.class)
            .put(float.class, Float.class)
            .build();

    ImmutableSet<Class<?>> COLLECTION_TYPES = immutableSetOf(
            List.class,
            Queue.class,
            Deque.class,
            Set.class,
            Collection.class,
            Stream.class,
            ImmutableArray.class,
            ImmutableSet.class
    );

    ImmutableMap<Type, PrimitiveType> JAVA_TO_PRIMITIVE_TYPE = ImmutableMap.<Type, PrimitiveType>immutableMapBuilder()
            .put(char.class, STRING)
            .put(int.class, INT)
            .put(short.class, INT)
            .put(long.class, LONG)
            .put(boolean.class, BOOL)
            .put(double.class, DOUBLE)
            .put(byte.class, BYTE)
            .put(float.class, FLOAT)
            .put(Character.class, STRING)
            .put(Integer.class, INT)
            .put(Short.class, INT)
            .put(Long.class, LONG)
            .put(Boolean.class, BOOL)
            .put(Double.class, DOUBLE)
            .put(Byte.class, BYTE)
            .put(Float.class, FLOAT)
            .build();
}
