package io.art.generator.constants;

public interface ProcessorOptions {
    String CLASS_PATH_PROCESSOR_OPTION = "art.generator.classpath";
    String DIRECTORY_PROCESSOR_OPTION = "art.generator.destination";
    String SOURCES_PROCESSOR_OPTION = "art.generator.sources";
    String DISABLE_OPTION = "art.generator.disable";
    String PROCESSOR_STUB_OPTION = "art.generator.stub";

    String[] PROCESSOR_OPTIONS = new String[]{
            CLASS_PATH_PROCESSOR_OPTION,
            DIRECTORY_PROCESSOR_OPTION,
            SOURCES_PROCESSOR_OPTION,
            DISABLE_OPTION,
            PROCESSOR_STUB_OPTION
    };
}

