package io.art.generator.creator.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.constants.*;
import io.art.generator.model.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.model.*;
import io.art.server.specification.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.MethodDecoratorScope.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.ServiceSpecificationMethods.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.determiner.ServiceMethodsDeterminer.*;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServicesMethodCreator {
    public NewMethod createServicesMethod(Class<?> serviceClass, TypeModel registryType) {
        return newMethod()
                .name(SERVICES_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType))
                .statement(() -> maker().Exec(createRegisterMethod(serviceClass)))
                .statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCMethodInvocation createRegisterMethod(Class<?> serviceClass) {
        List<JCExpression> arguments = List.of(literal(serviceClass.getSimpleName()), createServiceSpecificationBuilder(serviceClass));
        return applyMethod(REGISTRY_NAME, REGISTER_NAME, arguments);
    }

    private JCMethodInvocation createServiceSpecificationBuilder(Class<?> serviceClass) {
        NewBuilder builder = newBuilder(type(ServiceSpecification.class)).method(SERVICE_ID, literal(serviceClass.getSimpleName()));
        for (Method method : getServiceMethods(serviceClass)) {
            builder.method(METHOD, List.of(literal(method.getName()), createMethodSpecificationBuilder(serviceClass, method)));
        }
        return builder.generate();
    }

    private JCMethodInvocation createMethodSpecificationBuilder(Class<?> serviceClass, Method serviceMethod) {
        Class<?>[] parameterTypes = serviceMethod.getParameterTypes();
        ListBuffer<JCExpression> parameters = new ListBuffer<>();
        MethodProcessingMode inputMode = BLOCKING;
        for (Class<?> parameterType : parameterTypes) {
            parameters.add(select(type(parameterType), CLASS_KEYWORD));
            if (Flux.class.isAssignableFrom(parameterType)) {
                inputMode = FLUX;
                break;
            }
            if (Mono.class.isAssignableFrom(parameterType)) {
                inputMode = MONO;
                break;
            }
        }
        MethodProcessingMode outputMode = BLOCKING;
        Class<?> returnType = serviceMethod.getReturnType();
        if (Flux.class.isAssignableFrom(returnType)) {
            outputMode = FLUX;
        }
        if (Mono.class.isAssignableFrom(returnType)) {
            outputMode = MONO;
        }
        JCNewClass inputDecorator = newObject(type(ServiceLoggingDecorator.class), List.of(
                applyClassMethod(type(ServiceMethodIdentifier.class),
                        SERVICE_METHOD,
                        List.of(literal(serviceClass.getSimpleName()), literal(serviceMethod.getName()))),
                select(type(MethodDecoratorScope.class), INPUT.name())
        ));
        JCNewClass outputDecorator = newObject(type(ServiceLoggingDecorator.class), List.of(
                applyClassMethod(type(ServiceMethodIdentifier.class),
                        SERVICE_METHOD,
                        List.of(literal(serviceClass.getSimpleName()), literal(serviceMethod.getName()))),
                select(type(MethodDecoratorScope.class), OUTPUT.name())
        ));
        return newBuilder(type(ServiceMethodSpecification.class))
                .method(SERVICE_ID, literal(serviceClass.getSimpleName()))
                .method(METHOD_ID, literal(serviceMethod.getName()))
                .method(INPUT_MAPPER, applyMethod(applyMethod(MAPPERS_NAME), GET_TO_MODEL_NAME, parameters.toList()))
                .method(OUTPUT_MAPPER, applyMethod(applyMethod(MAPPERS_NAME), GET_FROM_MODEL_NAME, List.of(select(type(returnType), CLASS_KEYWORD))))
                .method(INPUT_MODE, select(type(MethodProcessingMode.class), inputMode.name()))
                .method(OUTPUT_MODE, select(type(MethodProcessingMode.class), outputMode.name()))
                .method(INPUT_DECORATOR, inputDecorator)
                .method(OUTPUT_DECORATOR, outputDecorator)
                .method(IMPLEMENTATION, createHandlerMethod(serviceClass, serviceMethod))
                .generate();
    }

    private JCMethodInvocation createHandlerMethod(Class<?> serviceClass, Method serviceMethod) {
        String name = HANDLER_METHOD;
        if (isEmpty(serviceMethod.getParameterTypes())) {
            name = PRODUCER_METHOD;
        }
        if (void.class.equals(serviceMethod.getReturnType())) {
            name = CONSUMER_METHOD;
        }
        JCMemberReference reference = maker().Reference(INVOKE, name(serviceMethod.getName()), type(serviceClass).generate(), null);
        JCLiteral serviceName = literal(serviceClass.getSimpleName());
        JCLiteral methodName = literal(serviceMethod.getName());
        return applyClassMethod(type(ServiceMethodImplementation.class), name, List.of(reference, serviceName, methodName));
    }
}
