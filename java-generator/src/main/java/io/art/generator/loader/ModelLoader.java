package io.art.generator.loader;

import io.art.generator.exception.*;
import io.art.model.implementation.*;
import io.art.model.modeler.*;
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
            Method modelMethod = stream(mainClass.getMethods())
                    .filter(ModelLoader::hasModelerAnnotation)
                    .findFirst()
                    .orElseThrow(() -> new GenerationException(MODULE_MODELER_NOT_FOUND));
            ModuleModeler modeler = (ModuleModeler) modelMethod.invoke(null);
            return modeler.apply();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private boolean hasModelerAnnotation(Method method) {
        return stream(method.getAnnotations())
                .map(annotation -> annotation.annotationType().getName())
                .anyMatch(name -> name.equals(MODELER_ANNOTATION_NAME));
    }
}
