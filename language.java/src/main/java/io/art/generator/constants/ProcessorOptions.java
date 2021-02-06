package io.art.generator.constants;

public interface ProcessorOptions {
    String CLASS_PATH_PROCESSOR_OPTION = "art.generator.recompilation.classpath";
    String DIRECTORY_PROCESSOR_OPTION = "art.generator.recompilation.destination";
    String SOURCES_PROCESSOR_OPTION = "art.generator.recompilation.sources";
    String SOURCES_ROOT_PROCESSOR_OPTION =  "art.generator.recompilation.sourcesRoot";
    String GENERATED_SOURCES_ROOT_PROCESSOR_OPTION = "art.generator.recompilation.generatedSourcesRoot";

    String[] PROCESSOR_OPTIONS = new String[]{
            CLASS_PATH_PROCESSOR_OPTION,
            DIRECTORY_PROCESSOR_OPTION,
            SOURCES_PROCESSOR_OPTION,
            SOURCES_ROOT_PROCESSOR_OPTION,
            GENERATED_SOURCES_ROOT_PROCESSOR_OPTION
    };
}

