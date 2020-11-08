package io.art.generator.implementor;

import io.art.generator.loader.*;
import io.art.generator.model.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.determiner.ServiceMethodsDeterminer.*;
import static io.art.generator.implementor.ServiceMethodModelImplementor.*;

@UtilityClass
public class ServerModelImplementor {
    public void implementServerModel(NewClass providerClass, ServerModel serverModel) {
        GeneratorClassLoader loader = classLoader();
        for (ServiceModel<?> service : serverModel.getServices()) {
            Class<?> serviceClass = loader.loadClass(service.getServiceClass().getName());
            getServiceMethods(serviceClass).forEach(method -> implementServiceMethodModel(providerClass, serviceClass, method));
        }
    }
}
