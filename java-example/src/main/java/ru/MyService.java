package ru;

import com.google.common.collect.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.factory.CollectionsFactory.*;

@UtilityClass
public class MyService {
    public Flux<Response> myMethod(Request request) {
        Model model = Model.builder()
                .stringValue(request.getStringValue() + ": from service")
                .build();
        return Flux.just(Response.builder()
                .stringValue("myModel2")
                .build());
    }

    public String myMethod2(Mono<Request> request) {
        request.subscribe(System.out::println);
        return "Test";
    }

    public String myMethod3() {
        return "Test";
    }
}
