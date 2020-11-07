package io.art.generator.creator.mapper;

import com.google.common.collect.*;
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
import java.lang.reflect.*;

@UtilityClass
public class MappersMethodCreator {
    public NewMethod createMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod method = newMethod()
                .name(MAPPERS_NAME)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));

        ImmutableSet<Field> mappingFields = collectUnknownTypeFieldsRecursive(returnClass);
        mappingFields.forEach(field -> method.statement(() -> createToModel(field.getType())));
        method.statement(() -> createToModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            collectUnknownTypeFieldsRecursive(parameterClass).forEach(field -> method.statement(() -> createToModel(field.getType())));
            method.statement(() -> createToModel(parameterClass));
        }

        mappingFields.forEach(field -> method.statement(() -> createFromModel(field.getType())));
        method.statement(() -> createFromModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            collectUnknownTypeFieldsRecursive(parameterClass).forEach(field -> method.statement(() -> createFromModel(field.getType())));
            method.statement(() -> createFromModel(parameterClass));
        }

        return method.statement(() -> returnVariable(REGISTRY_NAME));
    }

    private JCExpressionStatement createToModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), createToModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }

    private JCExpressionStatement createFromModel(Class<?> modelClass) {
        List<JCExpression> arguments = List.of(classReference(modelClass), createFromModelMapper(modelClass));
        return execMethodCall(REGISTRY_NAME, REGISTER, arguments);
    }
}
