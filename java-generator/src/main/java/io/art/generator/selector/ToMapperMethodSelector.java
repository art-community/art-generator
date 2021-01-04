package io.art.generator.selector;

import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.TypeConstants.*;
import static java.text.MessageFormat.*;

@UtilityClass
public class ToMapperMethodSelector {
    public static String selectToArrayJavaPrimitiveMethod(Class<?> arrayClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(arrayClass), TypeMethodNames::getTo), new GenerationException(format(NOT_PRIMITIVE_TYPE, arrayClass)));
    }

    public static String selectToPrimitiveMethod(Class<?> primitiveClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(primitiveClass), TypeMethodNames::getTo), new GenerationException(format(NOT_PRIMITIVE_TYPE, primitiveClass)));
    }

    public static String selectToCollectionMethod(Class<?> collectionClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(collectionClass), TypeMethodNames::getTo), new GenerationException(format(NOT_COLLECTION_TYPE, collectionClass)));
    }
}
