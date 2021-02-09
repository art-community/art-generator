package ru.service

import io.art.kotlin.communicator
import io.art.kotlin.logger
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.communicator.MyClient
import ru.model.Request
import ru.model.Response

object MyService : MyClient {
    override fun myMethod1() = logger().info("myMethod1")
    override fun myMethod2(request: Request) = communicator<MyClient>().myMethod6(Request()).run { logger().info("myMethod2") }
    override fun myMethod3(request: Mono<Request>) = logger().info("myMethod3:${request.block()}")
    override fun myMethod4(request: Flux<Request>) = logger().info("myMethod4:${request.blockFirst()}")
    override fun myMethod5(): Response = Response().also { logger().info("myMethod5") }
    override fun myMethod6(request: Request) = Response().also { logger().info("myMethod6") }
    override fun myMethod7(request: Mono<Request>) = Response().also { logger().info("myMethod7:${request.block()}") }
    override fun myMethod8(request: Flux<Request>) = Response().also { logger().info("myMethod8:${request.blockFirst()}") }
    override fun myMethod9(): Mono<Response> = Mono.empty<Response>().also { logger().info("myMethod9") }
    override fun myMethod10(request: Request): Mono<Response> = Mono.empty<Response>().also { logger().info("myMethod10:$request") }
    override fun myMethod11(request: Mono<Request>): Mono<Response> = Mono.empty<Response>().also { logger().info("myMethod11:${request.block()}") }
    override fun myMethod12(request: Flux<Request>): Mono<Response> = Mono.empty<Response>().also { logger().info("myMethod12:${request.blockFirst()}") }
    override fun myMethod13(): Flux<Response> = Flux.empty<Response>().also { logger().info("myMethod13") }
    override fun myMethod14(request: Request): Flux<Response> = Flux.empty<Response>().also { logger().info("myMethod14:$request") }
    override fun myMethod15(request: Mono<Request>): Flux<Response> = Flux.empty<Response>().also { logger().info("myMethod15:${request.block()}") }
    override fun myMethod16(request: Flux<Request>): Flux<Response> = Flux.empty<Response>().also { request.subscribe { data: Request -> logger().info("myMethod16:$data") } }
    override fun myMethod17(request: String) = "myMethod17:$request"
    override fun myMethod18(request: List<List<List<String>>>) = "myMethod18:$request"
    override fun myMethod19(request: List<Array<List<String>>>) = "myMethod19:$request"
}
