package io.art.generator.service;

import io.art.generator.context.*;
import io.art.generator.model.*;

import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

import static io.art.core.collector.SetCollector.*;
import static io.art.core.constants.CharacterConstants.DOT;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.FileExtensions.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.util.Arrays.*;


public class StubService {

    public static void generateStubs() {
        success(STUB_GENERATION_STARTED);
        generateProviderStubs();
        generateExistedClassesStubs(projectClasses());
        success(STUB_GENERATION_COMPLETED);
    }

    public static void generateProviderStubs(){
        moduleClasses()
                .values()
                .forEach(moduleClass -> generateClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
    }

    public static void generateExistedClassesStubs(Set<String> classNames){
        classNames.stream()
                .map(GeneratorContext::existedClass)
                .map(StubClass::from)
                .filter(stub -> !moduleClasses().containsKey(stub.name()))
                .forEach(stub -> generateStubClass(stub, stub.packageName()));
    }

    public static void recompileWithStubs(){
        Set<String> sources = moduleClasses().values().stream()
                .map(moduleClass -> moduleClass.getPackageUnit().getSourceFile().getName())
                .collect(setCollector());
        sources.addAll(generatedSources());
        compilationService().recompile(sources);
    }

    public static void deleteStubSources(){
        Set<String> moduleClassNames = moduleClasses().values().stream()
                .map(ExistedClass::getFullName)
                .collect(setCollector());
        projectClasses().stream()
                .filter(className -> !moduleClassNames.contains(className))
                .forEach(className ->{
                    String path = generatedClasses().get(className).getName();
                    flushFile(className);
                    deleteFileRecursive(path);
                });
        success(STUBS_REMOVED);
    }

    private static Set<String> generatedSources(){
        return generatedClasses().values().stream()
                .map(FileObject::getName)
                .collect(setCollector());
    }

    private static Set<String> projectClasses(){
        String sourcesRoot = processingEnvironment().getOptions().get(SOURCES_ROOT_PROCESSOR_OPTION);
        return stream(processingEnvironment().getOptions().get(SOURCES_PROCESSOR_OPTION).split(","))
                .filter(path -> path.startsWith(sourcesRoot))
                .map(path -> path.substring(sourcesRoot.length() + 1)
                        .replace(File.separatorChar, DOT)
                        .replace(JAVA_SOURCE_FILE_EXTENSION, EMPTY_STRING))
                .collect(Collectors.toSet());
    }
}
