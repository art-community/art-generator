package ru;

import io.art.entity.registry.*;
import io.art.launcher.*;
import io.art.model.annotation.*;
import io.art.model.module.*;
import io.art.server.registry.*;
import static io.art.model.module.ModuleModel.*;

@Module
public class Example {
    public static void main(String[] args) {
        ModuleLauncher.launch(configure());
    }

    @Configurator
    public static ModuleModel configure() {
        return module().serve(server -> server.rsocket(MyService.class));
    }

    public static class Configuration {
        public static MappersRegistry mappers() {
            //Stub for generator
            return null;
        }

        public static ServiceSpecificationRegistry services() {
            //Stub for generator
            return null;
        }
    }
}
