package ru.art.generator.javac.constants;

import static ru.art.generator.javac.factory.CollectionsFactory.*;
import java.util.*;

public interface Constants {
    String INIT_METHOD_NAME = "<init>";
    String EQUALS_METHOD_NAME = "equals";
    String HASH_CODE_METHOD_NAME = "hashCode";
    String TO_STRING_METHOD_NAME = "toString";
    String MAIN_METHOD_NAME = "main";
    String MODULE_METHOD_NAME = "module";
    String SERVICE_METHOD_NAME = "service";
    String JAVA_PACKAGE_PREFIX = "java.";
    String ARRAY_MARKER = "[";
    int ARRAY_ELEMENTS_CLASS_NAME_INDEX = 2;

    Set<String> IGNORING_METHODS = setOf(
            INIT_METHOD_NAME,
            EQUALS_METHOD_NAME,
            HASH_CODE_METHOD_NAME,
            TO_STRING_METHOD_NAME,
            MAIN_METHOD_NAME
    );
}
