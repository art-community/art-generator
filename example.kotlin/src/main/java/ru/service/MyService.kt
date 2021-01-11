package ru.service

import io.art.logging.LoggingModule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.communicator.MyClient
import ru.model.Request
import ru.model.Response
import java.util.concurrent.atomic.AtomicLong

class MyService : MyClient {
    override fun myMethod1() {
        LoggingModule.logger(MyService::class.java).info("myMethod1")
    }

    override fun myMethod100(request: String): String {
        println(counter.incrementAndGet())
        return "Mono.empty()"
    }

    private var counter = AtomicLong()

    override fun myMethod2(request: Request) {
        //logger(MyService.class).info("myMethod2");
    }

    override fun myMethod3(request: Mono<Request>) {
        LoggingModule.logger(MyService::class.java).info("myMethod3:" + request.block())
    }

    override fun myMethod4(request: Flux<Request>) {
        LoggingModule.logger(MyService::class.java).info("myMethod4:" + request.blockFirst())
    }

    override fun myMethod5(): Response {
        LoggingModule.logger(MyService::class.java).info("myMethod5")
        return Response()
    }

    override fun myMethod6(request: Request): Response {
        LoggingModule.logger(MyService::class.java).info("myMethod6:$request")
        return Response()
    }

    override fun myMethod7(request: Mono<Request>): Response {
        LoggingModule.logger(MyService::class.java).info("myMethod7:" + request.block())
        return Response()
    }

    override fun myMethod8(request: Flux<Request>): Response {
        val first = request.blockFirst()
        LoggingModule.logger(MyService::class.java).info("myMethod8:$first")
        return Response()
    }

    override fun myMethod9(): Mono<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod9")
        return Mono.just(Response())
    }

    override fun myMethod10(request: Request): Mono<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod10:$request")
        return Mono.just(Response())
    }

    override fun myMethod11(request: Mono<Request>): Mono<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod11:" + request.block())
        return Mono.just(Response())
    }

    override fun myMethod12(request: Flux<Request>): Mono<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod12:" + request.blockFirst())
        return Mono.just(Response())
    }

    override fun myMethod13(): Flux<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod13")
        return Flux.empty()
    }

    override fun myMethod14(request: Request): Flux<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod14:$request")
        return Flux.just(Response())
    }

    override fun myMethod15(request: Mono<Request>): Flux<Response> {
        LoggingModule.logger(MyService::class.java).info("myMethod15:" + request.block())
        return Flux.just(Response())
    }

    override fun myMethod16(request: Flux<Request>): Flux<Response> {
        request.subscribe { data: Request -> LoggingModule.logger(MyService::class.java).info("myMethod16:$data") }
        return Flux.empty()
    }
}
