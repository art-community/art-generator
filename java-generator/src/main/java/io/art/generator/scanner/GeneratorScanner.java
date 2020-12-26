package io.art.generator.scanner;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.model.*;
import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.context.GeneratorContextConfiguration.*;
import io.art.generator.model.*;
import lombok.*;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.code.Symbol.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.util.Objects.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.*;
import java.util.*;

@RequiredArgsConstructor
public class GeneratorScanner extends TreePathScanner<Object, Trees> {
    private ExistedClass mainClass;
    private final JavacElements elements;
    @Getter
    private final GeneratorContextConfigurationBuilder configurationBuilder;

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
        String existedClassName = packageUnit.getPackageName().toString() + DOT + classDeclaration.name.toString();
        configurationBuilder.exitedClass(existedClassName, existedClass);

        Optional<JCMethodDecl> modelMethodDeclaration = classDeclaration.getMembers()
                .stream()
                .filter(member -> member.getKind() == METHOD)
                .map(member -> (JCMethodDecl) member)
                .filter(method -> hasModelerAnnotation(method.getModifiers().getAnnotations()))
                .filter(method -> method.getModifiers().getFlags().containsAll(setOf(PUBLIC, STATIC)))
                .findFirst();

        if (isNull(mainClass) && modelMethodDeclaration.isPresent()) {
            Optional<JCMethodDecl> mainMethodDeclaration = classDeclaration.getMembers()
                    .stream()
                    .filter(member -> member.getKind() == METHOD)
                    .map(member -> (JCMethodDecl) member)
                    .filter(method -> method.getName().toString().equals(MAIN_NAME))
                    .filter(method -> method.getModifiers().getFlags().containsAll(setOf(STATIC, PUBLIC)))
                    .filter(method -> nonNull(method.getReturnType()))
                    .filter(method -> nonNull(method.getReturnType().type))
                    .filter(method -> nonNull(method.getReturnType().type.getTag()))
                    .filter(method -> method.getReturnType().type.getTag().equals(TypeTag.VOID))
                    .findFirst();

            ExistedClass.ExistedClassBuilder mainClassBuilder = ExistedClass.builder()
                    .name(classDeclaration.name.toString())
                    .declaration(classDeclaration)
                    .packageUnit(packageUnit);

            if (mainMethodDeclaration.isPresent()) {
                ExistedMethod mainMethod = ExistedMethod.builder()
                        .declaration(mainMethodDeclaration.get())
                        .name(mainMethodDeclaration.get().name.toString())
                        .build();
                configurationBuilder.mainMethod(mainMethod);
                mainClassBuilder.method(mainMethod.getName(), mainMethod);
            }

            ExistedMethod modelMethod = ExistedMethod.builder()
                    .declaration(modelMethodDeclaration.get())
                    .name(modelMethodDeclaration.get().name.toString())
                    .build();

            configurationBuilder.modelMethod(modelMethod);
            mainClassBuilder.method(modelMethod.getName(), modelMethod);

            configurationBuilder.mainClass(this.mainClass = mainClassBuilder.build());
        }
        return result;
    }

    private boolean hasModelerAnnotation(List<JCAnnotation> annotations) {
        return annotations
                .stream()
                .map(JCAnnotation::getAnnotationType)
                .filter(Objects::nonNull)
                .map(annotation -> annotation.type)
                .filter(Objects::nonNull)
                .map(Type::asElement)
                .filter(Objects::nonNull)
                .map(TypeSymbol::getQualifiedName)
                .filter(Objects::nonNull)
                .anyMatch(name -> name.toString().equals(CONFIGURATOR_ANNOTATION_NAME));
    }

}
