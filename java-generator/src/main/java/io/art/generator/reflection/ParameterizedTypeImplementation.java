package io.art.generator.reflection;

import lombok.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ParameterizedType)) {
            return false;
        }

        ParameterizedType that = (ParameterizedType) other;
        Type thatOwner = that.getOwnerType();
        Type thatRawType = that.getRawType();
        return Objects.equals(ownerType, thatOwner) && Objects.equals(rawType, thatRawType) && Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(actualTypeArguments) ^ Objects.hashCode(ownerType) ^ Objects.hashCode(rawType);
    }

    public static ParameterizedTypeImplementation parameterizedType(Class<?> rawType, Type[] actualTypeArguments) {
        return new ParameterizedTypeImplementation(rawType, actualTypeArguments, null);
    }
}
