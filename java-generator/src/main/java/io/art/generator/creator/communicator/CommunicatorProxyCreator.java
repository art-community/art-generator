package io.art.generator.creator.communicator;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.communicator.action.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.checker.NullityChecker.orElse;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.CommunicatorConstants.CommunicatorProxyMethods.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.CommunicatorsMethodsInspector.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GenerationState.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.function.*;

@UtilityClass
public class CommunicatorProxyCreator {
    public NewClass createNewProxyClass(CommunicatorModel communicatorModel, Function<Method, NewBuilder> implementationFactory) {
        JCExpression protocolExpression = select(type(communicatorModel.getProtocol().getClass()), communicatorModel.getProtocol().name());
        TypeModel implementationsReturnType = type(parameterizedType(ImmutableMap.class, String.class, CommunicatorAction.class));
        NewMethod getActions = overrideMethod(GET_ACTIONS_METHOD, implementationsReturnType).statement(() -> returnMethodCall(REGISTRY_NAME, GET_NAME));
        NewMethod getProtocol = overrideMethod(GET_PROTOCOL_METHOD).statement(() -> returnExpression(protocolExpression));
        NewClass proxy = newClass()
                .field(newField()
                        .modifiers(PRIVATE)
                        .type(COMMUNICATOR_MODEL_TYPE)
                        .name(COMMUNICATOR_MODEL_NAME)
                        .byConstructor(true))
                .name(communicatorName(communicatorModel.getProxyClass()))
                .modifiers(PRIVATE | STATIC)
                .implement(type(communicatorModel.getProxyClass()))
                .implement(COMMUNICATOR_PROXY_TYPE)
                .field(createRegistryField())
                .method(getActions)
                .method(getProtocol);
        int actionIndex = 0;
        for (Method method : getCommunicatorMethods(communicatorModel.getProxyClass())) {
            if (method.getParameterCount() > 1) {
                throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(communicatorModel.getProxyClass(), method));
            }
            String actionFieldName = ACTION_FIELD_PREFIX + actionIndex++;
            NewField actionField = newField()
                    .type(COMMUNICATOR_ACTION_TYPE)
                    .name(actionFieldName)
                    .modifiers(PRIVATE)
                    .byConstructor(true)
                    .initializer(() -> executeActionBuilder(communicatorModel, method, implementationFactory.apply(method)));
            NewMethod proxyMethod = createProxyMethod(method, actionFieldName);
            proxy.field(actionField).method(proxyMethod);
        }
        return proxy;
    }

    private NewField createRegistryField() {
        return newField()
                .type(COMMUNICATOR_ACTION_REGISTRY_TYPE)
                .name(REGISTRY_NAME)
                .modifiers(PRIVATE | FINAL)
                .initializer(() -> newObject(COMMUNICATOR_ACTION_REGISTRY_TYPE));
    }

    private NewMethod createProxyMethod(Method method, String actionFieldName) {
        NewMethod methodImplementation = overrideMethod(method);

        ImmutableArray<JCExpression> arguments = methodImplementation.parameters()
                .stream()
                .map(NewParameter::getName)
                .map(JavacService::ident)
                .collect(immutableArrayCollector());

        if (isVoidMethod(method)) {
            return methodImplementation.statement(() -> method(actionFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).execute());
        }

        return methodImplementation.statement(() -> returnExpression(method(actionFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).apply()));
    }

    private JCExpression executeActionBuilder(CommunicatorModel communicatorModel, Method proxyMethod, NewBuilder implementationBuilder) {
        TypeModel methodProcessingModeType = METHOD_PROCESSING_MODE_TYPE;
        Type[] parameterTypes = proxyMethod.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(communicatorModel.getProxyClass(), proxyMethod));
        }
        Type returnType = proxyMethod.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        NewBuilder actionBuilder = newBuilder(COMMUNICATOR_ACTION_TYPE)
                .method(COMMUNICATOR_ID_NAME, literal(communicatorModel.getId()))
                .method(ACTION_ID_NAME, literal(proxyMethod.getName()));

        if (nonNull(communicatorModel.getTargetServiceId())) {
            actionBuilder.method(TARGET_SERVICe_METHOD_NAME, method(SERVICE_METHOD_IDENTIFIER_TYPE, SERVICE_METHOD_NAME)
                            .addArgument(literal(communicatorModel.getTargetServiceId()))
                            .addArgument(literal(orElse(communicatorModel.getTargetMethodId(), proxyMethod.getName())))
                            .apply());
        }

        if (isNotEmpty(parameterTypes)) {
            switch (inputMode) {
                case BLOCKING:
                    actionBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    actionBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(extractFirstTypeParameter((ParameterizedType) parameterTypes[0])));
                    break;
            }
        }
        if (isNotVoid(returnType)) {
            switch (outputMode) {
                case BLOCKING:
                    actionBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(returnType));
                    break;
                case MONO:
                case FLUX:
                    actionBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(extractFirstTypeParameter((ParameterizedType) returnType)));
                    break;
            }
        }
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(proxyMethod.getName()))
                .addArguments(actionBuilder
                        .method(INPUT_MODE_NAME, select(methodProcessingModeType, inputMode.name()))
                        .method(OUTPUT_MODE_NAME, select(methodProcessingModeType, outputMode.name()))
                        .method(IMPLEMENTATION_NAME, implementationBuilder.generate())
                        .generate(builder -> method(COMMUNICATOR_MODEL_NAME, IMPLEMENT_NAME)
                                .addArguments(literal(communicatorModel.getProxyClass().getSimpleName()), builder)
                                .apply()))
                .apply();
    }
}
