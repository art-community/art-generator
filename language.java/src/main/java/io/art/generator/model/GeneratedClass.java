package io.art.generator.model;

import com.sun.tools.javac.tree.JCTree;
import io.art.core.collection.ImmutableSet;

import java.util.Set;

public interface GeneratedClass {
    String name();
    Set<ImportModel> imports();
    JCTree.JCClassDecl generate();
}
