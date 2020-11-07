package ru;

import io.art.model.annotation.*;
import io.art.model.module.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Example {
    public static void main(String[] args) {
        //Stub
    }

    @Configurator
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(rsocket -> rsocket.to(MyService.class)));
    }
}
