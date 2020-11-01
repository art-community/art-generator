package io.art.generator.javac.implementor.model;

import io.art.generator.javac.exception.*;
import io.art.model.module.*;
import lombok.experimental.*;
import static io.art.generator.javac.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.javac.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.model.ServerModelImplementor.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class ModuleModelImplementor {
    public void implementModuleModel() {
        ModuleModel model = loadModel();
        implementServerModel(model.getServerModel());
        ;
    }

    private ModuleModel loadModel() {
        try {
            Class<?> mainClass = classLoader().loadClass(mainClass().getFullName());
            Method configuratorMethod = stream(mainClass.getMethods())
                    .filter(ModuleModelImplementor::hasConfiguratorAnnotation)
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
