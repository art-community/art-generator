package io.art.generator.constants;

import io.art.communicator.action.*;
import io.art.communicator.constants.CommunicatorModuleConstants.*;
import io.art.communicator.proxy.*;
import io.art.communicator.registry.*;
import io.art.configurator.custom.*;
import io.art.core.checker.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.core.exception.*;
import io.art.core.extensions.*;
import io.art.core.factory.*;
import io.art.core.model.*;
import io.art.core.property.*;
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
import io.art.rsocket.constants.RsocketModuleConstants.*;
import io.art.rsocket.model.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.server.validation.*;
import io.art.storage.registry.*;
import io.art.value.constants.ValueModuleConstants.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import io.art.value.immutable.Value;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import io.art.value.registry.*;
import reactor.core.publisher.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public interface Imports {
    ImportModel NOT_IMPLEMENTED_EXCEPTION_MODEL = new ImportModel("io.art.core.exception.NotImplementedException", false, false);

    String[] IMPORTING_CLASSES = new String[]{
            NotImplementedException.class.getName(),
            Optional.class.getName(),
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
            Stream.class.getName(),
            SetFactory.class.getName(),
            ArrayFactory.class.getName(),
            ArrayExtensions.class.getName(),
            NestedConfiguration.class.getName(),

            Flux.class.getName(),
            Mono.class.getName(),

            MethodProcessingMode.class.getName(),
            LazyProperty.class.getName(),
            DisposableProperty.class.getName(),
            SingletonsRegistry.class.getName(),
            NullityChecker.class.getName(),

            ImmutableMap.class.getName(),
            ImmutableArray.class.getName(),
            ImmutableSet.class.getName(),

            PrimitiveMapping.class.getName(),
            ArrayMapping.class.getName(),
            EntityMapping.class.getName(),
            BinaryMapping.class.getName(),
            LazyMapping.class.getName(),
            OptionalMapping.class.getName(),

            ArrayValue.class.getName(),
            BinaryValue.class.getName(),
            Entity.class.getName(),
            Primitive.class.getName(),
            Value.class.getName(),
            ValueType.class.getName(),
            PrimitiveType.class.getName(),

            ValueToModelMapper.class.getName(),
            ValueFromModelMapper.class.getName(),
            ValueModuleModel.class.getName(),
            ValueMapperRegistry.class.getName(),

            Validatable.class.getName(),
            ServiceSpecification.class.getName(),
            ServiceMethodSpecification.class.getName(),
            ServiceMethodIdentifier.class.getName(),
            ServiceMethodImplementation.class.getName(),
            ServiceSpecificationRegistry.class.getName(),
            ServiceValidationDecorator.class.getName(),

            ModuleLauncher.class.getName(),

            ModuleModel.class.getName(),
            ModuleModelConfigurator.class.getName(),
            ServerModuleModel.class.getName(),
            ServerModelConfigurator.class.getName(),

            ModuleCustomizer.class.getName(),
            ValueCustomizer.class.getName(),
            ServerCustomizer.class.getName(),

            ConfigurationSource.class.getName(),
            CustomConfigurationRegistry.class.getName(),
            CustomConfigurator.class.getName(),
            ConfiguratorCustomizer.class.getName(),
            ConfiguratorModuleModel.class.getName(),

            CommunicatorProxyRegistry.class.getName(),
            CommunicatorModuleModel.class.getName(),
            CommunicatorCustomizer.class.getName(),
            CommunicatorAction.class.getName(),
            CommunicatorProxy.class.getName(),
            CommunicatorProtocol.class.getName(),
            CommunicatorActionIdentifier.class.getName(),

            RsocketCommunicatorAction.class.getName(),
            CommunicationMode.class.getName(),
            RsocketSetupPayload.class.getName(),
            RsocketProtocol.class.getName(),

            StorageSpacesRegistry.class.getName(),
            StorageModuleModel.class.getName(),
            StorageCustomizer.class.getName()
    };
}
