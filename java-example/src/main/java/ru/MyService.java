package ru;

import io.art.core.factory.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.factory.CollectionsFactory.fixedArrayOf;
import static io.art.core.factory.CollectionsFactory.mapOf;
import java.util.*;

@UtilityClass
public class MyService {
    public Flux<Response> myMethod(Request request) {
        Model model = Model.builder().FABString(new String[]{"test1", "test2"}).build();
        return Flux.just(Response.builder().FModel(model).FBString(request.getFBString()).build());
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
