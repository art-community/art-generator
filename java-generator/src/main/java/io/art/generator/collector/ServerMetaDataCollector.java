package io.art.generator.collector;

import com.google.common.collect.*;
import io.art.generator.loader.*;
import io.art.generator.model.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.generator.collector.MappingTypesCollector.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.determiner.ServiceMethodsDeterminer.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerMetaDataCollector {
    public ServerModelMetaData collectServerModelMetaData(ServerModel serverModel) {
        ImmutableSet.Builder<Type> modelTypes = ImmutableSet.builder();
        ImmutableSet.Builder<Class<?>> serviceClasses = ImmutableSet.builder();
        GeneratorClassLoader loader = classLoader();
        for (ServiceModel<?> service : serverModel.getServices()) {
            Class<?> serviceClass = loader.loadClass(service.getServiceClass().getName());
            serviceClasses.add(serviceClass);
            modelTypes.addAll(collectMappingTypes(getServiceMethods(serviceClass)));
        }
        return new ServerModelMetaData(modelTypes.build(), serviceClasses.build());
    }
}
