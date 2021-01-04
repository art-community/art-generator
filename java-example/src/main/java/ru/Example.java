package ru;

import io.art.communicator.module.*;
import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.model.*;
import static io.art.configurator.module.ConfiguratorModule.*;
import static io.art.logging.LoggingModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import java.util.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(() -> {
                    MyConfig myConfig = configuration(MyConfig.class);
                    logger(Example.class).info("value: " + myConfig.getInteger());
                    logger(Example.class).info("value: " + myConfig.getValue());
                    logger(Example.class).info("nested: " + myConfig.getNested().getNested().getValue());
                    Model model = new Model();
                    CommunicatorModule.communicator(MyClient.class).myMethod2(Request.builder().FModel(model).build());
                })
                .configure(configurator -> configurator.configuration(MyConfig.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }
}
