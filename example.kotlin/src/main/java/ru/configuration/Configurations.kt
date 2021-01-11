package ru.configuration

import io.art.configurator.module.ConfiguratorModule

fun myConfig(): MyConfig {
    return ConfiguratorModule.configuration(MyConfig::class.java)
}
