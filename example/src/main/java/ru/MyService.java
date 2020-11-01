package ru;

import lombok.experimental.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        return Response.builder().value("request was:" + request.getValue()).build();
    }
}
