package ru;

import io.art.communicator.module.*;
import io.art.core.factory.*;
import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.model.*;
import static io.art.configurator.module.ConfiguratorModule.*;
import static io.art.logging.LoggingModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;

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
                    model.setSimpleStream(ArrayFactory.fixedArrayOf("1").stream());
                    CommunicatorModule.communicator(MyClient.class).myMethod2(Request.builder().FModel(model).build());
                })
                .configure(configurator -> configurator.configuration(MyConfig.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::enableLogging))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }
}
