package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .serve(server -> server.rsocket(rsocket -> rsocket.to(MyService.class, ServiceModelConfigurator::enableLogging)))
                .communicate(communicator -> communicator.rsocket(MyClient.class));
    }
}
