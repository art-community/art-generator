@file:Suppress(JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE)

package io.art.generator.processor

import com.google.common.base.Throwables.getStackTraceAsString
import com.sun.source.util.Trees.instance
import com.sun.tools.javac.api.JavacTrees
import com.sun.tools.javac.main.JavaCompiler
import com.sun.tools.javac.model.JavacElements
import com.sun.tools.javac.processing.JavacProcessingEnvironment
import com.sun.tools.javac.tree.TreeMaker
import com.sun.tools.javac.util.Options
import io.art.core.extensions.CollectionExtensions.addToSet
import io.art.generator.constants.Annotations.CONFIGURATOR_ANNOTATION_NAME
import io.art.generator.constants.ExceptionMessages.GENERATION_FAILED_MESSAGE_FORMAT
import io.art.generator.constants.JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE
import io.art.generator.constants.JavaDialect.KOTLIN
import io.art.generator.constants.ProcessorOptions.PROCESSOR_OPTIONS
import io.art.generator.context.GeneratorContext
import io.art.generator.context.GeneratorContext.initialize
import io.art.generator.context.GeneratorContextConfiguration
import io.art.generator.logger.GeneratorLogger
import io.art.generator.logger.GeneratorLogger.error
import io.art.generator.scanner.GeneratorScanner
import io.art.generator.service.GenerationService.generate
import io.art.generator.service.KotlinCompilationService
import io.art.generator.state.GenerationState.complete
import io.art.generator.state.GenerationState.completed
import java.text.MessageFormat.format
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.SourceVersion.latest
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes(CONFIGURATOR_ANNOTATION_NAME)
class KotlinGeneratorProcessor : AbstractProcessor() {
    private var trees: JavacTrees? = null
    private var processingEnvironment: JavacProcessingEnvironment? = null
    private val configurationBuilder = GeneratorContextConfiguration.builder()

    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        this.processingEnvironment = processingEnvironment as JavacProcessingEnvironment
        trees = instance(processingEnvironment) as JavacTrees
    }

    override fun getSupportedSourceVersion(): SourceVersion = latest()

    override fun getSupportedOptions(): Set<String> = addToSet(super.getSupportedOptions(), *PROCESSOR_OPTIONS)

    @Synchronized
    override fun process(annotations: Set<TypeElement?>, roundEnvironment: RoundEnvironment): Boolean {
        if (GeneratorContext.isInitialized()) {
            if (completed()) {
                return true
            }
            try {
                generate()
                complete()
                return true
            } catch (exception: Throwable) {
                error(format(GENERATION_FAILED_MESSAGE_FORMAT, getStackTraceAsString(exception)))
            }
        }
        val elements = JavacElements.instance(processingEnvironment!!.context)
        val scanner = with(configurationBuilder) {
            dialect(KOTLIN)
            compilationService(KotlinCompilationService())
            options(Options.instance(processingEnvironment!!.context))
            processingEnvironment(processingEnvironment)
            compiler(JavaCompiler.instance(processingEnvironment!!.context))
            elements(elements)
            maker(TreeMaker.instance(processingEnvironment!!.context))
            logger(GeneratorLogger(System.out::println, System.err::println))
            GeneratorScanner(elements, this)
        }
        for (rootElement in roundEnvironment.rootElements) {
            scanner.scan(trees!!.getPath(rootElement), trees)
        }
        initialize(configurationBuilder.build())
        return false
    }
}
