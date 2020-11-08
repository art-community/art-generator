package ru;

import lombok.experimental.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        Model model = Model.builder()
                .stringValue(request.getModel() + ": from service")
                .build();
        return Response.builder()
                .model(model)
                .stringValue("request was:" + request.getStringValue())
                .build();
    }
}
