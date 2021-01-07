package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.configurator.module.ConfiguratorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .configure(configurator -> configurator.configuration("config", MyConfig.class).configuration(MyConfig.class).configuration(MyConfigParent.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)))
                .onLoad(() -> System.out.println(configuration("config", MyConfig.class)))
                .onLoad(() -> System.out.println(configuration(MyConfigParent.class)))
                .onLoad(() -> System.out.println(configuration(MyConfig.class)));
    }
}
