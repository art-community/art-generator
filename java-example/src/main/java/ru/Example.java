package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.configurator.module.ConfiguratorModule.*;
import static io.art.logging.LoggingModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(() -> {
                    MyConfig myConfig = configuration(MyConfig.class);
                    logger(Example.class).info("value: " + myConfig.getInteger());
                    logger(Example.class).info("value: " + myConfig.getValue());
                    logger(Example.class).info("nested: " + myConfig.getNested().getNested().getValue());
                })
                .configure(configurator -> configurator.configuration(MyConfig.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }
}
