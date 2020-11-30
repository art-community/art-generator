package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.generator.exception.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import lombok.experimental.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.ArrayMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.BinaryMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.EntityMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.selector.ToMapperMethodSelector.*;
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
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public JCExpression createToModelMapper(Type type) {
        String valueName = sequenceName(VALUE_NAME);

        if (isLibraryType(type)) {
            return createToModelMapperBody(type, valueName);
        }

        if (type instanceof GenericArrayType) {
            return createToModelMapperBody(type, valueName);
        }

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return createToModelMapperBody(type, valueName);
            }
        }

        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, valueName))
                .expression(() -> createToModelMapperBody(type, valueName))
                .generate();
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

    private JCExpression createClassMapperBody(Class<?> modelClass, String valueName) {
        if (byte[].class.equals(modelClass)) {
            return select(BINARY_MAPPING_TYPE, TO_BINARY);
        }
        if (modelClass.isArray()) {
            if (isJavaPrimitiveType(modelClass.getComponentType())) {
                return select(ARRAY_MAPPING_TYPE, selectToArrayJavaPrimitiveMethod(modelClass));
            }
            JCExpression parameterMapper = toModelMapper(modelClass.getComponentType());
            return applyClassMethod(ARRAY_MAPPING_TYPE, TO_ARRAY, List.of(parameterMapper));
        }
        if (isPrimitiveType(modelClass)) {
            return select(PRIMITIVE_MAPPING_TYPE, selectToPrimitiveMethod(modelClass));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass), BUILDER_METHOD_NAME);
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            JCMethodInvocation fieldMappers = createFieldMappers(fieldName, fieldType, valueName);
            builderInvocation = applyMethod(builderInvocation, fieldName, List.of(fieldMappers));
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createParametrizedTypeMapperBody(ParameterizedType parameterizedType, String valueName) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> rawClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(rawClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return applyClassMethod(ARRAY_MAPPING_TYPE, selectToCollectionMethod(rawClass), List.of(parameterMapper));
        }
        if (Map.class.isAssignableFrom(rawClass)) {
            if (isCustomType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = toModelMapper(typeArguments[1]);
            return applyClassMethod(ENTITY_MAPPING_TYPE, TO_MAP, List.of(keyToModelMapper, keyFromModelMapper, valueMapper));
        }
        JCMethodInvocation builderInvocation = applyClassMethod(type(parameterizedType), BUILDER_METHOD_NAME);
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            JCMethodInvocation fieldMapping = createFieldMappers(fieldName, fieldType, valueName);
            builderInvocation = applyMethod(builderInvocation, fieldName, List.of(fieldMapping));
        }
        return applyMethod(builderInvocation, BUILD_METHOD_NAME);
    }

    private JCExpression createGenericArrayTypeMapperBody(GenericArrayType genericArrayType) {
        JCExpression parameterMapper = toModelMapper(genericArrayType.getGenericComponentType());
        return applyClassMethod(ARRAY_MAPPING_TYPE, TO_ARRAY, List.of(parameterMapper));
    }

    private JCMethodInvocation createFieldMappers(String fieldName, Type fieldType, String valueName) {
        ListBuffer<JCExpression> arguments = new ListBuffer<>();
        arguments.add(maker().Literal(fieldName));
        boolean javaPrimitiveType = isJavaPrimitiveType(fieldType);
        String methodName = javaPrimitiveType ? MAP_PRIMITIVE_NAME : MAP_NAME;
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        return applyMethod(valueName, methodName, arguments.toList());
    }
}
