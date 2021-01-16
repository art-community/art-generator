package io.art.generator.registrar

import io.art.generator.context.KotlinGeneratorContext
import io.art.generator.option.KotlinGeneratorCliOption.PROJECT_BASE_DIRECTORY_OPTION
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.CONTENT_ROOTS
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.JVMConfigurationKeys.OUTPUT_DIRECTORY
import org.jetbrains.kotlin.kapt3.Kapt3ComponentRegistrar
import java.io.File


class KotlinGeneratorComponentRegistrar : ComponentRegistrar {
    private val kapt3ComponentRegistrar = Kapt3ComponentRegistrar()
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        val contentRoots = configuration[CONTENT_ROOTS] ?: emptyList()
        KotlinGeneratorContext.initialize(KotlinGeneratorContext.Configuration(
                classesOutputDir = configuration.get(OUTPUT_DIRECTORY)!!,
                messageCollector = configuration.get(MESSAGE_COLLECTOR_KEY)!!,
                compileClasspath = contentRoots.filterIsInstance<JvmClasspathRoot>().map { it.file },
                javaSourceRoots = contentRoots.filterIsInstance<JavaSourceRoot>().map { it.file }))
        kapt3ComponentRegistrar.registerProjectComponents(project, configuration)
        //registerExtension(project, KotlinGeneratorExtension())
    }
}
