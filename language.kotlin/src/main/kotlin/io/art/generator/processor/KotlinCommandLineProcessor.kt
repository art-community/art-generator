package io.art.generator.processor

import io.art.generator.context.KotlinGeneratorContext.projectBaseDir
import io.art.generator.context.UNKNOWN_OPTION_MESSAGE
import io.art.generator.option.KotlinGeneratorCliOption
import io.art.generator.option.KotlinGeneratorCliOption.Companion.KOTLIN_GENERATOR_COMPILER_PLUGIN_ID
import io.art.generator.option.KotlinGeneratorCliOption.PROJECT_BASE_DIRECTORY_OPTION
import io.art.generator.option.KotlinGeneratorCliOption.values
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import java.io.File

class KotlinCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = KOTLIN_GENERATOR_COMPILER_PLUGIN_ID

    override val pluginOptions: Collection<AbstractCliOption> = values().asList()

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        if (option !is KotlinGeneratorCliOption) throw CliOptionProcessingException(UNKNOWN_OPTION_MESSAGE(option.optionName))
        when (option) {
            PROJECT_BASE_DIRECTORY_OPTION -> projectBaseDir = File(value)
        }
    }
}
