package ru;

import ru.art.generator.javac.annotation.*;
import ru.art.generator.javac.model.*;
import static ru.art.generator.javac.model.ModuleModel.*;

@Module
public class Main {
    @Configurator
    public static ModuleModel configure() {
        return module().service(MyService.class);
    }
}
