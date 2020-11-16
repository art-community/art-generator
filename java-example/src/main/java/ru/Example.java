package ru;

import io.art.model.annotation.*;
import io.art.model.module.*;
import static io.art.model.module.ModuleModel.*;
import io.art.value.mapping.PrimitiveMapping;
import io.art.value.mapping.ArrayMapping;
import io.art.value.mapping.EntityMapping;
import io.art.value.mapping.BinaryMapping;
import io.art.value.immutable.ArrayValue;
import io.art.value.immutable.BinaryValue;
import io.art.value.immutable.Entity;
import io.art.value.immutable.Primitive;
import io.art.value.immutable.Value;
import io.art.value.mapper.ValueToModelMapper;
import io.art.value.mapper.ValueFromModelMapper;
import io.art.server.registry.ServiceSpecificationRegistry;
import io.art.server.specification.ServiceSpecification;
import io.art.server.specification.ServiceMethodSpecification;
import io.art.server.implementation.ServiceMethodImplementation;
import io.art.model.module.ModuleModel;
import io.art.server.decorator.ServiceLoggingDecorator;
import io.art.server.model.ServiceMethodIdentifier;
import io.art.core.constants.MethodProcessingMode;
import io.art.core.constants.MethodDecoratorScope;
import io.art.model.configurator.ConfiguratorModel;
import io.art.model.configurator.ValueConfiguratorModel;
import io.art.model.configurator.ServerConfiguratorModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.art.launcher.ModuleLauncher;
import io.art.value.factory.ArrayFactory;

@Module()
public class Example {

    public Example() {
        super();
    }

    @Configurator()
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(rsocket -> rsocket.to(MyService.class)));
    }
}
