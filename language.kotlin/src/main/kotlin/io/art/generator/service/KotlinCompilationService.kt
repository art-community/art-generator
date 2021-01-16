package io.art.generator.service

import io.art.core.constants.CharacterConstants.NEW_LINE
import io.art.core.constants.CharacterConstants.SEMICOLON
import io.art.core.constants.StringConstants.EMPTY_STRING
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.generator.constants.*
import io.art.generator.constants.CompilerOptions.*
import io.art.generator.constants.ExceptionMessages.REPROCESSING_FAILED
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.options
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.context.KotlinGeneratorContext.classesOutputDir
import io.art.generator.context.KotlinGeneratorContext.projectBaseDir
import io.art.generator.exception.GenerationException
import io.art.generator.logger.GeneratorLogger.info
import io.art.generator.logger.GeneratorLogger.success
import io.art.generator.processor.KotlinGeneratorProcessor
import org.jetbrains.kotlin.base.kapt3.AptMode.WITH_COMPILATION
import org.jetbrains.kotlin.base.kapt3.DetectMemoryLeaksMode.NONE
import org.jetbrains.kotlin.base.kapt3.KaptFlags
import org.jetbrains.kotlin.base.kapt3.KaptOptions
import org.jetbrains.kotlin.base.kapt3.logString
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import java.io.File
import java.text.MessageFormat.format

class KotlinCompilationService : CompilationService {
    override fun reprocess() {
        success(REPROCESSING_STARTED)
        val compileClasspath = options().get(CLASS_PATH_OPTION).split(SEMICOLON).map(::File) + options().get(PROCESSOR_PATH_OPTION).split(SEMICOLON).map(::File)
        val javacOptions = mapOf(NO_WARN_OPTION to EMPTY_STRING, PARAMETERS_OPTION to EMPTY_STRING)
        val processingOptions = mapOf(PROCESSOR_STUB_OPTION to EMPTY_STRING)
        val options = KaptOptions(
                changedFiles = emptyList(),
                classpathChanges = emptyList(),
                compiledSources = emptyList(),
                javaSourceRoots = emptyList(),
                processors = listOf(KotlinGeneratorProcessor::class.qualifiedName!!),
                detectMemoryLeaks = NONE,
                flags = KaptFlags.Empty,
                mode = WITH_COMPILATION,

                projectBaseDir = projectBaseDir,
                classesOutputDir = classesOutputDir,
                sourcesOutputDir = stubsOutputDir(projectBaseDir),
                stubsOutputDir = sourcesOutputDir(projectBaseDir),

                compileClasspath = compileClasspath,
                processingClasspath = compileClasspath,
                javacOptions = javacOptions,
                processingOptions = processingOptions,

                incrementalCache = null,
                incrementalDataOutputDir = null
        )
        info(format(REPROCESS_ARGUMENTS, NEW_LINE + options.logString()))
        if (!kapt(options)) {
            throw GenerationException(REPROCESSING_FAILED)
        }
        success(REPROCESSING_COMPLETED)
    }

    override fun recompile() {
        success(RECOMPILATION_STARTED)
        val arguments = with(mutableListOf<String>()) {
            add(KOTLIN_NO_STD_LIB)
            add(KOTLIN_NO_REFLECT)
            add(KOTLIN_JAVA_PARAMETERS)
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
