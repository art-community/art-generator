package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.core.extensions.StringExtensions.*;
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
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.function.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelMapperByInitializerCreator {
    private final String entityName = sequenceName(ENTITY_NAME);
    private final ToModelFieldMappingCreator fieldMappingCreator = new ToModelFieldMappingCreator(entityName);

    JCExpression create(Class<?> type) {
        if (hasNoArgumentsConstructor(type)) {
            return NewLambda.newLambda()
                    .parameter(newParameter(ENTITY_TYPE, entityName))
                    .addStatement(() -> newVariable()
                            .type(type(type))
                            .name(MODEL_NAME)
                            .initializer(() -> newObject(type(type), List.nil()))
                            .generate())
                    .addStatements(forProperties(type))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        if (hasConstructorWithAllProperties(type)) {

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
                    .addStatements(forProperties(type, extractClass(type)))
                    .addStatement(() -> returnVariable(MODEL_NAME))
                    .generate();
        }

        if (hasConstructorWithAllProperties(type)) {

        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    private List<Supplier<JCStatement>> forProperties(Class<?> modelClass) {
        ListBuffer<Supplier<JCStatement>> setters = new ListBuffer<>();
        for (Field field : getMutableProperties(modelClass)) {
            setters.add(() -> method(MODEL_NAME, SET_NAME + capitalize(field.getName()))
                    .addArguments(fieldMappingCreator.forField(field.getName(), field.getGenericType()))
                    .execute());
        }
        return setters.toList();
    }

    private List<Supplier<JCStatement>> forProperties(ParameterizedType parameterizedType, Class<?> rawClass) {
        ListBuffer<Supplier<JCStatement>> setters = new ListBuffer<>();
        for (Field field : getMutableProperties(rawClass)) {
            setters.add(() -> method(MODEL_NAME, SET_NAME + capitalize(field.getName()))
                    .addArguments(fieldMappingCreator.forField(field.getName(), extractGenericPropertyType(parameterizedType, field.getGenericType())))
                    .execute());
        }
        return setters.toList();
    }
}
