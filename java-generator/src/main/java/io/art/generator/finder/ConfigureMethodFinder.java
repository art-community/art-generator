package io.art.generator.finder;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.generator.constants.Annotations.*;
import static io.art.generator.constants.ExceptionMessages.*;
import java.util.*;

@UtilityClass
public class ConfigureMethodFinder {
    public ExistedMethod findConfigureAnnotatedMethod(ExistedClass inClass) {
        for (ExistedMethod method : inClass.getMethods()) {
            List<JCTree.JCAnnotation> annotations = method.getDeclaration().getModifiers().getAnnotations();
            boolean found = annotations
                    .stream()
                    .map(JCTree.JCAnnotation::getAnnotationType)
                    .filter(Objects::nonNull)
                    .map(annotation -> annotation.type)
                    .filter(Objects::nonNull)
                    .map(Type::asElement)
                    .filter(Objects::nonNull)
                    .map(Symbol.TypeSymbol::getQualifiedName)
                    .filter(Objects::nonNull)
                    .anyMatch(name -> name.toString().equals(CONFIGURATOR_ANNOTATION_NAME));
            if (found) {
                return method;
            }
        }
        throw new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND);
    }
}
