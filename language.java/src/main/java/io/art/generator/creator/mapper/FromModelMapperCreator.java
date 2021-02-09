package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.JavaDialect.*;
import static io.art.generator.constants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.MappersConstants.LazyValueMappingMethods.*;
import static io.art.generator.constants.MappersConstants.OptionalMappingMethods.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.FromMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GeneratorState.*;
import static io.art.generator.substitutor.TypeSubstitutor.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import static lombok.AccessLevel.*;
import java.lang.reflect.*;

@RequiredArgsConstructor(access = PRIVATE)
public class FromModelMapperCreator {
    private final String modelName;

    public static JCExpression fromModelMapper(Type type) {
        String generatedMapper = mappers().get(type);
        if (nonNull(generatedMapper)) {
            return select(generatedMapper, FROM_MODEL_NAME);
        }
        return createFromModelMapper(type);
    }

    public static JCExpression createFromModelMapper(Type type) {
        if (isWildcard(type)) {
            return createFromModelMapper(substituteWildcard((WildcardType) type));
        }

        FromModelMapperCreator creator = new FromModelMapperCreator(sequenceName(MODEL_NAME));

        if (isClass(type)) {
            return creator.create((Class<?>) type);
        }

        if (isParametrized(type)) {
            return creator.create((ParameterizedType) type);

        }

        if (isGenericArray(type)) {
            return creator.create((GenericArrayType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }


    private JCMethodInvocation create(GenericArrayType type) {
        JCExpression parameterMapper = fromModelMapper(type.getGenericComponentType());
        return method(ARRAY_MAPPING_TYPE, FROM_ARRAY).addArguments(parameterMapper).apply();
    }

    private JCExpression create(Class<?> modelClass) {
        if (isByteArray(modelClass)) {
            return select(BINARY_MAPPING_TYPE, FROM_BINARY);
        }
        if (isValue(modelClass)) {
            return method(VALUE_FROM_MODEL_MAPPER_TYPE, IDENTITY_NAME).apply();
        }
        if (isArray(modelClass)) {
            if (isJavaPrimitive(modelClass.getComponentType())) {
                return select(ARRAY_MAPPING_TYPE, selectFromArrayJavaPrimitiveMethod(modelClass));
            }
            JCExpression parameterMapper = fromModelMapper(modelClass.getComponentType());
            return method(ARRAY_MAPPING_TYPE, FROM_ARRAY).addArguments(parameterMapper).apply();
        }
        if (isPrimitive(modelClass)) {
            return select(PRIMITIVE_MAPPING_TYPE, selectFromPrimitiveMethod(modelClass));
        }
        JCMethodInvocation builderInvocation = method(ENTITY_TYPE, ENTITY_BUILDER_NAME).apply();
        for (ExtractedProperty property : getGettableProperties(modelClass)) {
            builderInvocation = forProperty(builderInvocation, property.name(), property.type());
        }
        final JCMethodInvocation finalBuilderInvocation = builderInvocation;
        return newLambda()
                .parameter(newParameter(type(modelClass), modelName))
                .expression(() -> method(finalBuilderInvocation, BUILD_METHOD_NAME).apply())
                .generate();
    }

    private JCPolyExpression create(ParameterizedType parameterizedType) {
        ExtractedParametrizedType extractedType = ExtractedParametrizedType.from(parameterizedType);
        Class<?> rawClass = extractedType.getRawClass();
        Type[] typeArguments = extractedType.getTypeArguments();
        if (isCollection(rawClass)) {
            JCExpression parameterMapper = fromModelMapper(typeArguments[0]);
            return method(ARRAY_MAPPING_TYPE, selectFromCollectionMethod(rawClass))
                    .addArguments(parameterMapper)
                    .apply();
        }

        if (isMap(rawClass) || isImmutableMap(rawClass)) {
            if (isUserType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = fromModelMapper(typeArguments[1]);
            if (isImmutableCollection(rawClass)) {
                return method(ENTITY_MAPPING_TYPE, FROM_IMMUTABLE_MAP)
                        .addArguments(keyToModelMapper, keyFromModelMapper, valueMapper)
                        .apply();
            }
            return method(ENTITY_MAPPING_TYPE, FROM_MAP)
                    .addArguments(keyToModelMapper, keyFromModelMapper, valueMapper)
                    .apply();
        }

        if (isLazyValue(parameterizedType)) {
            Type fieldTypeArgument = typeArguments[0];
            return method(LAZY_MAPPING_TYPE, FROM_LAZY)
                    .addArguments(fromModelMapper(fieldTypeArgument))
                    .apply();
        }

        if (isOptional(parameterizedType)) {
            Type fieldTypeArgument = typeArguments[0];
            return method(OPTIONAL_MAPPING_TYPE, FROM_OPTIONAL)
                    .addArguments(fromModelMapper(fieldTypeArgument))
                    .apply();
        }

        JCMethodInvocation builderInvocation = method(ENTITY_TYPE, ENTITY_BUILDER_NAME).apply();
        for (ExtractedProperty property : getGettableProperties(parameterizedType)) {
            Type fieldType = extractGenericPropertyType(parameterizedType, property.type());
            builderInvocation = forProperty(builderInvocation, property.name(), fieldType);
        }
        final JCMethodInvocation finalBuilderInvocation = builderInvocation;
        return newLambda()
                .parameter(newParameter(type(parameterizedType), modelName))
                .expression(() -> method(finalBuilderInvocation, BUILD_METHOD_NAME).apply())
                .generate();
    }

    private JCMethodInvocation forProperty(JCMethodInvocation builderInvocation, String propertyName, Type propertyType) {
        ImmutableArray.Builder<JCExpression> arguments = immutableArrayBuilder();
        arguments.add(literal(propertyName));
        String method = (isBoolean(propertyType) && dialect() == JAVA ? IS_NAME : GET_NAME) + capitalize(propertyName);
        JCMethodInvocation getter = method(modelName, method).apply();
        arguments.add(newLambda().expression(() -> getter).generate());
        arguments.add(fromModelMapper(propertyType));
        return method(builderInvocation, LAZY_PUT_NAME)
                .addArguments(arguments.build())
                .apply();
    }
}
