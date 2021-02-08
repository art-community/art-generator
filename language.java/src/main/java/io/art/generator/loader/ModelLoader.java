package io.art.generator.loader;

import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.model.configurator.*;
import io.art.model.implementation.module.*;
import lombok.experimental.*;
import static io.art.core.handler.ExceptionHandler.*;
import static io.art.core.singleton.SingletonsRegistry.*;
import static io.art.core.wrapper.ExceptionWrapper.wrapException;
import static io.art.generator.constants.Annotations.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.JavaDialect.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.state.GeneratorState.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class ModelLoader {
    public ModuleModel loadModel() {
        try {
            ExistedClass moduleClass = moduleClass();
            Class<?> mainClass = classLoader().loadClass(moduleClass.getFullName());
            Method configureMethod = stream(mainClass.getMethods())
                    .filter(ModelLoader::hasConfiguratorAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_CONFIGURATOR_NOT_FOUND));
            Object target = isStatic(configureMethod.getModifiers())
                    ? null
                    : dialect() == KOTLIN && nonNull(nullIfException(() -> mainClass.getField(INSTANCE_FIELD_NAME)))
                    ? mainClass.getField(INSTANCE_FIELD_NAME).get(null)
                    : singleton(mainClass, () -> wrapException(() -> mainClass.getConstructor().newInstance()));
            ModuleModelConfigurator modeler = (ModuleModelConfigurator) configureMethod.invoke(target);
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
