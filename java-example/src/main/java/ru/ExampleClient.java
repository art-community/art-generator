package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.core.magic.MagicRunner.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class ExampleClient {
    public static void main(String[] args) {
        doSomeMagic();
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(ExampleClient.class)
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)))
                .onLoad(() -> communicator(MyClient.class).myMethod100("test").block());
    }
}
