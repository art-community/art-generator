package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;
import java.util.function.*;


public class ToModelMapperByInitializerCreator {
    private final static String entityName = sequenceName(ENTITY_NAME);
    private final static ToModelPropertyMappingCreator fieldMappingCreator = new ToModelPropertyMappingCreator(entityName);

    static JCExpression create(Class<?> type) {
        if (hasConstructorWithAllProperties(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), forPropertiesByConstructor(type)))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        if (hasNoArgumentsConstructor(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type)))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    static JCExpression create(ParameterizedType type) {
        if (hasConstructorWithAllProperties(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), forPropertiesByConstructor(type)))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        if (hasNoArgumentsConstructor(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type)))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    private static ImmutableArray<Supplier<JCStatement>> forPropertiesBySetters(Class<?> modelClass) {
        ImmutableArray.Builder<Supplier<JCStatement>> setters = immutableArrayBuilder();
        for (ExtractedProperty property : getSettableProperties(modelClass)) {
            setters.add(() -> method(MODEL_NAME, property.setterName())
                    .addArguments(fieldMappingCreator.forProperty(property.name(), property.type()))
                    .execute());
        }
        return setters.build();
    }

    private static ImmutableArray<Supplier<JCStatement>> forPropertiesBySetters(ParameterizedType parameterizedType) {
        ImmutableArray.Builder<Supplier<JCStatement>> setters = immutableArrayBuilder();
        for (ExtractedProperty property : getSettableProperties(parameterizedType)) {
            setters.add(() -> method(MODEL_NAME, property.setterName())
                    .addArguments(fieldMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type())))
                    .execute());
        }
        return setters.build();
    }

    private static ImmutableArray<JCExpression> forPropertiesByConstructor(Class<?> modelClass) {
        ImmutableArray.Builder<JCExpression> setters = immutableArrayBuilder();
        for (ExtractedProperty property : getConstructorProperties(modelClass)) {
            setters.add(fieldMappingCreator.forProperty(property.name(), property.type()));
        }
        return setters.build();
    }

    private static ImmutableArray<JCExpression> forPropertiesByConstructor(ParameterizedType parameterizedType) {
        ImmutableArray.Builder<JCExpression> setters = immutableArrayBuilder();
        for (ExtractedProperty property : getConstructorProperties(parameterizedType)) {
            setters.add(fieldMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type())));
        }
        return setters.build();
    }
}
