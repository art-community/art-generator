package ru.art.generator.javac.loader;

import ru.art.generator.javac.exception.*;
import ru.art.generator.javac.service.*;
import static com.sun.tools.javac.main.Option.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import java.io.*;
import java.net.*;

public class GeneratorClassLoader {
    private final URLClassLoader loader;

    public GeneratorClassLoader() {
        try {
            URL[] urls = new URL[]{new File(options().get(D)).toURI().toURL()};
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
