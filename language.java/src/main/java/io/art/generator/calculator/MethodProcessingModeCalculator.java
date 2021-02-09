package io.art.generator.calculator;

import io.art.core.constants.*;
import io.art.generator.exception.*;
import lombok.experimental.*;

import java.lang.reflect.*;

import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;

@UtilityClass
public class MethodProcessingModeCalculator {
    public MethodProcessingMode calculateProcessingMode(Type parameterType) {
        if (isParametrized(parameterType)) {
            ParameterizedType parameterizedType = (ParameterizedType) parameterType;
            if (isFlux(parameterizedType.getRawType())) {
                return FLUX;
            }
            if (isMono(parameterizedType.getRawType())) {
                return MONO;
            }
            return BLOCKING;
        }
        if (isGenericArray(parameterType) || isWildcard(parameterType)) {
            return BLOCKING;
        }
        if (isClass(parameterType)) {
            if (isFlux(parameterType)) {
                return FLUX;
            }
            if (isMono(parameterType)) {
                return MONO;
            }
            return BLOCKING;
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, parameterType.getTypeName()));
    }

}
