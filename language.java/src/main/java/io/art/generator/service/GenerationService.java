package io.art.generator.service;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.implementor.MainMethodImplementor.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;

@UtilityClass
public class GenerationService {
    public void generateClasses() {
        success(GENERATION_STARTED);
        try {
            CompilationService compilationService = compilationService();
            compilationService.reprocess();
            compilationService.recompile();
            implementModuleModel();
            implementMainMethods();
            classLoader().close();
        } catch (ValidationException validationException) {
            error(validationException.write());
            throw validationException;
        } catch (GenerationException generationException) {
            error(generationException.write());
            throw generationException;
        }
        success(GENERATION_COMPLETED);
    }

    public void generateStubs() {
        moduleClasses()
                .values()
                .forEach(moduleClass -> generateClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
    }
}
