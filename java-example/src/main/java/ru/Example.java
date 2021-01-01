package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    public static void main(String[] args) {

    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class));
    }
}
