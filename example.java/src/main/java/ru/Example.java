package ru;

import io.art.launcher.*;
import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.configuration.*;
import ru.model.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    public static void main(String[] args) {
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .value(value -> value.model(Request.class))
                .configure(configurator -> configurator.configuration(MyConfig.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::logging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)))
                .onLoad(() -> communicator(MyClient.class).myMethod2(Request.builder().build()));
    }
}
