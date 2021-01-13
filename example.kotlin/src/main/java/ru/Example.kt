@file:JvmName("ru.Example")

package ru

import io.art.communicator.module.CommunicatorModule.communicator
import io.art.kotlin.module
import io.art.model.annotation.Configurator
import io.art.model.configurator.RsocketServiceModelConfigurator
import ru.communicator.MyClient
import ru.model.Request
import ru.service.MyService


object Example {
    @JvmStatic
    fun main(vararg args: String) {
    }

    @JvmStatic
    @Configurator
    fun configure() = module(Example::class) { value {} }
            .serve { server ->
                server.rsocket(MyService::class.java, RsocketServiceModelConfigurator::logging)
            }
            .communicate { communicator ->
                communicator.rsocket(MyClient::class.java, { client -> client.to(MyService::class.java) })
            }
            .onLoad {
                communicator(MyClient::class.java).myMethod2(Request())
            }
}
