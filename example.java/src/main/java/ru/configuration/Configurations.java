package ru.configuration;

import static io.art.configurator.module.ConfiguratorModule.*;

public interface Configurations {
    static MyConfig myConfig() {
        return configuration(MyConfig.class);
    }
}
