package ru;

import io.art.model.annotation.*;
import io.art.model.module.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Example {
    @Configurator
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(rsocket -> rsocket.toClass(MyService.class)));
    }
}
