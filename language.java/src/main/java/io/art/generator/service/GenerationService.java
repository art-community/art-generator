package io.art.generator.service;

import io.art.generator.context.GeneratorContext;
import io.art.generator.exception.*;
import io.art.generator.model.StubClass;
import lombok.*;
import lombok.experimental.*;
import javax.tools.FileObject;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import static io.art.core.collector.SetCollector.setCollector;
import static io.art.core.constants.CharacterConstants.DOT;
import static io.art.core.constants.StringConstants.EMPTY_STRING;
import static io.art.core.constants.StringConstants.JAVA_FILE_EXTENSION;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.SOURCES_PROCESSOR_OPTION;
import static io.art.generator.constants.ProcessorOptions.SOURCES_ROOT_DIRECTORY;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.implementor.ModuleModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.util.Arrays.stream;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        success(GENERATION_STARTED);
        try {
            refreshLocalState();
            generateStubs();
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

    private static void generateStubs() {
        generateProviderStubs();
        generateExistedClassesStubs(projectClasses());
        Set<String> sources = moduleClasses().values().stream()
                .map(moduleClass -> moduleClass.getPackageUnit().getSourceFile().getName())
                .collect(setCollector());
        generatedClasses().values().stream()
                .map(FileObject::getName)
                .forEach(sources::add);
        compilationService().recompile(sources);

    }

    private static void generateProviderStubs(){
        moduleClasses()
                .values()
                .forEach(moduleClass -> generateClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
    }

    private static void generateExistedClassesStubs(Set<String> classNames){
        classNames.stream()
                .map(GeneratorContext::existedClass)
                .map(StubClass::from)
                .filter(stub -> !moduleClasses().containsKey(stub.name()))
                .forEach(stub -> generateClass(stub, stub.packageName()));
    }

    private static Set<String> projectClasses(){
        String sourcesRoot = processingEnvironment().getOptions().get(SOURCES_ROOT_DIRECTORY);
        return stream(processingEnvironment().getOptions().get(SOURCES_PROCESSOR_OPTION).split(","))
                .filter(path -> path.startsWith(sourcesRoot))
                .map(path -> path.substring(sourcesRoot.length() + 1)
                        .replace(File.separatorChar, DOT)
                        .replace(JAVA_FILE_EXTENSION, EMPTY_STRING))
                .collect(Collectors.toSet());
    }

}
