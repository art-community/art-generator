package io.art.generator.creator.communicator;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.communicator.action.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.modeling.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.CommunicatorConstants.CommunicatorProxyMethods.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.communicator.CommunicatorImplementationCreator.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.CommunicatorsMethodsInspector.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static io.art.generator.type.TypeInspector.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class CommunicatorProxyCreator {
    public NewClass createNewProxyClass(TypeModel implementationType, CommunicatorModel model) {
        JCExpression protocolExpression = select(type(model.getProtocol().getClass()), model.getProtocol().name());
        TypeModel implementationsReturnType = type(parameterizedType(ImmutableMap.class, String.class, CommunicatorAction.class));
        NewMethod getActions = overrideMethod(GET_ACTIONS_METHOD, implementationsReturnType).statement(() -> returnMethodCall(REGISTRY_NAME, GET_NAME));
        NewMethod getProtocol = overrideMethod(GET_PROTOCOL_METHOD).statement(() -> returnExpression(protocolExpression));
        NewClass proxy = newClass()
                .field(newField()
                        .modifiers(PRIVATE)
                        .type(COMMUNICATOR_MODEL_TYPE)
                        .name(COMMUNICATOR_MODEL_NAME)
                        .byConstructor(true))
                .name(communicatorName(model.getCommunicatorInterface()))
                .modifiers(PRIVATE | STATIC)
                .implement(type(model.getCommunicatorInterface()))
                .implement(COMMUNICATOR_PROXY_TYPE)
                .field(createRegistryField())
                .method(getActions)
                .method(getProtocol);
        int actionIndex = 0;
        for (Method method : getCommunicatorMethods(model.getCommunicatorInterface())) {
            if (method.getParameterCount() > 1) {
                throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(model.getCommunicatorInterface(), method));
            }
            String actionFieldName = ACTION_FIELD_PREFIX + actionIndex++;
            NewBuilder implementation = createCommunicatorImplementation(implementationType, model, method);
            NewField actionField = newField()
                    .type(COMMUNICATOR_ACTION_TYPE)
                    .name(actionFieldName)
                    .modifiers(PRIVATE)
                    .byConstructor(true)
                    .initializer(() -> executeActionBuilder(model, method, implementation));
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

    private JCExpression executeActionBuilder(CommunicatorModel model, Method method, NewBuilder implementationBuilder) {
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(model.getCommunicatorInterface(), method));
        }
        Type returnType = method.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        Optional<CommunicatorActionModel> action = model.getActionByName(method.getName());
        String communicatorId = model.getId();
        String actionId = action.map(CommunicatorActionModel::getId).orElse(method.getName());
        NewBuilder actionBuilder = newBuilder(COMMUNICATOR_ACTION_TYPE)
                .method(COMMUNICATOR_ID_NAME, literal(communicatorId))
                .method(ACTION_ID_NAME, literal(actionId));

        String targetServiceId = action.map(CommunicatorActionModel::getTargetServiceId).orElse(model.getTargetServiceId());
        if (nonNull(targetServiceId)) {
            actionBuilder.method(TARGET_SERVICE_METHOD_NAME, method(SERVICE_METHOD_IDENTIFIER_TYPE, SERVICE_METHOD_NAME)
                    .addArgument(literal(targetServiceId))
                    .addArgument(literal(action.map(CommunicatorActionModel::getTargetMethodId).orElse(method.getName())))
                    .apply());
        }

        if (isNotEmpty(parameterTypes) && isNotVoid(parameterTypes[0])) {
            switch (inputMode) {
                case BLOCKING:
                    actionBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    Type firstTypeParameter = extractFirstTypeParameter((ParameterizedType) parameterTypes[0]);
                    if (isNotVoid(firstTypeParameter)) {
                        actionBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(firstTypeParameter));
                    }
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
                    Type firstTypeParameter = extractFirstTypeParameter((ParameterizedType) returnType);
                    if (isNotVoid(firstTypeParameter)) {
                        actionBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(firstTypeParameter));
                    }
                    break;
            }
        }
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(method.getName()))
                .addArguments(actionBuilder
                        .method(INPUT_MODE_NAME, select(METHOD_PROCESSING_MODE_TYPE, inputMode.name()))
                        .method(OUTPUT_MODE_NAME, select(METHOD_PROCESSING_MODE_TYPE, outputMode.name()))
                        .method(IMPLEMENTATION_NAME, implementationBuilder.generate())
                        .generate(builder -> method(COMMUNICATOR_MODEL_NAME, IMPLEMENT_NAME)
                                .addArguments(literal(communicatorId), literal(actionId), builder)
                                .apply()))
                .apply();
    }
}
