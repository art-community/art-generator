package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;
import java.util.function.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelMapperByInitializerCreator {
    private final String entityName = sequenceName(ENTITY_NAME);
    private final ToModelPropertyMappingCreator fieldMappingCreator = new ToModelPropertyMappingCreator(entityName);

    JCExpression create(Class<?> type) {
        if (hasNoArgumentsConstructor(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), List.nil()))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

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

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    JCExpression create(ParameterizedType type) {
        if (hasNoArgumentsConstructor(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), List.nil()))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        if (hasConstructorWithAllProperties(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), forPropertiesByConstructor(type, extractClass(type))))
                            .generate())
                    .addStatements(forPropertiesBySetters(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    private List<Supplier<JCStatement>> forPropertiesBySetters(Class<?> modelClass) {
        ListBuffer<Supplier<JCStatement>> setters = new ListBuffer<>();
        for (ExtractedProperty property : getSettableProperties(modelClass)) {
            setters.add(() -> method(MODEL_NAME, property.setterName())
                    .addArguments(fieldMappingCreator.forProperty(property.name(), property.type()))
                    .execute());
        }
        return setters.toList();
    }

    private List<Supplier<JCStatement>> forPropertiesBySetters(ParameterizedType parameterizedType) {
        ListBuffer<Supplier<JCStatement>> setters = new ListBuffer<>();
        for (ExtractedProperty property : getSettableProperties(extractClass(parameterizedType))) {
            setters.add(() -> method(MODEL_NAME, property.setterName())
                    .addArguments(fieldMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type())))
                    .execute());
        }
        return setters.toList();
    }

    private List<JCExpression> forPropertiesByConstructor(Class<?> modelClass) {
        ListBuffer<JCExpression> setters = new ListBuffer<>();
        for (ExtractedProperty property : getConstructorProperties(modelClass)) {
            setters.add(fieldMappingCreator.forProperty(property.name(), property.type()));
        }
        return setters.toList();
    }

    private List<JCExpression> forPropertiesByConstructor(ParameterizedType parameterizedType, Class<?> rawClass) {
        ListBuffer<JCExpression> setters = new ListBuffer<>();
        for (ExtractedProperty property : getConstructorProperties(rawClass)) {
            setters.add(fieldMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type())));
        }
        return setters.toList();
    }
}
