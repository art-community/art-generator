package io.art.generator.context;

import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.core.factory.*;
import io.art.generator.loader.*;
import io.art.generator.model.*;
import static io.art.core.factory.MapFactory.*;
import java.util.concurrent.atomic.*;

public class GeneratorContext {
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

    public static ImmutableMap<String, ExistedClass> existedClasses() {
        return existedClasses;
    }

    public static ExistedClass existedClass(String name) {
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

    public static void initialize(GeneratorContextConfiguration configuration) {
        if (initialized.compareAndSet(false, true)) {
            GeneratorContext.existedClasses = immutableMapOf(configuration.getExistedClasses());
            GeneratorContext.processingEnvironment.set(configuration.getProcessingEnvironment());
            GeneratorContext.options.set(configuration.getOptions());
            GeneratorContext.compiler.set(configuration.getCompiler());
            GeneratorContext.maker.set(configuration.getMaker());
            GeneratorContext.elements.set(configuration.getElements());
            GeneratorContext.mainClass.set(configuration.getMainClass());
            GeneratorContext.mainMethod.set(configuration.getMainMethod());
            GeneratorContext.configureMethod.set(configuration.getConfigureMethod());
            GeneratorContext.classLoader.set(new GeneratorClassLoader());
        }
    }
}
