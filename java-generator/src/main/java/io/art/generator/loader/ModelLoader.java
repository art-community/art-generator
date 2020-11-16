package io.art.generator.loader;

import io.art.generator.exception.*;
import io.art.generator.implementor.*;
import io.art.model.module.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.Annotations.CONFIGURATOR_ANNOTATION_NAME;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.MODULE_CONFIGURATOR_NOT_FOUND;
import static io.art.generator.context.GeneratorContext.classLoader;
import static io.art.generator.context.GeneratorContext.mainClass;
import static java.util.Arrays.stream;
import java.lang.reflect.*;

@UtilityClass
public class ModelLoader {
    public ModuleModel loadModel() {
        try {
            Class<?> mainClass = classLoader().loadClass(mainClass().getFullName());
            Method configuratorMethod = stream(mainClass.getMethods())
                    .filter(ModelLoader::hasConfiguratorAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND));
            return (ModuleModel) configuratorMethod.invoke(null);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasConfiguratorAnnotation(Method method) {
        return stream(method.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getName().equals(CONFIGURATOR_ANNOTATION_NAME));
    }
}