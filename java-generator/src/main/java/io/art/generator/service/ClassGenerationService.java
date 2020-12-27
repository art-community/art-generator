package io.art.generator.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.factory.*;
import io.art.generator.model.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.generator.constants.GeneratorConstants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.service.JavacService.*;
import static java.text.MessageFormat.*;
import static java.util.stream.Collectors.*;
import javax.tools.*;
import java.io.*;
import java.util.*;

@UtilityClass
public class ClassGenerationService {
    @SneakyThrows
    public void generateClass(NewClass newClass, String packageName) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.addAll(createImports(newClass.imports()));
        definitions.add(newClass.generate());
        JCCompilationUnit compilationUnit = maker().TopLevel(List.nil(), ident(packageName), definitions.toList());
        String className = packageName + DOT + newClass.name();
        JavaFileObject classFile = processingEnvironment().getFiler().createSourceFile(className);
        try (Writer writer = classFile.openWriter()) {
            writer.write(compilationUnit.toString());
        }
        success(format(GENERATED_CLASS, className));
    }

    private ListBuffer<JCTree> createImports(Set<ImportModel> newImports) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        java.util.List<JCTree.JCImport> imports = newImports
                .stream()
                .distinct()
                .map(newImport -> maker().Import(
                        maker().Select(
                                maker().Ident(elements().getName(newImport.getPackagePart())),
                                elements().getName(newImport.getImportPart())
                        ),
                        newImport.isAsStatic())
                )
                .collect(toCollection(ArrayFactory::dynamicArray));
        definitions.addAll(imports);
        return definitions;
    }
}
