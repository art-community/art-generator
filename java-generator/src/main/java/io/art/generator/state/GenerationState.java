package io.art.generator.state;

import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.service.NamingService.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class GenerationState {
    private static final Map<Type, String> GENERATED_MAPPERS = weakMap();
    private static final Map<Type, String> GENERATED_CUSTOM_CONFIGURATORS = weakMap();
    private static final Map<Type, String> GENERATED_COMMUNICATOR_PROXIES = weakMap();
    private static final AtomicBoolean COMPLETED = new AtomicBoolean();

    public static void putGeneratedMapper(Type type, String name) {
        GENERATED_MAPPERS.put(type, name);
    }

    public static String getGeneratedMapper(Type type) {
        return GENERATED_MAPPERS.get(type);
    }

    public static String computeCommunicatorProxyClassName(Class<?> proxyClass) {
        String proxy = GENERATED_COMMUNICATOR_PROXIES.get(proxyClass);
        if (nonNull(proxy)) return proxy;
        String name = proxy = sequenceName(proxyClass.getSimpleName() + PROXY_CLASS_SUFFIX);
        GENERATED_COMMUNICATOR_PROXIES.put(proxyClass, name);
        return proxy;
    }

    public static String getGeneratedCustomConfigurator(Type type) {
        return GENERATED_CUSTOM_CONFIGURATORS.get(type);
    }

    public static void putGeneratedCustomConfigurator(Type type, String name) {
        GENERATED_CUSTOM_CONFIGURATORS.put(type, name);
    }

    public static String computeCustomConfiguratorClassName(Type proxyClass) {
        String proxy = GENERATED_CUSTOM_CONFIGURATORS.get(proxyClass);
        if (nonNull(proxy)) return proxy;
        String name = proxy = sequenceName(extractClass(proxyClass).getSimpleName() + PROXY_CLASS_SUFFIX);
        GENERATED_CUSTOM_CONFIGURATORS.put(proxyClass, name);
        return proxy;
    }

    public static boolean completed() {
        return COMPLETED.get();
    }

    public static void complete() {
        COMPLETED.set(true);
    }
}
