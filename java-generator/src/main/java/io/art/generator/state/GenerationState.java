package io.art.generator.state;

import io.art.generator.registry.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import java.lang.reflect.*;

public class GenerationState {
    private static final GeneratedTypesRegistry GENERATED_MAPPERS = new GeneratedTypesRegistry(MAPPING_INTERFACE_NAME);
    private static final GeneratedTypesRegistry CONFIGURATIONS_REGISTRY = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    private static final GeneratedTypesRegistry COMMUNICATORS_REGISTRY = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    private static boolean COMPLETED = false;

    public static GeneratedTypesRegistry mappers() {
        return GENERATED_MAPPERS;
    }

    public static GeneratedTypesRegistry configurations() {
        return COMMUNICATORS_REGISTRY;
    }

    public static String communicatorName(Type type) {
        return COMMUNICATORS_REGISTRY.compute(type);
    }

    public static boolean completed() {
        return COMPLETED;
    }

    public static void complete() {
        COMPLETED = true;
    }
}
