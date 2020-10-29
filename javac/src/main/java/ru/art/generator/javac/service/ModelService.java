package ru.art.generator.javac.service;

import com.google.common.collect.*;
import io.art.model.identifier.*;
import io.art.model.module.*;
import lombok.experimental.*;
import ru.art.generator.javac.exception.*;
import ru.art.generator.javac.loader.*;
import ru.art.generator.javac.model.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import static ru.art.generator.javac.constants.GeneratorConstants.Annotations.*;
import static ru.art.generator.javac.constants.GeneratorConstants.ExceptionMessages.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.model.NewConfigureMethod.configureMethod;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.CompileService.*;
import static ru.art.generator.javac.service.MappersGenerationService.*;
import java.lang.reflect.*;

@UtilityClass
public class ModelService {
    public void generateModelImplementation() {
        GeneratorClassLoader loader = classLoader();
        ModuleModel model = loadModel();
        for (IdentifierModel identifier : model.getServerModel().getRsocketIdentifiers()) {
            Class<?> asClass = identifier.getAsClass();
            if (nonNull(asClass)) {
                loader.loadClass(asClass.getName());
                ExistedClass existedClass = getExistedClass(asClass.getName());
                ImmutableSet.Builder<String> methodNames = ImmutableSet.builder();
                for (Method method : asClass.getDeclaredMethods()) {
                    if (isPublic(method.getModifiers())) {
                        methodNames.add(method.getName());
                        generateMappers(method.getReturnType(), method.getParameterTypes());
                    }
                }
                replaceFields(existedClass, methodNames(existedClass, methodNames.build()).generateFields());
            }
        }
        replaceMethod(mainClass(), configureMethod(model));
    }

    private ModuleModel loadModel() {
        recompile();
        try {
            Class<?> mainClass = classLoader().loadClass(mainClass().getFullName());
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
