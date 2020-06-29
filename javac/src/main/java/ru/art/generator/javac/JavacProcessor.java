package ru.art.generator.javac;

import com.google.auto.service.*;
import com.sun.source.util.*;
import static javax.lang.model.SourceVersion.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.util.*;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(RELEASE_8)
@AutoService(Processor.class)
public class JavacProcessor extends AbstractProcessor {
    private final MainMethodScanner scanner = new MainMethodScanner();
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        trees = Trees.instance(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            return true;
        }
        
        for (Element element : roundEnvironment.getRootElements()) {
            scanner.scan(trees.getPath(element), trees);
        }

        return true;
    }
}
