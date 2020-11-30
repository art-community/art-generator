package ru;

import io.art.model.annotation.*;
import io.art.model.modeler.*;
import static io.art.model.implementation.ModuleModel.*;

@Module
public class Example {
    @Modeler
    public static ModuleModeler model() {
        return module().serve(server -> server.rsocket(rsocket -> rsocket.to(MyService.class, ServiceModeler::enableLogging)));
    }
}
