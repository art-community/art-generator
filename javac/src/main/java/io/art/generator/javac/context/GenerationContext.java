package io.art.generator.javac.context;

import com.google.common.collect.*;
import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.javac.loader.*;
import io.art.generator.javac.model.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class GenerationContext {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private static final AtomicReference<JavaCompiler> compiler = new AtomicReference<>();

    private static final AtomicReference<JavacProcessingEnvironment> processingEnvironment = new AtomicReference<>();

    private static final AtomicReference<Options> options = new AtomicReference<>();

    private static final AtomicReference<TreeMaker> maker = new AtomicReference<>();

    private static final AtomicReference<JavacElements> elements = new AtomicReference<>();

    private static final AtomicReference<ExistedClass> mainClass = new AtomicReference<>();

    private static final AtomicReference<ExistedMethod> mainMethod = new AtomicReference<>();

    private static final AtomicReference<ExistedMethod> configureMethod = new AtomicReference<>();

    private static final AtomicReference<GeneratorClassLoader> classLoader = new AtomicReference<>();

    private static ImmutableMap<String, ExistedClass> existedClasses;

    public static ImmutableMap<String, ExistedClass> getExistedClasses() {
        return existedClasses;
    }

    public static ExistedClass getExistedClass(String name) {
        return existedClasses.get(name);
    }

    public static JavaCompiler compiler() {
        return compiler.get();
    }

    public static Options options() {
        return options.get();
    }

    public static JavacProcessingEnvironment processingEnvironment() {
        return processingEnvironment.get();
    }

    public static TreeMaker maker() {
        return maker.get();
    }

    public static JavacElements elements() {
        return elements.get();
    }

    public static ExistedClass mainClass() {
        return mainClass.get();
    }

    public static ExistedMethod mainMethod() {
        return mainMethod.get();
    }

    public static ExistedMethod configureMethod() {
        return configureMethod.get();
    }

    public static GeneratorClassLoader classLoader() {
        return classLoader.get();
    }

    public static void initialize(GenerationContextConfiguration configuration) {
        if (initialized.compareAndSet(false, true)) {
            GenerationContext.existedClasses = configuration.getExistedClasses();
            GenerationContext.processingEnvironment.set(configuration.getProcessingEnvironment());
            GenerationContext.options.set(configuration.getOptions());
            GenerationContext.compiler.set(configuration.getCompiler());
            GenerationContext.maker.set(configuration.getMaker());
            GenerationContext.elements.set(configuration.getElements());
            GenerationContext.mainClass.set(configuration.getMainClass());
            GenerationContext.mainMethod.set(configuration.getMainMethod());
            GenerationContext.configureMethod.set(configuration.getConfigureMethod());
            GenerationContext.classLoader.set(new GeneratorClassLoader());
        }
    }
}
