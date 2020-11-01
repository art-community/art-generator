package io.art.generator.javac.service;

import lombok.*;
import lombok.experimental.*;
import static io.art.generator.javac.constants.GeneratorConstants.ProcessorOptions.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.service.CompileService.recompile;
import static io.art.generator.javac.implementor.model.ModuleModelImplementor.*;

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
