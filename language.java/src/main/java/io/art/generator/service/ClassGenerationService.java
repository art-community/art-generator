package io.art.generator.service;

import com.google.googlejavaformat.java.Formatter;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.ListBuffer;
import io.art.core.collection.ImmutableSet;
import io.art.generator.model.GeneratedClass;
import io.art.generator.model.ImportModel;
import io.art.generator.writer.PrettyWriter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.tools.FileObject;
import java.io.StringWriter;
import java.io.Writer;

import static io.art.core.constants.StringConstants.DOT;
import static io.art.core.extensions.FileExtensions.readFile;
import static io.art.core.extensions.FileExtensions.writeFile;
import static io.art.core.factory.SetFactory.immutableSetOf;
import static io.art.generator.constants.LoggingMessages.GENERATED_CLASS;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.factory.CompilationUnitFactory.createCompilationUnit;
import static io.art.generator.logger.GeneratorLogger.success;
import static io.art.generator.state.GeneratorState.*;
import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;

@UtilityClass
public class ClassGenerationService {
    @SneakyThrows
    public void generateClass(GeneratedClass generatedClass, String packageName) {
        ListBuffer<JCTree> definitions = collectDefinitions(generatedClass);
        String className = packageName + DOT + generatedClass.name();
        writeSource(packageName, definitions, getClassFile(className).getName());
    }

    public void generateStubClass(GeneratedClass generatedClass, String packageName){
        ListBuffer<JCTree> definitions = collectDefinitions(generatedClass);
        String className = packageName + DOT + generatedClass.name();
        writeSource(packageName, definitions, getStubFile(className).getName());
    }

    private ListBuffer<JCTree> collectDefinitions(GeneratedClass generatedClass){
        ListBuffer<JCTree> definitions = new ListBuffer<>();
        definitions.addAll(createImports(immutableSetOf(generatedClass.imports())));
        definitions.add(generatedClass.generate());
        return definitions;
    }

    @SneakyThrows
    private FileObject getClassFile(String className){
        FileObject file = generatedClasses().get(className);
        if (nonNull(file)) {
            return file;
        }
        file = processingEnvironment().getFiler().createSourceFile(className);
        putGeneratedClass(className, file);
        return file;
    }

    private FileObject getStubFile(String className){
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
    public void flushFile(String name){
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
