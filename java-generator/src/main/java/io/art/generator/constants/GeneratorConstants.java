package io.art.generator.constants;

import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.core.lazy.*;
import io.art.generator.model.*;
import io.art.launcher.*;
import io.art.model.customizer.*;
import io.art.model.implementation.*;
import io.art.model.modeler.*;
import io.art.server.implementation.*;
import io.art.server.model.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import io.art.value.immutable.Value;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import reactor.core.publisher.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.value.constants.ValueConstants.*;
import java.time.*;
import java.util.*;

public interface GeneratorConstants {
    String CLASS_KEYWORD = "class";
    String JAVA_PACKAGE_PREFIX = "java.";

    String[] IMPORTING_CLASSES = new String[]{
            PrimitiveMapping.class.getName(),
            ArrayMapping.class.getName(),
            EntityMapping.class.getName(),
            BinaryMapping.class.getName(),
            ArrayValue.class.getName(),
            BinaryValue.class.getName(),
            Entity.class.getName(),
            Primitive.class.getName(),
            Value.class.getName(),
            ValueType.class.getName().replace(DOLLAR, DOT),
            PrimitiveType.class.getName().replace(DOLLAR, DOT),

            ValueToModelMapper.class.getName(),
            ValueFromModelMapper.class.getName(),

            ServiceSpecification.class.getName(),
            ServiceMethodSpecification.class.getName(),
            ServiceMethodIdentifier.class.getName(),
            ServiceMethodImplementation.class.getName(),
            ServiceSpecificationRegistry.class.getName(),

            MethodProcessingMode.class.getName(),

            Flux.class.getName(),
            Mono.class.getName(),

            ModuleLauncher.class.getName(),

            ModuleModel.class.getName(),
            ModuleModeler.class.getName(),
            ServerModel.class.getName(),
            ServerModeler.class.getName(),

            ModuleCustomizer.class.getName(),
            ValueCustomizer.class.getName(),
            ServerCustomizer.class.getName(),
            List.class.getName(),
            Set.class.getName(),
            Collection.class.getName(),
            Queue.class.getName(),
            Deque.class.getName(),
            Map.class.getName(),

            LazyValue.class.getName(),
    };

    interface Names {
        String PROVIDER_CLASS_NAME_SUFFIX = "Provider";
        String BUILDER_METHOD_NAME = "builder";
        String CUSTOMIZER_NAME = "customizer";
        String BUILD_METHOD_NAME = "build";
        String MAIN_NAME = "main";
        String LAUNCH_NAME = "launch";
        String DECORATE_NAME = "decorate";
        String CUSTOMIZE_NAME = "customize";
        String APPLY_NAME = "apply";
        String SERVER_NAME = "server";
        String PROVIDE_NAME = "provide";
        String MODEL_NAME = "model";
        String MODELER_NAME = "modeler";
        String MODULE_MODEL_NAME = "moduleModel";
        String SERVER_MODEL_NAME = "serverModel";
        String MODEL_STATIC_NAME = "MODEL";
        String MAIN_METHOD_ARGUMENTS_NAME = "arguments";
        String SERVICES_NAME = "services";
        String REGISTRY_NAME = "registry";
        String REGISTER_NAME = "register";
        String IS_NAME = "is";
        String GET_NAME = "get";
        String SET_NAME = "set";
    }

    interface Annotations {
        String MODELER_ANNOTATION_NAME = "io.art.model.annotation.Modeler";
    }

    interface ProcessorOptions {
        String DISABLE_OPTION_ENABLED = "-Aart.generator.disable=true";
        String DISABLE_OPTION = "art.generator.disable";
    }

    interface ExceptionMessages {
        String MODULE_MODELER_NOT_FOUND = "Module modeler method not found";
        String MORE_THAN_ONE_PARAMETER = "More than one parameter not supported";
        String UNSUPPORTED_TYPE = "Unsupported type: {0}";
        String TYPE_VARIABLE_WAS_NOT_FOUND = "Type variable was not found: {0}";
        String NOT_PRIMITIVE_TYPE = "Not primitive type: {0}";
        String NOT_COLLECTION_TYPE = "Not collection type: {0}";
        String NOT_FOUND_FACTORY_METHODS = "Not found valid factory methods (builder() method, no-args or all-args constructor) for type: {0}";
    }

    interface MappersConstants {
        String TO_MODEL_NAME = "toModel";
        String FROM_MODEL_NAME = "fromModel";
        String MAPPING_METHOD_NAME = "mapping";
        String MAP_OR_DEFAULT_NAME = "mapOrDefault";
        String MAPPING_INTERFACE_NAME = "Mapping";
        String VALUE_NAME = "value";
        String ENTITY_BUILDER_NAME = "entityBuilder";
        String LAZY_PUT_NAME = "lazyPut";
        String MAP_NAME = "map";
        String LAZY_NAME = "lazy";

