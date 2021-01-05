package io.art.generator.selector;

import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.LIST_SUFFIX;
import static io.art.generator.constants.Names.SET_SUFFIX;
import static io.art.generator.constants.TypeConstants.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class ConfigurationSourceMethodSelector {
    public String selectConfigurationSourceMethod(Type propertyType) {
        if (isJavaPrimitiveType(propertyType)) {
            throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
        }

        if (isCollectionType(propertyType)) {
            if (isImmutableArrayType(propertyType)) {
                Type parameterType = ((ParameterizedType) propertyType).getActualTypeArguments()[0];
                return selectConfigurationSourceMethod(parameterType) + LIST_SUFFIX;
            }
            if (isImmutableSetType(propertyType)) {
                Type parameterType = ((ParameterizedType) propertyType).getActualTypeArguments()[0];
                return selectConfigurationSourceMethod(parameterType) + SET_SUFFIX;
            }
            if (isListType(propertyType)) {
                Type parameterType = ((ParameterizedType) propertyType).getActualTypeArguments()[0];
                return selectConfigurationSourceMethod(parameterType) + LIST_SUFFIX;
            }
            if (isSetType(propertyType)) {
                Type parameterType = ((ParameterizedType) propertyType).getActualTypeArguments()[0];
                return selectConfigurationSourceMethod(parameterType) + SET_SUFFIX;
            }
        }

        TypeMappingNames mappingNames;
        if (nonNull(let(mappingNames = TYPE_MAPPING_METHODS.get(propertyType), TypeMappingNames::getConfigurationSource))) {
            return mappingNames.getConfigurationSource();
        }

        throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, propertyType));
    }
}
