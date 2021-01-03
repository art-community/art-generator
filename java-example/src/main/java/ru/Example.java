package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import reactor.core.publisher.*;
import static io.art.communicator.module.CommunicatorModule.communicator;
import static io.art.model.configurator.ModuleModelConfigurator.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(Example::run)
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }

    private static void run() {
        communicator(MyClient.class).myMethod1();
        communicator(MyClient.class).myMethod2(Request.builder().FBString("ART").build());
        communicator(MyClient.class).myMethod3(Mono.just(Request.builder().FBString("ART").build()));
        communicator(MyClient.class).myMethod4(Flux.just(Request.builder().FBString("ART").build()));
        System.out.println("method5:" + communicator(MyClient.class).myMethod5());
        System.out.println("method6:" +communicator(MyClient.class).myMethod6(Request.builder().FBString("ART").build()));
        System.out.println("method7:" +communicator(MyClient.class).myMethod7(Mono.just(Request.builder().FBString("ART").build())));
        System.out.println("method8:" +communicator(MyClient.class).myMethod8(Flux.just(Request.builder().FBString("ART").build())));
        System.out.println("method9:" +communicator(MyClient.class).myMethod9().block());
        System.out.println("method10:" +communicator(MyClient.class).myMethod10(Request.builder().FBString("ART").build()).block());
        System.out.println("method11:" +communicator(MyClient.class).myMethod11(Mono.just(Request.builder().FBString("ART").build())).block());
        System.out.println("method12:" +communicator(MyClient.class).myMethod12(Flux.just(Request.builder().FBString("ART").build())).block());
        System.out.println("method13:" +communicator(MyClient.class).myMethod13().blockFirst());
        System.out.println("method14:" +communicator(MyClient.class).myMethod14(Request.builder().FBString("ART").build()).blockFirst());
        System.out.println("method15:" +communicator(MyClient.class).myMethod15(Mono.just(Request.builder().FBString("ART").build())).blockFirst());
        System.out.println("method16:" +communicator(MyClient.class).myMethod16(Flux.just(Request.builder().FBString("ART").build())).blockFirst());
    }
}
