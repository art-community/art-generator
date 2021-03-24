package ru.service;

import io.art.http.module.*;
import ru.model.*;

import static io.art.http.module.HttpModule.httpModuleState;
import static io.art.logging.LoggingModule.logger;

public class MyHttpService {
    public HttpResponse method1(){
        logger(MyHttpService.class).info("method1");
        return new HttpResponse("method1 ok");
    }

    public HttpResponse method2(String request){
        logger(MyHttpService.class).info("method1");
        httpModuleState().setStatus(205);
        return new HttpResponse("method2 ok. Request was: " + request);
    }
}
