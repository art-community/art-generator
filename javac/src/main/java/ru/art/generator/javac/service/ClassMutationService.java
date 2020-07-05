package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.context.GenerationContext.*;

@UtilityClass
public class ClassMutationService {
    public void addInnerClass(ExistedClass existedClass, NewClass inner) {
        ListBuffer<JCTree> classDefinitions = new ListBuffer<>();
        classDefinitions.addAll(existedClass.getDeclaration().defs);
        classDefinitions.add(inner.generate());
        existedClass.getDeclaration().defs = classDefinitions.toList();

        ListBuffer<JCTree> packageDefinitions = new ListBuffer<>();
        packageDefinitions.add(existedClass.getPackageUnit().getPackageName());
        packageDefinitions.addAll(existedClass.getPackageUnit().getImports());
        packageDefinitions.addAll(inner.imports().stream().map(newImport -> maker().Import(maker().Select(maker().Ident(elements().getName(newImport.getPackagePart())),
                elements().getName(newImport.getImportPart())),
                newImport.isAsStatic())).collect(toList()));
        existedClass.getPackageUnit().defs = packageDefinitions.toList();
    }
}
