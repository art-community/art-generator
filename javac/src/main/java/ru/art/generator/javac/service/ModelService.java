package ru.art.generator.javac.service;

import io.art.model.identifier.*;
import io.art.model.module.*;
import lombok.*;
import lombok.experimental.*;
import ru.art.generator.javac.exception.*;
import static com.sun.tools.javac.main.Option.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static ru.art.generator.javac.constants.Constants.Annotations.*;
import static ru.art.generator.javac.constants.Constants.ExceptionMessages.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.constants.Constants.MethodNames.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewConfiguratorMethodModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.CompileService.*;
import static ru.art.generator.javac.service.MapperGenerationService.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

@UtilityClass
public class ModelService {
    private URLClassLoader loader;

    @SneakyThrows
    public void generateModelImplementation() {
        ModuleModel model = loadModel();
        for (IdentifierModel identifier : model.getServerModel().getRsocketIdentifiers()) {
            Class<?> asClass = identifier.getAsClass();
            if (nonNull(asClass)) {
                loader.loadClass(asClass.getName());
                List<Method> methods = stream(asClass
                        .getMethods())
                        .filter(method -> (method.getModifiers() & Modifier.PUBLIC) != 0)
                        .filter(method -> !IGNORING_METHODS.contains(method.getName()))
                        .collect(toList());
                for (Method method : methods) {
                    Class<?> returnType = method.getReturnType();
                    generateMappers(returnType);
                }

            }
        }
        replaceMethod(mainClass(), CONFIGURE_METHOD_NAME, configuratorMethod(model).generate());
        loader.close();
    }

    private ModuleModel loadModel() {
        recompile();
        try {
            URL[] urls = new URL[]{new File(options().get(D)).toURI().toURL()};
            loader = new URLClassLoader(urls, GenerationService.class.getClassLoader());
            Class<?> mainClass = loader.loadClass(mainClass().getFullName());
            Method configuratorMethod = stream(mainClass.getMethods())
                    .filter(ModelService::hasConfiguratorAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND_EXCEPTION));
            return (ModuleModel) configuratorMethod.invoke(null);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasConfiguratorAnnotation(Method method) {
        return stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType().getName().equals(CONFIGURATOR_ANNOTATION_NAME));
    }
}
