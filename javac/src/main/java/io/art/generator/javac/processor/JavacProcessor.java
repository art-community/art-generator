package io.art.generator.javac.processor;

import com.google.auto.service.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.main.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.javac.context.*;
import io.art.generator.javac.context.GenerationContextConfiguration.*;
import io.art.generator.javac.scanner.Scanner;
import static io.art.generator.javac.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.service.GenerationService.*;
import static javax.lang.model.SourceVersion.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.util.*;

@AutoService(javax.annotation.processing.Processor.class)
@SupportedAnnotationTypes(MODULE_ANNOTATION_NAME)
@SupportedSourceVersion(RELEASE_8)
public class JavacProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private JavacProcessingEnvironment processingEnvironment;
    private final GenerationContextConfigurationBuilder configurationBuilder = GenerationContextConfiguration.builder();

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = (JavacProcessingEnvironment) processingEnvironment;
        trees = (JavacTrees) Trees.instance(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            initialize(configurationBuilder.build());
            generate();
            return false;
        }
        JavacElements elements = JavacElements.instance(processingEnvironment.getContext());
        configurationBuilder
                .options(Options.instance(processingEnvironment.getContext()))
                .processingEnvironment(processingEnvironment)
                .compiler(JavaCompiler.instance(processingEnvironment.getContext()))
                .elements(elements)
                .maker(TreeMaker.instance(processingEnvironment.getContext()));
        Scanner scanner = new Scanner(elements, configurationBuilder);
        for (Element rootElement : roundEnvironment.getRootElements()) {
            scanner.scan(trees.getPath(rootElement), trees);
        }
        return false;
    }
}
