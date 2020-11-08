package io.art.generator.collector;

import com.google.common.collect.*;
import lombok.experimental.*;
import static com.google.common.collect.ImmutableSet.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappingTypesCollector {
    public ImmutableSet<Type> collectMappingTypes(List<Method> methods) {
        return methods
                .stream()
                .flatMap(method -> collectMappingTypes(method).stream())
                .collect(toImmutableSet());
    }

    public ImmutableSet<Type> collectMappingTypes(Method method) {
        ImmutableSet.Builder<Type> types = ImmutableSet.builder();
        types.add(method.getGenericReturnType());
        for (Type parameterType : method.getGenericParameterTypes()) {
            types.add(parameterType);
        }
        return types.build();
    }
}
