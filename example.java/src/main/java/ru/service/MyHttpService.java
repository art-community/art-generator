package ru.service;

import ru.model.*;

import static io.art.logging.LoggingModule.logger;

public class MyHttpService {
    public HttpResponse method1(){
        logger(MyHttpService.class).info("method1");
        return new HttpResponse("method1 ok");
    }
}
