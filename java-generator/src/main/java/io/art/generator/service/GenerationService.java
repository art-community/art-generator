package io.art.generator.service;

import lombok.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.service.CompilationService.recompile;
import static io.art.generator.implementor.model.ModuleModelImplementor.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        if (processingEnvironment().getOptions().get(DISABLE_OPTION) != null) {
            return;
        }
        recompile();
        implementModuleModel();
        classLoader().close();
    }

}
