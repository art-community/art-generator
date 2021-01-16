package io.art.generator.constants;

public interface ProcessorOptions {
    String CLASS_PATH_PROCESSOR_OPTION = "art.generator.classpath";
    String DIRECTORY_PROCESSOR_OPTION = "art.generator.destination";
    String SOURCES_PROCESSOR_OPTION = "art.generator.sources";

    String[] PROCESSOR_OPTIONS = new String[]{
            CLASS_PATH_PROCESSOR_OPTION,
            DIRECTORY_PROCESSOR_OPTION,
            SOURCES_PROCESSOR_OPTION
    };
}

