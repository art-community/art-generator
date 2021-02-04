package io.art.generator.service

import io.art.core.constants.StringConstants.COMMA
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
import io.art.generator.normalizer.ClassPathNormalizer.normalizeClassPath
import io.art.generator.state.GeneratorState.generatedClasses
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.text.MessageFormat.format


class KotlinRecompilationService : RecompilationService {
    override fun recompile() {
        val sources = mutableListOf<String>().apply {
            addAll(processingEnvironment().options[SOURCES_PROCESSOR_OPTION]!!.split(COMMA))
            addAll(generatedClasses().values().map { file -> file.name })
        }
        recompile(sources)
    }

    override fun recompile(sources: Iterable<String>) {
        success(RECOMPILATION_STARTED)
        val arguments = with(mutableListOf<String>()) {
            add(KOTLIN_NO_STD_LIB)
            add(KOTLIN_NO_REFLECT)
            add(KOTLIN_JAVA_PARAMETERS)
            add(NO_WARN_OPTION)
            add(CLASS_PATH_OPTION)
            add(normalizeClassPath(processingEnvironment().options[CLASS_PATH_PROCESSOR_OPTION].toString().split(COMMA).toTypedArray()))
            add(DIRECTORY_OPTION)
            add(processingEnvironment().options[DIRECTORY_PROCESSOR_OPTION].toString())
            addAll(sources)
            toTypedArray()
        }
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(arguments)))
        K2JVMCompiler.main(arguments)
        success(RECOMPILATION_COMPLETED)
    }
}
