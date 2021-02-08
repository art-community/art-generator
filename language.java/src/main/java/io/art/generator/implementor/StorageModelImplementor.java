package io.art.generator.implementor;

import io.art.core.collection.*;
import io.art.core.exception.*;
import io.art.generator.model.*;
import io.art.model.implementation.storage.*;

import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.JavacService.*;
import static java.lang.reflect.Modifier.PRIVATE;

public class StorageModelImplementor {
    public static NewMethod implementStoragesMethod(StorageModuleModel model){
        TypeModel registryType = STORAGE_REGISTRY_TYPE;
        NewMethod storagesMethod = newMethod()
                .name(STORAGES_NAME)
                .parameter(newParameter(STORAGE_MODULE_MODEL_TYPE, STORAGE_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
//        model.getStorages()
//                .stream()
//                .map(CustomConfigurationModel::getConfigurationClass)
//                .collect(immutableSetCollector())
//                .forEach(type -> customConfigurationsMethod.statement(() -> maker().Exec(executeRegisterMethod(type))));
        storagesMethod.statement(() -> returnVariable(REGISTRY_NAME));
        return storagesMethod;
    }

    public static ImmutableArray<NewClass> implementStorageSpaces(StorageModuleModel model){
        ImmutableArray.Builder<NewClass> spaceClasses = immutableArrayBuilder();
        return spaceClasses.build();
    }

    private static NewClass implementTarantoolSpace(TarantoolSpaceModel spaceModel){
        throw new NotImplementedException("implementTarantoolSpace");
    }
}
