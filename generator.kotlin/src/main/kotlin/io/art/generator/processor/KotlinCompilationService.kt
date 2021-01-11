package io.art.generator.processor

import io.art.core.constants.CharacterConstants.COMMA
import io.art.core.constants.CharacterConstants.SEMICOLON
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.generator.constants.CompilerOptions.CLASS_PATH_OPTION
import io.art.generator.constants.CompilerOptions.DIRECTORY_OPTION
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.logger.GeneratorLogger.*
import io.art.generator.service.CompilationService
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.text.MessageFormat.format

class KotlinCompilationService : CompilationService {
    override fun recompile() {
        success(RECOMPILATION_STARTED)
        val arguments = mutableListOf<String>()
        arguments.add("-no-stdlib")
        arguments.add("-no-reflect")
        arguments.add("-java-parameters")
        arguments.add(CLASS_PATH_OPTION)
        arguments.add(processingEnvironment().options[CLASS_PATH_PROCESSOR_OPTION].toString())
        arguments.add(DIRECTORY_OPTION)
        arguments.add(processingEnvironment().options[DIRECTORY_PROCESSOR_OPTION].toString())
        arguments.addAll(processingEnvironment().options[SOURCES_PROCESSOR_OPTION].toString().split(SEMICOLON))
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(arguments)))
        K2JVMCompiler.main(arguments.toTypedArray())
        success(RECOMPILATION_COMPLETED)
    }

}
