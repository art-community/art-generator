package io.art.generator.implementor.model;

import io.art.generator.loader.*;
import io.art.generator.model.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.implementor.model.ServiceMethodModelImplementor.*;
import static java.lang.reflect.Modifier.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerModelImplementor {
    public void implementServerModel(NewClass providerClass, ServerModel serverModel) {
        GeneratorClassLoader loader = classLoader();
        for (ServiceModel<?> service : serverModel.getServices()) {
            Class<?> serviceClass = loader.loadClass(service.getServiceClass().getName());
            for (Method method : serviceClass.getDeclaredMethods()) {
                if (isPublic(method.getModifiers())) {
                    implementServiceMethodModel(providerClass, serviceClass, method);
                }
            }
        }
    }
}
