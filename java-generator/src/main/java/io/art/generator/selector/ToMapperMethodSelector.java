package io.art.generator.selector;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static java.text.MessageFormat.*;
import java.time.*;
import java.util.*;

@UtilityClass
public class ToMapperMethodSelector {
    public static String selectToArrayJavaPrimitiveMethod(Class<?> arrayClass) {
        if (char.class.equals(arrayClass.getComponentType())) {
            return TO_CHAR_ARRAY;
        }
        if (short.class.equals(arrayClass.getComponentType())) {
            return TO_SHORT_ARRAY;
        }
        if (int.class.equals(arrayClass.getComponentType())) {
            return TO_INT_ARRAY;
        }
        if (long.class.equals(arrayClass.getComponentType())) {
            return TO_LONG_ARRAY;
        }
        if (boolean.class.equals(arrayClass.getComponentType())) {
            return TO_BOOL_ARRAY;
        }
        if (double.class.equals(arrayClass.getComponentType())) {
            return TO_DOUBLE_ARRAY;
        }
        if (byte.class.equals(arrayClass.getComponentType())) {
            return TO_BYTE_ARRAY;
        }
        if (float.class.equals(arrayClass.getComponentType())) {
            return TO_FLOAT_ARRAY;
        }
        throw new GenerationException(format(NOT_PRIMITIVE_TYPE, arrayClass));
    }

    public static String selectToPrimitiveMethod(Class<?> primitiveClass) {
        if (String.class.equals(primitiveClass)) {
            return TO_STRING;
        }
        if (char.class.equals(primitiveClass) || Character.class.equals(primitiveClass)) {
            return TO_CHAR;
        }
        if (int.class.equals(primitiveClass) || Integer.class.equals(primitiveClass)) {
            return TO_INT;
        }
        if (short.class.equals(primitiveClass) || Short.class.equals(primitiveClass)) {
            return TO_SHORT;
        }
        if (long.class.equals(primitiveClass) || Long.class.equals(primitiveClass)) {
            return TO_LONG;
        }
        if (boolean.class.equals(primitiveClass) || Boolean.class.equals(primitiveClass)) {
            return TO_BOOL;
        }
        if (double.class.equals(primitiveClass) || Double.class.equals(primitiveClass)) {
            return TO_DOUBLE;
        }
        if (byte.class.equals(primitiveClass) || Byte.class.equals(primitiveClass)) {
            return TO_BYTE;
        }
        if (float.class.equals(primitiveClass) || Float.class.equals(primitiveClass)) {
            return TO_FLOAT;
        }
        if (UUID.class.equals(primitiveClass)) {
            return TO_UUID;
        }
        if (LocalDateTime.class.equals(primitiveClass)) {
            return TO_LOCAL_DATE_TIME;
        }
        if (ZonedDateTime.class.equals(primitiveClass)) {
            return TO_ZONED_DATE_TIME;
        }
        if (Date.class.equals(primitiveClass)) {
            return TO_DATE;
        }
        if (Duration.class.equals(primitiveClass)) {
            return TO_DURATION;
        }
        throw new GenerationException(format(NOT_PRIMITIVE_TYPE, primitiveClass));
    }

    public static String selectToCollectionMethod(Class<?> collectionClass) {
        if (List.class.isAssignableFrom(collectionClass)) {
            return TO_LIST;
        }
        if (Queue.class.isAssignableFrom(collectionClass)) {
            return TO_QUEUE;
        }
        if (Deque.class.isAssignableFrom(collectionClass)) {
            return TO_DEQUE;
        }
        if (Set.class.isAssignableFrom(collectionClass)) {
            return TO_SET;
        }
        if (Collection.class.isAssignableFrom(collectionClass)) {
            return TO_COLLECTION;
        }
        throw new GenerationException(format(NOT_COLLECTION_TYPE, collectionClass));
    }
}
