package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.formater.*;
import io.art.generator.model.*;
import io.art.model.implementation.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.LoggingMessages.*;
import static io.art.generator.constants.GeneratorConstants.ModelMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.ServiceSpecificationMethods.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerModelImplementor {
    public NewMethod implementServerModel(ServerModel serverModel) {
        TypeModel registryType = SERVICE_SPECIFICATION_REGISTRY_TYPE;
        NewMethod servicesMethod = newMethod()
                .name(SERVICES_NAME)
                .parameter(newParameter(SERVER_MODEL_TYPE, SERVER_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        ImmutableMap<String, ServiceModel<?>> services = serverModel.getServices();
        services.values().forEach(serviceModel -> servicesMethod.statement(() -> maker().Exec(executeRegisterMethod(servicesMethod, serviceModel))));
        return servicesMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCMethodInvocation executeRegisterMethod(NewMethod servicesMethod, ServiceModel<?> serviceModel) {
        JCMethodInvocation specificationBuilder = executeServiceSpecificationBuilder(servicesMethod, serviceModel.getServiceClass());
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(serviceModel.getServiceClass().getSimpleName()), specificationBuilder)
                .apply();
    }

    private JCMethodInvocation executeServiceSpecificationBuilder(NewMethod servicesMethod, Class<?> serviceClass) {
        NewBuilder builder = newBuilder(SERVICE_SPECIFICATION_TYPE).method(SERVICE_ID, literal(serviceClass.getSimpleName()));
        for (Method method : getServiceMethods(serviceClass)) {
            JCMethodInvocation methodSpecificationBuilder = executeMethodSpecificationBuilder(servicesMethod, serviceClass, method);
            builder.method(METHOD, literal(method.getName()), methodSpecificationBuilder);
            success(format(GENERATED_SERVICE_METHOD_SPECIFICATION, SignatureFormatter.formatSignature(serviceClass, method)));
        }
        return builder.generate();
    }

    private JCMethodInvocation executeMethodSpecificationBuilder(NewMethod servicesMethod, Class<?> serviceClass, Method serviceMethod) {
        TypeModel methodProcessingModeType = METHOD_PROCESSING_MODE_TYPE;
        Type[] parameterTypes = serviceMethod.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(serviceClass, serviceMethod));
        }
        Type returnType = serviceMethod.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        NewBuilder methodBuilder = newBuilder(SERVICE_METHOD_SPECIFICATION_TYPE)
                .method(SERVICE_ID, literal(serviceClass.getSimpleName()))
                .method(METHOD_ID, literal(serviceMethod.getName()));
        if (!isEmpty(parameterTypes)) {
            servicesMethod.addImport(classImport(type(parameterTypes[0]).getName()));
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
            servicesMethod.addImport(classImport(type(returnType).getName()));
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
                .generate(builder -> decorateMethodBuilder(builder, serviceClass, serviceMethod));
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
        return method(SERVICE_METHOD_IMPLEMENTATION_TYPE, name)
                .addArguments(reference, serviceName, methodName)
                .apply();
    }

    private JCMethodInvocation decorateMethodBuilder(JCMethodInvocation builder, Class<?> serviceClass, Method serviceMethod) {
        return method(SERVER_MODEL_NAME, IMPLEMENT_NAME)
                .addArguments(literal(serviceClass.getSimpleName()), literal(serviceMethod.getName()), builder)
                .apply();
    }
}
