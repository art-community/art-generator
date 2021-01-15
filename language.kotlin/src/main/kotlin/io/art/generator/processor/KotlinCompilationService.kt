package io.art.generator.processor

import io.art.core.constants.CharacterConstants.SEMICOLON
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.generator.constants.CompilerOptions.*
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.options
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.logger.GeneratorLogger.info
import io.art.generator.logger.GeneratorLogger.success
import io.art.generator.service.CompilationService
import org.jetbrains.kotlin.base.kapt3.AptMode.WITH_COMPILATION
import org.jetbrains.kotlin.base.kapt3.DetectMemoryLeaksMode.NONE
import org.jetbrains.kotlin.base.kapt3.KaptFlags
import org.jetbrains.kotlin.base.kapt3.KaptOptions
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import java.io.File
import java.text.MessageFormat.format

class KotlinCompilationService : CompilationService {
    override fun reprocess() {
        success(REPROCESSING_STARTED)
        val compileClasspath = options().get(CLASS_PATH_OPTION).split(SEMICOLON).map(::File) + options().get(PROCESSOR_PATH_OPTION).split(SEMICOLON).map(::File)
        val javacOptions = mapOf(
                NO_WARN_OPTION to "",
                PARAMETERS_OPTION to ""
        )
        val processingOptions = mapOf(
                PROCESSOR_STUB_OPTION to ""
        )
        println()
        val options = KaptOptions(
                projectBaseDir = File("C:\\Development\\Projects\\art\\art-generator\\example.kotlin"),
                changedFiles = emptyList(),
                classesOutputDir = File("C:\\Development\\Projects\\art\\art-generator\\example.kotlin\\build\\classes\\kotlin"),
                classpathChanges = emptyList(),
                compileClasspath = compileClasspath,
                compiledSources = emptyList(),
                detectMemoryLeaks = NONE,
                flags = KaptFlags.Empty,
                mode = WITH_COMPILATION,
                sourcesOutputDir = File("C:\\Development\\Projects\\art\\art-generator\\example.kotlin\\build\\generated\\source\\kapt\\main"),
                stubsOutputDir = File("C:\\Development\\Projects\\art\\art-generator\\example.kotlin\\build\\tmp\\kapt3\\stubs\\main"),
                javaSourceRoots = emptyList(),
                processors = listOf(KotlinGeneratorProcessor::class.qualifiedName!!),
                processingClasspath = compileClasspath,
                javacOptions = javacOptions,
                processingOptions = processingOptions,
                incrementalCache = null,
                incrementalDataOutputDir = null
        )
        info(format(REPROCESS_ARGUMENTS, options.toString()))
        kapt(options)
        success(REPROCESSING_COMPLETED)
    }

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
