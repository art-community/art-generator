package io.art.generator.state;

import static io.art.core.factory.MapFactory.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class GenerationState {
    private static final Map<Type, String> GENERATED_MAPPERS = weakMap();
    private static final Map<Type, String> GENERATED_CONFIGURATION_PROXIES = weakMap();
    private static final Map<Type, String> GENERATED_COMMUNICATOR_PROXIES = weakMap();
    private static final AtomicBoolean COMPLETED = new AtomicBoolean();

    public static void putGeneratedMapper(Type type, String name) {
        GENERATED_MAPPERS.put(type, name);
    }

    public static String getGeneratedMapper(Type type) {
        return GENERATED_MAPPERS.get(type);
    }

    public static void putGeneratedConfigurationProxy(Type type, String name) {
        GENERATED_COMMUNICATOR_PROXIES.put(type, name);
    }

    public static String getGeneratedConfigurationProxy(Type type) {
        return GENERATED_CONFIGURATION_PROXIES.get(type);
    }

    public static void putGeneratedCommunicatorProxy(Type type, String name) {
        GENERATED_COMMUNICATOR_PROXIES.put(type, name);
    }

    public static String getGeneratedCommunicatorProxy(Type type) {
        return GENERATED_COMMUNICATOR_PROXIES.get(type);
    }

    public static boolean completed() {
        return COMPLETED.get();
    }

    public static void complete() {
        COMPLETED.set(true);
    }
}
