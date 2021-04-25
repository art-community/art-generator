package io.art.generator.implementor;

import io.art.generator.model.*;
import lombok.experimental.*;

import static io.art.core.checker.EmptinessChecker.isEmpty;
import static io.art.core.constants.StringConstants.DOT;
import static io.art.generator.constants.Names.STORAGE_NAME;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.creator.storage.StorageSpaceInterfaceCreator.createStorageInterfaces;
import static io.art.generator.loader.ModelLoader.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.state.GeneratorState.*;

@UtilityClass
public class ModuleModelImplementor {
    public void implementModuleModel() {
        for (ExistedClass existedClass : moduleClasses().values()) {
            useModuleClass(existedClass);
            if (loadModel().getStorageModel().getStorages().size() > 0) {
                String packageName = isEmpty(moduleClass().getPackageName()) ? STORAGE_NAME : moduleClass().getPackageName() + DOT + STORAGE_NAME;
                generateProjectClass(createStorageInterfaces(loadModel().getStorageModel()), packageName);
            }
            generateProviderClass(createProviderClass(loadModel()), moduleClass().getPackageName());
        }
    }
}
