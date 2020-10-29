package ru.art.generator.javac.constants;

import com.google.common.collect.*;

public interface Constants {
    String INIT_METHOD_NAME = "<init>";
    String EQUALS_METHOD_NAME = "equals";
    String HASH_CODE_METHOD_NAME = "hashCode";
    String TO_STRING_METHOD_NAME = "toString";
    String MAIN_METHOD_NAME = "main";
    String JAVA_PACKAGE_PREFIX = "java.";
    String ARRAY_MARKER = "[";
    int ARRAY_ELEMENTS_CLASS_NAME_INDEX = 2;
    String METHODS_SUFFIX = "Methods";
    String METHODS_FIELD = "methods";

    ImmutableSet<String> IGNORING_METHODS = ImmutableSet.of(
            INIT_METHOD_NAME,
            EQUALS_METHOD_NAME,
            HASH_CODE_METHOD_NAME,
            TO_STRING_METHOD_NAME,
            MAIN_METHOD_NAME
    );

    interface Annotations {
        String MODULE_ANNOTATION_NAME = "io.art.model.annotation.Module";
        String CONFIGURATOR_ANNOTATION_NAME = "io.art.model.annotation.Configurator";
    }

    interface ProcessorOptions {
        String DISABLE_OPTION_ENABLED = "-Adisable=true";
        String DISABLE_OPTION = "disable";
    }

    interface ExceptionMessages {
        String MODULE_CONFIGURATOR_NOT_FOUND_EXCEPTION = "Module configurator method not found";
    }

    interface MethodNames {
        String CONFIGURE_METHOD_NAME = "configure";
        String MODULE_METHOD_NAME = "module";
    }
}
