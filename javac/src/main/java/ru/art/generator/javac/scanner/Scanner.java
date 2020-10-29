package ru.art.generator.javac.scanner;

import com.google.common.collect.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import lombok.*;
import ru.art.generator.javac.context.GenerationContextInitializer.*;
import ru.art.generator.javac.model.*;
import ru.art.generator.javac.model.ExistedClass.*;
import static com.sun.source.tree.Tree.Kind.METHOD;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.code.TypeTag.*;
import static io.art.core.constants.StringConstants.*;
import static java.util.Objects.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.*;
import static ru.art.generator.javac.constants.GeneratorConstants.Annotations.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
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
        ExistedClass existedClass = ExistedClass.builder()
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
                .build();
        putExistedClass(packageUnit.getPackageName().toString() + DOT + classDeclaration.name.toString(), existedClass);

        List<JCAnnotation> annotations = classDeclaration.getModifiers().getAnnotations();
        if (isNull(annotations) || annotations.isEmpty()) {
            return result;
        }

        if (isNull(mainClass()) && hasAnnotation(annotations, MODULE_ANNOTATION_NAME)) {
            Optional<JCMethodDecl> mainMethodDeclaration = classDeclaration.getMembers()
                    .stream()
                    .filter(member -> member.getKind() == METHOD)
                    .map(member -> (JCMethodDecl) member)
                    .filter(method -> method.getName().toString().equals(MAIN_METHOD_NAME))
                    .filter(method -> method.getModifiers().getFlags().containsAll(ImmutableSet.of(STATIC, PUBLIC)))
                    .filter(method -> nonNull(method.getReturnType()))
                    .filter(method -> nonNull(method.getReturnType().type))
                    .filter(method -> nonNull(method.getReturnType().type.getTag()))
                    .filter(method -> method.getReturnType().type.getTag().equals(VOID))
                    .findFirst();
            Optional<JCMethodDecl> configureMethodDeclaration = classDeclaration.getMembers()
                    .stream()
                    .filter(member -> member.getKind() == METHOD)
                    .map(member -> (JCMethodDecl) member)
                    .filter(method -> hasAnnotation(method.getModifiers().getAnnotations(), CONFIGURATOR_ANNOTATION_NAME))
                    .filter(method -> method.getModifiers().getFlags().containsAll(ImmutableSet.of(STATIC, PUBLIC)))
                    .findFirst();
            ExistedClassBuilder mainClass = ExistedClass.builder()
                    .name(classDeclaration.name.toString())
                    .declaration(classDeclaration)
                    .packageUnit(packageUnit);
            if (mainMethodDeclaration.isPresent()) {
                ExistedMethod mainMethod = ExistedMethod.builder()
                        .declaration(mainMethodDeclaration.get())
                        .name(mainMethodDeclaration.get().name.toString())
                        .build();
                initializerBuilder.mainMethod(mainMethod);
                mainClass.method(mainMethod.getName(), mainMethod);
            }
            if (configureMethodDeclaration.isPresent()) {
                ExistedMethod configureMethod = ExistedMethod.builder()
                        .declaration(configureMethodDeclaration.get())
                        .name(configureMethodDeclaration.get().name.toString())
                        .build();
                initializerBuilder.configureMethod(configureMethod);
                mainClass.method(configureMethod.getName(), configureMethod);
            }
            initialize(initializerBuilder.mainClass(mainClass.build()).build());
        }

        return result;
    }

    private boolean hasAnnotation(List<JCAnnotation> annotations, String annotationName) {
        return annotations
                .stream()
                .map(JCAnnotation::getAnnotationType)
                .filter(Objects::nonNull)
                .map(annotation -> ((JCIdent) annotation).sym)
                .filter(Objects::nonNull)
                .map(Symbol::getQualifiedName)
                .filter(Objects::nonNull)
                .anyMatch(name -> name.toString().equals(annotationName));
    }

}
