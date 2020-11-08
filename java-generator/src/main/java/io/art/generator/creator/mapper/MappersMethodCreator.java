package io.art.generator.creator.mapper;

import com.google.common.collect.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import io.art.generator.model.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappersMethodCreator {
    public NewMethod createMappersMethod(ImmutableSet<Type> types) {
        TypeModel registryType = type(MappersRegistry.class);
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));

        Set<Type> createdMappers = setOf();
        for (Type type : types) {
            createMapperForType(method, type, createdMappers);
            createdMappers.add(type);
        }

        return method.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private void createMapperForType(NewMethod method, Type type, Set<Type> createdMappers) {
        if (createdMappers.contains(type)) {
            return;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            createdMappers.add(rawType);
            method.statement(() -> createToModel(rawType)).statement(() -> createFromModel(rawType));
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                createdMappers.add(actualTypeArgument);
                createMapperForType(method, actualTypeArgument, createdMappers);
            }
            return;
        }
        if (type instanceof Class<?>) {
            createdMappers.add(type);
            Class<?> typeAsClass = (Class<?>) type;
            method.statement(() -> createToModel(typeAsClass)).statement(() -> createFromModel(typeAsClass));
        }
    }

    private JCExpressionStatement createToModel(Class<?> type) {
        List<JCExpression> arguments = List.of(classReference(type), createToModelMapper(type));
        return execMethodCall(REGISTRY_NAME, REGISTER_NAME, arguments);
    }

    private JCExpressionStatement createFromModel(Class<?> type) {
        List<JCExpression> arguments = List.of(classReference(type), createFromModelMapper(type));
        return execMethodCall(REGISTRY_NAME, REGISTER_NAME, arguments);
    }
}
