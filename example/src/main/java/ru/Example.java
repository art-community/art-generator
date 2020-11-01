package ru;

import io.art.model.annotation.*;
import io.art.model.module.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Example {
    //    public static void main(String[] args) {
//        launch(ExampleConfigurator.model);
//    }
//
    @Configurator
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(rsocket -> rsocket.toClass(MyService.class)));
//                .configure(configurator -> configurator
//                        .value(value -> value.registry(ExampleConfigurator.mappers()))
//                        .server(server -> server.registry(ExampleConfigurator.services())));
    }
//
//    private static class ExampleConfigurator {
//        private final static ModuleModel model = Example.configure();
//
//        private static MappersRegistry mappers() {
//            return null;
//        }
//
//        private static ServiceSpecificationRegistry services() {
//            MappersRegistry mappers = mappers();
//            ServiceSpecificationRegistry registry = new ServiceSpecificationRegistry();
//            ServiceSpecification.ServiceSpecificationBuilder myServiceBuilder = ServiceSpecification.builder();
//            myServiceBuilder.serviceId("myService");
//            myServiceBuilder.method("myMethod", ServiceMethodSpecification.builder()
//                    .serviceId("myService")
//                    .methodId("myMethod")
//                    .inputMode(MethodProcessingMode.BLOCKING)
//                    .outputMode(MethodProcessingMode.BLOCKING)
//                    .inputMapper(mappers.getToModel(Request.class))
//                    .outputMapper(mappers.getFromModel(Request.class))
//                    .implementation(ServiceMethodImplementation.handler(MyService::myMethod, "myService", "myMethod"))
//                    .build());
//            return registry;
//        }
//    }
}
