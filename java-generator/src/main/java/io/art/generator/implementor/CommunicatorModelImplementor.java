package io.art.generator.implementor;

import com.sun.tools.javac.tree.*;
import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.model.implementation.communicator.*;
import io.art.rsocket.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.communicator.CommunicatorProxyCreator.*;
import static io.art.generator.creator.communicator.RsocketCommunicatorImplementationCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GenerationState.*;
import java.lang.reflect.*;
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
        Function<Method, NewBuilder> implementationBuilder = (Method method) -> createRsocketCommunicator(communicatorModel, method);
        return createNewProxyClass(communicatorModel, RsocketCommunicator.class, implementationBuilder);
    }

    private JCTree.JCMethodInvocation executeRegisterMethod(CommunicatorModel specificationModel) {
        Class<?> implementationInterface = specificationModel.getProxyClass();
        String proxyClassName = computeCommunicatorProxyClassName(implementationInterface);
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(implementationInterface.getSimpleName()), newObject(proxyClassName, ident(COMMUNICATOR_MODEL_NAME)))
                .apply();
    }
}
