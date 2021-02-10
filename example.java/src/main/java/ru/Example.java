package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import ru.communicator.*;
import ru.configuration.*;
import ru.model.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.launcher.ModuleLauncher.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import static io.art.scheduler.manager.SchedulersManager.*;
import static java.time.Duration.*;
import static ru.ExampleProvider.*;

public class Example {
    public static void main(String[] args) {
        launch(provide());
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .value(value -> value.mapping(Request.class))
                .configure(configurator -> configurator.configuration(MyConfig.class))
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client
                        .to(MyService.class)
                        .decorate((id, communicatorActionBuilder) -> communicatorActionBuilder)
                        .implement((id, rsocketCommunicatorActionBuilder) -> rsocketCommunicatorActionBuilder)))
                .onLoad(() -> scheduleFixedRate(() -> communicator(MyClient.class).myMethod2(Request.builder().build()), ofSeconds(30)));
    }
}
