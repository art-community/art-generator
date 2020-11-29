package io.art.generator.selector;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static java.text.MessageFormat.*;
import java.time.*;
import java.util.*;

@UtilityClass
public class MappingToMethodSelector {
    public static String selectToArrayJavaPrimitiveMethod(Class<?> mappingClass) {
        if (char.class.equals(mappingClass.getComponentType())) {
            return TO_CHAR_ARRAY;
        }
        if (short.class.equals(mappingClass.getComponentType())) {
            return TO_SHORT_ARRAY;
        }
        if (int.class.equals(mappingClass.getComponentType())) {
            return TO_INT_ARRAY;
        }
        if (long.class.equals(mappingClass.getComponentType())) {
            return TO_LONG_ARRAY;
        }
        if (boolean.class.equals(mappingClass.getComponentType())) {
            return TO_BOOL_ARRAY;
        }
        if (double.class.equals(mappingClass.getComponentType())) {
            return TO_DOUBLE_ARRAY;
        }
        if (byte.class.equals(mappingClass.getComponentType())) {
            return TO_BYTE_ARRAY;
        }
        if (float.class.equals(mappingClass.getComponentType())) {
            return TO_FLOAT_ARRAY;
        }
        throw new GenerationException(format("Not java primitive type: {0}", mappingClass));
    }

    public static String selectToPrimitiveMethod(Class<?> mappingClass) {
        if (String.class.equals(mappingClass)) {
            return TO_STRING;
        }
        if (char.class.equals(mappingClass) || Character.class.equals(mappingClass)) {
            return TO_CHAR;
        }
        if (int.class.equals(mappingClass) || Integer.class.equals(mappingClass)) {
            return TO_INT;
        }
        if (short.class.equals(mappingClass) || Short.class.equals(mappingClass)) {
            return TO_SHORT;
        }
        if (long.class.equals(mappingClass) || Long.class.equals(mappingClass)) {
            return TO_LONG;
        }
        if (boolean.class.equals(mappingClass) || Boolean.class.equals(mappingClass)) {
            return TO_BOOL;
        }
        if (double.class.equals(mappingClass) || Double.class.equals(mappingClass)) {
            return TO_DOUBLE;
        }
        if (byte.class.equals(mappingClass) || Byte.class.equals(mappingClass)) {
            return TO_BYTE;
        }
        if (float.class.equals(mappingClass) || Float.class.equals(mappingClass)) {
            return TO_FLOAT;
        }
        if (UUID.class.equals(mappingClass)) {
            return TO_UUID;
        }
        if (LocalDateTime.class.equals(mappingClass)) {
            return TO_LOCAL_DATE_TIME;
        }
        if (ZonedDateTime.class.equals(mappingClass)) {
            return TO_ZONED_DATE_TIME;
        }
        if (Date.class.equals(mappingClass)) {
            return TO_DATE;
        }
        throw new GenerationException(format("Not primitive type: {0}", mappingClass));
    }

    public static String selectToCollectionMethod(Class<?> mappingClass) {
        if (List.class.isAssignableFrom(mappingClass)) {
            return TO_LIST;
        }
        if (Queue.class.isAssignableFrom(mappingClass)) {
            return TO_QUEUE;
        }
        if (Deque.class.isAssignableFrom(mappingClass)) {
            return TO_DEQUE;
        }
        if (Set.class.isAssignableFrom(mappingClass)) {
            return TO_SET;
        }
        if (Collection.class.isAssignableFrom(mappingClass)) {
            return TO_COLLECTION;
        }
        throw new GenerationException(format("Not collection type: {0}", mappingClass));
    }
}
