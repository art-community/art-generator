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
import static io.art.generator.factory.ReferenceFactory.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.ServiceMethodsInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.type.TypeInspector.*;
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
        JCMethodInvocation specificationBuilder = executeServiceSpecificationBuilder(servicesMethod, serviceModel);
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(serviceModel.getId()), specificationBuilder)
                .apply();
    }

    private JCMethodInvocation executeServiceSpecificationBuilder(NewMethod servicesMethod, ServiceModel model) {
        NewBuilder builder = newBuilder(SERVICE_SPECIFICATION_TYPE);
        Class<?> serviceClass = model.getServiceClass();
        for (Method method : getServiceMethods(serviceClass)) {
//            if (isNotEmpty(model.getMethods()) && !model.getMethodByName(method.getName()).isPresent()) {
//                continue;
//            }
            JCMethodInvocation methodSpecificationBuilder = executeMethodSpecificationBuilder(servicesMethod, model, method);
            builder.method(METHOD_NAME, literal(method.getName()), methodSpecificationBuilder);
            info(format(GENERATED_SERVICE_METHOD_SPECIFICATION, formatSignature(serviceClass, method)));
        }
        return builder.generate();
    }

    private JCMethodInvocation executeMethodSpecificationBuilder(NewMethod servicesMethod, ServiceModel model, Method method) {
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(model.getServiceClass(), method));
        }
        Type returnType = method.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? EMPTY : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        String serviceId = model.getId();
        String methodId = model.getMethodByName(method.getName()).map(ServiceMethodModel::getId).orElse(method.getName());
        NewBuilder methodBuilder = newBuilder(SERVICE_METHOD_SPECIFICATION_TYPE)
                .method(SERVICE_ID_NAME, literal(serviceId))
                .method(METHOD_ID_NAME, literal(methodId));
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
                JCNewClass serviceMethodIdentifier = newObject(SERVICE_METHOD_IDENTIFIER_TYPE, literal(serviceId), literal(methodId));
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
                .method(INPUT_MODE_NAME, select(METHOD_PROCESSING_MODE_TYPE, inputMode.name()))
                .method(OUTPUT_MODE_NAME, select(METHOD_PROCESSING_MODE_TYPE, outputMode.name()))
                .method(IMPLEMENTATION_NAME, executeHandlerMethod(model, method))
                .generate(builder -> method(SERVER_MODEL_NAME, IMPLEMENT_NAME)
                        .addArguments(literal(serviceId), literal(methodId), builder)
                        .apply());
    }

    private JCMethodInvocation executeHandlerMethod(ServiceModel model, Method method) {
        String name = HANDLER_NAME;
        if (isEmpty(method.getParameterTypes())) {
            name = PRODUCER_NAME;
        }
        if (isVoid(method.getReturnType())) {
            name = CONSUMER_NAME;
        }
        if (isVoid(method.getReturnType()) && isEmpty(method.getParameterTypes())) {
            name = RUNNER_NAME;
        }
        JCExpression owner = callOwner(model.getServiceClass(), isStatic(method.getModifiers()));
        JCMemberReference reference = invokeReference(owner, (method.getName()));
        String serviceId = model.getId();
        String methodId = model.getMethodByName(method.getName()).map(ServiceMethodModel::getId).orElse(method.getName());
        return method(SERVICE_METHOD_IMPLEMENTATION_TYPE, name)
                .addArguments(reference, literal(serviceId), literal(methodId))
                .apply();
    }
}
