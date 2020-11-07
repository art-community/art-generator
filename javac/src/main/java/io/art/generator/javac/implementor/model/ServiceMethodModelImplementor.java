package io.art.generator.javac.implementor.model;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.core.constants.*;
import io.art.generator.javac.model.*;
import io.art.model.module.*;
import io.art.server.decorator.*;
import io.art.server.implementation.*;
import io.art.server.model.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.constants.MethodDecoratorScope.*;
import static io.art.core.constants.MethodProcessingMode.*;
import static io.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.javac.constants.GeneratorConstants.Names.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.mapping.FromModelMapperImplementor.*;
import static io.art.generator.javac.implementor.mapping.ToModelMapperImplementor.*;
import static io.art.generator.javac.model.ImportModel.*;
import static io.art.generator.javac.model.NewBuilder.*;
import static io.art.generator.javac.model.NewConfigureMethod.*;
import static io.art.generator.javac.model.NewField.*;
import static io.art.generator.javac.model.NewMethod.*;
import static io.art.generator.javac.model.NewVariable.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.MakerService.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodModelImplementor {
    public void implementServiceMethodModel(NewClass providerClass, Class<?> serviceClass, Method serviceMethod) {
        Class<?> returnType = serviceMethod.getReturnType();
        Class<?>[] parameterTypes = serviceMethod.getParameterTypes();
        TypeModel registryType = type(MappersRegistry.class);

        NewField model = newField()
                .name(MODEL_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod(DECORATE_NAME, List.of(applyClassMethod(type(mainClass().getName()), CONFIGURE_NAME))));

        providerClass
                .addImport(classImport(serviceClass.getName()))
                .field(MODEL_NAME, model)
                .method(MAPPERS_NAME, generateMappersMethod(returnType, registryType, parameterTypes))
                .method(SERVICES_NAME, generateServicesMethod(returnType, type(ServiceSpecificationRegistry.class)))
                .method(DECORATE_NAME, generateDecorateMethod());

        for (Class<?> parameterType : parameterTypes) {
            providerClass.addImport(classImport(parameterType.getName()));
        }
    }

    private NewMethod generateMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateRegistryVariable(registryType));

        method.statement(() -> implementToModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            method.statement(() -> implementToModel(parameterClass));
        }

        method.statement(() -> implementFromModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            method.statement(() -> implementFromModel(parameterClass));
        }

        return method.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private NewMethod generateServicesMethod(Class<?> serviceClass, TypeModel registryType) {
        NewMethod method = newMethod()
                .name("services")
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateRegistryVariable(registryType));
        method.statement(() ->
                maker().Exec(applyMethod("registry", "register", List.of(
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
                )))
        );
        return method.statement(() -> returnVariable("registry"));
    }

    private JCExpressionStatement implementToModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), implementToModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }

    private JCExpressionStatement implementFromModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), implementFromModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }

    private JCVariableDecl generateRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name(REGISTRY_NAME)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
