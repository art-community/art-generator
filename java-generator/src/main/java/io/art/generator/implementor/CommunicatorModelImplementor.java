package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.CommunicatorSpecificationMethods.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class CommunicatorModelImplementor {
    public NewMethod implementCommunicatorsMethod(CommunicatorModel communicatorModel) {
        TypeModel registryType = COMMUNICATOR_PROXY_REGISTRY_TYPE;
        NewMethod communicatorsMethod = newMethod()
                .name(COMMUNICATORS_NAME)
                .parameter(newParameter(COMMUNICATOR_MODEL_TYPE, COMMUNICATOR_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        ImmutableMap<String, CommunicatorSpecificationModel> communicators = communicatorModel.getCommunicators();
        communicators.values().forEach(specificationModel -> communicatorsMethod.statement(() -> maker().Exec(executeRegisterMethod(specificationModel))));
        return communicatorsMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementCommunicatorProxies(CommunicatorModel communicatorModel) {
        ImmutableArray.Builder<NewClass> proxies = ImmutableArray.immutableArrayBuilder();
        for (Map.Entry<String, CommunicatorSpecificationModel> entry : communicatorModel.getCommunicators().entrySet()) {
            CommunicatorSpecificationModel specificationModel = entry.getValue();
            NewClass proxyClass = newClass()
                    .field(newField().modifiers(PRIVATE).type(COMMUNICATOR_MODEL_TYPE).name(COMMUNICATOR_MODEL_NAME).byConstructor(true))
                    .name(specificationModel.getImplementationInterface().getSimpleName() + PROXY_CLASS_SUFFIX)
                    .modifiers(PUBLIC | STATIC)
                    .implement(type(specificationModel.getImplementationInterface()));
            Method[] declaredMethods = specificationModel.getImplementationInterface().getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                Method method = declaredMethods[i];
                if (method.getParameterCount() > 1) {
                    throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(specificationModel.getImplementationInterface(), method));
                }
                String specificationFieldName = SPECIFICATION_FIELD_PREFIX + i;
                proxyClass.field(newField()
                        .type(COMMUNICATOR_SPECIFICATION_TYPE)
                        .name(specificationFieldName)
                        .modifiers(PRIVATE)
                        .byConstructor(true)
                        .initializer(() -> specificationBuilder(specificationModel, method)));

                NewMethod methodImplementation = overrideMethod(method);
                proxyClass.method(methodImplementation);

                List<JCExpression> arguments = methodImplementation.parameters()
                        .stream()
                        .map(NewParameter::getName)
                        .map(JavacService::ident)
                        .collect(toList());

                if (method.getReturnType() == void.class) {
                    methodImplementation.statement(() -> method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).execute());
                    continue;
                }

                methodImplementation.statement(() -> returnMethodCall(method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).apply()));
            }
            proxies.add(proxyClass);
        }
        return proxies.build();
    }

    private JCMethodInvocation executeRegisterMethod(CommunicatorSpecificationModel specificationModel) {
        Class<?> implementationInterface = specificationModel.getImplementationInterface();
        String proxyClassName = implementationInterface.getSimpleName() + PROXY_CLASS_SUFFIX;
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(implementationInterface.getSimpleName()), newObject(proxyClassName, com.sun.tools.javac.util.List.of(ident(COMMUNICATOR_MODEL_NAME))))
                .apply();
    }

    private JCExpression specificationBuilder(CommunicatorSpecificationModel specificationModel, Method method) {
        TypeModel methodProcessingModeType = METHOD_PROCESSING_MODE_TYPE;
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(specificationModel.getImplementationInterface(), method));
        }
        Type returnType = method.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        NewBuilder specificationBuilder = newBuilder(COMMUNICATOR_SPECIFICATION_TYPE).method(COMMUNICATOR_ID, literal(specificationModel.getId()));
        if (!isEmpty(parameterTypes)) {
            switch (inputMode) {
                case BLOCKING:
                    specificationBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    specificationBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(((ParameterizedType) parameterTypes[0]).getActualTypeArguments()[0]));
                    break;
            }
        }
        if (!void.class.equals(returnType)) {
            switch (outputMode) {
                case BLOCKING:
                    specificationBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(returnType));
                    break;
                case MONO:
                case FLUX:
                    specificationBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(((ParameterizedType) returnType).getActualTypeArguments()[0]));
                    break;
            }
        }
        return specificationBuilder
                .method(INPUT_MODE_NAME, select(methodProcessingModeType, inputMode.name()))
                .method(OUTPUT_MODE_NAME, select(methodProcessingModeType, outputMode.name()))
                .generate(builder -> decorateMethodBuilder(builder, specificationModel.getImplementationInterface()));
    }

    private JCMethodInvocation decorateMethodBuilder(JCMethodInvocation builder, Class<?> interfaceClass) {
        return method(COMMUNICATOR_MODEL_NAME, IMPLEMENT_NAME)
                .addArguments(literal(interfaceClass.getSimpleName()), builder)
                .apply();
    }

}
