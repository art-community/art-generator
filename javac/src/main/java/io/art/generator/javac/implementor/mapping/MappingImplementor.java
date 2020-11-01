package io.art.generator.javac.implementor.mapping;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.core.constants.*;
import io.art.generator.javac.model.*;
import io.art.launcher.*;
import io.art.model.configurator.*;
import io.art.model.module.*;
import io.art.server.implementation.*;
import io.art.server.registry.*;
import io.art.server.specification.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.source.tree.MemberReferenceTree.ReferenceMode.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.javac.constants.GeneratorConstants.*;
import static io.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.mapping.FromModelMapperImplementor.*;
import static io.art.generator.javac.implementor.mapping.ToModelMapperImplementor.*;
import static io.art.generator.javac.model.ImportModel.*;
import static io.art.generator.javac.model.NewBuilder.*;
import static io.art.generator.javac.model.NewClass.*;
import static io.art.generator.javac.model.NewConfigureMethod.decorateMethod;
import static io.art.generator.javac.model.NewField.*;
import static io.art.generator.javac.model.NewMethod.*;
import static io.art.generator.javac.model.NewVariable.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.ClassMutationService.*;
import static io.art.generator.javac.service.MakerService.*;
import static java.util.Arrays.*;

@UtilityClass
public class MappingImplementor {
    public void implementMethodMapping(Class<?> serviceClass, Class<?> returnClass, Class<?>[] parameterClasses) {
        TypeModel registryType = type(MappersRegistry.class);

        NewField model = newField()
                .name("model")
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod("decorate", List.of(applyClassMethod(type(mainClass().getName()), "configure"))));

        NewClass configurationClass = newClass()
                .modifiers(PRIVATE | STATIC)
                .name(mainClass().getName() + CONFIGURATOR_CLASS_NAME_SUFFIX)
                .addImport(importClass(returnClass.getName()))
                .addImport(importClass(PrimitiveMapping.class.getName()))
                .addImport(importClass(ArrayMapping.class.getName()))
                .addImport(importClass(EntityMapping.class.getName()))
                .addImport(importClass(BinaryMapping.class.getName()))
                .addImport(importClass(ArrayValue.class.getName()))
                .addImport(importClass(BinaryValue.class.getName()))
                .addImport(importClass(Entity.class.getName()))
                .addImport(importClass(Value.class.getName()))
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importClass(MappersRegistry.class.getName()))
                .addImport(importClass(ServiceSpecificationRegistry.class.getName()))
                .addImport(importClass(ServiceSpecification.class.getName()))
                .addImport(importClass(ServiceMethodSpecification.class.getName()))
                .addImport(importClass(MethodProcessingMode.class.getName()))
                .addImport(importClass(ServiceMethodImplementation.class.getName()))
                .addImport(importClass(ConfiguratorModel.class.getName()))
                .addImport(importClass(ValueConfiguratorModel.class.getName()))
                .addImport(importClass(ModuleModel.class.getName()))
                .addImport(importClass(ServerConfiguratorModel.class.getName()))
                .field("model", model)
                .method("mappers", generateMappersMethod(returnClass, registryType, parameterClasses))
                .method("services", generateServicesMethod(serviceClass, type(ServiceSpecificationRegistry.class)))
                .method("decorate", decorateMethod());
        stream(parameterClasses).forEach(parameter -> configurationClass.addImport(importClass(parameter.getName())));
        replaceInnerClass(mainClass(), configurationClass);
        NewMethod mainMethod = newMethod()
                .modifiers(PUBLIC | STATIC)
                .name("main")
                .returnType(type(void.class))
                .parameter(NewParameter.newParameter(type(String[].class), "args"))
                .addClassImport(importClass(ModuleLauncher.class.getName()))
                .statement(() -> maker().Exec(applyClassMethod(type(ModuleLauncher.class), "launch", List.of(select(mainClass().getName() + CONFIGURATOR_CLASS_NAME_SUFFIX, "model")))));
        replaceMethod(mainClass(), mainMethod);
    }

    private NewMethod generateMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod method = newMethod()
                .name("mappers")
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

        return method.statement(() -> returnVariable("registry"));
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
                                        .method("inputMode", select(type(MethodProcessingMode.class), "BLOCKING"))
                                        .method("outputMode", select(type(MethodProcessingMode.class), "BLOCKING"))
                                        .method("inputMapper", applyMethod(applyMethod("mappers"), "getToModel", List.of(select("Request", "class"))))
                                        .method("outputMapper", applyMethod(applyMethod("mappers"), "getFromModel", List.of(select("Response", "class"))))
                                        .method("implementation", applyClassMethod(type(ServiceMethodImplementation.class), "handler", List.of(
                                                maker().Reference(INVOKE, name("myMethod"), type(serviceClass).generate(), null),
                                                literal(serviceClass.getSimpleName()),
                                                literal("myMethod")
                                        ))).generate()))
                                .generate()
                )))
        );
        return method.statement(() -> returnVariable("registry"));
    }

    private JCTree.JCExpressionStatement implementToModel(Class<?> modelClass) {
        return execMethodCall("registry", REGISTER, List.of(classReference(modelClass), implementToModelMapper(modelClass)));
    }

    private JCTree.JCExpressionStatement implementFromModel(Class<?> modelClass) {
        return execMethodCall("registry", REGISTER, List.of(classReference(modelClass), implementFromModelMapper(modelClass)));
    }

    private JCTree.JCVariableDecl generateRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name("registry")
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
