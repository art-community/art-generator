package ru;

import io.art.communicator.module.*;
import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .configure(configurator -> configurator.configurations(MyConfig.class))
                .onLoad(() -> CommunicatorModule.communicator(MyClient.class).myMethod1())
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }
}
