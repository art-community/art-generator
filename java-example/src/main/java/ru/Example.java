package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(() -> {
                    communicator(MyClient.class).myMethod6(null);
                    communicator(MyClient.class).myMethod7(null);
                    communicator(MyClient.class).myMethod8(null);
                })
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }

}
