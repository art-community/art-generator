package io.art.generator.constants;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.wrapper.*;
import lombok.experimental.*;

import java.lang.reflect.*;

import static io.art.core.constants.CompilerSuppressingWarnings.*;
import static javax.lang.model.SourceVersion.*;

@UtilityClass
@SuppressWarnings(ALL)
public class CompatibilityConstants {
    public static Method TOP_LEVEL_METHOD;
    public static Method PACKAGE_DECL_METHOD;

    static {
        if (latest() == RELEASE_8) {
            TOP_LEVEL_METHOD = ExceptionWrapper.wrapExceptionCall(() -> TreeMaker.class.getMethod("TopLevel", List.class, JCTree.JCExpression.class, List.class));
        }
        if (latest().compareTo(RELEASE_8) > 0) {
            PACKAGE_DECL_METHOD = ExceptionWrapper.wrapExceptionCall(() -> TreeMaker.class.getMethod("PackageDecl", List.class, JCTree.JCExpression.class));
            TOP_LEVEL_METHOD = ExceptionWrapper.wrapExceptionCall(() -> TreeMaker.class.getMethod("TopLevel", List.class));
        }
    }
}
