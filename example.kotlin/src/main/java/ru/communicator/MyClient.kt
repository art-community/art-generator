package ru.communicator

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.model.Request
import ru.model.Response

interface MyClient {
    fun myMethod1()
    fun myMethod2(request: Request)
    fun myMethod3(request: Mono<Request>)
    fun myMethod4(request: Flux<Request>)
    fun myMethod5(): Response
    fun myMethod6(request: Request): Response
    fun myMethod7(request: Mono<Request>): Response
    fun myMethod8(request: Flux<Request>): Response
    fun myMethod9(): Mono<Response>
    fun myMethod10(request: Request): Mono<Response>
    fun myMethod11(request: Mono<Request>): Mono<Response>
    fun myMethod12(request: Flux<Request>): Mono<Response>
    fun myMethod13(): Flux<Response>
    fun myMethod14(request: Request): Flux<Response>
    fun myMethod15(request: Mono<Request>): Flux<Response>
    fun myMethod16(request: Flux<Request>): Flux<Response>
    fun myMethod17(request: String): String
    fun myMethod18(request: List<List<List<String>>>): String
    fun myMethod19(request: List<Array<List<String>>>): String
}
