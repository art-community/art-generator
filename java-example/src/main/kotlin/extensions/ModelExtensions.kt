package extensions

import io.art.model.modeler.ModuleModeler
import io.art.model.modeler.ServerModeler
import io.art.model.modeler.ServiceModeler
import io.art.rsocket.configuration.RsocketServiceConfiguration

class ModuleModelerDelegate(private val modeler: ModuleModeler) {
    fun serve(modeler: ServerModelerDelegate.() -> ServerModelerDelegate): ModuleModelerDelegate {
        this.modeler.serve { server -> modeler(ServerModelerDelegate(server)).modeler }
        return this;
    }
}

class ServerModelerDelegate(val modeler: ServerModeler) {
    fun rsocket(modeler: ServiceModelerDelegate<RsocketServiceConfiguration>.() -> ServiceModelerDelegate<RsocketServiceConfiguration>): ServerModelerDelegate {
        this.modeler.rsocket { rsocket -> modeler(ServiceModelerDelegate(rsocket)).modeler }
        return this;
    }
}

class ServiceModelerDelegate<T>(val modeler: ServiceModeler<T>) {
    fun to(service: Any): ServiceModelerDelegate<T> {
        modeler.to(service.javaClass)
        return this
    }

    fun to(service: Any, operator: ServiceModelerDelegate<T>.() -> ServiceModelerDelegate<T>): ServiceModelerDelegate<T> {
        modeler.to(service.javaClass) { operator(this).modeler }
        return this
    }

    fun enableLogging(): ServiceModelerDelegate<T> {
        modeler.enableLogging()
        return this
    }
}

fun module(modeler: ModuleModelerDelegate.() -> ModuleModelerDelegate) = modeler(ModuleModelerDelegate(ModuleModeler.module()))
