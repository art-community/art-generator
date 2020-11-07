package io.art.generator.javac.implementor.model;

import io.art.generator.javac.loader.*;
import io.art.generator.javac.model.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.model.ServiceMethodModelImplementor.*;
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
