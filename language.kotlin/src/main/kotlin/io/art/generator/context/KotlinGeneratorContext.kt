package io.art.generator.context

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

object KotlinGeneratorContext {
    private val initialized = AtomicBoolean(false);

    lateinit var projectBaseDir: File
    lateinit var classesOutputDir: File
        private set
    lateinit var javaSourceRoots: List<File>
        private set
    lateinit var compileClasspath: List<File>
        private set
    lateinit var messageCollector: MessageCollector
        private set

    fun initialize(configuration: Configuration) {
        if (initialized.compareAndSet(false, true)) {
            classesOutputDir = configuration.classesOutputDir
            compileClasspath = configuration.compileClasspath
            javaSourceRoots = configuration.javaSourceRoots
            messageCollector = configuration.messageCollector
        }
    }

    data class Configuration(
            val classesOutputDir: File,
            val compileClasspath: List<File>,
            val javaSourceRoots: List<File>,
            val messageCollector: MessageCollector,
    )
}
