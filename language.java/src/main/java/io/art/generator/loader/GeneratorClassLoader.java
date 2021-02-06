package io.art.generator.loader;

import io.art.core.managed.*;
import io.art.generator.exception.*;

import java.io.*;
import java.net.*;

import static io.art.core.managed.LazyValue.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;

public class GeneratorClassLoader {
    private final LazyValue<URLClassLoader> loader = lazy(this::createLoader);

    private URLClassLoader createLoader() {
        final URLClassLoader loader;
        try {
            URL[] urls = new URL[]{new File(processingEnvironment().getOptions().get(DIRECTORY_PROCESSOR_OPTION)).toURI().toURL()};
            loader = new URLClassLoader(urls, GeneratorClassLoader.class.getClassLoader());
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
        return loader;
    }

    public Class<?> loadClass(String name) {
        try {
            return loader.get().loadClass(name);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public void close() {
        try {
            loader.get().close();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }
}
