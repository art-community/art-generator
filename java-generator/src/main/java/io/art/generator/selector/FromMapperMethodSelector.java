package io.art.generator.selector;

import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.TypeConstants.*;
import static java.text.MessageFormat.*;

@UtilityClass
public class FromMapperMethodSelector {
    public static String selectFromArrayJavaPrimitiveMethod(Class<?> arrayClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(arrayClass), TypeMethodNames::getFrom), new GenerationException(format(NOT_PRIMITIVE_TYPE, arrayClass)));
    }

    public static String selectFromPrimitiveMethod(Class<?> primitiveClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(primitiveClass), TypeMethodNames::getFrom), new GenerationException(format(NOT_PRIMITIVE_TYPE, primitiveClass)));
    }

    public static String selectFromCollectionMethod(Class<?> collectionClass) {
        return orThrow(let(TYPE_MAPPING_METHODS.get(collectionClass), TypeMethodNames::getFrom), new GenerationException(format(NOT_COLLECTION_TYPE, collectionClass)));
    }
}
