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
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.time.*;
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
        }

        if (type instanceof ParameterizedType || type instanceof GenericArrayType) {
            return createToModelMapperBody(type, valueName);
        }

        return newLambda()
                .parameter(newParameter(type(Entity.class), valueName))
                .expression(() -> createToModelMapperBody(type, valueName))
                .generate();
    }


    private JCExpression createToModelMapperBody(Type type, String valueName) {
        if (type instanceof Class) {
            return createClassMapperBody((Class<?>) type, valueName);
        }

        if (type instanceof ParameterizedType) {
            return createParametrizedTypeMapperBody((ParameterizedType) type);
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
            if (char.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_CHAR_ARRAY);
            }
            if (short.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_SHORT_ARRAY);
            }
            if (int.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_INT_ARRAY);
            }
            if (long.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_LONG_ARRAY);
            }
            if (boolean.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_BOOL_ARRAY);
            }
            if (double.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_DOUBLE_ARRAY);
            }
            if (byte.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_BYTE_ARRAY);
            }
            if (float.class.equals(mappingClass.getComponentType())) {
                return select(type(ArrayMapping.class), TO_FLOAT_ARRAY);
            }
            JCExpression parameterMapper = toModelMapper(mappingClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), TO_ARRAY, List.of(parameterMapper));
        }
        if (String.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_STRING);
        }
        if (char.class.equals(mappingClass) || Character.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_CHAR);
        }
        if (short.class.equals(mappingClass) || Short.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_SHORT);
        }
        if (int.class.equals(mappingClass) || Integer.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_INT);
        }
        if (long.class.equals(mappingClass) || Long.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_LONG);
        }
        if (boolean.class.equals(mappingClass) || Boolean.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_BOOL);
        }
        if (double.class.equals(mappingClass) || Double.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_DOUBLE);
        }
        if (byte.class.equals(mappingClass) || Byte.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_BYTE);
        }
        if (float.class.equals(mappingClass) || Float.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_FLOAT);
        }
        if (UUID.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_UUID);
        }
        if (LocalDateTime.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_LOCAL_DATE_TIME);
        }
        if (ZonedDateTime.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_ZONED_DATE_TIME);
        }
        if (Date.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_DATE);
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

    private JCExpression createParametrizedTypeMapperBody(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> mappingClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (java.util.List.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_LIST, List.of(parameterMapper));
        }
        if (Queue.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_QUEUE, List.of(parameterMapper));
        }
        if (Deque.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_DEQUE, List.of(parameterMapper));
        }
        if (Set.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_SET, List.of(parameterMapper));
        }
        if (Collection.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_COLLECTION, List.of(parameterMapper));
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
        throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
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
