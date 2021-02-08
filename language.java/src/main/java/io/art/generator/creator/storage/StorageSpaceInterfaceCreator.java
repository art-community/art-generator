package io.art.generator.creator.storage;

import io.art.generator.model.*;
import io.art.model.implementation.storage.*;
import io.art.tarantool.model.record.*;
import io.art.tarantool.model.transaction.dependency.*;
import io.art.tarantool.space.*;
import io.art.tarantool.space.TarantoolSpaceImplementation.SelectRequest;

import java.lang.reflect.*;
import java.util.*;

import static io.art.core.factory.SetFactory.set;
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

    public static NewClass createStorageInterfaces(StorageModuleModel storageModel){
        NewClass storageInterfaces = newClass().modifiers(PUBLIC)
                .addImport(classImport(moduleClass().getFullName()))
                .name(moduleClass().getName() + STORAGE_INTERFACES_SUFFIX);
        storageModel.getTarantoolSpaces().values().forEach(space -> storageInterfaces.inner(tarantoolInterface(space, storageInterfaces)));
        return storageInterfaces;
    }

    private static NewClass tarantoolInterface(TarantoolSpaceModel spaceModel, NewClass ownerClass){
        NewClass spaceInterface = newClass()
                .name(spaceModel.getId() + STORAGE_SPACE_SUFFIX)
                .modifiers(PUBLIC | INTERFACE)
                .implement(type(parameterizedType(TarantoolSpace.class, spaceModel.getSpaceModelClass(), spaceModel.getPrimaryKeyClass())));
        Set<ImportModel> imports = set();

        imports.add(classImport(spaceModel.getBasicSpaceInterface().getName()));
        imports.add(classImport(spaceModel.getSpaceModelClass().getName()));
        imports.add(classImport(spaceModel.getPrimaryKeyClass().getName()));
        imports.add(classImport(SelectRequest.class.getName()));
        imports.add(classImport(TarantoolTransactionDependency.class.getName()));
        imports.add(classImport(TarantoolRecord.class.getName()));

        spaceModel.getSearchers().forEach((indexName, indexModel) -> {
            imports.add(classImport(indexModel.getName()));
            spaceInterface.method(newMethod()
                    .name(formatMethodName(indexName, STORAGE_GET_BY_PREFIX))
                    .returnType(type(parameterizedType(TarantoolRecord.class, spaceModel.getSpaceModelClass())))
                    .parameter(newParameter(type(indexModel), STORAGE_KEY_PARAMETER_NAME)));
            spaceInterface.method(newMethod()
                    .name(formatMethodName(indexName, STORAGE_GET_BY_PREFIX))
                    .returnType(type(parameterizedType(TarantoolRecord.class, spaceModel.getSpaceModelClass())))
                    .parameter(newParameter(type(TarantoolTransactionDependency.class), STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME)));
            spaceInterface.method(newMethod()
                    .name(formatMethodName(indexName, STORAGE_SELECT_BY_PREFIX))
                    .returnType(type(SelectRequest.class))
                    .parameter(newParameter(type(indexModel), STORAGE_KEY_PARAMETER_NAME)));
            spaceInterface.method(newMethod()
                    .name(formatMethodName(indexName, STORAGE_SELECT_BY_PREFIX))
                    .returnType(type(SelectRequest.class))
                    .parameter(newParameter(type(TarantoolTransactionDependency.class), STORAGE_TRANSACTION_DEPENDENCY_KEY_NAME)));

        });
        ownerClass.addImports(imports);
        return spaceInterface;
    }

    private static TypeModel updateType(Type type, SpaceModel space){
        return TypeModel.type(type);
    }

    private static String formatMethodName(String name, String prefix){
        return prefix + name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
