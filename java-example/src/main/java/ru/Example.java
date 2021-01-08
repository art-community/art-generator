package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.configuration.*;
import ru.model.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.magic.MagicRunner.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import static io.art.scheduler.manager.SchedulersManager.*;
import static io.art.server.constants.ServerModuleConstants.RequestValidationPolicy.*;
import static java.time.Duration.*;

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
                .onLoad(() -> scheduleFixedRate(() -> communicator(MyClient.class).myMethod2(Request.builder()
                                .FABString(arrayOf("test", "test"))
                                .build()),
                        ofSeconds(30))
                );
    }
}
