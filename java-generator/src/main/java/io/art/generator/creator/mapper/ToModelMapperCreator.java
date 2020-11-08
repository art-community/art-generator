package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.determiner.MappingFieldsDeterminer.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class ToModelMapperCreator {
    public JCLambda createToModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(Entity.class), VALUE_NAME))
                .expression(() -> createMapperContent(modelClass))
                .generate();
    }

    public JCExpression selectToModelMapper(Type type) {
        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (byte[].class.equals(typeAsClass)) {
                return select(type(BinaryMapping.class), TO_BINARY);
            }
            if (typeAsClass.isArray()) {
                JCExpression parameterMapper = selectToModelMapper(typeAsClass.getComponentType());
                return applyClassMethod(type(ArrayMapping.class), TO_ARRAY, List.of(parameterMapper));
            }
            if (String.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_STRING);
            }
            if (short.class.equals(typeAsClass) || Short.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_INT);
            }
            if (int.class.equals(typeAsClass) || Integer.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_INT);
            }
            if (long.class.equals(typeAsClass) || Long.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_LONG);
            }
            if (boolean.class.equals(typeAsClass) || Boolean.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_BOOL);
            }
            if (double.class.equals(typeAsClass) || Double.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_DOUBLE);
            }
            if (byte.class.equals(typeAsClass) || Byte.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_BYTE);
            }
            if (float.class.equals(typeAsClass) || Float.class.equals(typeAsClass)) {
                return select(type(PrimitiveMapping.class), TO_FLOAT);
            }
            return applyMethod(REGISTRY_NAME, GET_TO_MODEL_NAME, List.of(select(type(typeAsClass), CLASS_KEYWORD)));
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (java.util.List.class.isAssignableFrom(rawType)) {
                JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                return applyClassMethod(type(ArrayMapping.class), TO_LIST, List.of(parameterMapper));
            }
            if (Queue.class.isAssignableFrom(rawType)) {
                JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                return applyClassMethod(type(ArrayMapping.class), TO_QUEUE, List.of(parameterMapper));
            }
            if (Deque.class.isAssignableFrom(rawType)) {
                JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                return applyClassMethod(type(ArrayMapping.class), TO_DEQUE, List.of(parameterMapper));
            }
            if (Set.class.isAssignableFrom(rawType)) {
                JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                return applyClassMethod(type(ArrayMapping.class), TO_SET, List.of(parameterMapper));
            }
            if (Collection.class.isAssignableFrom(rawType)) {
                JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                return applyClassMethod(type(ArrayMapping.class), TO_COLLECTION, List.of(parameterMapper));
            }
            if (Map.class.isAssignableFrom(rawType)) {
                JCExpression keyToModelMapper = selectToModelMapper(typeArguments[0]);
                JCExpression keyFromModelMapper = selectFromModelMapper(typeArguments[0]);
                JCExpression valueMapper = selectToModelMapper(typeArguments[1]);
                return applyClassMethod(type(EntityMapping.class), TO_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
            }
        }
        throw new GenerationException(format(UNKNOWN_FIELD_TYPE, type.getTypeName()));
    }

    private JCMethodInvocation createMapperContent(Class<?> modelClass) {
        try {
            JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass.getName()), BUILDER_METHOD_NAME);
            for (Field field : getMappingFields(modelClass)) {
                String fieldName = field.getName();
                Type fieldType = field.getGenericType();
                builderInvocation = applyMethod(builderInvocation, fieldName, List.of(createFieldMapping(fieldName, fieldType)));
            }
            return applyMethod(builderInvocation, BUILD_METHOD_NAME);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private JCMethodInvocation createFieldMapping(String fieldName, Type fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(maker().Literal(fieldName));
        mapping.add(selectToModelMapper(fieldType));
        return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
    }
}
