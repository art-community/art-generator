package io.art.generator.constants;

public interface ProcessorOptions {
    String CLASS_PATH_PROCESSOR_OPTION = "activator.generator.recompilation.classpath";
    String DIRECTORY_PROCESSOR_OPTION = "activator.generator.recompilation.destination";
    String SOURCES_PROCESSOR_OPTION = "activator.generator.recompilation.sources";
    String SOURCES_ROOT_PROCESSOR_OPTION = "activator.generator.recompilation.sourcesRoot";
    String JAVA_GENERATED_SOURCES_ROOT_PROCESSOR_OPTION = "activator.generator.recompilation.generatedSourcesRoot";
    String KOTLIN_GENERATED_SOURCES_ROOT_PROCESSOR_OPTION = "kapt.kotlin.generated";

    String[] PROCESSOR_OPTIONS = new String[]{
            CLASS_PATH_PROCESSOR_OPTION,
            DIRECTORY_PROCESSOR_OPTION,
            SOURCES_PROCESSOR_OPTION,
            SOURCES_ROOT_PROCESSOR_OPTION,
            JAVA_GENERATED_SOURCES_ROOT_PROCESSOR_OPTION
    };
}

