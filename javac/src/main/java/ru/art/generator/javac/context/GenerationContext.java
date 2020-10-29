package ru.art.generator.javac.context;

import com.google.common.collect.*;
import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import ru.art.generator.javac.loader.*;
import ru.art.generator.javac.model.*;
import java.util.*;
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

    private static final ConcurrentHashMap<String, ExistedClass> existedClasses = new ConcurrentHashMap<>();

    public static void putExistedClass(String name, ExistedClass existedClass) {
        existedClasses.put(name, existedClass);
    }

    public static ImmutableMap<String, ExistedClass> getExistedClasses() {
        return ImmutableMap.copyOf(existedClasses);
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

    public static void initialize(GenerationContextInitializer initializer) {
        if (initialized.compareAndSet(false, true)) {
            GenerationContext.processingEnvironment.set(initializer.getProcessingEnvironment());
            GenerationContext.options.set(initializer.getOptions());
            GenerationContext.compiler.set(initializer.getCompiler());
            GenerationContext.maker.set(initializer.getMaker());
            GenerationContext.elements.set(initializer.getElements());
            GenerationContext.mainClass.set(initializer.getMainClass());
            GenerationContext.mainMethod.set(initializer.getMainMethod());
            GenerationContext.configureMethod.set(initializer.getConfigureMethod());
            GenerationContext.classLoader.set(new GeneratorClassLoader());;
        }
    }
}
