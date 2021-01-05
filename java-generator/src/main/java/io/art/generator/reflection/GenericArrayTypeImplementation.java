package io.art.generator.reflection;

import lombok.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.generator.inspector.TypeInspector.*;
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

    @Override
    public String toString() {
        Type component = this.getGenericComponentType();
        StringBuilder builder = new StringBuilder();
        if (isClass(component)) {
            return builder
                    .append(((Class<?>) component).getName())
                    .append(SQUARE_BRACES)
                    .toString();
        }

        return builder
                .append(component.toString())
                .append(SQUARE_BRACES)
                .toString();
    }


    public static GenericArrayTypeImplementation genericArrayType(Type componentType) {
        return new GenericArrayTypeImplementation(componentType);
    }
}
