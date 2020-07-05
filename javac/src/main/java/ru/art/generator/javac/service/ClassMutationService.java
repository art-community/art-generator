package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.source.tree.Tree.Kind.*;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import java.util.*;

@UtilityClass
public class ClassMutationService {
    public void addInnerClass(ExistedClass existedClass, NewClass inner) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(existedClass.getDeclaration().defs);
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();

        ListBuffer<JCTree> newPackageDefinitions = new ListBuffer<>();
        List<JCTree> currentPackageDefinitions = existedClass.getPackageUnit().defs;
        newPackageDefinitions.addAll(currentPackageDefinitions.stream().filter(definition -> definition.getKind() != CLASS).collect(toList()));
        newPackageDefinitions.addAll(inner.imports().stream().map(newImport -> maker().Import(maker().Select(maker().Ident(elements().getName(newImport.getPackagePart())),
                elements().getName(newImport.getImportPart())),
                newImport.isAsStatic())).collect(toList()));
        newPackageDefinitions.add(currentPackageDefinitions.last());
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
    }

    public void addFields(ExistedClass existedClass, Collection<NewField> fields) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(existedClass.getDeclaration().defs);
        classDefinitions.addAll(fields.stream().map(NewField::generate).collect(toList()));
        existedClass.getDeclaration().defs = classDefinitions.toList();

        ListBuffer<JCTree> newPackageDefinitions = new ListBuffer<>();
        List<JCTree> currentPackageDefinitions = existedClass.getPackageUnit().defs;
        newPackageDefinitions.addAll(currentPackageDefinitions.stream().filter(definition -> definition.getKind() != CLASS).collect(toList()));
        newPackageDefinitions.addAll(fields.stream()
                .map(NewField::type)
                .filter(type -> type.isHasPackage() && !type.isJdk())
                .map(type -> importClass(type.getTypeFullName()))
                .map(newImport -> maker().Import(maker().Select(
                        maker().Ident(elements().getName(newImport.getPackagePart())), elements().getName(newImport.getImportPart())),
                        newImport.isAsStatic()))
                .collect(toList()));
        newPackageDefinitions.add(currentPackageDefinitions.last());
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
        System.out.println(existedClass.getPackageUnit());
    }

    public void addMethod(ExistedClass existedClass, NewMethod method) {
        addMethods(existedClass, singletonList(method));
    }

    public void addMethods(ExistedClass existedClass, Collection<NewMethod> methods) {
        maker().at(existedClass.getDeclaration().pos);
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(existedClass.getDeclaration().defs);
        classDefinitions.addAll(methods.stream().map(NewMethod::generate).collect(toList()));
        existedClass.getDeclaration().defs = classDefinitions.toList();

        ListBuffer<JCTree> newPackageDefinitions = new ListBuffer<>();
        List<JCTree> currentPackageDefinitions = existedClass.getPackageUnit().defs;
        newPackageDefinitions.addAll(currentPackageDefinitions.stream().filter(definition -> definition.getKind() != CLASS).collect(toList()));
        newPackageDefinitions.addAll(methods.stream()
                .map(NewMethod::returnType)
                .filter(type -> type.isHasPackage() && !type.isJdk())
                .map(type -> importClass(type.getTypeFullName()))
                .map(newImport -> maker().Import(maker().Select(
                        maker().Ident(elements().getName(newImport.getPackagePart())), elements().getName(newImport.getImportPart())),
                        newImport.isAsStatic()))
                .collect(toList()));
        newPackageDefinitions.add(currentPackageDefinitions.last());
        existedClass.getPackageUnit().defs = newPackageDefinitions.toList();
        System.out.println(existedClass.getPackageUnit());
    }
}
