@file:JvmName("ru.Example")

package ru

import io.art.communicator.module.CommunicatorModule.communicator
import io.art.launcher.ModuleLauncher
import io.art.model.annotation.Configurator
import io.art.model.configurator.ModuleModelConfigurator
import io.art.model.configurator.ModuleModelConfigurator.module
import io.art.model.configurator.RsocketCommunicatorModelConfigurator
import io.art.model.configurator.RsocketServiceModelConfigurator
import io.art.model.implementation.module.ModuleModel
import ru.communicator.MyClient
import ru.model.Request
import ru.service.MyService
import java.util.function.UnaryOperator


object Example {
    @JvmStatic
    fun main(args: Array<String>) = ModuleLauncher.launch(Class.forName(Example::class.qualifiedName + "Provider").getDeclaredMethod("provide").invoke(null) as ModuleModel)

    @JvmStatic
    @Configurator
    fun configure(): ModuleModelConfigurator = module(Example::class.java)
            .serve { server ->
                server.rsocket(MyService::class.java, UnaryOperator { obj: RsocketServiceModelConfigurator -> obj.logging() })
            }
            .communicate { communicator ->
                communicator.rsocket(MyClient::class.java, UnaryOperator { client: RsocketCommunicatorModelConfigurator -> client.to(MyService::class.java) })
            }
            .onLoad {
                communicator(MyClient::class.java).myMethod2(Request())
            }
}
