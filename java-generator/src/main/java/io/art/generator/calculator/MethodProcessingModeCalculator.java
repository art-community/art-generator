package io.art.generator.calculator;

import io.art.core.constants.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@UtilityClass
public class MethodProcessingModeCalculator {
    public MethodProcessingMode calculateProcessingMode(Type parameterType) {
        if (parameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) parameterType;
            if (parameterizedType.getRawType().equals(Flux.class)) {
                return FLUX;
            }
            if (parameterizedType.getRawType().equals(Mono.class)) {
                return MONO;
            }
            return BLOCKING;
        }
        if (parameterType instanceof GenericArrayType) {
            return BLOCKING;
        }
        if (parameterType instanceof Class<?>) {
            if (parameterType.equals(Flux.class)) {
                return FLUX;
            }
            if (parameterType.equals(Mono.class)) {
                return MONO;
            }
            return BLOCKING;
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, parameterType.getTypeName()));
    }

}
