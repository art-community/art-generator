package ru;

import io.art.model.annotation.*;
import io.art.model.customizer.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Example {
    @Configurator
    public static ModuleCustomizer configure() {
        return module()
                .serve(server -> server
                        .rsocket(rsocket -> rsocket.to(MyService.class, ServiceCustomizer::enableLogging))
                );
    }
}
