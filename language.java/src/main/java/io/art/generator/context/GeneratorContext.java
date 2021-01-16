package io.art.generator.context;

import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.constants.*;
import io.art.generator.loader.*;
import io.art.generator.logger.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import static io.art.core.factory.MapFactory.*;
import java.util.concurrent.atomic.*;

public class GeneratorContext {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private static JavaCompiler compiler;

    private static JavacProcessingEnvironment processingEnvironment;

    private static Options options;

    private static TreeMaker maker;

    private static JavacElements elements;

    private static GeneratorClassLoader classLoader;

    private static Language language;

    private static CompilationService compilationService;

    private static GeneratorLogger logger;

    private static ImmutableMap<String, ExistedClass> existedClasses;

    private static ImmutableMap<String, ExistedClass> moduleClasses;

    public static ImmutableMap<String, ExistedClass> existedClasses() {
        return existedClasses;
    }

    public static ImmutableMap<String, ExistedClass> moduleClasses() {
        return moduleClasses;
    }

    public static ExistedClass existedClass(String name) {
        return existedClasses.get(name);
    }

    public static boolean isInitialized() {
        return initialized.get();
    }

    public static JavaCompiler compiler() {
        return GeneratorContext.compiler;
    }

    public static JavacProcessingEnvironment processingEnvironment() {
        return GeneratorContext.processingEnvironment;
    }

    public static Options options() {
        return GeneratorContext.options;
    }

    public static TreeMaker maker() {
        return GeneratorContext.maker;
    }

    public static JavacElements elements() {
        return GeneratorContext.elements;
    }

    public static GeneratorClassLoader classLoader() {
        return GeneratorContext.classLoader;
    }

    public static Language language() {
        return GeneratorContext.language;
    }

    public static CompilationService compilationService() {
        return GeneratorContext.compilationService;
    }

    public static GeneratorLogger logger() {
        return GeneratorContext.logger;
    }

    public static void initialize(GeneratorContextConfiguration configuration) {
        if (initialized.compareAndSet(false, true)) {
            processingEnvironment = configuration.getProcessingEnvironment();
            options = configuration.getOptions();
            compiler = configuration.getCompiler();
            maker = configuration.getMaker();
            elements = configuration.getElements();
            compilationService = configuration.getCompilationService();
            language = configuration.getLanguage();
            logger = configuration.getLogger();
            classLoader = new GeneratorClassLoader();
            existedClasses = immutableMapOf(configuration.getExistedClasses());
            moduleClasses = immutableMapOf(configuration.getModuleClasses());
        }
    }
}
