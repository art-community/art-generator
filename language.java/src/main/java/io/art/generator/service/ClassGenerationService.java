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
import static io.art.core.extensions.FileExtensions.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.factory.CompilationUnitFactory.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
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
        FileObject file = generatedClasses().get(className);
        if (nonNull(file)) {
            writeSource(packageName, definitions, file.getName());
            return;
        }
        file = processingEnvironment().getFiler().createSourceFile(className);
        putGeneratedClass(className, file);
        writeSource(packageName, definitions, file.getName());
    }

    @SneakyThrows
    public void closePendingSources() {
        for (FileObject fileObject : generatedClasses().values()) {
            String content = readFile(fileObject.getName());
            try (Writer writer = fileObject.openWriter()) {
                writer.write(content);
            }
        }
        clearGeneratedClasses();
    }

    @SneakyThrows
    private void writeSource(String packageName, ListBuffer<JCTree> definitions, String path) {
        StringWriter stringWriter = new StringWriter();
        new PrettyWriter(stringWriter).printExpr(createCompilationUnit(packageName, definitions.toList()));
        writeFile(path, new Formatter().formatSourceAndFixImports(stringWriter.toString()));
        success(format(GENERATED_CLASS, path));
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
