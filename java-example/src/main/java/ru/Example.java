package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(() -> communicator(MyClient.class).myMethod1())
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }

}
