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
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.toBinary;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.toString;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.text.MessageFormat.*;
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
        if (String.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toString);
        }

        if (Integer.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toInt);
        }

        if (Long.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toLong);
        }

        if (Boolean.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toBool);
        }

        if (Double.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toDouble);
        }

        if (Byte.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toByte);
        }

        if (Float.class.equals(type)) {
            return select(type(PrimitiveMapping.class), toFloat);
        }

        if (byte[].class.equals(type)) {
            return select(type(BinaryMapping.class), toBinary);
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Class<?> rawTypeAsClass = (Class<?>) parameterizedType.getRawType();
            if (rawType instanceof Class) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                if (java.util.List.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), toList, List.of(parameterMapper));
                }
                if (Queue.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), toQueue, List.of(parameterMapper));
                }
                if (Deque.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), toDeque, List.of(parameterMapper));
                }
                if (Set.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), toSet, List.of(parameterMapper));
                }
                if (Collection.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression parameterMapper = selectToModelMapper(typeArguments[0]);
                    return applyClassMethod(type(ArrayMapping.class), toCollection, List.of(parameterMapper));
                }
                if (Map.class.isAssignableFrom(rawTypeAsClass)) {
                    JCExpression keyToModelMapper = selectToModelMapper(typeArguments[0]);
                    JCExpression keyFromModelMapper = selectFromModelMapper(typeArguments[0]);
                    JCExpression valueMapper = selectToModelMapper(typeArguments[1]);
                    return applyClassMethod(type(EntityMapping.class), toMap, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
                }
            }
        }

        throw new GenerationException(format(UNKNOWN_FIELD_TYPE, type.getTypeName()));
    }

    private JCMethodInvocation createMapperContent(Class<?> modelClass) {
        try {
            JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass.getName()), BUILDER_METHOD_NAME);
            for (Method method : modelClass.getDeclaredMethods()) {
                String getterName = method.getName();
                if (getterName.startsWith(GET_PREFIX)) {
                    String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                    Type fieldType = modelClass.getDeclaredField(fieldName).getGenericType();
                    builderInvocation = applyMethod(builderInvocation, fieldName, List.of(createFieldMapping(fieldName, fieldType)));
                }
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
