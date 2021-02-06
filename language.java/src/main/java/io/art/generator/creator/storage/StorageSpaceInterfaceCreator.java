package io.art.generator.creator.storage;

import io.art.generator.model.*;
import io.art.model.implementation.storage.*;

import java.lang.reflect.*;

import static io.art.generator.constants.Names.*;
import static io.art.generator.model.NewClass.*;
import static java.lang.reflect.Modifier.*;

public class StorageSpaceInterfaceCreator {
    public static NewClass createSpaceInterface(SpaceModel space){
        NewClass spaceInterface = newClass()
                .name(space.getId() + STORAGE_SPACE_SUFFIX)
                .modifiers(PUBLIC | INTERFACE);

//        for(Method method: space.getBasicSpaceClass().getMethods()){
//            NewMethod newMethod = newMethod()
//                    .name(method.getName())
//                    .returnType(updateType(method.getGenericReturnType(), space));
//            for(Type arg: method.getGenericParameterTypes()){
//                newMethod.parameter(newParameter(updateType(arg, space), sequenceName(arg.getTypeName())));
//            }
//            spaceInterface.method(newMethod);
//        }
        return spaceInterface;
    }

    private static TypeModel updateType(Type type, SpaceModel space){
        return TypeModel.type(type);
    }
}
