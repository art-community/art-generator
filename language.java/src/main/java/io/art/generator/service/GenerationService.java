package io.art.generator.service;

import io.art.generator.exception.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.implementor.MainMethodImplementor.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;

public class GenerationService {
    public static void generateClasses() {
        success(GENERATION_STARTED);
        try {
            moduleClasses()
                    .values()
                    .forEach(moduleClass -> generateClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
            compilationService().recompile();
            implementModuleModel();
            implementMainMethods();
            classLoader().close();
        } catch (ValidationException validationException) {
            printError(validationException.write());
            throw validationException;
        } catch (GenerationException generationException) {
            printError(generationException.write());
            throw generationException;
        }
        success(GENERATION_COMPLETED);
    }

}
