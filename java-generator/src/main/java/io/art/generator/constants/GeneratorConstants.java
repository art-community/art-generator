package io.art.generator.constants;

import io.art.communicator.constants.*;
import io.art.communicator.proxy.*;
import io.art.communicator.registry.*;
import io.art.communicator.specification.*;
import io.art.configurator.custom.*;
import io.art.core.checker.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.core.lazy.*;
import io.art.core.model.*;
import io.art.core.singleton.*;
import io.art.core.source.*;
import io.art.generator.model.*;
import io.art.launcher.*;
import io.art.model.configurator.*;
import io.art.model.customizer.*;
import io.art.model.implementation.communicator.*;
import io.art.model.implementation.configurator.*;
import io.art.model.implementation.module.*;
import io.art.model.implementation.server.*;
import io.art.rsocket.communicator.*;
import io.art.rsocket.constants.*;
import io.art.rsocket.model.*;
import io.art.server.implementation.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import io.art.value.immutable.Value;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import reactor.core.publisher.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.value.constants.ValueModuleConstants.*;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

public interface GeneratorConstants {
    String[] IMPORTING_CLASSES = new String[]{
            ImmutableMap.class.getName(),
            ImmutableArray.class.getName(),
            ImmutableSet.class.getName(),

            PrimitiveMapping.class.getName(),
            ArrayMapping.class.getName(),
            EntityMapping.class.getName(),
            BinaryMapping.class.getName(),
            LazyValueMapping.class.getName(),

            ArrayValue.class.getName(),
            BinaryValue.class.getName(),
            Entity.class.getName(),
            Primitive.class.getName(),
            Value.class.getName(),
            ValueType.class.getName(),
            PrimitiveType.class.getName(),

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
            ModuleModelConfigurator.class.getName(),
            ServerModuleModel.class.getName(),
            ServerModelConfigurator.class.getName(),

            ModuleCustomizer.class.getName(),
            ValueCustomizer.class.getName(),
            ServerCustomizer.class.getName(),
            List.class.getName(),
            Set.class.getName(),
            Collection.class.getName(),
            Queue.class.getName(),
            Deque.class.getName(),
            Map.class.getName(),
            UUID.class.getName(),
            Duration.class.getName(),
            LocalDateTime.class.getName(),
            ZonedDateTime.class.getName(),
            LazyValue.class.getName(),
            SingletonsRegistry.class.getName(),
            NullityChecker.class.getName(),

            ConfigurationSource.class.getName(),
            CustomConfigurationRegistry.class.getName(),
            CustomConfigurationProxy.class.getName(),
            ConfiguratorCustomizer.class.getName(),
            ConfiguratorModuleModel.class.getName(),

            CommunicatorProxyRegistry.class.getName(),
            CommunicatorModuleModel.class.getName(),
            CommunicatorCustomizer.class.getName(),
            CommunicatorSpecification.class.getName(),
            CommunicatorProxy.class.getName(),
            CommunicatorModuleConstants.CommunicationProtocol.class.getName(),

            RsocketCommunicator.class.getName(),
            RsocketModuleConstants.CommunicationMode.class.getName(),
            RsocketSetupPayload.class.getName(),
            RsocketModuleConstants.RsocketProtocol.class.getName()
    };

    interface Names {
        String CLASS_KEYWORD = "class";
        String JAVA_PACKAGE_PREFIX = "java.";
        String CONSTRUCTOR_NAME = "<init>";
        String THIS_NAME = "this";
        String LET_NAME = "let";

        String PROVIDER_CLASS_SUFFIX = "Provider";
        String PROXY_CLASS_SUFFIX = "Proxy";

        String SPECIFICATION_FIELD_PREFIX = "specification";

