package ru.art.generator.javac.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.*;
import ru.art.generator.javac.context.GenerationContext.*;
import ru.art.generator.javac.exception.*;
import ru.art.generator.javac.model.*;
import static com.sun.source.tree.Tree.Kind.METHOD;
import static com.sun.tools.javac.code.TypeTag.*;
import static javax.lang.model.element.Modifier.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.factory.CollectionsFactory.*;

public class MainScanner extends TreePathScanner<Object, Trees> {
    private final TreeMaker maker;
    private final JavacElements elements;

    public MainScanner(TreeMaker maker, JavacElements elements) {
        this.maker = maker;
        this.elements = elements;
    }

    @Override
    public Object visitClass(ClassTree node, Trees trees) {
        Object result = super.visitClass(node, trees);

        JCClassDecl classDeclaration = (JCClassDecl) node;

        JCMethodDecl methodDeclaration = classDeclaration.getMembers()
                .stream()
                .filter(member -> member.getKind() == METHOD)
                .map(member -> (JCMethodDecl) member)
                .filter(method -> method.getModifiers().getFlags().containsAll(setOf(STATIC, PUBLIC)))
                .filter(method -> method.getReturnType().type.getTag().equals(VOID))
                .findFirst()
                .orElseThrow(() -> new GenerationException("Main method was not found"));

        ExistedMethod mainMethod = ExistedMethod.builder()
                .declaration(methodDeclaration)
                .name(MAIN_METHOD_NAME)
                .build();

        ExistedClass mainClass = ExistedClass.builder()
                .declaration(classDeclaration)
                .method(MAIN_METHOD_NAME, mainMethod)
                .packageUnit(elements.getTreeAndTopLevel(classDeclaration.sym, null, null).snd)
                .build();

        initialize(GenerationContextInitializer.builder()
                .mainClass(mainClass)
                .mainMethod(mainMethod)
                .elements(elements)
                .maker(maker)
                .build());

        return result;
    }
}
