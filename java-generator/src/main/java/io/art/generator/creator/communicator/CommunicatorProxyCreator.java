package io.art.generator.creator.communicator;

import com.sun.tools.javac.tree.*;
import io.art.communicator.proxy.*;
import io.art.communicator.registry.*;
import io.art.core.collection.*;
import io.art.core.constants.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.core.factory.ArrayFactory.*;
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
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GenerationState.*;
import java.lang.reflect.*;
import java.util.function.*;

@UtilityClass
public class CommunicatorProxyCreator {
    public NewClass createNewProxyClass(CommunicatorModel communicatorModel, Class<?> communicatorClass, Function<Method, NewBuilder> implementationFactory) {
        JCTree.JCExpression protocolExpression = select(type(communicatorModel.getProtocol().getClass()), communicatorModel.getProtocol().name());
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
                .name(computeCommunicatorProxyClassName(communicatorModel.getProxyClass()))
                .modifiers(PRIVATE | STATIC)
                .implement(type(communicatorModel.getProxyClass()))
                .implement(proxyType)
                .field(createRegistryField(communicatorClass))
                .method(getImplementations)
                .method(getProtocol);
        int specificationIndex = 0;
        for (Method method : getCommunicatorMethods(communicatorModel.getProxyClass())) {
            if (method.getParameterCount() > 1) {
                throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(communicatorModel.getProxyClass(), method));
            }
            String specificationFieldName = SPECIFICATION_FIELD_PREFIX + specificationIndex++;
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

        ImmutableArray<JCTree.JCExpression> arguments = methodImplementation.parameters()
                .stream()
                .map(NewParameter::getName)
                .map(JavacService::ident)
                .collect(immutableArrayCollector());

        if (isVoidMethod(method)) {
            return methodImplementation.statement(() -> method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).execute());
        }

        return methodImplementation.statement(() -> returnExpression(method(specificationFieldName, COMMUNICATE_METHOD_NAME).addArguments(arguments).apply()));
    }

    private JCTree.JCExpression executeSpecificationBuilder(CommunicatorModel specificationModel, Method method, NewBuilder implementationBuilder) {
        TypeModel methodProcessingModeType = METHOD_PROCESSING_MODE_TYPE;
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length > 1) {
            throw new ValidationException(MORE_THAN_ONE_PARAMETER, formatSignature(specificationModel.getProxyClass(), method));
        }
        Type returnType = method.getGenericReturnType();
        MethodProcessingMode inputMode = isEmpty(parameterTypes) ? BLOCKING : calculateProcessingMode(parameterTypes[0]);
        MethodProcessingMode outputMode = calculateProcessingMode(returnType);
        NewBuilder specificationBuilder = newBuilder(COMMUNICATOR_SPECIFICATION_TYPE).method(COMMUNICATOR_ID_NAME, literal(specificationModel.getId()));
        if (isNotEmpty(parameterTypes)) {
            switch (inputMode) {
                case BLOCKING:
                    specificationBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(parameterTypes[0]));
                    break;
                case MONO:
                case FLUX:
                    specificationBuilder.method(INPUT_MAPPER_NAME, fromModelMapper(extractFirstTypeParameter((ParameterizedType) parameterTypes[0])));
                    break;
            }
        }
        if (isNotVoid(returnType)) {
            switch (outputMode) {
                case BLOCKING:
                    specificationBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(returnType));
                    break;
                case MONO:
                case FLUX:
                    specificationBuilder.method(OUTPUT_MAPPER_NAME, toModelMapper(extractFirstTypeParameter((ParameterizedType) returnType)));
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
