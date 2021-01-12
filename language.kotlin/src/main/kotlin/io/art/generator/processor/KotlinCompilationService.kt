package io.art.generator.processor

import io.art.core.constants.CharacterConstants.SEMICOLON
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.generator.constants.CompilerOptions.*
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.options
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.logger.GeneratorLogger.*
import io.art.generator.service.CompilationService
import java.text.MessageFormat.format

class KCompilationService : CompilationService {
    override fun rp() {
        success(REPROCESSING_STARTED);
        val arguments = mutableListOf<String>()
        arguments.add("-no-stdlib")
        arguments.add("-no-reflect")
        arguments.add("-java-parameters")
        arguments.add("-Xjavac-argument").add(options().get(PROCESSOR_PATH_OPTION));
        arguments.add(PROCESSOR_OPTION).add(JavaGeneratorProcessor.class.getName());
        arguments.add(NO_WARN_OPTION);
        arguments.add(PROC_ONLY_OPTION);
        arguments.add(PARAMETERS_OPTION);
        arguments.add(COMPILER_STUB_OPTION);
        arguments.add(CLASS_PATH_OPTION);
        arguments.add(processingEnvironment().getOptions().get(CLASS_PATH_PROCESSOR_OPTION));
        arguments.addAll(fixedArrayOf(processingEnvironment().getOptions().get(SOURCES_PROCESSOR_OPTION).split(COLON)));
        String[] reprocessArguments = arguments.build().toArray(new String[0]);
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(reprocessArguments)));
        if (OK.exitCode != javacTool.run(inputStream, outputStream, System.err, reprocessArguments)) {
            throw new GenerationException(REPROCESSING_FAILED);
        }
        success(REPROCESSING_COMPLETED);
    }

    override fun rc() {
        success(RECOMPILATION_STARTED)
        a.add("-no-stdlib")
        a.add("-no-reflect")
        a.add("-java-parameters")
        a.add(CLASS_PATH_OPTION)
        a.add(processingEnvironment().options[CLASS_PATH_PROCESSOR_OPTION].toString())
        a.add(DIRECTORY_OPTION)
        a.add(processingEnvironment().options[DIRECTORY_PROCESSOR_OPTION].toString())
        a.addAll(processingEnvironment().options[SOURCES_PROCESSOR_OPTION].toString().split(SEMICOLON))
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(a)))
        success(RECOMPILATION_COMPLETED)
    }

}
