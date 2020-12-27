package io.art.generator.service;

import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.CompilationService.*;

@UtilityClass
public class GenerationService {
    public void generate() {
        success("Generation started");
        try {
            recompile();
            implementModuleModel();
            classLoader().close();
        } catch (ValidationException validationException) {
            error(validationException.write());
            throw validationException;
        } catch (GenerationException generationException) {
            error(generationException.write());
            throw generationException;
        }
        success("Generation ended");
    }
}
