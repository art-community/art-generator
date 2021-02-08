package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.model.implementation.storage.*;
import lombok.experimental.*;
import java.lang.reflect.*;

import static io.art.core.collection.ImmutableSet.*;

@UtilityClass
public class StorageTypesCollector {
    public static ImmutableSet<Type> collectStorageTypes(StorageModuleModel storageModel){
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        for (TarantoolSpaceModel space: storageModel.getTarantoolSpaces().values()){
            types.add(space.getSpaceModelClass());
            types.add(space.getPrimaryKeyClass());
            types.addAll(space.getSearchers().values());
        }
        return types.build();
    }
}
