package io.art.generator.selector;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@UtilityClass
public class ConfigurationSourceMethodSelector {
    public String selectConfigurationSourceMethod(Type propertyType) {
        if (isJavaPrimitiveType(propertyType)) {
            throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
        }

        if (isCollectionType(extractClass(propertyType))) {

        }

        if (isCollectionType(extractClass(propertyType))) {

        }


        throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
    }
}
