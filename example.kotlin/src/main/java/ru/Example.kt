package ru

import io.art.kotlin.extensions.model.module
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
import ru.ExampleProvider.provide
import ru.communicator.MyClient
import ru.configuration.MyConfig
import ru.model.Model
import ru.model.Request
import ru.model.Response
import ru.service.MyService

object Example {
    @Configurator
    fun configure() = module {
        value {
            mapping(Request::class, Model::class, Response::class)
            mapping<Model>()
        }

        configure {
            configuration(MyConfig::class.java)
            configuration<MyConfig>()
        }

        serve {
            rsocket<MyService>()
        }

        communicate {
            rsocket<MyClient>() to MyService
        }
    }

    @JvmStatic
    fun main(vararg arguments: String) = launch(provide())
}
