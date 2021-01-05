package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@Getter
@Builder
public class ExistedMethod {
    private final String name;
    private final JCMethodDecl declaration;

    @Override
    public boolean equals(Object other) {
        if (isNull(other)) return false;
        if (!(other instanceof ExistedMethod)) return false;
        return declaration.toString().equals(((ExistedMethod) other).declaration.toString());
    }

    @Override
    public int hashCode() {
        return declaration.toString().hashCode();
    }
}
