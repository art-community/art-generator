package ru;

import lombok.experimental.*;
import reactor.core.publisher.*;

@UtilityClass
public class MyService {
    public Flux<Response> myMethod(Request request) {
        Model model = Model.builder().build();
        return Flux.just(Response.builder().build());
    }

    public String myMethod2(Mono<Request> request) {
        request.subscribe(System.out::println);
        return "Test";
    }

    public String myMethod3() {
        return "Test";
    }

    public String myMethod4(String req) {
        return req;
    }
}
