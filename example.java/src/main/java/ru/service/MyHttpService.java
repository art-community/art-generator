package ru.service;

import ru.model.*;

import static io.art.http.module.HttpModule.*;
import static io.art.logging.LoggingModule.logger;

public class MyHttpService {
    public HttpResponse method1(){
        logger(MyHttpService.class).info("method1");
        return new HttpResponse("method1 ok");
    }

    public HttpResponse method2(String request){
        logger(MyHttpService.class).info("method1");
        httpContext()
                .status(201)
                .header("header1", "value1");
        return new HttpResponse("method2 ok. Request was: " + request);
    }
}
