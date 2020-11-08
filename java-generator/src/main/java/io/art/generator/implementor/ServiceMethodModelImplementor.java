package io.art.generator.implementor;

import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import io.art.model.module.*;
import io.art.server.registry.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.decorate.DecorateMethodCreator.*;
import static io.art.generator.creator.mapper.MappersMethodCreator.*;
import static io.art.generator.creator.service.ServicesMethodCreator.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodModelImplementor {
    public void implementServiceMethodModel(NewClass providerClass, Class<?> serviceClass, Method serviceMethod) {
        Type returnType = serviceMethod.getGenericReturnType();
        Type[] parameterTypes = serviceMethod.getGenericParameterTypes();

        NewField model = newField()
                .name(MODEL_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod(DECORATE_NAME, List.of(applyClassMethod(type(mainClass().getName()), CONFIGURE_NAME))));

        NewField mappersRegistry = newField()
                .name(MAPPERS_REGISTRY_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(MappersRegistry.class))
                .initializer(() -> applyMethod(MAPPERS_NAME));

        NewField servicesRegistry = newField()
                .name(SERVICES_REGISTRY_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ServiceSpecificationRegistry.class))
                .initializer(() -> applyMethod(SERVICES_NAME));

        providerClass
                .addImport(classImport(serviceClass.getName()))
                .field(MODEL_NAME, model)
                .field(MAPPERS_REGISTRY_NAME, mappersRegistry)
                .field(SERVICES_REGISTRY_NAME, servicesRegistry)
                .method(MAPPERS_NAME, createMappersMethod(returnType, type(MappersRegistry.class), parameterTypes))
                .method(SERVICES_NAME, createServicesMethod(serviceClass, type(ServiceSpecificationRegistry.class)))
                .method(DECORATE_NAME, createDecorateMethod());

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
