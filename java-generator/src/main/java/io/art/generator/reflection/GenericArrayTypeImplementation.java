package io.art.generator.reflection;

import lombok.*;
import java.lang.reflect.*;
import java.util.*;

@Getter
@RequiredArgsConstructor
public class GenericArrayTypeImplementation implements GenericArrayType {
    private final Type genericComponentType;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof GenericArrayType) {
            GenericArrayType that = (GenericArrayType) other;
            return Objects.equals(genericComponentType, that.getGenericComponentType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(genericComponentType);
    }

    public static GenericArrayTypeImplementation genericArrayType(Type componentType) {
        return new GenericArrayTypeImplementation(componentType);
    }
}
