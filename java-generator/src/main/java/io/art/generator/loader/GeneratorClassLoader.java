package io.art.generator.loader;

import io.art.generator.exception.*;
import static io.art.generator.constants.ProcessorOptions.DIRECTORY_PROCESSOR_OPTION;
import static io.art.generator.context.GeneratorContext.*;
import java.io.*;
import java.net.*;

public class GeneratorClassLoader {
    private final URLClassLoader loader;

    public GeneratorClassLoader() {
        try {
            URL[] urls = new URL[]{new File(processingEnvironment().getOptions().get(DIRECTORY_PROCESSOR_OPTION)).toURI().toURL()};
            loader = new URLClassLoader(urls, GeneratorClassLoader.class.getClassLoader());
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public Class<?> loadClass(String name) {
        try {
            return loader.loadClass(name);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public void close() {
        try {
            loader.close();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }
}
