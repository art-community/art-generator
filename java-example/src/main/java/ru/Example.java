package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.core.magic.MagicRunner.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    public static void main(String[] args) {
        doSomeMagic();
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)))
                .onLoad(() -> communicator(MyClient.class).myMethod100("test").block());
    }
}
