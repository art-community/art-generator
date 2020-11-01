package io.art.generator.javac.implementor.model;

import io.art.generator.javac.loader.*;
import io.art.model.server.*;
import lombok.experimental.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.mapping.MappingImplementor.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerModelImplementor {
    public void implementServerModel(ServerModel serverModel) {
        GeneratorClassLoader loader = classLoader();
        for (ServiceModel<?> service : serverModel.getServices()) {
            Class<?> serviceClass = service.getServiceClass();
            if (isNull(serviceClass)) {
                continue;
            }
            serviceClass = loader.loadClass(serviceClass.getName());
            for (Method method : serviceClass.getDeclaredMethods()) {
                if (isPublic(method.getModifiers())) {
                    implementMethodMapping(method.getReturnType(), method.getParameterTypes());
                }
            }
        }
    }

}
