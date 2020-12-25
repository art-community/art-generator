package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.lazy.*;
import io.art.generator.exception.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import lombok.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.ToMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ToModelMapperCreator {
    private final String entityName;
    private final Mode mode;

    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        boolean hasBuilder = hasBuilder(extractClass(type));
        ToModelMapperCreator creator = new ToModelMapperCreator(sequenceName(ENTITY_NAME), hasBuilder ? Mode.BUILDER : Mode.INITIALIZER);

        if (isLibraryType(type)) {
            return creator.body(type);
        }

        if (type instanceof GenericArrayType) {
            return creator.body(type);
        }

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return creator.body(type);
            }
        }

        if (hasBuilder) {
            return byBuilder(type, creator);
        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }

    private static JCLambda byBuilder(Type type, ToModelMapperCreator creator) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, creator.entityName))
                .expression(() -> creator.body(type))
                .generate();
    }

    private static JCLambda byInitializer(Type type, ToModelMapperCreator creator) {
        String modelName = creator.entityName + capitalize(MODEL_NAME);
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, creator.entityName))
                .addStatement(() -> newVariable()
                        .type(type(type))
                        .name(modelName)
                        .modifiers(parameterModifiers())
                        .generate())
                .addStatements(

                )
                .expression(() -> creator.body(type))
                .generate();
    }


    private JCExpression body(Type type) {
        if (type instanceof Class) {
            return body((Class<?>) type);
        }

        if (type instanceof ParameterizedType) {
            return body((ParameterizedType) type);
        }

        if (type instanceof GenericArrayType) {
            return body((GenericArrayType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression body(Class<?> modelClass) {
        if (byte[].class.equals(modelClass)) {
            return select(BINARY_MAPPING_TYPE, TO_BINARY);
        }
        if (modelClass.isArray()) {
            if (isJavaPrimitiveType(modelClass.getComponentType())) {
                return select(ARRAY_MAPPING_TYPE, selectToArrayJavaPrimitiveMethod(modelClass));
            }
            JCExpression parameterMapper = toModelMapper(modelClass.getComponentType());
            return method(ARRAY_MAPPING_TYPE, TO_ARRAY)
                    .addArguments(newReference(type(modelClass)), parameterMapper)
                    .apply();
        }
        if (isPrimitiveType(modelClass)) {
            return select(PRIMITIVE_MAPPING_TYPE, selectToPrimitiveMethod(modelClass));
        }
        return forProperties(modelClass);
    }

    private JCExpression body(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> rawClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(rawClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return method(ARRAY_MAPPING_TYPE, selectToCollectionMethod(rawClass))
                    .addArguments(parameterMapper)
                    .apply();
        }
        if (Map.class.isAssignableFrom(rawClass)) {
            if (isComplexType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = toModelMapper(typeArguments[1]);
            return method(ENTITY_MAPPING_TYPE, TO_MAP)
                    .addArguments(keyToModelMapper, keyFromModelMapper, valueMapper)
                    .apply();
        }

        return forProperties(parameterizedType, rawClass);
    }

    private JCExpression body(GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        return method(ARRAY_MAPPING_TYPE, TO_ARRAY)
                .addTypeParameters(type(genericComponentType))
                .addArguments(newReference(type(genericArrayType)), toModelMapper(genericComponentType))
                .apply();
    }


    private JCMethodInvocation forProperties(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = method(type(modelClass), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            JCMethodInvocation fieldMappers = forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(fieldMappers).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

    private JCMethodInvocation forProperties(ParameterizedType parameterizedType, Class<?> rawClass) {
        JCMethodInvocation builderInvocation = method(type(parameterizedType), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            JCMethodInvocation fieldMapping = forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(fieldMapping).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }


    private List<Supplier<JCMethodInvocation>> forPropertiesBySetters(Class<?> modelClass) {
        List<Supplier<JCMethodInvocation>> setters = dynamicArray();
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            setters.add(() -> method(entityName + capitalize(MODEL_NAME), SET_NAME + capitalize(fieldName))
                    .addArgument(forField(fieldName, fieldType))
                    .apply());
        }
        return setters;
    }

    private List<Supplier<JCMethodInvocation>> forPropertiesBySetters(ParameterizedType parameterizedType, Class<?> rawClass) {
        List<Supplier<JCMethodInvocation>> setters = dynamicArray();
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            setters.add(() -> method(entityName + capitalize(MODEL_NAME), SET_NAME + capitalize(fieldName))
                    .addArgument(forField(fieldName, fieldType))
                    .apply());
        }
        return setters;
    }


    private JCMethodInvocation forField(String fieldName, Type fieldType) {
        boolean javaPrimitiveType = isJavaPrimitiveType(fieldType);
        if (isLazyValue(fieldType)) {
            return forLazyField(fieldName, ((ParameterizedType) fieldType).getActualTypeArguments()[0], javaPrimitiveType);
        }
        List<JCExpression> arguments = dynamicArray();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        return mappingMethod(javaPrimitiveType, arguments);
    }

    private JCMethodInvocation forLazyField(String fieldName, Type fieldType, boolean javaPrimitiveType) {
        List<JCExpression> arguments = dynamicArray();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        JCLambda lambda = newLambda().expression(() -> mappingMethod(javaPrimitiveType, arguments)).generate();
        return method(type(LazyValue.class), LAZY_NAME)
                .addArguments(lambda)
                .apply();
    }

    private JCMethodInvocation mappingMethod(boolean javaPrimitiveType, List<JCExpression> arguments) {
        String method = javaPrimitiveType ? MAP_OR_DEFAULT_NAME : MAP_NAME;
        return method(method(entityName, MAPPING_METHOD_NAME).apply(), method)
                .addArguments(arguments)
                .apply();
    }

    private enum Mode {
        BUILDER,
        INITIALIZER
    }
}
