package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.inspector.*;
import io.art.generator.model.*;
import io.art.model.server.*;
import io.art.server.implementation.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableSet.immutableSetBuilder;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.ServiceSpecificationMethods.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerModelImplementor {
    public NewMethod implementServerModel(ServerModel serverModel) {
        TypeModel registryType = type(ServiceSpecificationRegistry.class);
        NewMethod servicesMethod = newMethod()
                .name(SERVICES_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        ImmutableSet<ServiceModel<?>> services = serverModel.getServices();
        services.forEach(serviceModel -> servicesMethod.statement(() -> maker().Exec(executeRegisterMethod(servicesMethod, serviceModel))));
        return servicesMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableSet<Type> collectCustomTypes(ServerModel serverModel) {
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        ImmutableSet<ServiceModel<?>> services = serverModel.getServices();
        for (ServiceModel<?> service : services) {
            for (Method method : service.getServiceClass().getDeclaredMethods()) {
                Type[] parameterTypes = method.getGenericParameterTypes();
                if (parameterTypes.length > 1) {
                    throw new GenerationException(MORE_THAN_ONE_PARAMETER);
                }
                types.addAll(TypeInspector.collectCustomTypes(method.getGenericReturnType()));
                if (isNotEmpty(parameterTypes)) {
                    types.addAll(TypeInspector.collectCustomTypes(parameterTypes[0]));
                }
            }
        }
        return types.build();
    }

    private JCMethodInvocation executeRegisterMethod(NewMethod servicesMethod, ServiceModel<?> serviceModel) {
        JCMethodInvocation specificationBuilder = executeServiceSpecificationBuilder(servicesMethod, serviceModel.getServiceClass());
        List<JCExpression> arguments = List.of(literal(serviceModel.getServiceClass().getSimpleName()), specificationBuilder);
        return applyMethod(REGISTRY_NAME, REGISTER_NAME, arguments);
    }

    private JCMethodInvocation executeServiceSpecificationBuilder(NewMethod servicesMethod, Class<?> serviceClass) {
        NewBuilder builder = newBuilder(type(ServiceSpecification.class)).method(SERVICE_ID, literal(serviceClass.getSimpleName()));
        for (Method method : getServiceMethods(serviceClass)) {
            JCMethodInvocation methodSpecificationBuilder = executeMethodSpecificationBuilder(servicesMethod, serviceClass, method);
            List<JCExpression> arguments = List.of(literal(method.getName()), methodSpecificationBuilder);
            builder.method(METHOD, arguments);
        }
        return builder.generate();
    }

    private JCMethodInvocation executeMethodSpecificationBuilder(NewMethod servicesMethod, Class<?> serviceClass, Method serviceMethod) {
        TypeModel methodProcessingModeType = type(MethodProcessingMode.class);
        Type[] parameterTypes = serviceMethod.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new GenerationException(MORE_THAN_ONE_PARAMETER);
        }
        Type returnType = serviceMethod.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        NewBuilder methodBuilder = newBuilder(type(ServiceMethodSpecification.class))
                .method(SERVICE_ID, literal(serviceClass.getSimpleName()))
                .method(METHOD_ID, literal(serviceMethod.getName()));
        if (!isEmpty(parameterTypes)) {
            servicesMethod.addClassImport(classImport(type(parameterTypes[0]).getName()));
            switch (inputMode) {
                case BLOCKING:
                    methodBuilder.method(INPUT_MAPPER, toModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    methodBuilder.method(INPUT_MAPPER, toModelMapper(((ParameterizedType) parameterTypes[0]).getActualTypeArguments()[0]));
                    break;
            }
        }
        if (!void.class.equals(returnType)) {
            servicesMethod.addClassImport(classImport(type(returnType).getName()));
            switch (outputMode) {
                case BLOCKING:
                    methodBuilder.method(OUTPUT_MAPPER, fromModelMapper(returnType));
                    break;
                case MONO:
                case FLUX:
                    methodBuilder.method(OUTPUT_MAPPER, fromModelMapper(((ParameterizedType) returnType).getActualTypeArguments()[0]));
                    break;
            }
        }
        return methodBuilder
                .method(INPUT_MODE, select(methodProcessingModeType, inputMode.name()))
                .method(OUTPUT_MODE, select(methodProcessingModeType, outputMode.name()))
                .method(IMPLEMENTATION, executeHandlerMethod(serviceClass, serviceMethod))
                .generate();
    }

    private JCMethodInvocation executeHandlerMethod(Class<?> serviceClass, Method serviceMethod) {
        String name = HANDLER_METHOD;
        if (isEmpty(serviceMethod.getParameterTypes())) {
            name = PRODUCER_METHOD;
        }
        if (void.class.equals(serviceMethod.getReturnType())) {
            name = CONSUMER_METHOD;
        }
        if (void.class.equals(serviceMethod.getReturnType()) && isEmpty(serviceMethod.getParameterTypes())) {
            name = RUNNER_METHOD;
        }
        JCMemberReference reference = invokeReference(type(serviceClass), (serviceMethod.getName()));
        JCLiteral serviceName = literal(serviceClass.getSimpleName());
        JCLiteral methodName = literal(serviceMethod.getName());
        List<JCExpression> arguments = List.of(reference, serviceName, methodName);
        return applyClassMethod(type(ServiceMethodImplementation.class), name, arguments);
    }
}
