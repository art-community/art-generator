package io.art.generator.state;

import static io.art.core.factory.MapFactory.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class GenerationState {
    private static final Map<Type, String> GENERATED_MAPPERS = concurrentHashMap();
    private static final AtomicBoolean COMPLETED = new AtomicBoolean();

    public static void putGeneratedMapper(Type type, String name) {
        GENERATED_MAPPERS.put(type, name);
    }

    public static String getGeneratedMapper(Type type) {
        return GENERATED_MAPPERS.get(type);
    }

    public static boolean completed() {
        return COMPLETED.get();
    }

    public static void complete() {
        COMPLETED.set(true);
    }
}
