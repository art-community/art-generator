package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.*;

import java.lang.reflect.*;

import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.MappersConstants.LazyValueMappingMethods.*;
import static io.art.generator.constants.MappersConstants.OptionalMappingMethods.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.type.TypeInspector.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.ToMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static io.art.generator.type.TypeSubstitutor.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ToModelMapperCreator {
    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = mappers().get(type);
        if (nonNull(generatedMapper)) {
            return select(generatedMapper, TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        if (isClass(type)) {
            Class<?> modelClass = (Class<?>) type;

            if (isByteArray(modelClass)) {
                return select(BINARY_MAPPING_TYPE, TO_BINARY);
            }

            if (isValue(modelClass)) {
                return method(VALUE_TO_MODEL_MAPPER_TYPE, IDENTITY_NAME).apply();
            }

            if (isArray(modelClass)) {
                if (isJavaPrimitive(modelClass.getComponentType())) {
                    return select(ARRAY_MAPPING_TYPE, selectToArrayJavaPrimitiveMethod(modelClass));
                }
                JCExpression parameterMapper = toModelMapper(modelClass.getComponentType());
                return method(ARRAY_MAPPING_TYPE, TO_ARRAY_RAW)
                        .addArguments(newReference(type(modelClass)), parameterMapper)
                        .apply();
            }

            if (isPrimitive(modelClass)) {
                return select(PRIMITIVE_MAPPING_TYPE, selectToPrimitiveMethod(modelClass));
            }

            if (hasBuilder(type)) {
                return ToModelMapperByBuilderCreator.create(modelClass);
            }

            if (hasConstructorWithAllProperties(type) || (hasNoArgumentsConstructor(type) && hasAtLeastOneSetter(type))) {
                return ToModelMapperByInitializerCreator.create(modelClass);
            }

            throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
        }

        if (isParametrized(type)) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ExtractedParametrizedType extractedType = ExtractedParametrizedType.from(parameterizedType);
            Class<?> rawClass = extractedType.getRawClass();
            Type[] typeArguments = extractedType.getTypeArguments();

            if (isCollection(rawClass)) {
                JCExpression parameterMapper = toModelMapper(typeArguments[0]);
                return method(ARRAY_MAPPING_TYPE, selectToCollectionMethod(rawClass))
                        .addArguments(parameterMapper)
                        .apply();
            }

            if (isMap(rawClass) || isImmutableMap(rawClass)) {
                if (isUserType(typeArguments[0])) {
                    throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
                }
                JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
                JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
                JCExpression valueMapper = toModelMapper(typeArguments[1]);
                if (isImmutableCollection(rawClass)) {
                    return method(ENTITY_MAPPING_TYPE, TO_IMMUTABLE_MAP)
                            .addArguments(keyToModelMapper, keyFromModelMapper, valueMapper)
                            .apply();
                }
                return method(ENTITY_MAPPING_TYPE, TO_MUTABLE_MAP)
                        .addArguments(keyToModelMapper, valueMapper)
                        .apply();
            }

            if (isLazyValue(type)) {
                Type fieldTypeArgument = typeArguments[0];
                return method(LAZY_MAPPING_TYPE, TO_LAZY).addArguments(toModelMapper(fieldTypeArgument)).apply();
            }

            if (isOptional(type)) {
                Type fieldTypeArgument = typeArguments[0];
                return method(OPTIONAL_MAPPING_TYPE, TO_OPTIONAL).addArguments(toModelMapper(fieldTypeArgument)).apply();
            }

            if (hasBuilder(type)) {
                return ToModelMapperByBuilderCreator.create(parameterizedType);
            }

            if (hasConstructorWithAllProperties(type) || (hasNoArgumentsConstructor(type) && hasAtLeastOneSetter(type))) {
                return ToModelMapperByInitializerCreator.create(parameterizedType);
            }

            throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
        }

        if (isGenericArray(type)) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            return method(ARRAY_MAPPING_TYPE, TO_ARRAY_RAW)
                    .addTypeParameters(type(genericComponentType))
                    .addArguments(newReference(type(type)), toModelMapper(genericComponentType))
                    .apply();
        }

        if (isWildcard(type)) {
            return createToModelMapper(substituteWildcard((WildcardType) type));
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }
}
