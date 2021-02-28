package ru

import io.art.kotlin.extensions.communicator.communicator
import io.art.kotlin.extensions.model.module
import io.art.kotlin.extensions.scheduler.scheduleDelayed
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ExampleProvider.provide
import ru.communicator.MyClient
import ru.configuration.MyConfig
import ru.model.Model
import ru.model.Request
import ru.service.MyService
import java.time.Duration.ofSeconds

object Example {
    @Configurator
    fun configure() = module {
        configure {
            configuration<MyConfig>()
        }

        serve {
            rsocket(MyService::class)
            http(MyService::class)
        }

        communicate {
            rsocket<MyClient>() to MyService::class
        }

        onLoad {
            scheduleDelayed(ofSeconds(10)) {
                communicator(MyClient::class) {
                    val request = Request()
                    val mono = Mono.just(request)
                    val flux = Flux.just(request)
                    myMethod1()
                    myMethod1()
                    myMethod1()
                    myMethod2(request)
                    myMethod2(request)
                    myMethod2(request)
                    myMethod3(mono)
                    myMethod4(flux)
                    myMethod5()
                    myMethod6(request)
                    myMethod7(mono)
                    myMethod8(flux)
                    myMethod9().block()
                    myMethod10(request).block()
                    myMethod11(mono).block()
                    myMethod12(flux).block()
                    myMethod13().blockFirst()
                    myMethod14(request).blockFirst()
                    myMethod15(mono).blockFirst()
                    myMethod16(flux).blockFirst()
                    myMethod17("test")
                }
            }
        }

        store{
            tarantool("routers") {
                space<Model, Int>("someSpace"){
                    searchBy("customIndex", Model::class.java)
                    sharded { model -> model.FBLong }
                }
                space<Model, Long>("anotherSpace"){
                    sharded { 99 }
                }
            }
        }

    }

    @JvmStatic
    fun main(vararg arguments: String) = launch(provide())
}