        interface PrimitiveMappingMethods {
            String TO_UUID = "toUuid";
            String TO_STRING = "toString";
            String TO_CHAR = "toChar";
            String TO_INT = "toInt";
            String TO_SHORT = "toShort";
            String TO_BOOL = "toBool";
            String TO_LONG = "toLong";
            String TO_DOUBLE = "toDouble";
            String TO_BYTE = "toByte";
            String TO_FLOAT = "toFloat";
            String TO_LOCAL_DATE_TIME = "toLocalDateTime";
            String TO_ZONED_DATE_TIME = "toZonedDateTime";
            String TO_DATE = "toDate";

            String FROM_UUID = "fromUuid";
            String FROM_STRING = "fromString";
            String FROM_CHAR = "fromChar";
            String FROM_INT = "fromInt";
            String FROM_SHORT = "fromShort";
            String FROM_BOOL = "fromBool";
            String FROM_LONG = "fromLong";
            String FROM_DOUBLE = "fromDouble";
            String FROM_BYTE = "fromByte";
            String FROM_FLOAT = "fromFloat";
            String FROM_LOCAL_DATE_TIME = "fromLocalDateTime";
            String FROM_ZONED_DATE_TIME = "fromZonedDateTime";
            String FROM_DATE = "fromDate";
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

            String TO_INT_ARRAY = "toIntArray";
            String TO_LONG_ARRAY = "toLongArray";
            String TO_SHORT_ARRAY = "toShortArray";
            String TO_DOUBLE_ARRAY = "toDoubleArray";
            String TO_FLOAT_ARRAY = "toFloatArray";
            String TO_BYTE_ARRAY = "toByteArray";
            String TO_CHAR_ARRAY = "toCharArray";
            String TO_BOOL_ARRAY = "toBoolArray";


            String FROM_INT_ARRAY = "fromIntArray";
            String FROM_LONG_ARRAY = "fromLongArray";
            String FROM_SHORT_ARRAY = "fromShortArray";
            String FROM_DOUBLE_ARRAY = "fromDoubleArray";
            String FROM_FLOAT_ARRAY = "fromFloatArray";
            String FROM_BYTE_ARRAY = "fromByteArray";
            String FROM_CHAR_ARRAY = "fromCharArray";
            String FROM_BOOL_ARRAY = "fromBoolArray";
        }

        interface EntityMappingMethods {
            String TO_MAP = "toMap";
            String FROM_MAP = "fromMap";
        }

        interface BinaryMappingMethods {
            String FROM_BINARY = "fromBinary";
            String TO_BINARY = "toBinary";
        }

        ImmutableSet<Class<?>> LIBRARY_BASED_TYPES = immutableSetOf(
                void.class,
                Void.class,
                String.class,
                boolean.class,
                Boolean.class,
                short.class,
                Short.class,
                char.class,
                Character.class,
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
                Queue.class,
                Deque.class,
                Map.class,
                Flux.class,
                Mono.class,
                UUID.class,
                LocalDateTime.class,
                ZonedDateTime.class,
                Date.class
        );

        ImmutableSet<Class<?>> LIBRARY_TYPES = immutableSetOf(
                Object.class,
                LazyValue.class
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
        String IMPLEMENTATION = "implementation";
        String RUNNER_METHOD = "runner";
        String HANDLER_METHOD = "handler";
        String PRODUCER_METHOD = "producer";
        String CONSUMER_METHOD = "consumer";
    }

    interface ModelMethods {
        String IMPLEMENT_NAME = "implement";
        String GET_SERVER_MODEL = "getServerModel";
    }

    interface TypeModels {
        TypeModel ENTITY_TYPE = type(Entity.class);
        TypeModel BINARY_MAPPING_TYPE = type(BinaryMapping.class);
        TypeModel ARRAY_MAPPING_TYPE = type(ArrayMapping.class);
        TypeModel PRIMITIVE_MAPPING_TYPE = type(PrimitiveMapping.class);
        TypeModel ENTITY_MAPPING_TYPE = type(EntityMapping.class);

        TypeModel MODULE_MODEL_TYPE = type(ModuleModel.class);
        TypeModel MODULE_MODELER_TYPE = type(ModuleModeler.class);

        TypeModel SERVER_MODEL_TYPE = type(ServerModel.class);

        TypeModel SERVER_CUSTOMIZER_TYPE = type(ServerCustomizer.class);
        TypeModel MODULE_CUSTOMIZER_TYPE = type(ModuleCustomizer.class);

        TypeModel SERVICE_SPECIFICATION_TYPE = type(ServiceSpecification.class);
        TypeModel SERVICE_METHOD_SPECIFICATION_TYPE = type(ServiceMethodSpecification.class);
        TypeModel SERVICE_METHOD_IMPLEMENTATION_TYPE = type(ServiceMethodImplementation.class);
        TypeModel SERVICE_SPECIFICATION_REGISTRY_TYPE = type(ServiceSpecificationRegistry.class);
        TypeModel METHOD_PROCESSING_MODE_TYPE = type(MethodProcessingMode.class);

        TypeModel MODULE_LAUNCHER_TYPE = type(ModuleLauncher.class);
        TypeModel STRING_ARRAY_TYPE = type(String[].class);
        TypeModel VOID_TYPE = type(void.class);
    }
}
