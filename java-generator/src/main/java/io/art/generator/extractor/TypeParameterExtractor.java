package io.art.generator.extractor;

import com.google.common.collect.*;
import lombok.experimental.*;
import static com.google.common.collect.ImmutableList.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class TypeParameterExtractor {
    public ImmutableList<Class<?>> extractTypeParameters(ParameterizedType generic) {
        Type[] typeArguments = generic.getActualTypeArguments();
        return stream(typeArguments)
                .filter(argument -> argument instanceof Class<?>)
                .map(argument -> ((Class<?>) argument))
                .collect(toImmutableList());
    }

    public Class<?> extractFirstTypeParameter(ParameterizedType owner) {
        return extractTypeParameters(owner).get(0);
    }
}
