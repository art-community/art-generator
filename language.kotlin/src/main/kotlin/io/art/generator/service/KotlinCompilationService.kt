package io.art.generator.service

import io.art.core.constants.CharacterConstants.SEMICOLON
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.generator.constants.CompilerOptions.*
import io.art.generator.constants.KOTLIN_JAVA_PARAMETERS
import io.art.generator.constants.KOTLIN_NO_REFLECT
import io.art.generator.constants.KOTLIN_NO_STD_LIB
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.logger.GeneratorLogger.info
import io.art.generator.logger.GeneratorLogger.success
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.lang.System.setProperty
import java.text.MessageFormat.format


class KotlinCompilationService : CompilationService {
    override fun recompile() {
        success(RECOMPILATION_STARTED)
        val arguments = with(mutableListOf<String>()) {
            add(KOTLIN_NO_STD_LIB)
            add(KOTLIN_NO_REFLECT)
            add(KOTLIN_JAVA_PARAMETERS)
            add(NO_WARN_OPTION)
            add(CLASS_PATH_OPTION)
            add(processingEnvironment().options[CLASS_PATH_PROCESSOR_OPTION].toString())
            add(DIRECTORY_OPTION)
            add(processingEnvironment().options[DIRECTORY_PROCESSOR_OPTION].toString())
            addAll(processingEnvironment().options[SOURCES_PROCESSOR_OPTION].toString().split(SEMICOLON))
            toTypedArray()
        }
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(arguments)))
        K2JVMCompiler.main(arguments)
        success(RECOMPILATION_COMPLETED)

    }


}
