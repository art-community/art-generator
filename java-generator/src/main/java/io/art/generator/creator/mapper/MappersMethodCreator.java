package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.determiner.MappingFieldsDeterminer.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class MappersMethodCreator {
    public NewMethod createMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));

        for (Class<?> parameterClass : parameterClasses) {
            collectUnknownClassesRecursive(parameterClass)
                    .stream()
                    .peek(type -> method.statement(() -> createToModel(type)))
                    .forEach(type -> method.statement(() -> createFromModel(type)));
            method.statement(() -> createToModel(parameterClass)).statement(() -> createFromModel(parameterClass));
        }

        collectUnknownClassesRecursive(returnClass).stream()
                .peek(type -> method.statement(() -> createToModel(type)))
                .forEach(type -> method.statement(() -> createFromModel(type)));
        method.statement(() -> createToModel(returnClass)).statement(() -> createFromModel(returnClass));

        return method.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCExpressionStatement createToModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), createToModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER_NAME, arguments);
    }

    private JCExpressionStatement createFromModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), createFromModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER_NAME, arguments);
    }
}
