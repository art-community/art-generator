package ru;

import lombok.experimental.*;

@UtilityClass
public class MyService {
    public Response myMethod(Request request) {
        Model model = Model.builder()
                .stringValue(request.getModel() + ": from service")
                .build();
        return Response.builder()
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .listModel(model)
                .stringModel("myModel", model)
                .stringModel("myModel2", model)
                .intModel(46347, model)
                .stringValue("request was:" + request.getStringValue())
                .build();
    }
}
