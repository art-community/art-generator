package io.art.generator.creator.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.constants.*;
import io.art.generator.model.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.model.*;
import io.art.server.specification.*;
import lombok.experimental.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.INVOKE;
import static com.sun.tools.javac.code.Flags.PRIVATE;
import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.constants.MethodDecoratorScope.INPUT;
import static io.art.core.constants.MethodDecoratorScope.OUTPUT;
import static io.art.core.constants.MethodProcessingMode.BLOCKING;
import static io.art.generator.constants.GeneratorConstants.Names.REGISTRY_NAME;
import static io.art.generator.context.GeneratorContext.maker;
import static io.art.generator.model.NewBuilder.newBuilder;
import static io.art.generator.model.NewMethod.newMethod;
import static io.art.generator.model.NewVariable.newVariable;
import static io.art.generator.model.TypeModel.type;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class ServicesMethodCreator {
    public static NewMethod createServicesMethod(Class<?> serviceClass, TypeModel registryType) {
        return newMethod()
                .name("services")
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateRegistryVariable(registryType))
                .statement(() -> maker().Exec(applyMethod("registry", "register", List.of(
                        literal(serviceClass.getSimpleName()),
                        newBuilder(type(ServiceSpecification.class))
                                .method("serviceId", literal(serviceClass.getSimpleName()))
                                .method("method", List.of(literal("myMethod"), newBuilder(type(ServiceMethodSpecification.class))
                                        .method("serviceId", literal(serviceClass.getSimpleName()))
                                        .method("methodId", literal("myMethod"))
                                        .method("inputMode", select(type(MethodProcessingMode.class), BLOCKING.name()))
                                        .method("outputMode", select(type(MethodProcessingMode.class), BLOCKING.name()))
                                        .method("inputMapper", applyMethod(applyMethod("mappers"), "getToModel", List.of(select("Request", "class"))))
                                        .method("outputMapper", applyMethod(applyMethod("mappers"), "getFromModel", List.of(select("Response", "class"))))
                                        .method("inputDecorator", newObject(type(ServiceLoggingDecorator.class), List.of(
                                                applyClassMethod(type(ServiceMethodIdentifier.class),
                                                        "serviceMethod",
                                                        List.of(literal(serviceClass.getSimpleName()), literal("myMethod"))),
                                                select(type(MethodDecoratorScope.class), INPUT.name())
                                        )))
                                        .method("outputDecorator", newObject(type(ServiceLoggingDecorator.class), List.of(
                                                applyClassMethod(type(ServiceMethodIdentifier.class),
                                                        "serviceMethod",
                                                        List.of(literal(serviceClass.getSimpleName()), literal("myMethod"))),
                                                select(type(MethodDecoratorScope.class), OUTPUT.name())
                                        )))
                                        .method("implementation", applyClassMethod(type(ServiceMethodImplementation.class), "handler", List.of(
                                                maker().Reference(INVOKE, name("myMethod"), type(serviceClass).generate(), null),
                                                literal(serviceClass.getSimpleName()),
                                                literal("myMethod")
                                        )))
                                        .generate()))
                                .generate()
                ))))
                .statement(() -> returnVariable("registry"));
    }

    private JCTree.JCVariableDecl generateRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name(REGISTRY_NAME)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
