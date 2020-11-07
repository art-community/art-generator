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
        Class<?> returnType = serviceMethod.getReturnType();
        Class<?>[] parameterTypes = serviceMethod.getParameterTypes();

        NewField model = newField()
                .name(MODEL_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod(DECORATE_NAME, List.of(applyClassMethod(type(mainClass().getName()), CONFIGURE_NAME))));

        providerClass
                .addImport(classImport(serviceClass.getName()))
                .field(MODEL_NAME, model)
                .method(MAPPERS_NAME, createMappersMethod(returnType, type(MappersRegistry.class), parameterTypes))
                .method(SERVICES_NAME, createServicesMethod(serviceClass, type(ServiceSpecificationRegistry.class)))
                .method(DECORATE_NAME, createDecorateMethod());

        for (Class<?> parameterType : parameterTypes) {
            providerClass.addImport(classImport(parameterType.getName()));
        }
    }
}
