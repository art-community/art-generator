package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.factory.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayFactoryMethods.*;
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
    public JCExpression createFromModelMapper(Type type) {
        if (typeIsKnown(type)) {
            return createFromModelMapperBody(type);
        }
        return newLambda()
                .parameter(newParameter(type(type), MODEL_NAME))
                .expression(() -> createFromModelMapperBody(type))
                .generate();
    }

    public JCExpression createFromModelMapperBody(Type type) {
        if (type instanceof Class) {
            return createClassMapper((Class<?>) type);
        }

        if (type instanceof ParameterizedType) {
            return createParameterizedTypeMapper((ParameterizedType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression createClassMapper(Class<?> mappingClass) {
        if (byte[].class.equals(mappingClass)) {
            return select(type(BinaryMapping.class), FROM_BINARY);
        }
        if (mappingClass.isArray()) {
            if (short.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), SHORT_ARRAY);
            }
            if (int.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), INT_ARRAY);
            }
            if (long.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), LONG_ARRAY);
            }
            if (boolean.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), BOOL_ARRAY);
            }
            if (double.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), DOUBLE_ARRAY);
            }
            if (byte.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), BYTE_ARRAY);
            }
            if (float.class.equals(mappingClass.getComponentType())) {
                return invokeReference(type(ArrayFactory.class), FLOAT_ARRAY);
            }
            JCExpression parameterMapper = createFromModelMapperBody(mappingClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), FROM_ARRAY, List.of(parameterMapper));
        }
        if (String.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_STRING);
        }
        if (int.class.equals(mappingClass) || Integer.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_INT);
        }
        if (short.class.equals(mappingClass) || Short.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_SHORT);
        }
        if (long.class.equals(mappingClass) || Long.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_LONG);
        }
        if (boolean.class.equals(mappingClass) || Boolean.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_BOOL);
        }
        if (double.class.equals(mappingClass) || Double.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_DOUBLE);
        }
        if (byte.class.equals(mappingClass) || Byte.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_BYTE);
        }
        if (float.class.equals(mappingClass) || Float.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), FROM_FLOAT);
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
        for (Field field : getMappingFields(mappingClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            builderInvocation = createFieldMapping(builderInvocation, fieldName, fieldType);
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createParameterizedTypeMapper(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> mappingClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (Flux.class.isAssignableFrom(mappingClass)) {
            return createFromModelMapperBody(typeArguments[0]);
        }
        if (Mono.class.isAssignableFrom(mappingClass)) {
            return createFromModelMapperBody(typeArguments[0]);
        }
        if (java.util.List.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createFromModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), FROM_LIST, List.of(parameterMapper));
        }
        if (Queue.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createFromModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), FROM_QUEUE, List.of(parameterMapper));
        }
        if (Deque.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createFromModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), FROM_DEQUE, List.of(parameterMapper));
        }
        if (Set.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createFromModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), FROM_SET, List.of(parameterMapper));
        }
        if (Collection.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createFromModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), FROM_COLLECTION, List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(mappingClass)) {
            JCExpression keyToModelMapper = createToModelMapperBody(typeArguments[0]);
            JCExpression keyFromModelMapper = createFromModelMapperBody(typeArguments[0]);
            JCExpression valueMapper = createFromModelMapperBody(typeArguments[1]);
            return applyClassMethod(type(EntityMapping.class), FROM_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
    }

    private JCMethodInvocation createFieldMapping(JCMethodInvocation builderInvocation, String fieldName, Type fieldType) {
        ListBuffer<JCExpression> mapping = new ListBuffer<>();
        mapping.add(literal(fieldName));
        mapping.add(newLambda().expression(() -> applyMethod(MODEL_NAME, GET_PREFIX + capitalize(fieldName))).generate());
        mapping.add(createFromModelMapperBody(fieldType));
        return applyMethod(builderInvocation, LAZY_PUT_NAME, mapping.toList());
    }
}
