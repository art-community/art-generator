package ru;

import lombok.experimental.*;
import static io.art.core.factory.CollectionsFactory.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        Model model = Model.builder()
                .stringValue(request.getModel() + ": from service")
                .models(mapOf("m", Model.builder().stringValue("test").build()))
                .build();
        return Response.builder().stringModel("myModel", model)
                .stringModel("myModel2", model)
                .intModel(46347, model)
                .stringValue("request was:" + request.getStringValue())
                .build();
    }
}
