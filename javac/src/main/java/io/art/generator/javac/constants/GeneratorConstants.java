package io.art.generator.javac.constants;

public interface GeneratorConstants {
    String CLASS_KEYWORD = "class";
    String JAVA_PACKAGE_PREFIX = "java.";
    String ARRAY_MARKER = "[";
    int ARRAY_ELEMENTS_CLASS_NAME_INDEX = 2;

    interface Names {
        String PROVIDER_CLASS_NAME_SUFFIX = "Provider";
        String BUILDER_METHOD_NAME = "builder";
        String BUILD_METHOD_NAME = "build";
        String MAIN_NAME = "main";
        String LAUNCH_NAME = "launch";
        String DECORATE_NAME = "decorate";
        String CONFIGURE_NAME = "configure";
        String MODULE_NAME = "module";
        String MODEL_NAME = "model";
        String MAIN_METHOD_ARGUMENTS_NAME = "arguments";
        String MAPPERS_NAME = "mappers";
        String SERVICES_NAME = "services";
        String REGISTRY_NAME = "registry";
    }

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

    interface MappersConstants {
        String CREATE_MAPPERS = "createMappers";
        String PUT_TO_MODEL = "putToModel";
        String PUT_FROM_MODEL = "putFromModel";
        String GET_TO_MODEL = "getToModel";
        String GET_FROM_MODEL = "getFromModel";
        String REGISTER = "register";
        String VALUE = "value";
        String MODEL = "model";
        String ENTITY_BUILDER = "entityBuilder";
        String LAZY_PUT = "lazyPut";
        String MAP = "map";
        String GET_PREFIX = "get";

        interface PrimitiveMappingMethods {
            String toString = "toString";
            String toInt = "toInt";
            String toBool = "toBool";
            String toLong = "toLong";
            String toDouble = "toDouble";
            String toByte = "toByte";
            String toFloat = "toFloat";

            String fromString = "fromString";
            String fromInt = "fromInt";
            String fromBool = "fromBool";
            String fromLong = "fromLong";
            String fromDouble = "fromDouble";
            String fromByte = "fromByte";
            String fromFloat = "fromFloat";
        }
    }
}
