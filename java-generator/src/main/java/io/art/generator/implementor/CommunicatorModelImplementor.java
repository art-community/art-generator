package io.art.generator.implementor;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.service.*;
import io.art.model.implementation.communicator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
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
                        .modifiers(PRIVATE | STATIC | FINAL)
                        .initializer(() -> specificationBuilder(specificationModel)));

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
        String proxyClassName = specificationModel.getImplementationInterface().getSimpleName() + PROXY_CLASS_SUFFIX;
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(literal(specificationModel.getImplementationInterface().getSimpleName()), newObject(proxyClassName))
                .apply();
    }

    private JCExpression specificationBuilder(CommunicatorSpecificationModel specificationModel) {
        return newBuilder(COMMUNICATOR_SPECIFICATION_TYPE).generate();
    }

}
