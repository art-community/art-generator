package io.art.generator.registry;

import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.service.NamingService.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

public class GeneratedTypesRegistry {
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
        name = sequenceName(extractClass(type).getSimpleName() + PROXY_CLASS_SUFFIX);
        REGISTRY.put(type, name);
        return name;
    }
}
