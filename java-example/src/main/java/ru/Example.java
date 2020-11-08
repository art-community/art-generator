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
import io.art.value.registry.MappersRegistry;
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
import io.art.launcher.ModuleLauncher;
import ru.MyService;
import ru.Request;

@Module()
public class Example {

    public Example() {
        super();
    }

    @Configurator()
    public static ModuleModel configure() {
        return module().serve((server)->server.rsocket((rsocket)->rsocket.to(MyService.class)));
    }

    public static class ExampleProvider {
        private static final ModuleModel model = decorate(Example.configure());

        private static MappersRegistry mappers() {
            MappersRegistry registry = new MappersRegistry();
            registry.register(Model.class, (Entity value)->Model.builder().models(value.map("models", EntityMapping.toMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getToModel(Model.class)))).stringValue(value.map("stringValue", PrimitiveMapping.toString)).build());
            registry.register(Model.class, (Model model)->Entity.entityBuilder().lazyPut("models", ()->model.getModels(), EntityMapping.fromMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getFromModel(Model.class))).lazyPut("stringValue", ()->model.getStringValue(), PrimitiveMapping.fromString).build());
            registry.register(Request.class, (Entity value)->Request.builder().intValue(value.map("intValue", PrimitiveMapping.toInt)).stringValue(value.map("stringValue", PrimitiveMapping.toString)).model(value.map("model", registry.getToModel(Model.class))).build());
            registry.register(Request.class, (Request model)->Entity.entityBuilder().lazyPut("intValue", ()->model.getIntValue(), PrimitiveMapping.fromInt).lazyPut("stringValue", ()->model.getStringValue(), PrimitiveMapping.fromString).lazyPut("model", ()->model.getModel(), registry.getFromModel(Model.class)).build());
            registry.register(Model.class, (Entity value)->Model.builder().models(value.map("models", EntityMapping.toMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getToModel(Model.class)))).stringValue(value.map("stringValue", PrimitiveMapping.toString)).build());
            registry.register(Model.class, (Model model)->Entity.entityBuilder().lazyPut("models", ()->model.getModels(), EntityMapping.fromMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getFromModel(Model.class))).lazyPut("stringValue", ()->model.getStringValue(), PrimitiveMapping.fromString).build());
            registry.register(Response.class, (Entity value)->Response.builder().arrayModels(value.map("arrayModels", ArrayMapping.toArray(registry.getToModel(Model.class)))).collectionModels(value.map("collectionModels", ArrayMapping.toCollection(ArrayMapping.toCollection(registry.getToModel(Model.class))))).collectionArrayModels(value.map("collectionArrayModels", ArrayMapping.toCollection(ArrayMapping.toArray(registry.getToModel(Model.class))))).stringModels(value.map("stringModels", EntityMapping.toMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getToModel(Model.class)))).intModels(value.map("intModels", EntityMapping.toMap(PrimitiveMapping.toInt, PrimitiveMapping.fromInt, registry.getToModel(Model.class)))).stringValue(value.map("stringValue", PrimitiveMapping.toString)).build());
            registry.register(Response.class, (Response model)->Entity.entityBuilder().lazyPut("arrayModels", ()->model.getArrayModels(), ArrayMapping.fromArray(registry.getFromModel(Model.class))).lazyPut("collectionModels", ()->model.getCollectionModels(), ArrayMapping.fromCollection(ArrayMapping.fromCollection(registry.getFromModel(Model.class)))).lazyPut("collectionArrayModels", ()->model.getCollectionArrayModels(), ArrayMapping.fromCollection(ArrayMapping.fromArray(registry.getFromModel(Model.class)))).lazyPut("stringModels", ()->model.getStringModels(), EntityMapping.fromMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, registry.getFromModel(Model.class))).lazyPut("intModels", ()->model.getIntModels(), EntityMapping.fromMap(PrimitiveMapping.toInt, PrimitiveMapping.fromInt, registry.getFromModel(Model.class))).lazyPut("stringValue", ()->model.getStringValue(), PrimitiveMapping.fromString).build());
            return registry;
        }

        private static ServiceSpecificationRegistry services() {
            ServiceSpecificationRegistry registry = new ServiceSpecificationRegistry();
            registry.register("MyService", ServiceSpecification.builder().serviceId("MyService").method("myMethod", ServiceMethodSpecification.builder().serviceId("MyService").methodId("myMethod").inputMode(MethodProcessingMode.BLOCKING).outputMode(MethodProcessingMode.BLOCKING).inputMapper(mappers().getToModel(Request.class)).outputMapper(mappers().getFromModel(Response.class)).inputDecorator(new ServiceLoggingDecorator(ServiceMethodIdentifier.serviceMethod("MyService", "myMethod"), MethodDecoratorScope.INPUT)).outputDecorator(new ServiceLoggingDecorator(ServiceMethodIdentifier.serviceMethod("MyService", "myMethod"), MethodDecoratorScope.OUTPUT)).implementation(ServiceMethodImplementation.handler(MyService::myMethod, "MyService", "myMethod")).build()).build());
            return registry;
        }

        public static ModuleModel decorate(ModuleModel model) {
            return model.configure((ConfiguratorModel configurator)->configurator.value((ValueConfiguratorModel value)->value.registry(ExampleProvider.mappers())).server((ServerConfiguratorModel server)->server.registry(ExampleProvider.services())));
        }
    }

    public static void main(String[] arguments) {
        ModuleLauncher.launch(ExampleProvider.model);
    }
}
