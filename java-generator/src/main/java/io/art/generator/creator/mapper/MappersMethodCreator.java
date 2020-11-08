package io.art.generator.creator.mapper;

import com.google.common.reflect.*;
import com.sun.beans.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import com.sun.tools.javac.util.List;
import io.art.generator.model.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.determiner.MappingFieldsDeterminer.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappersMethodCreator {
    public NewMethod createMappersMethod(Type returnType, TypeModel registryType, Type[] parameterClasses) {
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));

        for (Type parameterType : parameterClasses) {
            if (parameterType instanceof ParameterizedType) {
                Class<?> parameterClass = (Class<?>) ((ParameterizedType) parameterType).getActualTypeArguments()[0];
                collectUnknownClassesRecursive(parameterClass)
                        .stream()
                        .peek(type -> method.statement(() -> createToModel(type)))
                        .forEach(type -> method.statement(() -> createFromModel(type)));
                if (typeIsUnknown(parameterClass)) {
                    method.statement(() -> createToModel(parameterClass)).statement(() -> createFromModel(parameterClass));
                }
            }
            if (parameterType instanceof Class) {
                if (typeIsUnknown(parameterType)) {
                    Class<?> parameterClass = (Class<?>) parameterType;
                    method.statement(() -> createToModel(parameterClass)).statement(() -> createFromModel(parameterClass));
                }
            }
        }

        if (returnType instanceof ParameterizedType) {
            Class<?> returnClass = (Class<?>) ((ParameterizedType) returnType).getActualTypeArguments()[0];
            collectUnknownClassesRecursive(returnClass)
                    .stream()
                    .peek(type -> method.statement(() -> createToModel(type)))
                    .forEach(type -> method.statement(() -> createFromModel(type)));
            if (typeIsUnknown(returnClass)) {
                method.statement(() -> createToModel(returnClass)).statement(() -> createFromModel(returnClass));
            }
        }

        if (returnType instanceof Class) {
            if (typeIsUnknown(returnType)) {
                Class<?> returnClass = (Class<?>) returnType;
                method.statement(() -> createToModel(returnClass)).statement(() -> createFromModel(returnClass));
            }
        }

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
