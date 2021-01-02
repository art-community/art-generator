package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(() -> System.out.println(communicator(MyClient.class).myMethod6(Request.builder().FBString("ART rulezzzzzzzzzzzzzzzzzz").build())))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }
}