        String IMPLEMENT_NAME = "implement";
        String IMPLEMENTATION_NAME = "implementation";
        String COMMUNICATE_METHOD_NAME = "communicate";
        String BUILDER_METHOD_NAME = "builder";
        String BUILD_METHOD_NAME = "build";
        String CUSTOMIZER_NAME = "customizer";
        String MAIN_NAME = "main";
        String LAUNCH_NAME = "launch";
        String DECORATE_NAME = "decorate";
        String CUSTOMIZE_NAME = "customize";
        String CONFIGURE_NAME = "configure";
        String SERVER_NAME = "server";
        String COMMUNICATOR_NAME = "communicator";
        String PROVIDE_NAME = "provide";
        String MODEL_NAME = "model";
        String CONFIGURATOR_NAME = "configurator";
        String CONFIGURATOR_MODEL_NAME = "configuratorModel";
        String MODULE_MODEL_NAME = "moduleModel";
        String SERVER_MODEL_NAME = "serverModel";
        String COMMUNICATOR_MODEL_NAME = "communicatorModel";
        String MODEL_STATIC_NAME = "MODEL";
        String MAIN_METHOD_ARGUMENTS_NAME = "arguments";
        String SERVICES_NAME = "services";
        String COMMUNICATORS_NAME = "communicators";
        String REGISTRY_NAME = "registry";
        String REGISTER_NAME = "register";
        String IS_NAME = "is";
        String GET_NAME = "get";
        String SET_NAME = "set";
        String SINGLETON_NAME = "singleton";
        String INPUT_MODE_NAME = "inputMode";
        String OUTPUT_MODE_NAME = "outputMode";
        String INPUT_MAPPER_NAME = "inputMapper";
        String OUTPUT_MAPPER_NAME = "outputMapper";
        String SERVICE_METHOD_NAME = "serviceMethod";
        String CUSTOM_CONFIGURATIONS_NAME = "customConfigurations";
        String CONFIGURATOR_CUSTOMIZER_NAME = "configuratorCustomizer";
    }

    interface Annotations {
        String CONFIGURATOR_ANNOTATION_NAME = "io.art.model.annotation.Configurator";
    }

    interface ProcessorOptions {
        String DISABLE_OPTION_ENABLED = "-Aart.generator.disable=true";
        String DISABLE_OPTION = "art.generator.disable";
    }

    interface ExceptionMessages {
        String MODULE_CONFIGURATOR_NOT_FOUND = "Module configurator method not found";
        String MORE_THAN_ONE_PARAMETER = "More than one parameter not supported";
        String UNSUPPORTED_TYPE = "Unsupported type: {0}";
        String TYPE_VARIABLE_WAS_NOT_FOUND = "Type variable was not found: {0}";
        String NOT_PRIMITIVE_TYPE = "Not primitive type: {0}";
        String NOT_COLLECTION_TYPE = "Not collection type: {0}";
        String NOT_FOUND_FACTORY_METHODS = "Not found valid factory methods (builder() method, no-args or all-args constructor) for type: {0}";
        String VALIDATION_EXCEPTION_MESSAGE_FORMAT = "Validation exception for signature: [{0}]\n\t{1}";
        String GENERATION_FAILED_MESSAGE_FORMAT = "Generation failed:\n\t{0}";
        String RECOMPILATION_FAILED = "Recompilation failed";
        String NOT_CONFIGURATION_SOURCE_TYPE = "Type is not valid for configuration value: {0}";
    }

    interface LoggingMessages {
        String RECOMPILE_ARGUMENTS = "Executing recompilation with arguments: {0}";
        String RECOMPILATION_STARTED = "Recompilation started";
        String RECOMPILATION_COMPLETED = "Recompilation completed";
        String GENERATION_STARTED = "Generation started";
        String GENERATION_COMPLETED = "Generation completed";
        String GENERATED_MAPPER = "Generated mapper for type: {0}";
        String GENERATED_CLASS = "Generated class: {0}";
        String GENERATED_SERVICE_METHOD_SPECIFICATION = "Generated service method specification: {0}";
        String GENERATED_MAIN_METHOD = "Generated main method for existed main class: {0}";
        String GENERATED_MAPPERS = "All mappers were successfully generated";
        String GENERATED_SERVICE_SPECIFICATIONS = "All service specifications were successfully generated";
        String GENERATED_COMMUNICATOR_PROXIES = "All communicator proxies were successfully generated";
        String GENERATED_CUSTOM_CONFIGURATION_PROXIES = "All custom configuration proxies were successfully generated";
    }

