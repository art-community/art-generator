package io.art.generator.selector;

import io.art.generator.exception.*;
import io.art.generator.formater.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.generator.constants.ConfiguratorConstants.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class ConfigurationSourceMethodSelector {
    public String selectConfigurationSourceMethod(Type propertyType) {

        String name;
        if (nonNull(name = CONFIGURATOR_PROPERTY_TYPE_METHODS.get(propertyType))) {
            return name;
        }

        throw new ValidationException(SignatureFormatter.formatSignature(propertyType), format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
    }
}
