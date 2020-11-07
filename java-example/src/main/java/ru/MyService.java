package ru;

import lombok.experimental.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        return Response.builder().stringValue("request was:" + request.getStringValue()).model(request.getModel()).build();
    }
}
