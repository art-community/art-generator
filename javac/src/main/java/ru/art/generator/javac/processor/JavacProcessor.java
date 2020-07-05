package ru.art.generator.javac.processor;

import com.google.auto.service.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import ru.art.generator.javac.annotation.*;
import ru.art.generator.javac.context.*;
import ru.art.generator.javac.scanner.*;
import static javax.lang.model.SourceVersion.*;
import static ru.art.generator.javac.context.GenerationContext.initialized;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("ru.art.generator.javac.annotation.Module")
@SupportedSourceVersion(RELEASE_8)
public class JavacProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private JavacProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = (JavacProcessingEnvironment) processingEnvironment;
        trees = (JavacTrees) Trees.instance(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (initialized()) {
            return true;
        }
        TreeMaker maker = TreeMaker.instance(processingEnvironment.getContext());
        JavacElements elements = JavacElements.instance(processingEnvironment.getContext());
        for (Element rootElement : roundEnvironment.getRootElements()) {
            if (rootElement.getAnnotation(Module.class) != null) {
                new MainScanner(maker, elements).scan(trees.getPath(rootElement), trees);
                return true;
            }
        }
        return true;
    }
}
