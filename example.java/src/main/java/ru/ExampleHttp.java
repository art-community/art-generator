package ru;

import io.art.model.annotation.*;
import io.art.model.configurator.*;
import io.netty.handler.codec.http.*;
import ru.model.*;
import ru.model.HttpResponse;
import ru.service.*;

import static io.art.core.constants.NetworkConstants.LOCALHOST;
import static io.art.http.module.HttpModule.httpContext;
import static io.art.launcher.ModuleLauncher.*;
import static io.art.model.configurator.ModuleModelConfigurator.*;
import static ru.ExampleHttpProvider.*;
import static ru.model.HttpResponse.httpResponse;

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
                                .host("0.0.0.0")
                                .logging(true)
                                .compress()
                                .route("/my-http-service", MyHttpService.class, route->route
                                        .logging(true)
                                        .get("method1", method -> method
                                                .pathName("{id}/1"))
                                        .post("method2", method -> method
                                                .pathName("2"))
                                        .websocket("websocket", method -> method
                                                .logging(true))
                                        .websocket("wsFlux")
                                        .exceptions(e -> e
                                                .mapException(HttpExampleException.class, 404, () -> httpResponse("httpExampleException"))
                                                .mapException(IllegalStateException.class, exception -> {
                                                    httpContext().status(405);
                                                    return httpResponse(exception.getMessage());
                                                })
                                                .mapException(Throwable.class, HttpResponseStatus.CONFLICT)
                                        )
                                )
                        )
                ); 
    }
}
