package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import org.apache.logging.log4j.*;
import reactor.core.publisher.*;
import reactor.core.scheduler.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.logging.LoggingModule.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import java.time.*;

public class Example {
    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(Example.class)
                .onLoad(Example::run)
                .serve(server -> server.rsocket(MyService.class))
                .communicate(communicator -> communicator.rsocket(MyClient.class, client -> client.to(MyService.class)));
    }

    private static void run() {
        Logger logger = logger(Example.class);
        MyClient myClient = communicator(MyClient.class);
        myClient.myMethod1();
        myClient.myMethod2(Request.builder().FBString("ART").build());
        myClient.myMethod3(Mono.just(Request.builder().FBString("ART").build()));
        myClient.myMethod4(Flux.just(Request.builder().FBString("ART").build()));
        logger.info("method5:" + myClient.myMethod5());
        logger.info("method6:" + myClient.myMethod6(Request.builder().FBString("ART").build()));
        logger.info("method7:" + myClient.myMethod7(Mono.just(Request.builder().FBString("ART").build())));
        logger.info("method8:" + myClient.myMethod8(Flux.just(Request.builder().FBString("ART").build())));
        logger.info("method9:" + myClient.myMethod9().block());
        logger.info("method10:" + myClient.myMethod10(Request.builder().FBString("ART").build()).block());
        logger.info("method11:" + myClient.myMethod11(Mono.just(Request.builder().FBString("ART").build())).block());
        logger.info("method12:" + myClient.myMethod12(Flux.just(Request.builder().FBString("ART").build())).block());
        myClient.myMethod13().subscribe(data -> logger.info("method13:" + data));
        logger.info("method14:" + myClient.myMethod14(Request.builder().FBString("ART").build()).blockFirst());
        logger.info("method15:" + myClient.myMethod15(Mono.just(Request.builder().FBString("ART").build())).blockFirst());
        myClient.myMethod16(Flux.interval(Duration.ofSeconds(1), Schedulers.newParallel("myMethod16-logging")).map(value -> Request.builder().FBString("ART").build())).subscribe(data -> logger.info("method16:" + data));
    }
}
