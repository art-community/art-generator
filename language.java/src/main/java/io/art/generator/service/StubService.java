package io.art.generator.service;

import io.art.generator.context.*;
import io.art.generator.model.*;

import javax.tools.*;
import java.util.*;

import static io.art.core.collector.SetCollector.*;
import static io.art.core.extensions.FileExtensions.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GeneratorState.*;


public class StubService {

    public static void generateStubs() {
        success(STUB_GENERATION_STARTED);
        generateProviderStubs();
        generateExistedClassesStubs(existedClassNames());
        success(STUB_GENERATION_COMPLETED);
    }

    public static void generateProviderStubs(){
        moduleClasses()
                .values()
                .forEach(moduleClass -> generateProviderClass(createProviderStub(moduleClass), moduleClass.getPackageName()));
    }

    public static void generateExistedClassesStubs(Set<String> classNames){
        classNames.stream()
                .map(GeneratorContext::existedClass)
                .map(StubClass::from)
                .forEach(stub -> generateStubClass(stub, stub.packageName()));
    }

    public static void recompileWithStubs(){
        success(STUB_RECOMPILATION_STARTED);
        new JavaRecompilationService().recompile(generatedSources());
    }

    public static void deleteStubSources(){
        existedClassNames().forEach(className -> {
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

    private static Set<String> existedClassNames(){
        return existedClasses().entrySet().stream()
                .filter(entry -> !existedClasses().containsKey(entry.getValue().getDeclaration().sym.owner.name.toString()))
                .map(Map.Entry::getKey)
                .collect(setCollector());
    }
}
