package ru.service

import io.art.kotlin.logger
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.communicator.MyClient
import ru.model.Request
import ru.model.Response
import java.util.concurrent.atomic.AtomicLong

object MyService : MyClient {
    override fun myMethod1() {
        logger().info("myMethod1")
    }

    override fun myMethod100(request: String): String {
        println(counter.incrementAndGet())
        return "Mono.empty()"
    }

    private var counter = AtomicLong()

    override fun myMethod2(request: Request) {
        logger().info("myMethod2")
    }

    override fun myMethod3(request: Mono<Request>) {
        logger().info("myMethod3:" + request.block())
    }

    override fun myMethod4(request: Flux<Request>) {
        logger().info("myMethod4:" + request.blockFirst())
    }

    override fun myMethod5(): Response {
        logger().info("myMethod5")
        error("error")
    }

    override fun myMethod6(request: Request): Response {
        logger().info("myMethod6:$request")
        error("error")
    }

    override fun myMethod7(request: Mono<Request>): Response {
        logger().info("myMethod7:" + request.block())
        error("error")
    }

    override fun myMethod8(request: Flux<Request>): Response {
        val first = request.blockFirst()
        logger().info("myMethod8:$first")
        error("error")
    }

    override fun myMethod9(): Mono<Response> {
        logger().info("myMethod9")
        return Mono.empty()
    }

    override fun myMethod10(request: Request): Mono<Response> {
        logger().info("myMethod10:$request")
        return Mono.empty()
    }

    override fun myMethod11(request: Mono<Request>): Mono<Response> {
        logger().info("myMethod11:" + request.block())
        return Mono.empty()
    }

    override fun myMethod12(request: Flux<Request>): Mono<Response> {
        logger().info("myMethod12:" + request.blockFirst())
        return Mono.empty()
    }

    override fun myMethod13(): Flux<Response> {
        logger().info("myMethod13")
        return Flux.empty()
    }

    override fun myMethod14(request: Request): Flux<Response> {
        logger().info("myMethod14:$request")
        return Flux.empty()
    }

    override fun myMethod15(request: Mono<Request>): Flux<Response> {
        logger().info("myMethod15:" + request.block())
        return Flux.empty()
    }

    override fun myMethod16(request: Flux<Request>): Flux<Response> {
        request.subscribe { data: Request -> logger().info("myMethod16:$data") }
        return Flux.empty()
    }
}
