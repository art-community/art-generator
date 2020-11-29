package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.MappingToMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class ToModelMapperCreator {
    public JCExpression toModelMapper(Type type) {
        String generatedMapping = getGeneratedMapping(type);
        if (nonNull(generatedMapping)) {
            return select(select(mainClass().getName(), generatedMapping), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public JCExpression createToModelMapper(Type type) {
        String valueName = sequenceName(VALUE_NAME);

        if (isLibraryType(type)) {
            return createToModelMapperBody(type, valueName);
        }

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return createToModelMapperBody(type, valueName);
            }
            return newLambda()
                    .parameter(newParameter(type(Entity.class), valueName))
                    .expression(() -> createToModelMapperBody(type, valueName))
                    .generate();
        }

        if (type instanceof ParameterizedType || type instanceof GenericArrayType) {
            return createToModelMapperBody(type, valueName);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }


    private JCExpression createToModelMapperBody(Type type, String valueName) {
        if (type instanceof Class) {
            return createClassMapperBody((Class<?>) type, valueName);
        }

        if (type instanceof ParameterizedType) {
            return createParametrizedTypeMapperBody((ParameterizedType) type, valueName);
        }

        if (type instanceof GenericArrayType) {
            return createGenericArrayTypeMapperBody((GenericArrayType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression createClassMapperBody(Class<?> mappingClass, String valueName) {
        if (byte[].class.equals(mappingClass)) {
            return select(type(BinaryMapping.class), TO_BINARY);
        }
        if (mappingClass.isArray()) {
            if (isJavaPrimitiveType(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), selectToArrayJavaPrimitiveMethod(mappingClass));
            }
            JCExpression parameterMapper = toModelMapper(mappingClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), TO_ARRAY, List.of(parameterMapper));
        }
        if (isPrimitiveType(mappingClass)) {
            return select(type(PrimitiveMapping.class), selectToPrimitiveMethod(mappingClass));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(mappingClass), BUILDER_METHOD_NAME);
        for (Field field : getProperties(mappingClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            JCMethodInvocation fieldMapping = createFieldMapping(fieldName, fieldType, valueName);
            builderInvocation = applyMethod(builderInvocation, fieldName, List.of(fieldMapping));
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createParametrizedTypeMapperBody(ParameterizedType parameterizedType, String valueName) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> mappingClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), selectToCollectionMethod(mappingClass), List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(mappingClass)) {
            if (isCustomType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = toModelMapper(typeArguments[1]);
            return applyClassMethod(type(EntityMapping.class), TO_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(mappingClass), BUILDER_METHOD_NAME);
        for (Field field : getProperties(mappingClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericType(parameterizedType, field.getGenericType());
            JCMethodInvocation fieldMapping = createFieldMapping(fieldName, fieldType, valueName);
            builderInvocation = applyMethod(builderInvocation, fieldName, List.of(fieldMapping));
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createGenericArrayTypeMapperBody(GenericArrayType genericArrayType) {
        JCExpression parameterMapper = toModelMapper(genericArrayType.getGenericComponentType());
        return applyClassMethod(type(ArrayMapping.class), TO_ARRAY, List.of(parameterMapper));
    }

    private JCMethodInvocation createFieldMapping(String fieldName, Type fieldType, String valueName) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(maker().Literal(fieldName));
        String methodName = isJavaPrimitiveType(fieldType) ? VALIDATED_MAP_NAME : MAP_NAME;
        mapping.add(toModelMapper(fieldType));
        return applyMethod(valueName, methodName, mapping.toList());
    }
}
