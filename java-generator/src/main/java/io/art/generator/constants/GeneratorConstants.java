package io.art.generator.constants;

import static io.art.core.factory.CollectionsFactory.*;
import java.util.*;

public interface GeneratorConstants {
    String CLASS_KEYWORD = "class";
    String JAVA_PACKAGE_PREFIX = "java.";
    String ARRAY_MARKER = "[";
    int ARRAY_ELEMENTS_CLASS_NAME_INDEX = 2;

    interface Names {
        String PROVIDER_CLASS_NAME_SUFFIX = "Provider";
        String BUILDER_METHOD_NAME = "builder";
        String CONFIGURATOR_NAME = "configurator";
        String BUILD_METHOD_NAME = "build";
        String MAIN_NAME = "main";
        String LAUNCH_NAME = "launch";
        String DECORATE_NAME = "decorate";
        String CONFIGURE_NAME = "configure";
        String SERVER_NAME = "server";
        String MODULE_NAME = "module";
        String MODEL_NAME = "model";
        String MAIN_METHOD_ARGUMENTS_NAME = "arguments";
        String MAPPERS_NAME = "mappers";
        String SERVICES_NAME = "services";
        String REGISTRY_NAME = "registry";
        String PUT_TO_MODEL_NAME = "putToModel";
        String PUT_FROM_MODEL_NAME = "putFromModel";
        String GET_TO_MODEL_NAME = "getToModel";
        String GET_FROM_MODEL_NAME = "getFromModel";
        String REGISTER_NAME = "register";
        String VALUE_NAME = "value";
        String ENTITY_BUILDER_NAME = "entityBuilder";
        String LAZY_PUT_NAME = "lazyPut";
        String MAP_NAME = "map";
        String GET_PREFIX = "get";
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
        String MODULE_CONFIGURATOR_NOT_FOUND = "Module configurator method not found";
        String UNKNOWN_FIELD_TYPE = "Field type unknown: {0}";
    }

    interface MappersConstants {
        interface PrimitiveMappingMethods {
            String TO_STRING = "toString";
            String TO_INT = "toInt";
            String TO_BOOL = "toBool";
            String TO_LONG = "toLong";
            String TO_DOUBLE = "toDouble";
            String TO_BYTE = "toByte";
            String TO_FLOAT = "toFloat";

            String FROM_STRING = "fromString";
            String FROM_INT = "fromInt";
            String FROM_BOOL = "fromBool";
            String FROM_LONG = "fromLong";
            String FROM_DOUBLE = "fromDouble";
            String FROM_BYTE = "fromByte";
            String FROM_FLOAT = "fromFloat";
        }

        interface ArrayMappingMethods {
            String FROM_COLLECTION = "fromCollection";
            String FROM_ARRAY = "fromArray";
            String FROM_LIST = "fromList";
            String FROM_SET = "fromSet";
            String FROM_QUEUE = "fromQueue";
            String FROM_DEQUE = "fromDeque";

            String TO_COLLECTION = "toCollection";
            String TO_ARRAY = "toArray";
            String TO_LIST = "toList";
            String TO_SET = "toSet";
            String TO_QUEUE = "toQueue";
            String TO_DEQUE = "toDeque";
        }

        interface EntityMappingMethods {
            String TO_MAP = "toMap";
            String FROM_MAP = "fromMap";
        }

        interface BinaryMappingMethods {
            String FROM_BINARY = "fromBinary";
            String TO_BINARY = "toBinary";
        }

        Set<Class<?>> KNOWN_TYPES = setOf(
                String.class,
                short.class,
                Short.class,
                int.class,
                Integer.class,
                long.class,
                Long.class,
                byte.class,
                Byte.class,
                float.class,
                Float.class,
                double.class,
                Double.class,
                List.class,
                Collection.class,
                Set.class,
                Map.class
        );

        Set<Class<?>> KNOWN_STRICT_TYPES = setOf(
                Object.class
        );
    }

    interface ServiceSpecificationMethods {
        String SERVICE_ID = "serviceId";
        String METHOD_ID = "methodId";
        String METHOD = "method";
        String INPUT_MODE = "inputMode";
        String OUTPUT_MODE = "outputMode";
        String INPUT_MAPPER = "inputMapper";
        String OUTPUT_MAPPER = "outputMapper";
        String INPUT_DECORATOR = "inputDecorator";
        String OUTPUT_DECORATOR = "outputDecorator";
        String IMPLEMENTATION = "implementation";
        String SERVICE_METHOD = "serviceMethod";
        String HANDLER_METHOD = "handler";
        String PRODUCER_METHOD = "producer";
        String CONSUMER_METHOD = "consumer";
    }
}
