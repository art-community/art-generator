package io.art.generator.selector;

import io.art.generator.exception.*;
import io.art.generator.formater.*;
import lombok.experimental.*;
import static io.art.generator.constants.ConfiguratorConstants.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.type.TypeInspector.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class ConfigurationSourceMethodSelector {
    public String selectConfigurationSourceMethod(Type propertyType) {
        propertyType = boxed(propertyType);
        String name;
        if (nonNull(name = CONFIGURATOR_PROPERTY_TYPE_METHODS.get(propertyType))) {
            return name;
        }

        throw new ValidationException(SignatureFormatter.formatSignature(propertyType), format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
    }
}
