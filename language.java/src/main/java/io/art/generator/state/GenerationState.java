package io.art.generator.state;

import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.generator.registry.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import javax.tools.*;
import java.lang.reflect.*;
import java.util.*;

public class GenerationState {
    private final static ThreadLocal<GenerationState> LOCAL_STATE = new ThreadLocal<>();

    private final Map<String, FileObject> GENERATED_CLASSES = map();
    private final GeneratedTypesRegistry mappersRegistry = new GeneratedTypesRegistry(MAPPING_INTERFACE_NAME);
    private final GeneratedTypesRegistry configurationsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    private final GeneratedTypesRegistry communicatorsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);

    private ExistedClass currentModuleClass;

    public static void useModuleClass(ExistedClass currentModuleClass) {
        getLocalState().currentModuleClass = currentModuleClass;
    }

    public static void updateLocalState() {
        LOCAL_STATE.set(new GenerationState());
    }

    public static void clearLocalState() {
        LOCAL_STATE.remove();
    }

    private static boolean COMPLETED = false;

    public static ExistedClass moduleClass() {
        return let(getLocalState(), state -> state.currentModuleClass);
    }

    public static GeneratedTypesRegistry mappers() {
        return getLocalState().mappersRegistry;
    }

    public static GeneratedTypesRegistry configurations() {
        return getLocalState().configurationsRegistry;
    }

    public static String communicatorName(Type type) {
        return getLocalState().communicatorsRegistry.compute(type);
    }

    public static boolean completed() {
        return COMPLETED;
    }

    public static void complete() {
        COMPLETED = true;
    }

    public static ImmutableMap<String, FileObject> generatedClasses() {
        return immutableMapOf(getLocalState().GENERATED_CLASSES);
    }

    public static void clearGeneratedClasses() {
        getLocalState().GENERATED_CLASSES.clear();
    }

    public static void putGeneratedClass(String name, FileObject file) {
        getLocalState().GENERATED_CLASSES.put(name, file);
    }

    private static GenerationState getLocalState() {
        return LOCAL_STATE.get();
    }
}
