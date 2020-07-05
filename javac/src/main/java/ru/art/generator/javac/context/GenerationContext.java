package ru.art.generator.javac.context;

import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import ru.art.generator.javac.model.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class GenerationContext {
    private final static ReentrantLock lock = new ReentrantLock();

    private static final AtomicBoolean initialized = new AtomicBoolean();

    private static final AtomicReference<JavaCompiler> compiler = new AtomicReference<>();

    private static final AtomicReference<JavacProcessingEnvironment> processingEnvironment = new AtomicReference<>();

    private static final AtomicReference<Options> options = new AtomicReference<>();

    private static final AtomicReference<TreeMaker> maker = new AtomicReference<>();

    private static final AtomicReference<JavacElements> elements = new AtomicReference<>();

    private static final AtomicReference<ExistedClass> mainClass = new AtomicReference<>();

    private static final AtomicReference<ExistedMethod> mainMethod = new AtomicReference<>();

    private static final ConcurrentHashMap<String, ExistedClass> existedClasses = new ConcurrentHashMap<>();

    public static boolean initialized() {
        return initialized.get();
    }

    public static void putExistedClass(String name, ExistedClass existedClass) {
        existedClasses.put(name, existedClass);
    }

    public static Map<String, ExistedClass> getExistedClasses() {
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

    public static void initialize(GenerationContextInitializer initializer) {
        if (initialized()) {
            return;
        }
        lock.lock();
        if (initialized()) {
            return;
        }
        GenerationContext.processingEnvironment.set(initializer.getProcessingEnvironment());
        GenerationContext.options.set(initializer.getOptions());
        GenerationContext.compiler.set(initializer.getCompiler());
        GenerationContext.maker.set(initializer.getMaker());
        GenerationContext.elements.set(initializer.getElements());
        GenerationContext.mainClass.set(initializer.getMainClass());
        GenerationContext.mainMethod.set(initializer.getMainMethod());
        initialized.set(true);
        lock.unlock();
    }
}
