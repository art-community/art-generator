package extensions

import io.art.model.configurator.ModuleModelConfigurator
import io.art.model.configurator.ServerModelConfigurator
import io.art.model.configurator.ServiceModelConfigurator
import io.art.rsocket.configuration.RsocketServiceConfiguration

class ModuleModelerDelegate(private val modeler: ModuleModelConfigurator) {
    fun serve(modeler: ServerModelerDelegate.() -> ServerModelerDelegate): ModuleModelerDelegate {
        this.modeler.serve { server -> modeler(ServerModelerDelegate(server)).configurator }
        return this;
    }
}

class ServerModelerDelegate(val configurator: ServerModelConfigurator) {
    fun rsocket(modeler: ServiceModelerDelegate<RsocketServiceConfiguration>.() -> ServiceModelerDelegate<RsocketServiceConfiguration>): ServerModelerDelegate {
        this.configurator.rsocket { rsocket -> modeler(ServiceModelerDelegate(rsocket)).modelConfigurator }
        return this;
    }
}

class ServiceModelerDelegate<T>(val modelConfigurator: ServiceModelConfigurator<T>) {
    fun to(service: Any): ServiceModelerDelegate<T> {
        modelConfigurator.to(service.javaClass)
        return this
    }

    fun to(service: Any, operator: ServiceModelerDelegate<T>.() -> ServiceModelerDelegate<T>): ServiceModelerDelegate<T> {
        modelConfigurator.to(service.javaClass) { operator(this).modelConfigurator }
        return this
    }

    fun enableLogging(): ServiceModelerDelegate<T> {
        modelConfigurator.enableLogging()
        return this
    }
}

fun module(modeler: ModuleModelerDelegate.() -> ModuleModelerDelegate) = modeler(ModuleModelerDelegate(ModuleModelConfigurator.module()))
