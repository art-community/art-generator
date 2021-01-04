package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.communicator.proxy.*;
import io.art.communicator.registry.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.core.factory.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.implementation.communicator.*;
import io.art.rsocket.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.calculator.MethodProcessingModeCalculator.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.CommunicatorProxyMethods.*;
import static io.art.generator.constants.GeneratorConstants.CommunicatorSpecificationMethods.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.communicator.RsocketCommunicatorImplementationCreator.*;
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
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.service.JavacService.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

@UtilityClass
public class CommunicatorModelImplementor {
    public NewMethod implementCommunicatorsMethod(CommunicatorModuleModel communicatorModel) {
        TypeModel registryType = COMMUNICATOR_PROXY_REGISTRY_TYPE;
        NewMethod communicatorsMethod = newMethod()
                .name(COMMUNICATORS_NAME)
                .parameter(newParameter(COMMUNICATOR_MODEL_TYPE, COMMUNICATOR_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        ImmutableMap<String, CommunicatorModel> communicators = communicatorModel.getCommunicators();
        communicators.values().forEach(specificationModel -> communicatorsMethod.statement(() -> maker().Exec(executeRegisterMethod(specificationModel))));
        return communicatorsMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementCommunicatorProxies(CommunicatorModuleModel communicatorModel) {
        ImmutableArray.Builder<NewClass> proxies = ImmutableArray.immutableArrayBuilder();
        communicatorModel.getRsocketCommunicators().values().forEach(model -> proxies.add(createRsocketProxy(model)));
        return proxies.build();
    }

    private NewClass createRsocketProxy(RsocketCommunicatorModel communicatorModel) {
        Function<Method, NewBuilder> implementationBuilder = (Method method) -> createRsocketCommunicatorImplementation(communicatorModel, method);
        return createNewProxyClass(communicatorModel, RsocketCommunicator.class, implementationBuilder);
    }

    private NewClass createNewProxyClass(CommunicatorModel communicatorModel, Class<?> communicatorClass, Function<Method, NewBuilder> implementationFactory) {
        JCExpression protocolExpression = select(type(communicatorModel.getProtocol().getClass()), communicatorModel.getProtocol().name());
        TypeModel proxyType = type(parameterizedType(CommunicatorProxy.class, arrayOf(communicatorClass)));
        TypeModel implementationsReturnType = type(parameterizedType(ImmutableArray.class, arrayOf(communicatorClass)));
        NewMethod getImplementations = overrideMethod(GET_IMPLEMENTATIONS_METHOD, implementationsReturnType).statement(() -> returnMethodCall(REGISTRY_NAME, GET_NAME));
        NewMethod getProtocol = overrideMethod(GET_PROTOCOL_METHOD).statement(() -> returnExpression(protocolExpression));
        NewClass proxy = newClass()
                .field(newField()
                        .modifiers(PRIVATE)
                        .type(COMMUNICATOR_MODEL_TYPE)
                        .name(COMMUNICATOR_MODEL_NAME)
                        .byConstructor(true))
                .name(communicatorModel.getProxyClass().getSimpleName() + PROXY_CLASS_SUFFIX)
                .modifiers(PRIVATE | STATIC)
                .implement(type(communicatorModel.getProxyClass()))
                .implement(proxyType)
                .field(createRegistryField(communicatorClass))
                .method(getImplementations)
                .method(getProtocol);
        Method[] declaredMethods = communicatorModel.getProxyClass().getDeclaredMethods();
        for (int index = 0; index < declaredMethods.length; index++) {
            Method method = declaredMethods[index];
            if (method.isDefault() || method.isSynthetic()) continue;
            if (method.getParameterCount() > 1) {
                throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(communicatorModel.getProxyClass(), method));
            }
            String specificationFieldName = SPECIFICATION_FIELD_PREFIX + index;
            NewField specificationField = newField()
                    .type(COMMUNICATOR_SPECIFICATION_TYPE)
                    .name(specificationFieldName)
                    .modifiers(PRIVATE)
                    .byConstructor(true)
                    .initializer(() -> executeSpecificationBuilder(communicatorModel, method, implementationFactory.apply(method)));
            NewMethod proxyMethod = createProxyMethod(method, specificationFieldName);
            proxy.field(specificationField).method(proxyMethod);
        }
        return proxy;
    }

    private NewField createRegistryField(Class<?> communicatorClass) {
        return newField()
                .type(type(parameterizedType(CommunicatorImplementationRegistry.class, arrayOf(communicatorClass))))
                .name(REGISTRY_NAME)
                .modifiers(PRIVATE | FINAL)
                .initializer(() -> newObject(type(parameterizedType(CommunicatorImplementationRegistry.class, arrayOf(communicatorClass)))));
    }

    private NewMethod createProxyMethod(Method method, String specificationFieldName) {
        NewMethod methodImplementation = overrideMethod(method);

        List<JCExpression> arguments = methodImplementation.parameters()
                .stream()
                .map(NewParameter::getName)
                .map(JavacService::ident)
                .collect(toCollection(ArrayFactory::dynamicArray));

        if (method.getReturnType() == void.class) {
            return methodImplementation.statement(() -> method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).execute());
        }

        return methodImplementation.statement(() -> returnExpression(method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).apply()));
    }

    private JCMethodInvocation executeRegisterMethod(CommunicatorModel specificationModel) {
        Class<?> implementationInterface = specificationModel.getProxyClass();
        String proxyClassName = implementationInterface.getSimpleName() + PROXY_CLASS_SUFFIX;
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(implementationInterface.getSimpleName()), newObject(proxyClassName, com.sun.tools.javac.util.List.of(ident(COMMUNICATOR_MODEL_NAME))))
                .apply();
    }

    private JCExpression executeSpecificationBuilder(CommunicatorModel specificationModel, Method method, NewBuilder implementationBuilder) {
        TypeModel methodProcessingModeType = METHOD_PROCESSING_MODE_TYPE;
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(specificationModel.getProxyClass(), method));
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
                .method(IMPLEMENTATION_NAME, method(REGISTRY_NAME, REGISTER_NAME).addArguments(implementationBuilder.generate()).apply())
                .generate(builder -> method(COMMUNICATOR_MODEL_NAME, IMPLEMENT_NAME)
                        .addArguments(literal(specificationModel.getProxyClass().getSimpleName()), builder)
                        .apply());
    }
}