    interface MappersConstants {
        String TO_MODEL_NAME = "toModel";
        String FROM_MODEL_NAME = "fromModel";
        String MAPPING_METHOD_NAME = "mapping";
        String MAP_OR_DEFAULT_NAME = "mapOrDefault";
        String MAPPING_INTERFACE_NAME = "Mapping";
        String ENTITY_NAME = "entity";
        String ENTITY_BUILDER_NAME = "entityBuilder";
        String LAZY_PUT_NAME = "lazyPut";
        String MAP_NAME = "map";

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
            String TO_DURATION = "toDuration";

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
            String FROM_DURATION = "fromDuration";
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

        interface LazyMappingMethods {
            String TO_LAZY = "toLazy";
            String FROM_LAZY = "fromLazy";
        }

        ImmutableSet<Class<?>> LIBRARY_BASED_TYPES = immutableSetOf(
                List.class,
                Collection.class,
                Set.class,
                Queue.class,
                Deque.class,
                Map.class,
                Flux.class,
                Mono.class,
                Date.class
        );

        ImmutableSet<Class<?>> LIBRARY_TYPES = immutableSetOf(
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
                UUID.class,
                Duration.class,
                LocalDateTime.class,
                ZonedDateTime.class,
                Object.class,
                LazyValue.class
        );
    }

    interface ServiceSpecificationMethods {
        String SERVICE_ID = "serviceId";
        String METHOD_ID = "methodId";
        String METHOD = "method";
        String RUNNER = "runner";
        String HANDLER = "handler";
        String PRODUCER = "producer";
        String CONSUMER = "consumer";
    }

    interface CommunicatorSpecificationMethods {
        String COMMUNICATOR_ID = "communicatorId";
    }

    interface CommunicatorProxyMethods {
        Method GET_IMPLEMENTATIONS_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getImplementations"));
        Method GET_PROTOCOL_METHOD = wrapException(() -> CommunicatorProxy.class.getDeclaredMethod("getProtocol"));
    }

    interface RsocketImplementationMethods {
        String CONNECTOR_ID = "connectorId";
        String SETUP_PAYLOAD = "setupPayload";
        String COMMUNICATION_MODE = "communicationMode";
    }

    interface ModelMethods {
        String GET_CONFIGURATOR_MODEL = "getConfiguratorModel";
        String GET_SERVER_MODEL = "getServerModel";
        String GET_COMMUNICATOR_MODEL = "getCommunicatorModel";
    }

    interface ConfiguratorMethods {
        Method CONFIGURE_METHOD = wrapException(() -> CustomConfigurationProxy.class.getDeclaredMethod("configure", ConfigurationSource.class));
    }

    interface ConfigurationSourceMethods {
        String GET_BOOL = "getBool";
        String GET_STRING = "getString";
        String GET_NESTED = "getNested";
        String GET_INT = "getInt";
        String GET_LONG = "getLong";
        String GET_DOUBLE = "getDouble";
        String GET_FLOAT = "getFloat";
        String GET_SHORT = "getShort";
        String GET_CHAR = "getChar";
        String GET_BYTE = "getByte";
        String GET_DURATION = "getDuration";

        String GET_BOOL_LIST = "getBoolList";
        String GET_STRING_LIST = "getStringList";
        String GET_NESTED_LIST = "getNestedList";
        String GET_INT_LIST = "getIntList";
        String GET_LONG_LIST = "getLongList";
        String GET_DOUBLE_LIST = "getDoubleList";
        String GET_SHORT_LIST = "getShortList";
        String GET_CHAR_LIST = "getCharList";
        String GET_BYTE_LIST = "getByteList";
        String GET_DURATION_LIST = "getDurationList";

