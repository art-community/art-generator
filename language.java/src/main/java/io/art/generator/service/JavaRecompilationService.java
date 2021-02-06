package io.art.generator.service;

import com.sun.tools.javac.api.*;
import com.sun.tools.javac.file.*;
import io.art.core.collection.*;
import io.art.core.managed.*;
import io.art.generator.exception.*;
import lombok.*;

import javax.tools.*;
import java.io.*;
import java.util.*;

import static com.sun.tools.javac.main.Main.Result.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collector.SetCollector.*;
import static io.art.core.constants.ArrayConstants.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.managed.LazyValue.*;
import static io.art.generator.constants.CompilerOptions.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.normalizer.ClassPathNormalizer.*;
import static io.art.generator.state.GeneratorState.*;
import static java.text.MessageFormat.*;

public class JavaRecompilationService implements RecompilationService {
    JavacTool javacTool;
    LazyValue<JavacFileManager> stubFileManager;

    @SneakyThrows
    public JavaRecompilationService(){
        javacTool = JavacTool.create();
        stubFileManager = lazy( () -> {
        JavacFileManager manager = javacTool.getStandardFileManager(new DiagnosticCollector<>(), null, null);
        Set<File> generatedSourcesRoot = setOf(new File(processingEnvironment().getOptions().get(GENERATED_SOURCES_ROOT_PROCESSOR_OPTION)));
        try {
            manager.setLocation(StandardLocation.SOURCE_OUTPUT, generatedSourcesRoot);
        } catch (IOException e){
            return manager;
        }
        return manager;
        });
    }

    @Override
    public void recompile() {
        ImmutableSet<String> sources = ImmutableSet.<String>immutableSetBuilder()
                .addAll(immutableArrayOf(processingEnvironment().getOptions().get(SOURCES_PROCESSOR_OPTION).split(COMMA)))
                .addAll(generatedClasses().values().stream().map(FileObject::getName).collect(setCollector()))
                .build();
        recompile(sources);
    }

    @Override
    public void recompile(Iterable<String> sources){
        success(RECOMPILATION_STARTED);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_BYTES);
        String[] recompileArguments = immutableArrayBuilder()
                .add(NO_WARN_OPTION)
                .add(PARAMETERS_OPTION)
                .add(CLASS_PATH_OPTION)
                .add(normalizeClassPath(processingEnvironment().getOptions().get(CLASS_PATH_PROCESSOR_OPTION).split(COMMA)))
                .add(DIRECTORY_OPTION)
                .add(processingEnvironment().getOptions().get(DIRECTORY_PROCESSOR_OPTION))
                .addAll(sources)
                .build()
                .toArray(new String[0]);
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(recompileArguments)));
        if (OK.exitCode != javacTool.run(inputStream, outputStream, System.err, recompileArguments)) {
            throw new GenerationException(RECOMPILATION_FAILED);
        }
        success(RECOMPILATION_COMPLETED);
    }

    @Override
    @SneakyThrows
    public FileObject createStubFile(String className){
        return stubFileManager.get().getJavaFileForOutput(
                StandardLocation.SOURCE_OUTPUT,
                className,
                JavaFileObject.Kind.SOURCE,
                null
        );
    }
}
