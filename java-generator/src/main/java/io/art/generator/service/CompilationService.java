package io.art.generator.service;

import com.sun.tools.javac.api.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static com.sun.tools.javac.main.Main.Result.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.constants.ArrayConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.CompilerOptions.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static java.text.MessageFormat.*;
import java.io.*;

@UtilityClass
public class CompilationService {
    public void recompile() {
        success(RECOMPILATION_STARTED);
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_BYTES);
        ImmutableArray.Builder<String> arguments = immutableArrayBuilder();
        arguments.add(PARAMETERS_OPTION);
        arguments.add(CLASS_PATH_OPTION);
        arguments.add(processingEnvironment().getOptions().get(CLASS_PATH_PROCESSOR_OPTION));
        arguments.add(DIRECTORY_OPTION);
        arguments.add(processingEnvironment().getOptions().get(DIRECTORY_PROCESSOR_OPTION));
        arguments.add(DISABLE_OPTION_ENABLED);
        ImmutableArray<String> classes = existedClasses()
                .values()
                .stream()
                .map(existed -> existed.getPackageUnit().getSourceFile().getName())
                .collect(immutableArrayCollector());
        arguments.addAll(classes);
        String[] recompileArguments = arguments.build().toArray(new String[0]);
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(recompileArguments)));
        if (OK.exitCode != javacTool.run(inputStream, outputStream, System.err, recompileArguments)) {
            throw new GenerationException(RECOMPILATION_FAILED);
        }
        success(RECOMPILATION_COMPLETED);
    }
}
