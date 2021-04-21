package io.art.generator.service;

import com.google.googlejavaformat.java.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.generator.writer.*;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.FileExtensions.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.factory.CompilationUnitFactory.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.state.GeneratorState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import javax.tools.*;
import java.io.*;

@UtilityClass
public class ClassGenerationService {
    @SneakyThrows
    public void generateProviderClass(NewClass generatedClass, String packageName) {
        String className = letIfNotEmpty(packageName, name -> name + DOT + generatedClass.name(), generatedClass.name());
        generateClass(generatedClass, packageName, getClassFile(className).getName());
    }

    public void generateStubClass(StubClass generatedClass, String packageName) {
        String className = letIfNotEmpty(packageName, name -> name + DOT + generatedClass.name(), generatedClass.name());
        generateClass(generatedClass, packageName, getStubFile(className).getName());
    }

    @SneakyThrows
    public void generateProjectClass(NewClass generatedClass, String packageName) {
        String className = letIfNotEmpty(packageName, name -> name + DOT + generatedClass.name(), generatedClass.name());
        String filename = compilationService().createProjectFile(className).getName();
        generateClass(generatedClass, packageName, filename);
    }

    private void generateClass(GeneratedClass generatedClass, String packageName, String filePath) {
        ListBuffer<JCTree> definitions = collectDefinitions(generatedClass);
        writeSource(packageName, definitions, filePath);
    }

    private ListBuffer<JCTree> collectDefinitions(GeneratedClass generatedClass) {
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.addAll(createImports(immutableSetOf(generatedClass.imports())));
        definitions.add(generatedClass.generate());
        return definitions;
    }

    @SneakyThrows
    private FileObject getClassFile(String className) {
        FileObject file = generatedClasses().get(className);
        if (nonNull(file)) {
            return file;
        }
        file = processingEnvironment().getFiler().createSourceFile(className);
        putGeneratedClass(className, file);
        return file;
    }

    private FileObject getStubFile(String className) {
        FileObject file = generatedClasses().get(className);
        if (nonNull(file)) {
            return file;
        }
        file = compilationService().createStubFile(className);
        putGeneratedClass(className, file);
        return file;
    }

    @SneakyThrows
    public void flushPendingSources() {
        generatedClasses().keySet().forEach(ClassGenerationService::flushFile);
    }

    @SneakyThrows
    public void flushFile(String name) {
        FileObject fileObject = generatedClasses().get(name);
        String content = readFile(fileObject.getName());
        try (Writer writer = fileObject.openWriter()) {
            writer.write(content);
        }
        removeFromGeneratedClasses(name);
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
                .filter(newImport -> isNotEmpty(newImport.getImportPart()))
                .map(newImport -> isEmpty(newImport.getImportPart())
                        ? maker().Import(maker().Ident(elements().getName(newImport.getPackagePart())), newImport.isAsStatic())
                        : maker().Import(maker().Select(maker().Ident(elements().getName(newImport.getPackagePart())),
                        elements().getName(newImport.getImportPart())),
                        newImport.isAsStatic())
                )
                .forEach(definitions::add);
        return definitions;
    }
}
