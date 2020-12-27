package ru;

import reactor.core.publisher.*;

public interface MyClient {
    void myMethod1();

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
}
