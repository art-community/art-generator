package io.art.generator.model;

import com.sun.tools.javac.tree.*;

import java.util.*;

public interface GeneratedClass {
    String name();
    Set<ImportModel> imports();
    JCTree.JCClassDecl generate();
}
