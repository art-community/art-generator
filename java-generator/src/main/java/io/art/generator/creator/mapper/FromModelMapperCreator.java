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
import static io.art.generator.selector.MappingFromMethodSelector.*;
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
        String generatedMapping = getGeneratedMapping(type);
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

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return createFromModelMapperBody(type, modelName);
            }
            return newLambda()
                    .parameter(newParameter(type(type), modelName))
                    .expression(() -> createFromModelMapperBody(type, modelName))
                    .generate();
        }

        if (type instanceof ParameterizedType || type instanceof GenericArrayType) {
            return createFromModelMapperBody(type, modelName);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
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

    private JCExpression createClassMapperBody(Class<?> mappingClass, String modelName) {
        if (byte[].class.equals(mappingClass)) {
            return select(type(BinaryMapping.class), FROM_BINARY);
        }
        if (mappingClass.isArray()) {
            if (isJavaPrimitiveType(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), selectFromArrayJavaPrimitiveMethod(mappingClass));
            }
            JCExpression parameterMapper = fromModelMapper(mappingClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), FROM_ARRAY, List.of(parameterMapper));
        }
        if (isPrimitiveType(mappingClass)) {
            return select(type(PrimitiveMapping.class), selectFromPrimitiveMethod(mappingClass));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
        for (Field field : getProperties(mappingClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            builderInvocation = createFieldMapping(builderInvocation, fieldName, fieldType, modelName);
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createParameterizedTypeMapperBody(ParameterizedType parameterizedType, String modelName) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> mappingClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(mappingClass)) {
            JCExpression parameterMapper = fromModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), selectFromCollectionMethod(mappingClass), List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(mappingClass)) {
            if (isCustomType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = fromModelMapper(typeArguments[1]);
            return applyClassMethod(type(EntityMapping.class), FROM_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
        for (Field field : getProperties(mappingClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericType(parameterizedType, field.getGenericType());
            builderInvocation = createFieldMapping(builderInvocation, fieldName, fieldType, modelName);
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createGenericArrayTypeMapperBody(GenericArrayType genericArrayType) {
        JCExpression parameterMapper = fromModelMapper(genericArrayType.getGenericComponentType());
        return applyClassMethod(type(ArrayMapping.class), FROM_ARRAY, List.of(parameterMapper));
    }

    private JCMethodInvocation createFieldMapping(JCMethodInvocation builderInvocation, String fieldName, Type fieldType, String modelName) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(literal(fieldName));
        mapping.add(newLambda().expression(() -> applyMethod(modelName, (isBoolean(fieldType) ? IS_PREFIX : GET_PREFIX) + capitalize(fieldName))).generate());
        mapping.add(fromModelMapper(fieldType));
        return applyMethod(builderInvocation, LAZY_PUT_NAME, mapping.toList());
    }
}
