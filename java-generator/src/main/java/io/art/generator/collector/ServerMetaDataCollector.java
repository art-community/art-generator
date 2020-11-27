package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.generator.loader.*;
import io.art.generator.model.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.generator.collector.MappingTypesCollector.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerMetaDataCollector {
    public ServerModelMetaData collectServerModelMetaData(ServerModel serverModel) {
        ImmutableSet.Builder<Type> modelTypes = immutableSetBuilder();
        ImmutableSet.Builder<Class<?>> serviceClasses = immutableSetBuilder();
        GeneratorClassLoader loader = classLoader();
        for (ServiceModel<?> service : serverModel.getServices()) {
            Class<?> serviceClass = loader.loadClass(service.getServiceClass().getName());
            serviceClasses.add(serviceClass);
            modelTypes.addAll(collectMappingTypes(getServiceMethods(serviceClass)));
        }
        return new ServerModelMetaData(modelTypes.build(), serviceClasses.build());
    }
}
