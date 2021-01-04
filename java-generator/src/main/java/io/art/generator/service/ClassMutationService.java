package io.art.generator.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.core.factory.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.source.tree.Tree.Kind.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.extensions.CollectionExtensions.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@UtilityClass
public class ClassMutationService {
    public void replaceFields(ExistedClass existedClass, Collection<NewField> fields) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        ListBuffer<JCTree> definitions = filterDefinitions(existedClass,
                VARIABLE,
                definition -> fields.stream().noneMatch(field -> field.name().equals(((JCVariableDecl) definition).name.toString()))
        );
        classDefinitions.addAll(definitions);
        fields.stream().map(NewField::generate).forEach(classDefinitions::add);
        existedClass.getDeclaration().defs = classDefinitions.toList();
        ImmutableSet<ImportModel> importModels = fields.stream()
                .map(NewField::type)
                .filter(type -> !type.getPackageName().isEmpty() && !type.isJdk())
                .map(type -> classImport(type.getFullName()))
                .collect(immutableSetCollector());
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, importModels);
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceMethod(ExistedClass existedClass, NewMethod method) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(filterDefinitions(existedClass, METHOD, definition -> !((JCMethodDecl) definition).name.toString().equals(method.name())));
        classDefinitions.add(method.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        Set<ImportModel> importModels = Stream.of(method)
                .map(NewMethod::returnType)
                .filter(type -> !type.getPackageName().isEmpty() && !type.isJdk())
                .map(type -> classImport(type.getFullName()))
                .collect(toCollection(SetFactory::set));
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, immutableSetOf(combine(importModels, method.classImports())));
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceInnerInterface(ExistedClass existedClass, NewClass inner) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(filterDefinitions(existedClass, INTERFACE, definition -> !((JCClassDecl) definition).name.toString().equals(inner.name())));
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, immutableSetOf(inner.imports()));
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void replaceInnerClass(ExistedClass existedClass, NewClass inner) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(filterDefinitions(existedClass, CLASS, definition -> !((JCClassDecl) definition).name.toString().equals(inner.name())));
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();
        ListBuffer<JCTree> newPackageDefinitions = addImports(existedClass, immutableSetOf(inner.imports()));
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }


    private ListBuffer<JCTree> addImports(ExistedClass existedClass, ImmutableSet<ImportModel> newImports) {
        ListBuffer<JCTree> newPackageDefinitions = new ListBuffer<>();
        List<JCTree> currentPackageDefinitions = existedClass.getPackageUnit().defs;
        currentPackageDefinitions.stream().filter(definition -> definition.getKind() != CLASS).forEach(newPackageDefinitions::add);
        newImports
                .stream()
                .distinct()
                .map(newImport -> maker().Import(
                        maker().Select(
                                maker().Ident(elements().getName(newImport.getPackagePart())),
                                elements().getName(newImport.getImportPart())
                        ),
                        newImport.isAsStatic())
                )
                .forEach(newPackageDefinitions::add);
        newPackageDefinitions.add(currentPackageDefinitions.last());
        return newPackageDefinitions;
    }

    private ListBuffer<JCTree> filterDefinitions(ExistedClass existedClass, Kind kind, Predicate<JCTree> filter) {
        return existedClass
                .getDeclaration()
                .defs
                .stream()
                .filter(definition -> definition.getKind() != kind || filter.test(definition))
                .collect(toCollection(ListBuffer::new));
    }
}
