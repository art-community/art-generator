package io.art.generator.service;

import io.art.generator.exception.*;
import lombok.*;
import lombok.experimental.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static io.art.core.collector.SetCollector.setCollector;
import static io.art.core.constants.StringConstants.COMMA;
import static io.art.core.constants.StringConstants.DOT;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.SOURCES_PROCESSOR_OPTION;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.service.StubService.*;
import static io.art.generator.state.GeneratorState.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        success(GENERATION_STARTED);
        try {
            refreshLocalState();
            generateStubs();
            recompileWithStubs();
            deleteStubSources();
            recompileModuleClasses();
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

    private void recompileModuleClasses(){
        Set<String> moduleSources = moduleClasses().values().stream()
                .map(moduleClass -> moduleClass.getFullName().replace(DOT, File.separator))
                .flatMap(searchedString -> Arrays.stream(processingEnvironment().getOptions().get(SOURCES_PROCESSOR_OPTION).split(COMMA))
                        .filter(path -> path.contains(searchedString)))
                .collect(setCollector());
        success(MODULE_CLASS_RECOMPILATION_STARTED);
        compilationService().recompile(moduleSources);
    }

}
