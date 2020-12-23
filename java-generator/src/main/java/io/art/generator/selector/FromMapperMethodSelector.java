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
public class FromMapperMethodSelector {
    public static String selectFromArrayJavaPrimitiveMethod(Class<?> arrayClass) {
        if (char.class.equals(arrayClass.getComponentType())) {
            return FROM_CHAR_ARRAY;
        }
        if (short.class.equals(arrayClass.getComponentType())) {
            return FROM_SHORT_ARRAY;
        }
        if (int.class.equals(arrayClass.getComponentType())) {
            return FROM_INT_ARRAY;
        }
        if (long.class.equals(arrayClass.getComponentType())) {
            return FROM_LONG_ARRAY;
        }
        if (boolean.class.equals(arrayClass.getComponentType())) {
            return FROM_BOOL_ARRAY;
        }
        if (double.class.equals(arrayClass.getComponentType())) {
            return FROM_DOUBLE_ARRAY;
        }
        if (byte.class.equals(arrayClass.getComponentType())) {
            return FROM_BYTE_ARRAY;
        }
        if (float.class.equals(arrayClass.getComponentType())) {
            return FROM_FLOAT_ARRAY;
        }
        throw new GenerationException(format(NOT_PRIMITIVE_TYPE, arrayClass));
    }

    public static String selectFromPrimitiveMethod(Class<?> primitiveClass) {
        if (String.class.equals(primitiveClass)) {
            return FROM_STRING;
        }
        if (char.class.equals(primitiveClass) || Character.class.equals(primitiveClass)) {
            return FROM_CHAR;
        }
        if (int.class.equals(primitiveClass) || Integer.class.equals(primitiveClass)) {
            return FROM_INT;
        }
        if (short.class.equals(primitiveClass) || Short.class.equals(primitiveClass)) {
            return FROM_SHORT;
        }
        if (long.class.equals(primitiveClass) || Long.class.equals(primitiveClass)) {
            return FROM_LONG;
        }
        if (boolean.class.equals(primitiveClass) || Boolean.class.equals(primitiveClass)) {
            return FROM_BOOL;
        }
        if (double.class.equals(primitiveClass) || Double.class.equals(primitiveClass)) {
            return FROM_DOUBLE;
        }
        if (byte.class.equals(primitiveClass) || Byte.class.equals(primitiveClass)) {
            return FROM_BYTE;
        }
        if (float.class.equals(primitiveClass) || Float.class.equals(primitiveClass)) {
            return FROM_FLOAT;
        }
        if (UUID.class.equals(primitiveClass)) {
            return FROM_UUID;
        }
        if (LocalDateTime.class.equals(primitiveClass)) {
            return FROM_LOCAL_DATE_TIME;
        }
        if (ZonedDateTime.class.equals(primitiveClass)) {
            return FROM_ZONED_DATE_TIME;
        }
        if (Date.class.equals(primitiveClass)) {
            return FROM_DATE;
        }
        throw new GenerationException(format(NOT_PRIMITIVE_TYPE, primitiveClass));
    }

    public static String selectFromCollectionMethod(Class<?> collectionClass) {
        if (java.util.List.class.isAssignableFrom(collectionClass)) {
            return FROM_LIST;
        }
        if (Queue.class.isAssignableFrom(collectionClass)) {
            return FROM_QUEUE;
        }
        if (Deque.class.isAssignableFrom(collectionClass)) {
            return FROM_DEQUE;
        }
        if (Set.class.isAssignableFrom(collectionClass)) {
            return FROM_SET;
        }
        if (Collection.class.isAssignableFrom(collectionClass)) {
            return FROM_COLLECTION;
        }
        throw new GenerationException(format(NOT_COLLECTION_TYPE, collectionClass));
    }
}
