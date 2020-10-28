package ru.art.generator.javac.service;

import io.art.model.module.*;
import lombok.*;
import lombok.experimental.*;
import ru.art.generator.javac.exception.*;
import static com.sun.tools.javac.main.Option.*;
import static java.util.Arrays.*;
import static ru.art.generator.javac.constants.Constants.Annotations.*;
import static ru.art.generator.javac.constants.Constants.ExceptionMessages.*;
import static ru.art.generator.javac.constants.Constants.ProcessorOptions.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.CompileService.*;
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
        recompile();
        URL[] urls = new URL[]{new File(options().get(D)).toURI().toURL()};
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


    private void generateClassMethodNames() {
        getExistedClasses()
                .values()
                .forEach(existed -> addFields(existed, methodNames(existed).toClass().fields().values()));
    }
}
