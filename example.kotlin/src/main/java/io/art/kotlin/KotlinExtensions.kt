package io.art.kotlin

import io.art.model.configurator.ModuleModelConfigurator
import io.art.model.configurator.ValueModelConfigurator
import kotlin.reflect.KClass

fun module(mainClass: KClass<*>, configurator: ModuleModelConfiguratorExtension.() -> ModuleModelConfiguratorExtension) = configurator(ModuleModelConfiguratorExtension(mainClass.simpleName!!))

class ModuleModelConfiguratorExtension(id: String) : ModuleModelConfigurator(id) {
    fun value(configurator: ValueModelConfiguratorExtension.() -> Unit): ModuleModelConfiguratorExtension {
        super.value {
            val extension = ValueModelConfiguratorExtension()
            configurator(extension)
            extension
        }
        return this
    }
}

class ValueModelConfiguratorExtension() : ValueModelConfigurator() {
}
