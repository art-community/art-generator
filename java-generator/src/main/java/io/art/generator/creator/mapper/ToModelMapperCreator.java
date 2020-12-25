package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.exception.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.ToMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ToModelMapperCreator {
    private final String entityName = sequenceName(ENTITY_NAME);
    private final ToModelFieldMappingCreator fieldMappingCreator = new ToModelFieldMappingCreator(entityName);
    private final ToModelMapperCreatorByBuilder builder = new ToModelMapperCreatorByBuilder(fieldMappingCreator);
    private final ToModelMapperCreatorByInitializer initializer = new ToModelMapperCreatorByInitializer(fieldMappingCreator);

    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        ToModelMapperCreator creator = new ToModelMapperCreator();

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

        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, creator.entityName))
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

        if (hasBuilder(modelClass)) {
            return builder.forProperties(modelClass);
        }

        if (hasAtLeastOneFieldConstructorArgument(modelClass) || (hasNoArgumentsConstructor(modelClass) && hasAtLeastOneSetter(modelClass))) {
            return initializer.forProperties(modelClass);
        }

        throw new IllegalStateException();
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

        if (hasBuilder(rawClass)) {
            return builder.forProperties(parameterizedType, rawClass);
        }

        if (hasAtLeastOneFieldConstructorArgument(rawClass) || hasAtLeastOneSetter(rawClass)) {
            return initializer.forProperties(parameterizedType, rawClass);
        }

        throw new IllegalStateException();
    }

    private JCExpression body(GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        return method(ARRAY_MAPPING_TYPE, TO_ARRAY)
                .addTypeParameters(type(genericComponentType))
                .addArguments(newReference(type(genericArrayType)), toModelMapper(genericComponentType))
                .apply();
    }


}
