package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
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
public class FromModelMapperCreator {
    public JCLambda createFromModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(modelClass), MODEL_NAME))
                .expression(() -> createMapperContent(modelClass))
                .generate();
    }

    public JCExpression selectFromModelMapper(Type type) {
        if (type instanceof Class) {
            Class<?> typeAssClass = (Class<?>) type;
            if (byte[].class.equals(typeAssClass)) {
                return select(type(BinaryMapping.class), fromBinary);
            }
            if (typeAssClass.isArray()) {
                JCExpression parameterMapper = selectFromModelMapper(typeAssClass.getComponentType());
                return applyClassMethod(type(ArrayMapping.class), fromArray, List.of(parameterMapper));
            }
            if (String.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromString);
            }
            if (int.class.equals(typeAssClass) || Integer.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromInt);
            }
            if (short.class.equals(typeAssClass) || Short.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromInt);
            }
            if (long.class.equals(typeAssClass) || Long.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromLong);
            }
            if (boolean.class.equals(typeAssClass) || Boolean.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromBool);
            }
            if (double.class.equals(typeAssClass) || Double.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromDouble);
            }
            if (byte.class.equals(typeAssClass) || Byte.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromByte);
            }
            if (float.class.equals(typeAssClass) || Float.class.equals(typeAssClass)) {
                return select(type(PrimitiveMapping.class), fromFloat);
            }
            return applyMethod(REGISTRY_NAME, GET_FROM_MODEL_NAME, List.of(select(type(typeAssClass), CLASS_KEYWORD)));
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Class<?> rawTypeAsClass = (Class<?>) parameterizedType.getRawType();
            if (rawType instanceof Class) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                if (java.util.List.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectFromModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), fromList, List.of(parameterMapper));
                }
                if (Queue.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectFromModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), fromQueue, List.of(parameterMapper));
                }
                if (Deque.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectFromModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), fromDeque, List.of(parameterMapper));
                }
                if (Set.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectFromModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), fromSet, List.of(parameterMapper));
                }
                if (Collection.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectFromModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), fromCollection, List.of(parameterMapper));
                }
                if (Map.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression keyToModelMapper = selectToModelMapper(typeArguments[0]);
                    JCExpression keyFromModelMapper = selectFromModelMapper(typeArguments[0]);
                    JCExpression valueMapper = selectFromModelMapper(typeArguments[1]);
                    return applyClassMethod(type(EntityMapping.class), fromMap, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
                }
            }
        }

        throw new GenerationException(format(UNKNOWN_FIELD_TYPE, type.getTypeName()));
    }

    private JCMethodInvocation createMapperContent(Class<?> modelClass) {
        try {
            JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
            for (Field field : getMappingFields(modelClass)) {
                String fieldName = field.getName();
                Type fieldType = field.getGenericType();
                builderInvocation = createFieldMapping(builderInvocation, fieldName, fieldType);
            }
            return applyMethod(builderInvocation, BUILD_METHOD_NAME);
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    private JCMethodInvocation createFieldMapping(JCMethodInvocation builderInvocation, String fieldName, Type fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(literal(fieldName));
        mapping.add(newLambda().expression(() -> applyMethod(MODEL_NAME, GET_PREFIX + capitalize(fieldName))).generate());
        mapping.add(selectFromModelMapper(fieldType));
        return applyMethod(builderInvocation, LAZY_PUT_NAME, mapping.toList());
    }
}