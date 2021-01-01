package io.art.generator.loader;

import io.art.generator.exception.*;
import io.art.model.configurator.*;
import io.art.model.implementation.module.*;
import lombok.experimental.*;
import static io.art.core.singleton.SingletonsRegistry.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.constants.GeneratorConstants.Annotations.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.context.GeneratorContext.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class ModelLoader {
    public ModuleModel loadModel() {
        try {
            Class<?> mainClass = classLoader().loadClass(mainClass().getFullName());
            Method configureMethod = stream(mainClass.getMethods())
                    .filter(ModelLoader::hasConfiguratorAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND));
            ModuleModelConfigurator modeler = (ModuleModelConfigurator) configureMethod.invoke(isStatic(configureMethod.getModifiers()) ? null : singleton(mainClass, () -> wrapException(mainClass::newInstance)));
            return modeler.configure();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasConfiguratorAnnotation(Method method) {
        return stream(method.getAnnotations())
                .map(annotation -> annotation.annotationType().getName())
                .anyMatch(name -> name.equals(CONFIGURATOR_ANNOTATION_NAME));
    }
}
