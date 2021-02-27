package io.art.generator.creator.storage;

import io.art.generator.model.*;
import io.art.model.implementation.storage.*;
import io.art.tarantool.model.record.*;
import io.art.tarantool.model.transaction.dependency.*;
import io.art.tarantool.space.*;
import io.art.tarantool.space.TarantoolSpaceImplementation.*;

import java.lang.reflect.*;
import java.util.*;

import static io.art.core.collector.ArrayCollector.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.lang.reflect.Modifier.*;

public class StorageSpaceInterfaceCreator {

    public static NewClass createStorageInterfaces(StorageModuleModel storageModel){
        NewClass storageInterfaces = newClass().modifiers(PUBLIC)
                .addImport(classImport(moduleClass().getFullName()))
                .name(moduleClass().getName() + STORAGE_INTERFACES_SUFFIX);

        Set<String> modelFieldNames = set();
        storageModel.getTarantoolSpaces().values().forEach(space -> {
            modelFieldNames.addAll(fieldNames(space.getSpaceModelClass()));
            storageInterfaces.inner(modelFieldNamesInterface(space.getSpaceModelClass()));
            storageInterfaces.inner(tarantoolInterface(space, storageInterfaces));
        });
        storageInterfaces.inner(fieldNamesInterface(modelFieldNames));
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
                    .returnType(type(parameterizedType(SelectRequest.class, spaceModel.getSpaceModelClass())))
                    .parameter(newParameter(type(indexModel), STORAGE_KEY_PARAMETER_NAME)));
            spaceInterface.method(newMethod()
                    .name(formatMethodName(indexName, STORAGE_SELECT_BY_PREFIX))
                    .returnType(type(parameterizedType(SelectRequest.class, spaceModel.getSpaceModelClass())))
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

    private static NewClass modelFieldNamesInterface(Class<?> model){
        NewClass newInterface = newClass().name(model.getSimpleName() + FIELD_NAMES_NAME)
                .modifiers(PUBLIC | INTERFACE);
        List<String> fieldNames = existedClass(model.getName()).getFields().values().stream()
                .sequential()
                .map(ExistedField::getName)
                .collect(listCollector());
        for (int i = 0; i<fieldNames.size(); i++){
            int index = i;
            newInterface.field(newField()
                    .name(fieldNames.get(i))
                    .type(TARANTOOL_FIELD_TYPE)
                    .initializer(() ->
                            newObject(TARANTOOL_FIELD_TYPE, immutableArrayOf( literal(index + 1), literal(fieldNames.get(index)) ))
            ));
        }
        return newInterface;
    }

    private static NewClass fieldNamesInterface(Set<String> names){
        NewClass fieldNames = newClass().name(FIELD_NAMES_NAME)
                .modifiers(PUBLIC | INTERFACE);
        names.forEach(name -> fieldNames.field(newField()
                .type(STRING_TYPE)
                .name(name)
                .constant(name)
        ));
        return fieldNames;
    }

    private static Set<String> fieldNames(Class<?> model){
        return existedClass(model.getName()).getFields().keySet();
    }
}
