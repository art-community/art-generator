package io.art.generator.state;

import io.art.generator.model.*;
import io.art.generator.registry.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import javax.tools.*;
import java.lang.reflect.*;
import java.util.*;

@RequiredArgsConstructor
public class GenerationState {
    private final static ThreadLocal<GenerationState> LOCAL_STATE = new ThreadLocal<>();
    private final static Map<String, FileObject> GENERATED_CLASSES = map();
    private final ExistedClass currentModuleClass;
    private final GeneratedTypesRegistry mappersRegistry = new GeneratedTypesRegistry(MAPPING_INTERFACE_NAME);
    private final GeneratedTypesRegistry configurationsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    private final GeneratedTypesRegistry communicatorsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);

    public static void updateLocalState(ExistedClass moduleClass) {
        LOCAL_STATE.set(new GenerationState(moduleClass));
    }

    public static void clearLocalState() {
        LOCAL_STATE.remove();
    }

    private static boolean COMPLETED = false;

    public static ExistedClass moduleClass() {
        return let(LOCAL_STATE.get(), state -> state.currentModuleClass);
    }

    public static GeneratedTypesRegistry mappers() {
        return LOCAL_STATE.get().mappersRegistry;
    }

    public static GeneratedTypesRegistry configurations() {
        return LOCAL_STATE.get().configurationsRegistry;
    }

    public static String communicatorName(Type type) {
        return LOCAL_STATE.get().communicatorsRegistry.compute(type);
    }

    public static boolean completed() {
        return COMPLETED;
    }

    public static void complete() {
        COMPLETED = true;
    }

    public static Map<String, FileObject> generatedClasses() {
        return GENERATED_CLASSES;
    }

    public static void clearGeneratedClasses() {
        GENERATED_CLASSES.clear();
    }

    public static void putGeneratedClass(String name, FileObject file) {
        GENERATED_CLASSES.put(name, file);
    }
}
