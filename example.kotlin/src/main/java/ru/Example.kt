package ru

import io.art.kotlin.extensions.model.module
import io.art.kotlin.extensions.scheduler.scheduleDelayed
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
import ru.ExampleProvider.provide
import ru.communicator.MyClient
import ru.configuration.MyConfig
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

            }
        }
    }

    @JvmStatic
    fun main(vararg arguments: String) = launch(provide())
}
