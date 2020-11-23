package io.art.generator.state;

import static io.art.core.factory.CollectionsFactory.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

public class GenerationState {
    private final static Map<Type, String> GENERATED_MAPPINGS = concurrentHashMap();

    public static void putGeneratedMapping(Type type, String name) {
        GENERATED_MAPPINGS.put(type, name);
    }

    public static String getGeneratedMapping(Type type) {
        return GENERATED_MAPPINGS.get(type);
    }

    public static boolean hasGeneratedMapping(Type type) {
        return nonNull(getGeneratedMapping(type));
    }
}
