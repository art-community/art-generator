package ru.art.generator.javac;

import com.google.auto.service.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;

public class MainMethodScanner extends TreePathScanner<Object, Trees> {
    @Override
    public Object visitMethod(MethodTree node, Trees trees) {
        System.out.println(node.getName());
        return super.visitMethod(node, trees);
    }
}
