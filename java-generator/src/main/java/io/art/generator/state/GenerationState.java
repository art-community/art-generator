package io.art.generator.state;

import io.art.generator.registry.*;
import java.lang.reflect.*;

public class GenerationState {
    private static final GeneratedTypesRegistry GENERATED_MAPPERS = new GeneratedTypesRegistry();
    private static final GeneratedTypesRegistry CONFIGURATIONS_REGISTRY = new GeneratedTypesRegistry();
    private static final GeneratedTypesRegistry COMMUNICATORS_REGISTRY = new GeneratedTypesRegistry();
    private static boolean COMPLETED = false;

    public static GeneratedTypesRegistry mappers() {
        return GENERATED_MAPPERS;
    }

    public static String configurationName(Type type) {
        return CONFIGURATIONS_REGISTRY.compute(type);
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
