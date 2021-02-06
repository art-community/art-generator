package io.art.generator.service;

import io.art.generator.exception.GenerationException;
import io.art.generator.exception.ValidationException;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import static io.art.generator.constants.LoggingMessages.GENERATION_COMPLETED;
import static io.art.generator.constants.LoggingMessages.GENERATION_STARTED;
import static io.art.generator.context.GeneratorContext.classLoader;
import static io.art.generator.implementor.ModuleModelImplementor.implementModuleModel;
import static io.art.generator.logger.GeneratorLogger.error;
import static io.art.generator.logger.GeneratorLogger.success;
import static io.art.generator.service.ClassGenerationService.flushPendingSources;
import static io.art.generator.service.StubService.*;
import static io.art.generator.state.GeneratorState.clearLocalState;
import static io.art.generator.state.GeneratorState.refreshLocalState;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        success(GENERATION_STARTED);
        try {
            refreshLocalState();
            generateStubs();
            recompileWithStubs();
            deleteExistedClassesStubs();
            implementModuleModel();
            flushPendingSources();
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


}
