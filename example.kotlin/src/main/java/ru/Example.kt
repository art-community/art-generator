package ru

import io.art.communicator.module.CommunicatorModule
import io.art.kotlin.extensions.model.module
import io.art.kotlin.extensions.scheduler.scheduleDelayed
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ExampleProvider.provide
import ru.communicator.MyClient
import ru.configuration.MyConfig
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
            rsocket<MyService>()
            http<MyService>()
        }

        communicate {
            rsocket<MyClient>() to MyService
        }

        onLoad {
            scheduleDelayed(ofSeconds(10)) {
                val communicator = CommunicatorModule.communicator(MyClient::class.java)
                val request = Request()
                val mono = Mono.just(request)
                val flux = Flux.just(request)
                communicator.myMethod1()
                communicator.myMethod1()
                communicator.myMethod1()
                communicator.myMethod2(request)
                communicator.myMethod2(request)
                communicator.myMethod2(request)
                communicator.myMethod3(mono)
                communicator.myMethod4(flux)
                communicator.myMethod5()
                communicator.myMethod6(request)
                communicator.myMethod7(mono)
                communicator.myMethod8(flux)
                communicator.myMethod9().block()
                communicator.myMethod10(request).block()
                communicator.myMethod11(mono).block()
                communicator.myMethod12(flux).block()
                communicator.myMethod13().blockFirst()
                communicator.myMethod14(request).blockFirst()
                communicator.myMethod15(mono).blockFirst()
                communicator.myMethod16(flux).blockFirst()
                communicator.myMethod17("test")
            }
        }
    }

    @JvmStatic
    fun main(vararg arguments: String) = launch(provide())
}
