package ru;

import lombok.experimental.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        System.out.println(request);
        Response response = Response.builder().output(request.getInput() + "_output").build();
        System.out.println(response);
        return response;
    }
}
