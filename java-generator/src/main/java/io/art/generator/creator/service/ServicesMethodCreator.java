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
import static io.art.generator.model.ImportModel.classImport;
import static io.art.generator.model.NewBuilder.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServicesMethodCreator {
    public NewMethod createServicesMethod(Class<?> serviceClass, TypeModel registryType) {
        NewMethod newMethod = newMethod();
        return newMethod
                .name(SERVICES_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType))
                .statement(() -> maker().Exec(createRegisterMethod(newMethod, serviceClass)))
                .statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCMethodInvocation createRegisterMethod(NewMethod newMethod, Class<?> serviceClass) {
        List<JCExpression> arguments = List.of(literal(serviceClass.getSimpleName()), createServiceSpecificationBuilder(newMethod, serviceClass));
        return applyMethod(REGISTRY_NAME, REGISTER_NAME, arguments);
    }

    private JCMethodInvocation createServiceSpecificationBuilder(NewMethod newMethod, Class<?> serviceClass) {
        NewBuilder builder = newBuilder(type(ServiceSpecification.class)).method(SERVICE_ID, literal(serviceClass.getSimpleName()));
        for (Method method : getServiceMethods(serviceClass)) {
            List<JCExpression> arguments = List.of(literal(method.getName()), createMethodSpecificationBuilder(newMethod, serviceClass, method));
            builder.method(METHOD, arguments);
        }
        return builder.generate();
    }

    private JCMethodInvocation createMethodSpecificationBuilder(NewMethod newMethod, Class<?> serviceClass, Method serviceMethod) {
        Type[] parameterTypes = serviceMethod.getGenericParameterTypes();
        MethodProcessingMode inputMode = BLOCKING;
        for (Type parameterType : parameterTypes) {
            if (parameterType instanceof Flux) {
                inputMode = FLUX;
                break;
            }
            if (parameterType instanceof Mono) {
                inputMode = MONO;
                break;
            }
        }
        MethodProcessingMode outputMode = BLOCKING;
        Type returnType = serviceMethod.getGenericReturnType();
        Class<?> returnClass = serviceMethod.getReturnType();
        if (returnClass.isAssignableFrom(Flux.class)) {
            outputMode = FLUX;
        }
        if (returnClass.isAssignableFrom(Mono.class)) {
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
                .method(INPUT_MAPPER, createInputMapper(newMethod, parameterTypes))
                .method(OUTPUT_MAPPER, createOutputMapper(newMethod, returnType))
                .method(INPUT_MODE, select(type(MethodProcessingMode.class), inputMode.name()))
                .method(OUTPUT_MODE, select(type(MethodProcessingMode.class), outputMode.name()))
                .method(INPUT_DECORATOR, inputDecorator)
                .method(OUTPUT_DECORATOR, outputDecorator)
                .method(IMPLEMENTATION, createHandlerMethod(serviceClass, serviceMethod))
                .generate();
    }

    private JCMethodInvocation createOutputMapper(NewMethod newMethod, Type returnType) {
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            if (((Class<?>) parameterizedType.getRawType()).isAssignableFrom(Flux.class)) {
                Class<?> typeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                List<JCExpression> arguments = List.of(select(type(typeArgument), CLASS_KEYWORD));
                newMethod.addClassImport(classImport(typeArgument.getName()));
                return applyMethod(MAPPERS_REGISTRY_NAME, GET_FROM_MODEL_NAME, arguments);
            }
            if (((Class<?>) parameterizedType.getRawType()).isAssignableFrom(Mono.class)) {
                Class<?> typeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                List<JCExpression> arguments = List.of(select(type(typeArgument), CLASS_KEYWORD));
                newMethod.addClassImport(classImport(typeArgument.getName()));
                return applyMethod(MAPPERS_REGISTRY_NAME, GET_FROM_MODEL_NAME, arguments);
            }
        }
        List<JCExpression> arguments = List.of(select(returnType.getTypeName(), CLASS_KEYWORD));
        newMethod.addClassImport(classImport(returnType.getTypeName()));
        return applyMethod(MAPPERS_REGISTRY_NAME, GET_FROM_MODEL_NAME, arguments);
    }

    private JCMethodInvocation createInputMapper(NewMethod newMethod, Type[] parameterTypes) {
        ListBuffer<JCExpression> parameters = new ListBuffer<>();
        for (Type parameterType : parameterTypes) {
            if (parameterType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) parameterType;
                if (parameterizedType instanceof Flux) {
                    Class<?> typeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    parameters.add(select(type(typeArgument), CLASS_KEYWORD));
                    newMethod.addClassImport(classImport(typeArgument.getName()));
                }
                if (parameterizedType instanceof Mono) {
                    Class<?> typeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    parameters.add(select(type(typeArgument), CLASS_KEYWORD));
                    newMethod.addClassImport(classImport(typeArgument.getName()));
                }
                continue;
            }
            parameters.add(select(type((Class<?>) parameterType), CLASS_KEYWORD));
            newMethod.addClassImport(classImport(((Class<?>) parameterType).getName()));
        }
        return applyMethod(MAPPERS_REGISTRY_NAME, GET_TO_MODEL_NAME, parameters.toList());
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
