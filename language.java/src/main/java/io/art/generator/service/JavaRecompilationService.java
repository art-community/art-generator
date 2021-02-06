package io.art.generator.service;

import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import io.art.core.collection.ImmutableSet;
import io.art.core.managed.LazyValue;
import io.art.generator.exception.GenerationException;
import lombok.SneakyThrows;

import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.sun.tools.javac.main.Main.Result.OK;
import static io.art.core.collection.ImmutableArray.immutableArrayBuilder;
import static io.art.core.collector.SetCollector.setCollector;
import static io.art.core.constants.ArrayConstants.EMPTY_BYTES;
import static io.art.core.constants.StringConstants.COMMA;
import static io.art.core.extensions.StringExtensions.toCommaDelimitedString;
import static io.art.core.factory.ArrayFactory.immutableArrayOf;
import static io.art.core.factory.SetFactory.setOf;
import static io.art.core.managed.LazyValue.lazy;
import static io.art.generator.constants.CompilerOptions.*;
import static io.art.generator.constants.ExceptionMessages.RECOMPILATION_FAILED;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.processingEnvironment;
import static io.art.generator.logger.GeneratorLogger.info;
import static io.art.generator.logger.GeneratorLogger.success;
import static io.art.generator.normalizer.ClassPathNormalizer.normalizeClassPath;
import static io.art.generator.state.GeneratorState.generatedClasses;
import static java.text.MessageFormat.format;

public class JavaRecompilationService implements RecompilationService {
    JavacTool javacTool;
    LazyValue<JavacFileManager> fileManager;

    @SneakyThrows
    public JavaRecompilationService(){
        javacTool = JavacTool.create();
        fileManager = lazy( () -> {
        JavacFileManager manager = javacTool.getStandardFileManager(new DiagnosticCollector<>(), null, null);
        Set<File> generatedSourcesRoot = setOf(new File(processingEnvironment().getOptions().get(GENERATED_SOURCES_ROOT_DIRECTORY)));
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
        return fileManager.get().getJavaFileForOutput(
                StandardLocation.SOURCE_OUTPUT,
                className,
                JavaFileObject.Kind.SOURCE,
                null
        );
    }
}
