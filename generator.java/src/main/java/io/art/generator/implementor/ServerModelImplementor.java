package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.model.implementation.server.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.PRIVATE;
import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.lang.reflect.Modifier.isStatic;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@UtilityClass
public class ServerModelImplementor {
    public NewMethod implementServicesMethod(ServerModuleModel serverModel) {
        TypeModel registryType = SERVICE_SPECIFICATION_REGISTRY_TYPE;
        NewMethod servicesMethod = newMethod()
                .name(SERVICES_NAME)
                .parameter(newParameter(SERVER_MODEL_TYPE, SERVER_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        ImmutableMap<String, ServiceModel> services = serverModel.getServices();
        services.values()
                .stream()
                .filter(serviceModel -> !type(serviceModel.getServiceClass()).isJdk())
                .peek(serviceModel -> servicesMethod.addImport(classImport(serviceModel.getServiceClass().getName())))
                .forEach(serviceModel -> servicesMethod.statement(() -> maker().Exec(executeRegisterMethod(servicesMethod, serviceModel))));
        return servicesMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCMethodInvocation executeRegisterMethod(NewMethod servicesMethod, ServiceModel serviceModel) {
        JCMethodInvocation specificationBuilder = executeServiceSpecificationBuilder(servicesMethod, serviceModel.getServiceClass());
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(serviceModel.getServiceClass().getSimpleName()), specificationBuilder)
                .apply();
    }

    private JCMethodInvocation executeServiceSpecificationBuilder(NewMethod servicesMethod, Class<?> serviceClass) {
        NewBuilder builder = newBuilder(SERVICE_SPECIFICATION_TYPE);
        for (Method method : getServiceMethods(serviceClass)) {
            JCMethodInvocation methodSpecificationBuilder = executeMethodSpecificationBuilder(servicesMethod, serviceClass, method);
            builder.method(METHOD_NAME, literal(method.getName()), methodSpecificationBuilder);
            info(format(GENERATED_SERVICE_METHOD_SPECIFICATION, formatSignature(serviceClass, method)));
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
                .method(SERVICE_ID_NAME, literal(serviceClass.getSimpleName()))
                .method(METHOD_ID_NAME, literal(serviceMethod.getName()));
        if (isNotEmpty(parameterTypes) && isNotVoid(parameterTypes[0])) {
            TypeModel parameterTypeModel = type(parameterTypes[0]);
            if (!parameterTypeModel.isJdk()) {
                servicesMethod.addImport(classImport(parameterTypeModel.getFullName()));
            }
            switch (inputMode) {
                case BLOCKING:
                    methodBuilder.method(INPUT_MAPPER_NAME, toModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    Type firstTypeParameter = extractFirstTypeParameter((ParameterizedType) parameterTypes[0]);
                    if (isNotVoid(firstTypeParameter)) {
                        methodBuilder.method(INPUT_MAPPER_NAME, toModelMapper(firstTypeParameter));
                    }
                    break;
            }
            if (isValidatable(parameterTypes[0])) {
                JCNewClass serviceMethodIdentifier = newObject(SERVICE_METHOD_IDENTIFIER_TYPE, literal(serviceClass.getSimpleName()), literal(serviceMethod.getName()));
                JCNewClass decorator = newObject(SERVICE_VALIDATION_DECORATOR_TYPE, serviceMethodIdentifier);
                methodBuilder.method(INPUT_DECORATOR_NAME, decorator);
            }
        }
        if (isNotVoid(returnType)) {
            TypeModel returnTypeModel = type(returnType);
            if (!returnTypeModel.isJdk()) {
                servicesMethod.addImport(classImport(returnTypeModel.getName()));
            }
            switch (outputMode) {
                case BLOCKING:
                    methodBuilder.method(OUTPUT_MAPPER_NAME, fromModelMapper(returnType));
                    break;
                case MONO:
                case FLUX:
                    Type firstTypeParameter = extractFirstTypeParameter((ParameterizedType) returnType);
                    if (isNotVoid(firstTypeParameter)) {
                        methodBuilder.method(OUTPUT_MAPPER_NAME, fromModelMapper((extractFirstTypeParameter((ParameterizedType) returnType))));
                    }
                    break;
            }
        }
        return methodBuilder
                .method(INPUT_MODE_NAME, select(methodProcessingModeType, inputMode.name()))
                .method(OUTPUT_MODE_NAME, select(methodProcessingModeType, outputMode.name()))
                .method(IMPLEMENTATION_NAME, executeHandlerMethod(serviceClass, serviceMethod))
                .generate(builder -> method(SERVER_MODEL_NAME, IMPLEMENT_NAME)
                        .addArguments(literal(serviceClass.getSimpleName()), literal(serviceMethod.getName()), builder)
                        .apply());
    }

    private JCMethodInvocation executeHandlerMethod(Class<?> serviceClass, Method serviceMethod) {
        String name = HANDLER_NAME;
        if (isEmpty(serviceMethod.getParameterTypes())) {
            name = PRODUCER_NAME;
        }
        if (isVoid(serviceMethod.getReturnType())) {
            name = CONSUMER_NAME;
        }
        if (isVoid(serviceMethod.getReturnType()) && isEmpty(serviceMethod.getParameterTypes())) {
            name = RUNNER_NAME;
        }
        JCExpression owner = isStatic(serviceMethod.getModifiers()) ? type(serviceClass).generateBaseType() : method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                .addArguments(classReference(serviceClass))
                .addArguments(newReference(type(serviceClass)))
                .apply();
        JCMemberReference reference = invokeReference(owner, (serviceMethod.getName()));
        JCLiteral serviceName = literal(serviceClass.getSimpleName());
        JCLiteral methodName = literal(serviceMethod.getName());
        return method(SERVICE_METHOD_IMPLEMENTATION_TYPE, name)
                .addArguments(reference, serviceName, methodName)
                .apply();
    }
}
