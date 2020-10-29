package ru.art.generator.javac.service;

import com.sun.source.tree.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@UtilityClass
public class ClassMutationService {
    public void replaceFields(ExistedClass existedClass, Collection<NewField> fields) {
        ListBuffer<JCTree> classDefinitions = prepareToMutation(existedClass);
        java.util.List<JCTree> definitions = filterDefinitions(existedClass,
                VARIABLE,
                definition -> fields.stream().noneMatch(field -> field.name().equals(((JCVariableDecl) definition).name.toString()))
        );
        classDefinitions.addAll(definitions);
        classDefinitions.addAll(fields.stream().map(NewField::generate).collect(toList()));
        existedClass.getDeclaration().defs = classDefinitions.toList();
        Set<ImportModel> importModels = fields.stream()
                .map(NewField::type)
                .filter(type -> !type.getPackageName().isEmpty() && !type.isJdk())
                .map(type -> importClass(type.getFullName()))
                .collect(toSet());
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, importModels);
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceMethod(ExistedClass existedClass, NewMethod method) {
        ListBuffer<JCTree> classDefinitions = prepareToMutation(existedClass);
        classDefinitions.addAll(filterDefinitions(existedClass, METHOD, definition -> ((JCMethodDecl) definition).name.toString().equals(method.name())));
        classDefinitions.add(method.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        Set<ImportModel> importModels = Stream.of(method)
                .map(NewMethod::returnType)
                .filter(type -> !type.getPackageName().isEmpty() && !type.isJdk())
                .map(type -> importClass(type.getFullName()))
                .collect(toSet());
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, importModels);
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceInnerInterface(ExistedClass existedClass, NewClass inner) {
        ListBuffer<JCTree> classDefinitions = prepareToMutation(existedClass);
        classDefinitions.addAll(filterDefinitions(existedClass, INTERFACE, definition -> ((JCClassDecl) definition).name.toString().equals(inner.name())));
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, inner.imports());
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceInnerClass(ExistedClass existedClass, NewClass inner) {
        ListBuffer<JCTree> classDefinitions = prepareToMutation(existedClass);
        classDefinitions.addAll(filterDefinitions(existedClass, CLASS, definition -> ((JCClassDecl) definition).name.toString().equals(inner.name())));
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, inner.imports());
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }


    private static ListBuffer<JCTree> prepareToMutation(ExistedClass existedClass) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(existedClass.getDeclaration().defs);
        return classDefinitions;
    }

    private static ListBuffer<JCTree> addImports(ExistedClass existedClass, Set<ImportModel> newImports) {
        ListBuffer<JCTree> newPackageDefinitions = new ListBuffer<>();
        List<JCTree> currentPackageDefinitions = existedClass.getPackageUnit().defs;
        newPackageDefinitions.addAll(currentPackageDefinitions.stream().filter(definition -> definition.getKind() != CLASS).collect(toList()));
        java.util.List<JCImport> imports = newImports
                .stream()
                .distinct()
                .map(newImport -> maker().Import(
                        maker().Select(
                                maker().Ident(elements().getName(newImport.getPackagePart())),
                                elements().getName(newImport.getImportPart())
                        ),
                        newImport.isAsStatic())
                )
                .collect(toList());
        newPackageDefinitions.addAll(imports);
        newPackageDefinitions.add(currentPackageDefinitions.last());
        return newPackageDefinitions;
    }

    private static java.util.List<JCTree> filterDefinitions(ExistedClass existedClass, Tree.Kind kind, Predicate<JCTree> filter) {
        return existedClass
                .getDeclaration()
                .defs
                .stream()
                .filter(definition -> definition.getKind() != kind || filter.test(definition))
                .collect(toList());
    }
}
