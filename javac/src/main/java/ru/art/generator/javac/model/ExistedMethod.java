package ru.art.generator.javac.model;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;

@Builder
public class ExistedMethod {
    private final String name;
    private final JCMethodDecl declaration;
}