        String GET_INT_MAP = "getIntMap";
        String GET_LONG_MAP = "getLongMap";
        String GET_BOOL_MAP = "getBoolMap";
        String GET_DOUBLE_MAP = "getDoubleMap";
        String GET_STRING_MAP = "getStringMap";
        String GET_DURATION_MAP = "getDurationMap";
        String GET_NESTED_MAP = "getNestedMap";
    }

    interface TypeModels {
        TypeModel NULLITY_CHECKER_TYPE =  type(NullityChecker.class);
        TypeModel STRING_ARRAY_TYPE = type(String[].class);
        TypeModel VOID_TYPE = type(void.class);
        TypeModel SINGLETON_REGISTRY_TYPE = type(SingletonsRegistry.class);

        TypeModel ENTITY_TYPE = type(Entity.class);
        TypeModel BINARY_MAPPING_TYPE = type(BinaryMapping.class);
        TypeModel ARRAY_MAPPING_TYPE = type(ArrayMapping.class);
        TypeModel PRIMITIVE_MAPPING_TYPE = type(PrimitiveMapping.class);
        TypeModel ENTITY_MAPPING_TYPE = type(EntityMapping.class);

        TypeModel MODULE_MODEL_TYPE = type(ModuleModel.class);
        TypeModel SERVER_MODEL_TYPE = type(ServerModuleModel.class);
        TypeModel COMMUNICATOR_MODEL_TYPE = type(CommunicatorModuleModel.class);

        TypeModel MODULE_CONFIGURATOR_TYPE = type(ModuleModelConfigurator.class);

        TypeModel CONFIGURATOR_CUSTOMIZER_TYPE = type(ConfiguratorCustomizer.class);
        TypeModel SERVER_CUSTOMIZER_TYPE = type(ServerCustomizer.class);
        TypeModel COMMUNICATOR_CUSTOMIZER_TYPE = type(CommunicatorCustomizer.class);
        TypeModel MODULE_CUSTOMIZER_TYPE = type(ModuleCustomizer.class);

        TypeModel MODULE_LAUNCHER_TYPE = type(ModuleLauncher.class);

        TypeModel CUSTOM_CONFIGURATION_REGISTRY_TYPE = type(CustomConfigurationRegistry.class);
        TypeModel CUSTOM_CONFIGURATION_PROXY_TYPE = type(CustomConfigurationProxy.class);
        TypeModel CONFIGURATOR_MODULE_MODEL_TYPE = type(ConfiguratorModuleModel.class);

        TypeModel SERVICE_SPECIFICATION_TYPE = type(ServiceSpecification.class);
        TypeModel SERVICE_METHOD_SPECIFICATION_TYPE = type(ServiceMethodSpecification.class);
        TypeModel SERVICE_METHOD_IDENTIFIER_TYPE = type(ServiceMethodIdentifier.class);
        TypeModel SERVICE_METHOD_IMPLEMENTATION_TYPE = type(ServiceMethodImplementation.class);
        TypeModel SERVICE_SPECIFICATION_REGISTRY_TYPE = type(ServiceSpecificationRegistry.class);
        TypeModel METHOD_PROCESSING_MODE_TYPE = type(MethodProcessingMode.class);

        TypeModel COMMUNICATOR_PROXY_REGISTRY_TYPE = type(CommunicatorProxyRegistry.class);
        TypeModel COMMUNICATOR_SPECIFICATION_TYPE = type(CommunicatorSpecification.class);
        TypeModel RSOCKET_COMMUNICATOR_IMPLEMENTATION_TYPE = type(RsocketCommunicator.class);
        TypeModel RSOCKET_COMMUNICATION_MODE_TYPE = type(RsocketModuleConstants.CommunicationMode.class);
        TypeModel RSOCKET_SETUP_PAYLOAD_TYPE = type(RsocketSetupPayload.class);
    }
}
