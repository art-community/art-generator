@file:JvmName("Example")

package ru

import io.art.communicator.module.CommunicatorModule.communicator
import io.art.json.descriptor.JsonReader
import io.art.kotlin.module
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
import io.art.model.configurator.ModuleModelConfigurator
import io.art.model.configurator.RsocketServiceModelConfigurator
import io.art.value.module.ValueModule
import ru.ExampleProvider.provide
import ru.communicator.MyClient
import ru.model.Request
import ru.service.MyService


fun main() = launch(provide())

@Configurator
fun configure(): ModuleModelConfigurator = module("Example") { value {} }
        .value { value -> value.model(Request::class.java) }
        .serve { server ->
            server.rsocket(MyService::class.java, RsocketServiceModelConfigurator::logging)
        }
        .communicate { communicator ->
            communicator.rsocket(MyClient::class.java, { client -> client.to(MyService::class.java) })
        }
        .onLoad {
            println(ValueModule.model(Request::class.java, JsonReader.readJson("{}")))
        }
