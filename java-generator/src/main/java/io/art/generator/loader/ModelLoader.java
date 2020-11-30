package io.art.generator.loader;

import io.art.generator.exception.*;
import io.art.model.customizer.*;
import io.art.model.module.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.util.Arrays.*;
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
            ModuleCustomizer moduleCustomizer = (ModuleCustomizer) configuratorMethod.invoke(null);
            return moduleCustomizer.apply();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasConfiguratorAnnotation(Method method) {
        return stream(method.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getName().equals(CONFIGURATOR_ANNOTATION_NAME));
    }
}
