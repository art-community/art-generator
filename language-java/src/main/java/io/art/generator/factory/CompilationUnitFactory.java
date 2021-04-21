package io.art.generator.factory;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.*;
import lombok.experimental.*;

import static com.sun.tools.javac.tree.JCTree.*;
import static com.sun.tools.javac.util.List.*;
import static io.art.core.checker.EmptinessChecker.isEmpty;
import static io.art.core.extensions.CollectionExtensions.*;
import static io.art.generator.constants.CompatibilityConstants.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.service.JavacService.*;
import static javax.lang.model.SourceVersion.*;

@UtilityClass
public class CompilationUnitFactory {
    public JCCompilationUnit createCompilationUnit(String packageName, List<JCTree> definitions) {
        if (latest() == RELEASE_8) {
            return createLegacyCompilationUnit(packageName, definitions);
        }
        return createLatestCompilationUnit(packageName, definitions);
    }

    @SneakyThrows
    private JCCompilationUnit createLegacyCompilationUnit(String packageName, List<JCTree> definitions) {
        if (isEmpty(packageName)) return (JCCompilationUnit) TOP_LEVEL_METHOD.invoke(maker(), nil(), null, definitions);
        return (JCCompilationUnit) TOP_LEVEL_METHOD.invoke(maker(), nil(), ident(packageName), definitions);
    }

    @SneakyThrows
    private JCCompilationUnit createLatestCompilationUnit(String packageName, List<JCTree> definitions) {
        if (isEmpty(packageName)) return (JCCompilationUnit) TOP_LEVEL_METHOD.invoke(maker(), definitions);
        JCTree packageDeclaration = (JCTree) PACKAGE_DECL_METHOD.invoke(maker(), nil(), ident(packageName));
        List<JCTree> arguments = from(addFirstToList(packageDeclaration, definitions));
        return (JCCompilationUnit) TOP_LEVEL_METHOD.invoke(maker(), arguments);
    }
}
