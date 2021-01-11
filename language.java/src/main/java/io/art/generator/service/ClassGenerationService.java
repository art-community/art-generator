package io.art.generator.service;

import com.google.googlejavaformat.java.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.generator.writer.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.factory.CompilationUnitFactory.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static java.text.MessageFormat.*;
import javax.tools.*;
import java.io.*;

@UtilityClass
public class ClassGenerationService {
    @SneakyThrows
    public void generateClass(NewClass newClass, String packageName) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.addAll(createImports(immutableSetOf(newClass.imports())));
        definitions.add(newClass.generate());
        String className = packageName + DOT + newClass.name();
        JavaFileObject classFile = processingEnvironment().getFiler().createSourceFile(className);
        StringWriter stringWriter = new StringWriter();
        new PrettyWriter(stringWriter).printExpr(createCompilationUnit(packageName, definitions.toList()));
        try (Writer writer = classFile.openWriter()) {
            Formatter formatter = new Formatter();
            writer.write(formatter.formatSourceAndFixImports(stringWriter.toString()));
        }
        success(format(GENERATED_CLASS, className));
    }

    private ListBuffer<JCTree> createImports(ImmutableSet<ImportModel> newImports) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
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
                .forEach(definitions::add);
        return definitions;
    }
}