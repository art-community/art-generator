package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import io.netty.handler.codec.http.*;
import ru.service.*;

import static io.art.core.constants.NetworkConstants.LOCALHOST;
import static io.art.launcher.ModuleLauncher.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import static ru.ExampleHttpProvider.*;

public class ExampleHttp {

    public static void main(String[] args) {
        launch(provide());
    }

    @Configurator
    public static ModuleModelConfigurator configure() {
        return module(ExampleHttp.class)
                .serve(server -> server
                        .http(http -> http
                                .port(8080)
                                .host(LOCALHOST)
                                .compress()
                                .logging(true)
                                .route("/my-service", MyService.class, route -> route
                                        .method("myMethod9", method -> method
                                                .pathName("9")
                                                .httpMethod(HttpMethod.POST)))
                                .route("/my-http-service", MyHttpService.class, route->route
                                        .logging(true)
                                        .method("method1", method -> method
                                                .pathName("1")
                                                .httpMethod(HttpMethod.GET)
                                        )
                                )
                        )
                );
    }
}
