package io.art.generator.implementor;

import com.sun.tools.javac.tree.*;
import io.art.core.caster.*;
import io.art.generator.model.*;
import io.art.model.modeling.storage.*;

import java.util.*;

import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.creator.storage.TarantoolSpaceCreator.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.JavacService.*;
import static java.lang.reflect.Modifier.PRIVATE;

public class StorageModelImplementor {
    public static NewMethod implementStoragesMethod(Map<String, NewClass> spaceClasses){
        TypeModel registryType = STORAGE_REGISTRY_TYPE;
        NewMethod storagesMethod = newMethod()
                .name(STORAGES_NAME)
                .parameter(newParameter(STORAGE_MODULE_MODEL_TYPE, STORAGE_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        spaceClasses.forEach((id, spaceClass) -> storagesMethod.statement(() -> maker().Exec(executeRegisterMethod(id, spaceClass))));
        storagesMethod.statement(() -> returnVariable(REGISTRY_NAME));
        return storagesMethod;
    }

    public static Map<String, NewClass> implementStorageSpaces(StorageModuleModel model){
        Map<String, NewClass> spaceClasses = map();
        model.getTarantoolSpaces().forEach((id, spaceModel) -> spaceClasses.put(id, createTarantoolSpaceClass(spaceModel)));
        return spaceClasses;
    }

    private static JCTree.JCMethodInvocation executeRegisterMethod(String spaceId, NewClass spaceClass){
        String spaceClassName = spaceId + STORAGE_SPACE_SUFFIX;
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(spaceId),
                        method(Caster.class.getSimpleName(), "cast")
                                .addArgument(
                                        method(STORAGE_MODEL_NAME, IMPLEMENT_NAME)
                                                .addArguments(literal(spaceId), newReference(spaceClass.name()))
                                                .apply())
                                .apply())
                .apply();
    }
}
