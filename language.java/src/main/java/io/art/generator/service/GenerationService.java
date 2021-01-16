package io.art.generator.service;

import io.art.generator.exception.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GenerationState.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        success(GENERATION_STARTED);
        try {
            updateLocalState();
            generateStubs();
            compilationService().recompile();
            implementModuleModel();
            closePendingSources();
            classLoader().close();
            clearLocalState();
        } catch (ValidationException validationException) {
            error(validationException.write());
            throw validationException;
        } catch (GenerationException generationException) {
            error(generationException.write());
            throw generationException;
        }
        success(GENERATION_COMPLETED);
    }

    private static void generateStubs() {
        moduleClasses()
                .values()
                .forEach(moduleClass -> generateClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
    }

}
