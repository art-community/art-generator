package io.art.generator.state;

import io.art.generator.model.*;
import io.art.generator.registry.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.let;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import java.lang.reflect.*;

@RequiredArgsConstructor
public class GenerationState {
    private final static ThreadLocal<GenerationState> STATE = new ThreadLocal<>();

    private final ExistedClass currentModuleClass;
    private final GeneratedTypesRegistry mappersRegistry = new GeneratedTypesRegistry(MAPPING_INTERFACE_NAME);
    private final GeneratedTypesRegistry configurationsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);
    private final GeneratedTypesRegistry communicatorsRegistry = new GeneratedTypesRegistry(PROXY_CLASS_SUFFIX);

    public static void updateState(ExistedClass existedClass) {
        STATE.set(new GenerationState(existedClass));
    }

    public static void clearState() {
        STATE.remove();
    }

    private static boolean COMPLETED = false;

    public static ExistedClass moduleClass() {
        return let(STATE.get(), state -> state.currentModuleClass);
    }

    public static GeneratedTypesRegistry mappers() {
        return STATE.get().mappersRegistry;
    }

    public static GeneratedTypesRegistry configurations() {
        return STATE.get().configurationsRegistry;
    }

    public static String communicatorName(Type type) {
        return STATE.get().communicatorsRegistry.compute(type);
    }

    public static boolean completed() {
        return COMPLETED;
    }

    public static void complete() {
        COMPLETED = true;
    }
}
