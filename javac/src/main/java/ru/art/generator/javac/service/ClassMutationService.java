package ru.art.generator.javac.service;

import com.sun.source.tree.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.source.tree.Tree.Kind.CLASS;
import static java.util.stream.Collectors.toList;
import static ru.art.generator.javac.context.GenerationContext.elements;
import static ru.art.generator.javac.context.GenerationContext.maker;

@UtilityClass
public class ClassMutationService {
    public void addInnerClass(ExistedClass existedClass, NewClass inner) {
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
}
