package ru.art.generator.javac;

import com.google.auto.service.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import ru.art.generator.javac.scanner.*;
import static javax.lang.model.SourceVersion.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.util.*;

@SupportedAnnotationTypes({"ru.art.generator.javac.Module"})
@SupportedSourceVersion(RELEASE_8)
@AutoService(Processor.class)
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
        TreeMaker maker = TreeMaker.instance(processingEnvironment.getContext());
        JavacElements elements = JavacElements.instance(processingEnvironment.getContext());
        for (Element rootElement : roundEnvironment.getRootElements()) {
            new MethodScanner(maker, elements).scan(trees.getPath(rootElement), trees);
            return true;
        }
        return true;
    }
}
