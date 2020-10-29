package ru.art.generator.javac.constants;

public interface GeneratorConstants {
    String MAIN_METHOD_NAME = "main";
    String CLASS_KEYWORD = "class";
    String JAVA_PACKAGE_PREFIX = "java.";
    String ARRAY_MARKER = "[";
    int ARRAY_ELEMENTS_CLASS_NAME_INDEX = 2;
    String CONFIGURATION_CLASS_NAME = "Configuration";

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

    interface MappersConstants {
        String CREATE_MAPPERS = "createMappers";
        String MAPPERS = "mappers";
        String PUT_TO_MODEL = "putToModel";
        String PUT_FROM_MODEL = "putFromModel";
        String VALUE = "value";
        String BUILDER = "builder";
        String BUILD = "build";
        String MAP = "map";
        String GET_PREFIX = "get";

        interface PrimitiveMappingMethods {
            String toString = "toString";
        }
    }
}
