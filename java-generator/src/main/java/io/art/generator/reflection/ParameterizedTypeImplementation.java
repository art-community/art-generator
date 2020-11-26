package io.art.generator.reflection;

import lombok.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@Getter
public class ParameterizedTypeImplementation implements ParameterizedType {
    private final Type[] actualTypeArguments;
    private final Class<?> rawType;
    private final Type ownerType;

    private ParameterizedTypeImplementation(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
        this.ownerType = nonNull(ownerType) ? ownerType : rawType.getDeclaringClass();
    }

    public static ParameterizedTypeImplementation parameterizedType(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
        return new ParameterizedTypeImplementation(rawType, actualTypeArguments, ownerType);
    }
}
