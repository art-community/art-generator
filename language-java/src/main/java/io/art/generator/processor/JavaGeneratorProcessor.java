package io.art.generator.processor;

import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.context.*;
import io.art.generator.context.GeneratorContextConfiguration.*;
import io.art.generator.logger.*;
import io.art.generator.scanner.*;
import io.art.generator.service.*;
import lombok.*;
import static io.art.core.extensions.CollectionExtensions.*;
import static io.art.generator.constants.Annotations.*;
import static io.art.generator.constants.JavaDialect.*;
import static io.art.generator.constants.ProcessorOptions.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.service.GenerationService.*;
import static io.art.generator.state.GeneratorState.*;
import static javax.lang.model.SourceVersion.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import java.util.*;


@SupportedAnnotationTypes(CONFIGURATOR_ANNOTATION_NAME)
public class JavaGeneratorProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private JavacProcessingEnvironment processingEnvironment;
    @Getter
    private final SourceVersion supportedSourceVersion = latest();
    @Getter
    private final Set<String> supportedOptions = addToSet(super.getSupportedOptions(), PROCESSOR_OPTIONS);
    private final GeneratorContextConfigurationBuilder configurationBuilder = GeneratorContextConfiguration.builder();

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = (JavacProcessingEnvironment) processingEnvironment;
        trees = (JavacTrees) Trees.instance(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (initialized()) {
            if (completed()) {
                return true;
            }
            try {
                generate();
            } finally {
                complete();
            }
            return true;
        }
        JavacElements elements = JavacElements.instance(processingEnvironment.getContext());
        configurationBuilder
                .dialect(JAVA)
                .recompilationService(new JavaRecompilationService())
                .processingEnvironment(processingEnvironment)
                .elements(elements)
                .options(Options.instance(processingEnvironment.getContext()))
                .compiler(JavaCompiler.instance(processingEnvironment.getContext()))
                .logger(new GeneratorLogger(System.out::println, System.err::println))
                .maker(TreeMaker.instance(processingEnvironment.getContext()));
        GeneratorScanner scanner = new GeneratorScanner(elements, configurationBuilder);
        for (Element rootElement : roundEnvironment.getRootElements()) {
            scanner.scan(trees.getPath(rootElement), trees);
        }
        initialize(configurationBuilder.build());
        return false;
    }
}
