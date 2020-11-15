package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.Value;
import io.art.value.mapping.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
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
    public JCLambda createToModelMapper(Type type) {
        return newLambda()
                .parameter(newParameter(type(Value.class), VALUE_NAME))
                .expression(() -> createToModelMapperBody(type))
                .generate();
    }

    public JCExpression createToModelMapperBody(Type type) {
        if (type instanceof Class) {
            return createClassMapper((Class<?>) type);
        }

        if (type instanceof ParameterizedType) {
            return creteParametrizedTypeMapper((ParameterizedType) type);
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression creteParametrizedTypeMapper(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        Class<?> mappingClass = (Class<?>) rawType;
        if (Flux.class.isAssignableFrom(mappingClass)) {
            return createToModelMapperBody(typeArguments[0]);
        }
        if (Mono.class.isAssignableFrom(mappingClass)) {
            return createToModelMapperBody(typeArguments[0]);
        }
        if (java.util.List.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createToModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_LIST, List.of(parameterMapper));
        }
        if (Queue.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createToModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_QUEUE, List.of(parameterMapper));
        }
        if (Deque.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createToModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_DEQUE, List.of(parameterMapper));
        }
        if (Set.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createToModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_SET, List.of(parameterMapper));
        }
        if (Collection.class.isAssignableFrom(mappingClass)) {
            JCExpression parameterMapper = createToModelMapperBody(typeArguments[0]);
            return applyClassMethod(type(ArrayMapping.class), TO_COLLECTION, List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(mappingClass)) {
            JCExpression keyToModelMapper = createToModelMapperBody(typeArguments[0]);
            JCExpression keyFromModelMapper = createFromModelMapperBody(typeArguments[0]);
            JCExpression valueMapper = createToModelMapperBody(typeArguments[1]);
            return applyClassMethod(type(EntityMapping.class), TO_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
    }

    private JCExpression createClassMapper(Class<?> mappingClass) {
        if (byte[].class.equals(mappingClass)) {
            return select(type(BinaryMapping.class), TO_BINARY);
        }
        if (mappingClass.isArray()) {
            JCExpression parameterMapper = createToModelMapperBody(mappingClass.getComponentType());
            return applyClassMethod(type(ArrayMapping.class), TO_ARRAY, List.of(parameterMapper));
        }
        if (String.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_STRING);
        }
        if (short.class.equals(mappingClass) || Short.class.equals(mappingClass)) {
            return select(type(PrimitiveMapping.class), TO_INT);
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
        return createMapperContent(mappingClass);
    }

    private JCMethodInvocation createMapperContent(Class<?> mappingClass) {
        try {
            JCMethodInvocation builderInvocation = applyClassMethod(type(mappingClass), BUILDER_METHOD_NAME);
            for (Field field : getMappingFields(mappingClass)) {
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
        mapping.add(createToModelMapperBody(fieldType));
        return applyMethod(VALUE_NAME, MAP_NAME, mapping.toList());
    }
}
