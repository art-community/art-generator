package io.art.generator.implementor.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import io.art.model.module.*;
import io.art.server.registry.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.implementor.mapping.FromModelMapperImplementor.*;
import static io.art.generator.implementor.mapping.ToModelMapperImplementor.*;
import static io.art.generator.implementor.service.ServicesMethodImplementor.implementServicesMethod;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.implementor.decorator.DecorateMethodImplementor.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodModelImplementor {
    public void implementServiceMethodModel(NewClass providerClass, Class<?> serviceClass, Method serviceMethod) {
        Class<?> returnType = serviceMethod.getReturnType();
        Class<?>[] parameterTypes = serviceMethod.getParameterTypes();
        TypeModel registryType = type(MappersRegistry.class);

        NewField model = newField()
                .name(MODEL_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod(DECORATE_NAME, List.of(applyClassMethod(type(mainClass().getName()), CONFIGURE_NAME))));

        providerClass
                .addImport(classImport(serviceClass.getName()))
                .field(MODEL_NAME, model)
                .method(MAPPERS_NAME, generateMappersMethod(returnType, registryType, parameterTypes))
                .method(SERVICES_NAME, implementServicesMethod(returnType, type(ServiceSpecificationRegistry.class)))
                .method(DECORATE_NAME, implementDecorateMethod());

        for (Class<?> parameterType : parameterTypes) {
            providerClass.addImport(classImport(parameterType.getName()));
        }
    }

    private NewMethod generateMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateRegistryVariable(registryType));

        method.statement(() -> implementToModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            method.statement(() -> implementToModel(parameterClass));
        }

        method.statement(() -> implementFromModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            method.statement(() -> implementFromModel(parameterClass));
        }

        return method.statement(() -> returnVariable(REGISTRY_NAME));
    }


    private JCExpressionStatement implementToModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), implementToModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }

    private JCExpressionStatement implementFromModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), implementFromModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }

    private JCVariableDecl generateRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name(REGISTRY_NAME)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
