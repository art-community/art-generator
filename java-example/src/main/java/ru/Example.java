package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import java.time.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(Example::onLoad)
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }

    private static void onLoad() {
        MyClient communicator = communicator(MyClient.class);
        Response response = communicator.myMethod6(Request.builder().FBString("ART").build());
        communicator.myMethod13().delayElements(Duration.ofSeconds(5)).subscribe(System.out::println);
        System.out.println(response);
    }
}
