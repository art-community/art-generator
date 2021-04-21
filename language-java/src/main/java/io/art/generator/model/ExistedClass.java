package io.art.generator.model;

import com.sun.tools.javac.code.*;
import io.art.core.collection.*;
import lombok.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import java.util.*;

@Getter
@Builder
public class ExistedClass {
    private final String name;
    private final JCClassDecl declaration;
    private final JCCompilationUnit packageUnit;

    @Singular("method")
    private final Set<ExistedMethod> methods;

    @Singular("field")
    private final Map<String, ExistedField> fields;

    public ImmutableSet<ExistedMethod> getMethods() {
        return immutableSetOf(methods);
    }

    public ImmutableMap<String, ExistedField> getFields() {
        return immutableMapOf(fields);
    }

    public String getPackageName() {
        return emptyIfNull(packageUnit.getPackageName());
    }

    public String getFullName() {
        return letIfNotEmpty(getPackageName(), name -> name + DOT + getName(), getName());
    }

    public Type getType() {
        return declaration.sym.type;
    }

    public Class<?> asClass() {
        return classLoader().loadClass(getFullName());
    }
}
