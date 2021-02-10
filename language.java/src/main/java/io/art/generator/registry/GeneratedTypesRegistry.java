package io.art.generator.registry;

import lombok.*;

import java.lang.reflect.*;
import java.util.*;

import static io.art.core.factory.MapFactory.*;
import static io.art.generator.type.TypeInspector.*;
import static io.art.generator.service.NamingService.*;
import static java.util.Objects.*;

@RequiredArgsConstructor
public class GeneratedTypesRegistry {
    private final String suffix;
    private final Map<Type, String> REGISTRY = map();

    public String get(Type type) {
        return REGISTRY.get(type);
    }

    public void put(Type type, String name) {
        REGISTRY.put(type, name);
    }

    public String compute(Type type) {
        String name;
        if (nonNull(name = REGISTRY.get(type))) return name;
        name = sequenceName(extractClass(type).getSimpleName() + suffix);
        REGISTRY.put(type, name);
        return name;
    }

    public boolean contains(Type type) {
        return REGISTRY.containsKey(type);
    }
}
