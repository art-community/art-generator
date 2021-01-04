package io.art.generator.model;

import io.art.generator.exception.*;
import lombok.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@Getter
@Builder
public class ExtractedParametrizedType {
    private final Class<?> rawClass;
    private final Type[] typeArguments;

    public static ExtractedParametrizedType from(ParameterizedType type) {
        Type rawType = type.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }

        Class<?> rawClass = (Class<?>) rawType;
        Type[] typeArguments = type.getActualTypeArguments();
        return ExtractedParametrizedType.builder()
                .rawClass(rawClass)
                .typeArguments(typeArguments)
                .build();
    }
}
