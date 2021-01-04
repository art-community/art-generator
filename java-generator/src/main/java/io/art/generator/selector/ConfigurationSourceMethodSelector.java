package io.art.generator.selector;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfigurationSourceMethods.*;
import static io.art.generator.constants.ExceptionMessages.NOT_CONFIGURATION_SOURCE_TYPE;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.format;
import java.lang.reflect.*;
import java.time.*;

@UtilityClass
public class ConfigurationSourceMethodSelector {
    public String selectConfigurationSourceMethod(Type propertyType) {
        if (!isClass(propertyType)) {
            throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
        }

        if (isJavaPrimitiveType(propertyType)) {
            throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
        }

        if (Boolean.class.equals(propertyType)) return GET_BOOL;
        if (String.class.equals(propertyType)) return GET_STRING;
        if (Integer.class.equals(propertyType)) return GET_INT;
        if (Long.class.equals(propertyType)) return GET_LONG;
        if (Double.class.equals(propertyType)) return GET_DOUBLE;
        if (Float.class.equals(propertyType)) return GET_FLOAT;
        if (Short.class.equals(propertyType)) return GET_SHORT;
        if (Character.class.equals(propertyType)) return GET_CHAR;
        if (Byte.class.equals(propertyType)) return GET_BYTE;
        if (Duration.class.equals(propertyType)) return GET_DURATION;

        throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
    }
}
