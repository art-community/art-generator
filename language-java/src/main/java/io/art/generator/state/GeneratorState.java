package io.art.generator.state;

import io.art.core.collection.*;
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

public class GeneratorState {
    private final static ThreadLocal<GeneratorState> LOCAL_STATE = new ThreadLocal<>();
    private final ThreadLocal<ClassState> CLASS_STATE = new ThreadLocal<>();
    private final Map<String, FileObject> GENERATED_CLASSES = map();
    private static volatile boolean COMPLETED = false;


    public static boolean completed() {
        return COMPLETED;
    }

    public static void complete() {
        COMPLETED = true;
    }


    public static void useModuleClass(ExistedClass currentModuleClass) {
        getLocalState().CLASS_STATE.set(new ClassState(currentModuleClass));
    }


    public static void refreshLocalState() {
        LOCAL_STATE.set(new GeneratorState());
    }

    public static void clearLocalState() {
        LOCAL_STATE.get().CLASS_STATE.remove();
        LOCAL_STATE.remove();
    }


    public static ExistedClass moduleClass() {
        return let(getClassState(), state -> state.currentModuleClass);
    }

    public static GeneratedTypesRegistry mappers() {
        return getClassState().mappersRegistry;
    }

    public static GeneratedTypesRegistry configurations() {
        return getClassState().configurationsRegistry;
    }

    public static String communicatorName(Type type) {
        return getClassState().communicatorsRegistry.compute(type);
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


    private static GeneratorState getLocalState() {
        return LOCAL_STATE.get();
    }

    private static ClassState getClassState() {
        return let(getLocalState(), state -> state.CLASS_STATE.get());
    }

    @RequiredArgsConstructor
    private static class ClassState {
        private final ExistedClass currentModuleClass;
        private final GeneratedTypesRegistry mappersRegistry = new GeneratedTypesRegistry(MAPPING_INTERFACE_NAME);
        private final GeneratedTypesRegistry configurationsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
        private final GeneratedTypesRegistry communicatorsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    }
}
