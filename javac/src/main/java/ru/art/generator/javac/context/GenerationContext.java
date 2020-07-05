package ru.art.generator.javac.context;

import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.*;
import lombok.*;
import ru.art.generator.javac.model.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class GenerationContext {
    private final static ReentrantLock lock = new ReentrantLock();

    private static final AtomicBoolean initialized = new AtomicBoolean();

    private static final AtomicReference<TreeMaker> maker = new AtomicReference<>();

    private static final AtomicReference<JavacElements> elements = new AtomicReference<>();

    private static final AtomicReference<ExistedClass> mainClass = new AtomicReference<>();

    private static final AtomicReference<ExistedMethod> mainMethod = new AtomicReference<>();

    public static boolean initialized() {
        return initialized.get();
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
        lock.lock();
        GenerationContext.maker.set(initializer.maker);
        GenerationContext.elements.set(initializer.elements);
        GenerationContext.mainClass.set(initializer.mainClass);
        GenerationContext.mainMethod.set(initializer.mainMethod);
        initialized.set(true);
        lock.unlock();
    }

    @Builder
    public static class GenerationContextInitializer {
        private final TreeMaker maker;
        private final JavacElements elements;
        private final ExistedClass mainClass;
        private final ExistedMethod mainMethod;
    }
}
