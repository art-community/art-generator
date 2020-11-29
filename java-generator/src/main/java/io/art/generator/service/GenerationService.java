package io.art.generator.service;

import lombok.experimental.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.service.CompilationService.*;

@UtilityClass
public class GenerationService {
    public void generate() {
        recompile();
        implementModuleModel();
        classLoader().close();
    }
}
