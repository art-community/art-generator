package io.art.generator.implementor;

import io.art.generator.model.*;
import lombok.experimental.*;

import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.loader.ModelLoader.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GeneratorState.*;

@UtilityClass
public class ModuleModelImplementor {
    public void implementModuleModel() {
        for (ExistedClass existedClass : moduleClasses().values()) {
            useModuleClass(existedClass);
            generateClass(createProviderClass(loadModel()), moduleClass().getPackageName());
        }
    }
}
