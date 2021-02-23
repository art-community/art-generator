package io.art.generator.constants;

import io.art.communicator.action.*;
import io.art.communicator.proxy.*;
import io.art.communicator.registry.*;
import io.art.configurator.custom.*;
import io.art.core.constants.*;
import io.art.core.exception.*;
import io.art.core.extensions.*;
import io.art.core.factory.*;
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
import io.art.model.implementation.storage.*;
import io.art.model.implementation.value.*;
import io.art.rsocket.communicator.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.storage.registry.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import io.art.value.registry.*;
import static io.art.generator.model.TypeModel.*;
import java.util.*;

public interface TypeModels {
    TypeModel OPTIONAL_TYPE = type(Optional.class);
    TypeModel ARRAY_EXTENSIONS_TYPE = type(ArrayExtensions.class);
    TypeModel NESTED_CONFIGURATION_TYPE = type(NestedConfiguration.class);
    TypeModel SET_FACTORY_TYPE = type(SetFactory.class);
    TypeModel STRING_ARRAY_TYPE = type(String[].class);
    TypeModel VOID_TYPE = type(void.class);
    TypeModel SINGLETON_REGISTRY_TYPE = type(SingletonsRegistry.class);

    TypeModel VALUE_FROM_MODEL_MAPPER_TYPE = type(ValueFromModelMapper.class);
    TypeModel VALUE_TO_MODEL_MAPPER_TYPE = type(ValueToModelMapper.class);
    TypeModel ENTITY_TYPE = type(Entity.class);
    TypeModel BINARY_MAPPING_TYPE = type(BinaryMapping.class);
    TypeModel ARRAY_MAPPING_TYPE = type(ArrayMapping.class);
    TypeModel PRIMITIVE_MAPPING_TYPE = type(PrimitiveMapping.class);
    TypeModel ENTITY_MAPPING_TYPE = type(EntityMapping.class);
    TypeModel LAZY_MAPPING_TYPE = type(LazyMapping.class);
    TypeModel OPTIONAL_MAPPING_TYPE = type(OptionalMapping.class);
    TypeModel PRIMITIVE_ENUM_TYPE = type(PrimitiveType.class);

    TypeModel MODULE_MODEL_TYPE = type(ModuleModel.class);
    TypeModel SERVER_MODEL_TYPE = type(ServerModuleModel.class);
    TypeModel COMMUNICATOR_MODEL_TYPE = type(CommunicatorModuleModel.class);

    TypeModel MODULE_CONFIGURATOR_TYPE = type(ModuleModelConfigurator.class);

    TypeModel CONFIGURATOR_CUSTOMIZER_TYPE = type(ConfiguratorCustomizer.class);
    TypeModel VALUE_CUSTOMIZER_TYPE = type(ValueCustomizer.class);
    TypeModel SERVER_CUSTOMIZER_TYPE = type(ServerCustomizer.class);
    TypeModel COMMUNICATOR_CUSTOMIZER_TYPE = type(CommunicatorCustomizer.class);
    TypeModel MODULE_CUSTOMIZER_TYPE = type(ModuleCustomizer.class);

    TypeModel MODULE_LAUNCHER_TYPE = type(ModuleLauncher.class);

    TypeModel CUSTOM_CONFIGURATION_REGISTRY_TYPE = type(CustomConfigurationRegistry.class);
    TypeModel CONFIGURATOR_MODULE_MODEL_TYPE = type(ConfiguratorModuleModel.class);

    TypeModel VALUE_MODULE_MODEL_TYPE = type(ValueModuleModel.class);
    TypeModel VALUE_MAPPER_REGISTRY_TYPE = type(ValueMapperRegistry.class);

    TypeModel SERVICE_VALIDATION_DECORATOR_TYPE = type(ServiceValidationDecorator.class);
    TypeModel SERVICE_SPECIFICATION_TYPE = type(ServiceSpecification.class);
    TypeModel SERVICE_METHOD_SPECIFICATION_TYPE = type(ServiceMethodSpecification.class);
    TypeModel SERVICE_METHOD_IDENTIFIER_TYPE = type(ServiceMethodIdentifier.class);
    TypeModel SERVICE_METHOD_IMPLEMENTATION_TYPE = type(ServiceMethodImplementation.class);
    TypeModel SERVICE_SPECIFICATION_REGISTRY_TYPE = type(ServiceSpecificationRegistry.class);
    TypeModel METHOD_PROCESSING_MODE_TYPE = type(MethodProcessingMode.class);

    TypeModel COMMUNICATOR_PROXY_TYPE = type(CommunicatorProxy.class);
    TypeModel COMMUNICATOR_PROXY_REGISTRY_TYPE = type(CommunicatorProxyRegistry.class);
    TypeModel COMMUNICATOR_ACTION_TYPE = type(CommunicatorAction.class);
    TypeModel COMMUNICATOR_ACTION_REGISTRY_TYPE = type(CommunicatorActionRegistry.class);
    TypeModel COMMUNICATOR_ACTION_IDENTIFIER_TYPE = type(CommunicatorActionIdentifier.class);

    TypeModel STORAGE_MODULE_MODEL_TYPE = type(StorageModuleModel.class);
    TypeModel STORAGE_CUSTOMIZER_TYPE = type(StorageCustomizer.class);
    TypeModel STORAGE_REGISTRY_TYPE = type(StorageSpacesRegistry.class);

    TypeModel CONFIGURATION_SOURCE_TYPE = type(ConfigurationSource.class);

    TypeModel NOT_IMPLEMENTED_EXCEPTION_TYPE = type(NotImplementedException.class);
}
