package ru.art.generator.javac.service;

import com.google.common.collect.*;
import com.sun.tools.javac.api.*;
import io.art.model.module.*;
import lombok.*;
import lombok.experimental.*;
import ru.art.generator.javac.exception.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.main.Option.*;
import static io.art.core.constants.ArrayConstants.*;
import static io.art.core.constants.StringConstants.*;
import static java.io.File.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.constants.Constants.Annotations.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.constants.Constants.ExceptionMessages.*;
import static ru.art.generator.javac.constants.Constants.ProcessorOptions.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        generateClassMethodNames();
        if (processingEnvironment().getOptions().get(DISABLE_OPTION) != null) {
            return;
        }
        ModuleModel model = loadModel();
    }

    @SneakyThrows
    private ModuleModel loadModel() {
        JavacTool javacTool = JavacTool.create();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        ImmutableList.Builder<String> arguments = ImmutableList.builder();
        arguments.add(
                CLASSPATH_ARGUMENT,
                options().get(CLASSPATH),
                DISABLE_OPTION_ENABLED
        );
        arguments.addAll(getExistedClasses().values().stream().map(existed -> existed.getPackageUnit().getSourceFile().getName()).collect(toList()));

        javacTool.run(new ByteArrayInputStream(EMPTY_BYTES), new ByteArrayOutputStream(), errorStream, arguments.build().toArray(new String[0]));
        URL[] urls = getExistedClasses().values()
                .stream()
                .map(GenerationService::classFile)
                .distinct()
                .toArray(URL[]::new);
        URLClassLoader loader = new URLClassLoader(urls, GenerationService.class.getClassLoader());
        Class<?> mainClass = loader.loadClass(mainClass().getFullName());
        Method configuratorMethod = stream(mainClass.getMethods())
                .filter(method -> stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType().getName().equals(CONFIGURATOR_ANNOTATION_NAME)))
                .findFirst()
                .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND_EXCEPTION));
        ModuleModel model = (ModuleModel) configuratorMethod.invoke(null);
        loader.close();
        return model;
    }

    @SneakyThrows
    private URL classFile(ExistedClass existed) {
        JavaFileObject sourceFile = existed.getPackageUnit().getSourceFile();
        String path = sourceFile.getName().substring(0, sourceFile.getName().indexOf(existed.getPackageName().replace(separator, DOT)));
        return new File(path).toURI().toURL();
    }

    private void generateClassMethodNames() {
        getExistedClasses()
                .values()
                .forEach(existed -> addFields(existed, methodNames(existed).toClass().fields().values()));
    }
}
