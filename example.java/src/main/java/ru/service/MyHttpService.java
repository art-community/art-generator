package ru.service;

import io.art.http.exception.HttpException;
import io.art.value.immutable.*;
import lombok.*;
import org.apache.logging.log4j.core.*;
import reactor.core.publisher.*;
import ru.model.*;

import static io.art.http.module.HttpModule.*;
import static io.art.logging.LoggingModule.logger;
import static ru.model.HttpResponse.httpResponse;


public class MyHttpService {
    private static final Logger logger = logger(MyHttpService.class);

    public HttpResponse method1(){
        logger.info("method1");
        return new HttpResponse("method1 ok");
    }

    public HttpResponse method2(String request){
        logger.info("method1");
        httpContext()
                .status(201)
                .header("header1", "value1");
        return new HttpResponse("method2 ok. Request was: " + request);
    }

    @SneakyThrows
    public HttpResponse exampleException(){
        logger.info("exampleException");
        throw new HttpExampleException("exampleException");
    }

    @SneakyThrows
    public HttpResponse illegalState(){
        logger.info("illegalState");
        throw new IllegalStateException("illegalState");
    }

    @SneakyThrows
    public HttpResponse throwable(){
        logger.info("throwable");
        throw new Throwable("throwable");
    }

    public HttpResponse websocket(HttpResponse req){
        logger.info("websocket");
        return req;
    }

    public Flux<HttpResponse> wsFlux(Flux<HttpResponse> req){
        logger.info("wsFlux");
        return req;
    }
}
