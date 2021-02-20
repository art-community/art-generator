package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import reactor.core.publisher.*;
import ru.communicator.*;
import ru.configuration.*;
import ru.model.*;
import ru.service.*;
import static io.art.communicator.module.CommunicatorModule.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.launcher.ModuleLauncher.*;
import static io.art.core.constants.EmptyFunctions.emptyFunction;
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
                .onLoad(() -> scheduleFixedRate(() -> {
                    MyClient communicator = communicator(MyClient.class);
                    Request request = Request.builder().build();
                    Mono<Request> mono = Mono.just(request);
                    Flux<Request> flux = Flux.just(request);
                    communicator.myMethod1();
                    communicator.myMethod1();
                    communicator.myMethod1();
                    communicator.myMethod2(request);
                    communicator.myMethod2(request);
                    communicator.myMethod2(request);
                    communicator.myMethod3(mono);
                    communicator.myMethod4(flux);
                    communicator.myMethod5();
                    communicator.myMethod6(request);
                    communicator.myMethod7(mono);
                    communicator.myMethod8(flux);
                    communicator.myMethod9().block();
                    communicator.myMethod10(request).block();
                    communicator.myMethod11(mono).block();
                    communicator.myMethod12(flux).block();
                    communicator.myMethod13().blockFirst();
                    communicator.myMethod14(request).blockFirst();
                    communicator.myMethod15(mono).blockFirst();
                    communicator.myMethod16(flux).blockFirst();
                    communicator.myMethod17("test");
                    communicator.myMethod18(fixedArrayOf("test"));
                    communicator.myMethod19(GenericModel.<String, GenericTypeParameter<String>>builder().build());
                }, ofSeconds(30)))
                .store(storage -> storage.tarantool("example", StorageExampleModel.class, Integer.class, space -> space
                        .cluster("routers")
                        .sharded(ignored -> 99L)
                        .searchBy("secondary", Integer.class)));
    }
}
