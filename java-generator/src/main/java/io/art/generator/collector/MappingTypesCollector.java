package io.art.generator.collector;

import io.art.core.collection.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableSet.*;
import java.lang.reflect.*;

@UtilityClass
public class MappingTypesCollector {
    public ImmutableSet<Type> collectMappingTypes(ImmutableArray<Method> methods) {
        return methods
                .stream()
                .flatMap(method -> collectMappingTypes(method).stream())
                .collect(immutableSetCollector());
    }

    public ImmutableSet<Type> collectMappingTypes(Method method) {
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        types.add(method.getGenericReturnType());
        for (Type parameterType : method.getGenericParameterTypes()) {
            types.add(parameterType);
        }
        return types.build();
    }
}
