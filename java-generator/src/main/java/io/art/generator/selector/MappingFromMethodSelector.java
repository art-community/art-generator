package io.art.generator.selector;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static java.text.MessageFormat.*;
import java.time.*;
import java.util.*;

@UtilityClass
public class MappingFromMethodSelector {
    public static String selectFromArrayJavaPrimitiveMethod(Class<?> mappingClass) {
        if (char.class.equals(mappingClass.getComponentType())) {
            return FROM_CHAR_ARRAY;
        }
        if (short.class.equals(mappingClass.getComponentType())) {
            return FROM_SHORT_ARRAY;
        }
        if (int.class.equals(mappingClass.getComponentType())) {
            return FROM_INT_ARRAY;
        }
        if (long.class.equals(mappingClass.getComponentType())) {
            return FROM_LONG_ARRAY;
        }
        if (boolean.class.equals(mappingClass.getComponentType())) {
            return FROM_BOOL_ARRAY;
        }
        if (double.class.equals(mappingClass.getComponentType())) {
            return FROM_DOUBLE_ARRAY;
        }
        if (byte.class.equals(mappingClass.getComponentType())) {
            return FROM_BYTE_ARRAY;
        }
        if (float.class.equals(mappingClass.getComponentType())) {
            return FROM_FLOAT_ARRAY;
        }
        throw new GenerationException(format("Not java primitive type: {0}", mappingClass));
    }

    public static String selectFromPrimitiveMethod(Class<?> mappingClass) {
        if (String.class.equals(mappingClass)) {
            return FROM_STRING;
        }
        if (char.class.equals(mappingClass) || Character.class.equals(mappingClass)) {
            return FROM_CHAR;
        }
        if (int.class.equals(mappingClass) || Integer.class.equals(mappingClass)) {
            return FROM_INT;
        }
        if (short.class.equals(mappingClass) || Short.class.equals(mappingClass)) {
            return FROM_SHORT;
        }
        if (long.class.equals(mappingClass) || Long.class.equals(mappingClass)) {
            return FROM_LONG;
        }
        if (boolean.class.equals(mappingClass) || Boolean.class.equals(mappingClass)) {
            return FROM_BOOL;
        }
        if (double.class.equals(mappingClass) || Double.class.equals(mappingClass)) {
            return FROM_DOUBLE;
        }
        if (byte.class.equals(mappingClass) || Byte.class.equals(mappingClass)) {
            return FROM_BYTE;
        }
        if (float.class.equals(mappingClass) || Float.class.equals(mappingClass)) {
            return FROM_FLOAT;
        }
        if (UUID.class.equals(mappingClass)) {
            return FROM_UUID;
        }
        if (LocalDateTime.class.equals(mappingClass)) {
            return FROM_LOCAL_DATE_TIME;
        }
        if (ZonedDateTime.class.equals(mappingClass)) {
            return FROM_ZONED_DATE_TIME;
        }
        if (Date.class.equals(mappingClass)) {
            return FROM_DATE;
        }
        throw new GenerationException(format("Not primitive type: {0}", mappingClass));
    }

    public static String selectFromCollectionMethod(Class<?> mappingClass) {
        if (java.util.List.class.isAssignableFrom(mappingClass)) {
            return FROM_LIST;
        }
        if (Queue.class.isAssignableFrom(mappingClass)) {
            return FROM_QUEUE;
        }
        if (Deque.class.isAssignableFrom(mappingClass)) {
            return FROM_DEQUE;
        }
        if (Set.class.isAssignableFrom(mappingClass)) {
            return FROM_SET;
        }
        if (Collection.class.isAssignableFrom(mappingClass)) {
            return FROM_COLLECTION;
        }
        throw new GenerationException(format("Not collection type: {0}", mappingClass));
    }
}
