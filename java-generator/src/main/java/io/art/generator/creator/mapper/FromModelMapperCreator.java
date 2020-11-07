package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.immutable.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.extractor.TypeParameterExtractor.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
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

    private JCMethodInvocation createMapperContent(Class<?> modelClass) {
        try {
            JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER_NAME);
            for (Method method : modelClass.getDeclaredMethods()) {
                String getterName = method.getName();
                if (getterName.startsWith(GET_PREFIX)) {
                    String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                    Type fieldType = modelClass.getDeclaredField(fieldName).getGenericType();
                    builderInvocation = createFieldMapping(builderInvocation, fieldName, fieldType);
                }
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
        mapping.add(selectMapper(fieldType));
        return applyMethod(builderInvocation, LAZY_PUT_NAME, mapping.toList());
    }

    private JCExpression selectMapper(Type type) {
        if (String.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromString);
        }

        if (Integer.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromInt);
        }

        if (Long.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromLong);
        }

        if (Boolean.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromBool);
        }

        if (Double.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromDouble);
        }

        if (Byte.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromByte);
        }

        if (Float.class.equals(type)) {
            return select(type(PrimitiveMapping.class), fromFloat);
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
                Class<?> rawTypeAsClass = (Class<?>) rawType;
                JCExpression parameterMapper = selectMapper(extractFirstTypeParameter(parameterizedType));
                if (rawTypeAsClass.isAssignableFrom(List.class)) {
                    return applyClassMethod(type(ArrayMapping.class), fromList, List.of(parameterMapper));
                }
                if (rawTypeAsClass.isAssignableFrom(Queue.class)) {
                    return applyClassMethod(type(ArrayMapping.class), fromQueue, List.of(parameterMapper));
                }
                if (rawTypeAsClass.isAssignableFrom(Deque.class)) {
                    return applyClassMethod(type(ArrayMapping.class), fromDeque, List.of(parameterMapper));
                }
                if (rawTypeAsClass.isAssignableFrom(Set.class)) {
                    return applyClassMethod(type(ArrayMapping.class), fromSet, List.of(parameterMapper));
                }
                if (rawTypeAsClass.isAssignableFrom(Collection.class)) {
                    return applyClassMethod(type(ArrayMapping.class), fromCollection, List.of(parameterMapper));
                }
            }
        }

        return null;
    }
}
