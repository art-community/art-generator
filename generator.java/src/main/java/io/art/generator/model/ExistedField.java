package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;

@Getter
@Builder
public class ExistedField {
    private final String name;
    private final JCVariableDecl declaration;
}
