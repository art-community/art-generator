package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.service.*;
import static io.art.core.magic.MagicRunner.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class ExampleServer {
    public static void main(String[] args) {
        doSomeMagic();
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(ExampleServer.class).serve(server -> server.rsocket(MyService.class));
    }
}
