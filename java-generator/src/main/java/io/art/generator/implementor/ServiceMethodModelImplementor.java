package io.art.generator.implementor;

import io.art.generator.model.*;
import io.art.server.registry.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.MappersMethodCreator.*;
import static io.art.generator.creator.service.ServicesMethodCreator.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.TypeModel.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodModelImplementor {
    public void implementServiceMethodModel(NewClass providerClass, Class<?> serviceClass, Method serviceMethod) {
        Type returnType = serviceMethod.getGenericReturnType();
        Type[] parameterTypes = serviceMethod.getGenericParameterTypes();

        providerClass
                .addImport(classImport(serviceClass.getName()))
                .method(MAPPERS_NAME, createMappersMethod(returnType, type(MappersRegistry.class), parameterTypes))
                .method(SERVICES_NAME, createServicesMethod(serviceClass, type(ServiceSpecificationRegistry.class)));

        for (Type parameterType : parameterTypes) {
            if (parameterType instanceof ParameterizedType) {
                for (Type actualTypeArgument : ((ParameterizedType) parameterType).getActualTypeArguments()) {
                    providerClass.addImport(classImport(type((Class<?>) actualTypeArgument).getFullName()));
                }
            }
            if (parameterType instanceof Class) {
                providerClass.addImport(classImport(type((Class<?>) parameterType).getFullName()));
            }
        }
    }
}
