package ru;

import io.art.communicator.module.*;
import io.art.core.source.*;
import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.configuration.*;
import ru.model.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.configurator.module.ConfiguratorModule.*;
import static io.art.core.factory.ArrayFactory.arrayOf;
import static io.art.core.magic.MagicRunner.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import static io.art.server.constants.ServerModuleConstants.RequestValidationPolicy.*;

public class Example {
    public static void main(String[] args) {
        doSomeMagic();
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .configure(configurator -> configurator.configuration("config", MyConfig.class).configuration(MyConfig.class).configuration(MyConfigParent.class))
                .serve(server -> server.rsocket(MyService.class, RsocketServiceModelConfigurator::logging, rsocket -> rsocket.validation(NOT_NULL)))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)))
                .onLoad(() -> {
                    MyConfig config = configuration("config", MyConfig.class);
                    System.out.println(config);
                    System.out.println(configuration("config").getNested("SIM").asMap(sim -> sim.asArray(NestedConfiguration::asString)));
                    communicator(MyClient.class).myMethod2(Request.builder().FABString(arrayOf("test", "test")).build());
                })
                .onLoad(() -> System.out.println(configuration(MyConfigParent.class)))
                .onLoad(() -> System.out.println(configuration(MyConfig.class)));
    }
}
