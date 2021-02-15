package io.art.generator.service

import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.file.JavacFileManager
import io.art.core.constants.StringConstants.COMMA
import io.art.core.extensions.StringExtensions.toCommaDelimitedString
import io.art.core.factory.SetFactory
import io.art.core.property.LazyProperty
import io.art.generator.constants.CompilerOptions.*
import io.art.generator.constants.KOTLIN_JAVA_PARAMETERS
import io.art.generator.constants.KOTLIN_NO_REFLECT
import io.art.generator.constants.KOTLIN_NO_STD_LIB
import io.art.generator.constants.LoggingMessages.*
import io.art.generator.constants.ProcessorOptions.*
import io.art.generator.context.GeneratorContext.processingEnvironment
import io.art.generator.exception.GenerationException
import io.art.generator.logger.GeneratorLogger.info
import io.art.generator.logger.GeneratorLogger.success
import io.art.generator.normalizer.ClassPathNormalizer.normalizeClassPath
import io.art.generator.state.GeneratorState.generatedClasses
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.io.File
import java.text.MessageFormat.format
import javax.tools.DiagnosticCollector
import javax.tools.FileObject
import javax.tools.JavaFileObject
import javax.tools.StandardLocation


class KotlinRecompilationService : RecompilationService {
    private var javacTool: JavacTool = JavacTool.create()?:throw GenerationException("no javac in kotlin")
    private var stubFileManager: LazyProperty<JavacFileManager> = LazyProperty.lazy {
        val manager = javacTool.getStandardFileManager(DiagnosticCollector(), null, null)?:throw GenerationException("no fileMan in kotlin")
        val generatedSourcesRoot = SetFactory.setOf(File(processingEnvironment().options[GENERATED_SOURCES_ROOT_PROCESSOR_OPTION]!!))
        manager.setLocation(StandardLocation.SOURCE_OUTPUT, generatedSourcesRoot)
        return@lazy manager
    }

    override fun recompile() {
        val sources = mutableSetOf<String>().apply {
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
            add(normalizeClassPath(processingEnvironment().options[CLASS_PATH_PROCESSOR_OPTION].toString().split(COMMA)
                    .toSet().filter { file -> File(file).exists() }.toTypedArray()))
            add(DIRECTORY_OPTION)
            add(processingEnvironment().options[DIRECTORY_PROCESSOR_OPTION].toString())
            addAll(sources.filter { file -> File(file).exists() })
            toTypedArray()
        }
        info(format(RECOMPILE_ARGUMENTS, toCommaDelimitedString(arguments)))
        K2JVMCompiler.main(arguments)
        success(RECOMPILATION_COMPLETED)
    }

    override fun createStubFile(className: String?): FileObject {
        return stubFileManager.get().getJavaFileForOutput(
                StandardLocation.SOURCE_OUTPUT,
                className,
                JavaFileObject.Kind.SOURCE,
                null
        )
    }
}
