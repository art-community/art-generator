package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;

@Getter
@Builder
public class ExistedMethod {
    private final String name;
    private final JCMethodDecl declaration;
}