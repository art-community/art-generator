package ru.art.generator.javac.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import lombok.*;
import ru.art.generator.javac.context.GenerationContextInitializer.*;
import ru.art.generator.javac.model.*;
import static com.sun.source.tree.Tree.Kind.METHOD;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static io.art.core.constants.StringConstants.*;
import static java.util.Objects.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.factory.CollectionsFactory.*;
import java.util.*;

@AllArgsConstructor
public class Scanner extends TreePathScanner<Object, Trees> {
    private final JavacElements elements;
    private final GenerationContextInitializerBuilder initializerBuilder;

    @Override
    public Object visitClass(ClassTree node, Trees trees) {
        Object result = super.visitClass(node, trees);

        JCClassDecl classDeclaration = (JCClassDecl) node;

        if (isNull(classDeclaration.sym)) {
            return result;
        }

        JCCompilationUnit packageUnit = elements.getTreeAndTopLevel(classDeclaration.sym, null, null).snd;
        putExistedClass(packageUnit.getPackageName().toString() + DOT + classDeclaration.name.toString(), ExistedClass.builder()
                .name(classDeclaration.name.toString())
                .packageUnit(packageUnit)
                .declaration(classDeclaration)
                .methods(classDeclaration.getMembers()
                        .stream()
                        .filter(member -> member.getKind() == METHOD)
                        .map(member -> (JCMethodDecl) member)
                        .map(member -> ExistedMethod.builder().name(member.name.toString()).declaration(member).build())
                        .collect(toMap(ExistedMethod::getName, identity())))
                .fields(classDeclaration.getMembers()
                        .stream()
                        .filter(member -> member.getKind() == VARIABLE)
                        .map(member -> (JCVariableDecl) member)
                        .map(member -> ExistedField.builder().name(member.name.toString()).declaration(member).build())
                        .collect(toMap(ExistedField::getName, identity())))
                .build());

        List<JCAnnotation> annotations = classDeclaration.getModifiers().getAnnotations();
        if (isNull(annotations) || annotations.isEmpty()) {
            return result;
        }

        boolean hasModuleAnnotation = annotations
                .stream()
                .map(JCAnnotation::getAnnotationType)
                .filter(Objects::nonNull)
                .map(annotation -> ((JCIdent) annotation).sym)
                .filter(Objects::nonNull)
                .map(Symbol::getQualifiedName)
                .filter(Objects::nonNull)
                .anyMatch(name -> name.toString().equals(MODULE_ANNOTATION_NAME));

        if (isNull(mainClass()) && hasModuleAnnotation) {
            Optional<JCMethodDecl> mainMethodDeclaration = classDeclaration.getMembers()
                    .stream()
                    .filter(member -> member.getKind() == METHOD)
                    .map(member -> (JCMethodDecl) member)
                    .filter(method -> method.getModifiers().getFlags().containsAll(setOf(STATIC, PUBLIC)))
                    .filter(method -> nonNull(method.getReturnType()))
                    .filter(method -> nonNull(method.getReturnType().type))
                    .filter(method -> nonNull(method.getReturnType().type.getTag()))
                    .filter(method -> method.getReturnType().type.getTag().equals(VOID))
                    .findFirst();
            if (mainMethodDeclaration.isPresent()) {
                initializeContext(packageUnit, classDeclaration, mainMethodDeclaration.get());
                return result;
            }
            initializeContext(packageUnit, classDeclaration);
        }

        return result;
    }

    private void initializeContext(JCCompilationUnit packageUnit, JCClassDecl classDeclaration, JCMethodDecl methodDeclaration) {
        ExistedMethod mainMethod = ExistedMethod.builder()
                .declaration(methodDeclaration)
                .name(MAIN_METHOD_NAME)
                .build();

        ExistedClass mainClass = ExistedClass.builder()
                .name(classDeclaration.name.toString())
                .declaration(classDeclaration)
                .method(MAIN_METHOD_NAME, mainMethod)
                .packageUnit(packageUnit)
                .build();

        initialize(initializerBuilder.mainClass(mainClass).mainMethod(mainMethod).build());
    }

    private void initializeContext(JCCompilationUnit packageUnit, JCClassDecl classDeclaration) {
        ExistedClass mainClass = ExistedClass.builder()
                .name(classDeclaration.name.toString())
                .declaration(classDeclaration)
                .packageUnit(packageUnit)
                .build();

        initialize(initializerBuilder.mainClass(mainClass).build());
    }
}
