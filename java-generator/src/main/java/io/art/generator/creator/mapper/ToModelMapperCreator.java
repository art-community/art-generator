package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.value.mapping.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.LazyMappingMethods.TO_LAZY;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.ToMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ToModelMapperCreator {
    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        if (type instanceof Class) {
            Class<?> modelClass = (Class<?>) type;
            if (byte[].class.equals(type)) {
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

            if (hasBuilder(type)) {
                return new ToModelMapperByBuilderCreator().create(modelClass);
            }

            if (hasConstructorWithAllProperties(type) || (hasNoArgumentsConstructor(type) && hasAtLeastOneSetter(type))) {
                return new ToModelMapperByInitializerCreator().create(modelClass);
            }

            throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ExtractedParametrizedType extractedType = ExtractedParametrizedType.from(parameterizedType);
            Class<?> rawClass = extractedType.getRawClass();
            Type[] typeArguments = extractedType.getTypeArguments();

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

            if (isLazyValue(type)) {
                Type fieldTypeArgument = typeArguments[0];
                return method(type(LazyValueMapping.class), TO_LAZY).addArguments(toModelMapper(fieldTypeArgument)).apply();
            }

            if (hasBuilder(type)) {
                return new ToModelMapperByBuilderCreator().create(parameterizedType);
            }

            if (hasConstructorWithAllProperties(type) || (hasNoArgumentsConstructor(type) && hasAtLeastOneSetter(type))) {
                return new ToModelMapperByInitializerCreator().create(parameterizedType);
            }

            throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
        }

        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            return method(ARRAY_MAPPING_TYPE, TO_ARRAY)
                    .addTypeParameters(type(genericComponentType))
                    .addArguments(newReference(type(type)), toModelMapper(genericComponentType))
                    .apply();
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }
}
