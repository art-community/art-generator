package io.art.generator.service;

import com.google.googlejavaformat.java.Formatter;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.generator.writer.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.extensions.FileExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.factory.CompilationUnitFactory.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import static javax.tools.StandardLocation.*;
import javax.tools.*;
import java.io.*;
import java.util.*;

@UtilityClass
public class ClassGenerationService {
    private final Map<String, File> GENERATED_CLASSES = map();

    @SneakyThrows
    public void generateClass(NewClass newClass, String packageName) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.addAll(createImports(immutableSetOf(newClass.imports())));
        definitions.add(newClass.generate());
        File file = GENERATED_CLASSES.get(newClass.name());
        if (nonNull(file)) {
            writeSource(packageName, definitions, file);
            return;
        }
        FileObject resource = processingEnvironment().getFiler().createResource(SOURCE_OUTPUT, packageName, newClass.name() + JAVA_FILE_SUFFIX);
        file = new File(resource.getName());
        GENERATED_CLASSES.put(newClass.name(), file);
        writeSource(packageName, definitions, file);
    }

    @SneakyThrows
    private void writeSource(String packageName, ListBuffer<JCTree> definitions, File classFile) {
        StringWriter stringWriter = new StringWriter();
        new PrettyWriter(stringWriter).printExpr(createCompilationUnit(packageName, definitions.toList()));
        writeFile(classFile.getPath(), new Formatter().formatSourceAndFixImports(stringWriter.toString()));
        success(format(GENERATED_CLASS, classFile.getPath()));
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
