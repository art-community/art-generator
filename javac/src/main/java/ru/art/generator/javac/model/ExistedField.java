package ru.art.generator.javac.model;

import com.sun.tools.javac.tree.*;
import lombok.*;

@Getter
@Builder
public class ExistedField {
    private final String name;
    private final JCTree.JCVariableDecl declaration;
}
