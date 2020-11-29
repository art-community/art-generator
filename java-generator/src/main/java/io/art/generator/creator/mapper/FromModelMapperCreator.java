package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.FromMapperMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class FromModelMapperCreator {
    public JCExpression fromModelMapper(Type type) {
        String generatedMapping = getGeneratedMapper(type);
        if (nonNull(generatedMapping)) {
            return select(select(mainClass().getName(), generatedMapping), FROM_MODEL_NAME);
        }
        return createFromModelMapper(type);
    }

    public JCExpression createFromModelMapper(Type type) {
        String modelName = sequenceName(MODEL_NAME);

        if (isLibraryType(type)) {
            return createFromModelMapperBody(type, modelName);
        }

        if (type instanceof GenericArrayType) {
            return createFromModelMapperBody(type, modelName);
        }

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return createFromModelMapperBody(type, modelName);
            }
        }

        return newLambda()
                .parameter(newParameter(type(type), modelName))
                .expression(() -> createFromModelMapperBody(type, modelName))
                .generate();
    }


    private JCExpression createFromModelMapperBody(Type type, String modelName) {
        if (type instanceof Class) {
            return createClassMapperBody((Class<?>) type, modelName);
        }

        if (type instanceof ParameterizedType) {
            return createParameterizedTypeMapperBody((ParameterizedType) type, modelName);
        }

        if (type instanceof GenericArrayType) {
            return createGenericArrayTypeMapperBody((GenericArrayType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression createClassMapperBody(Class<?> modelClass, String modelName) {
        if (byte[].class.equals(modelClass)) {
            return select(type(BinaryMapping.class), FROM_BINARY);
        }
        if (modelClass.isArray()) {
            if (isJavaPrimitiveType(modelClass.getComponentType())) {
                return select(type(ArrayMapping.class), selectFromArrayJavaPrimitiveMethod(modelClass));
            }
            JCExpression parameterMapper = fromModelMapper(modelClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), FROM_ARRAY, List.of(parameterMapper));
        }
        if (isPrimitiveType(modelClass)) {
            return select(type(PrimitiveMapping.class), selectFromPrimitiveMethod(modelClass));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            builderInvocation = createFieldMappers(builderInvocation, fieldName, fieldType, modelName);
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createParameterizedTypeMapperBody(ParameterizedType parameterizedType, String modelName) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> rawClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(rawClass)) {
            JCExpression parameterMapper = fromModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), selectFromCollectionMethod(rawClass), List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(rawClass)) {
            if (isCustomType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = fromModelMapper(typeArguments[1]);
            return applyClassMethod(type(EntityMapping.class), FROM_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            builderInvocation = createFieldMappers(builderInvocation, fieldName, fieldType, modelName);
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createGenericArrayTypeMapperBody(GenericArrayType genericArrayType) {
        JCExpression parameterMapper = fromModelMapper(genericArrayType.getGenericComponentType());
        return applyClassMethod(type(ArrayMapping.class), FROM_ARRAY, List.of(parameterMapper));
    }

    private JCMethodInvocation createFieldMappers(JCMethodInvocation builderInvocation, String fieldName, Type fieldType, String modelName) {
        ListBuffer<JCExpression> arguments = new ListBuffer<>();
        arguments.add(literal(fieldName));
        arguments.add(newLambda().expression(() -> applyMethod(modelName, (isBoolean(fieldType) ? IS_PREFIX : GET_PREFIX) + capitalize(fieldName))).generate());
        arguments.add(fromModelMapper(fieldType));
        return applyMethod(builderInvocation, LAZY_PUT_NAME, arguments.toList());
    }
}
