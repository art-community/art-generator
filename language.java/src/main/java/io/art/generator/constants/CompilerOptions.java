package io.art.generator.constants;

import static io.art.generator.constants.ProcessorOptions.PROCESSOR_STUB_OPTION;

public interface CompilerOptions {
    String PARAMETERS_OPTION = "-parameters";
    String CLASS_PATH_OPTION = "-classpath";
    String DIRECTORY_OPTION = "-d";
    String PROCESSOR_OPTION = "-processor";
    String PROCESSOR_PATH_OPTION = "-processorpath";
    String PROC_ONLY_OPTION = "-proc:only";
    String NO_WARN_OPTION = "-nowarn";
    String COMPILER_STUB_OPTION = "-A" + PROCESSOR_STUB_OPTION;
}
