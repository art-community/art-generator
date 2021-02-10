package ru

import io.art.kotlin.extensions.descriptor.parseJson
import io.art.kotlin.extensions.model.module
import io.art.kotlin.extensions.scheduler.scheduleDelayed
import io.art.launcher.ModuleLauncher.launch
import io.art.model.annotation.Configurator
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
                val test1: Request = """ {"FModel": {"FInteger": 123 } } """.parseJson()
                println(test1.FModel?.FInteger)
            }
        }
    }

    @JvmStatic
    fun main(vararg arguments: String) = launch(provide())
}
