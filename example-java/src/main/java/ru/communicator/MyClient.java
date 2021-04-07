package ru.communicator;

import reactor.core.publisher.*;
import ru.model.*;
import java.util.*;

public interface MyClient {
    void myMethod1();

    String myMethod17(String request);

    void myMethod2(Request request);

    void myMethod3(Mono<Request> request);

    void myMethod4(Flux<Request> request);

    Response myMethod5();

    Response myMethod6(Request request);

    Response myMethod7(Mono<Request> request);

    Response myMethod8(Flux<Request> request);

    Mono<Response> myMethod9();

    Mono<Response> myMethod10(Request request);

    Mono<Response> myMethod11(Mono<Request> request);

    Mono<Response> myMethod12(Flux<Request> request);

    Flux<Response> myMethod13();

    Flux<Response> myMethod14(Request request);

    Flux<Response> myMethod15(Mono<Request> request);

    Flux<Response> myMethod16(Flux<Request> request);

    Set<String> myMethod18(List<String> request);

    GenericModel<String, GenericTypeParameter<String>> myMethod19(GenericModel<String, GenericTypeParameter<String>> request);
}
