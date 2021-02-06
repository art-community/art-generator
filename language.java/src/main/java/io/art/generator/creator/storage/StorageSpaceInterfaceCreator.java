package io.art.generator.creator.storage;

import io.art.generator.model.*;
import io.art.model.implementation.storage.*;

import java.lang.reflect.*;

import static io.art.core.reflection.ParameterizedTypeImplementation.parameterizedType;
import static io.art.generator.constants.Names.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewMethod.newMethod;
import static io.art.generator.model.NewParameter.newParameter;
import static io.art.generator.state.GeneratorState.*;
import static io.art.generator.model.TypeModel.*;
import static java.lang.reflect.Modifier.*;

public class StorageSpaceInterfaceCreator {

    public static NewClass createStorageSpaceInterfaces(StorageModuleModel storageModel){
        NewClass storageInterfaces = newClass().modifiers(PUBLIC)
                .addImport(classImport(moduleClass().getFullName()))
                .name(moduleClass().getName() + STORAGE_INTERFACES_SUFFIX);
        storageModel.getTarantoolSpaces().values().forEach(space -> storageInterfaces.inner(createTarantoolInterface(space, storageInterfaces)));
        return storageInterfaces;
    }

    private static NewClass createTarantoolInterface(TarantoolSpaceModel spaceModel, NewClass ownerClass){
        NewClass spaceInterface = newClass()
                .name(spaceModel.getId() + STORAGE_SPACE_SUFFIX)
                .modifiers(PUBLIC | INTERFACE)
                .implement(type(parameterizedType(spaceModel.getBasicSpaceInterface(),
                        spaceModel.getSpaceModelClass(), spaceModel.getPrimaryKeyClass())));
        ownerClass.addImport(classImport(spaceModel.getBasicSpaceInterface().getName()));
        ownerClass.addImport(classImport(spaceModel.getSpaceModelClass().getName()));
        ownerClass.addImport(classImport(spaceModel.getPrimaryKeyClass().getName()));
        spaceModel.getSearchers().forEach((name, model) -> {
            spaceInterface.method(newMethod()
                    .name(formatName(name, STORAGE_GET_BY_PREFIX))
                    .returnType(type(spaceModel.getSpaceModelClass()))
                    .parameter(newParameter(type(model), name)));
//            spaceInterface.method(newMethod()
//                    .name(formatName(name, STORAGE_GET_BY_PREFIX)));
//            spaceInterface.method(newMethod()
//                    .name(formatName(name, STORAGE_SELECT_BY_PREFIX)));
//            spaceInterface.method(newMethod()
//                    .name(formatName(name, STORAGE_SELECT_BY_PREFIX)));

        });
        return spaceInterface;
    }

    private static TypeModel updateType(Type type, SpaceModel space){
        return TypeModel.type(type);
    }

    private static String formatName(String name, String prefix){
        return prefix + name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
