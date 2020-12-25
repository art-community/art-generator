package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.*;
import io.art.core.exception.*;
import io.art.core.lazy.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.method;
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
import static lombok.AccessLevel.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

@RequiredArgsConstructor(access = PRIVATE)
public class ToModelMapperCreator {
    private final String valueName;

    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        ToModelMapperCreator creator = new ToModelMapperCreator(sequenceName(VALUE_NAME));

        if (isLibraryType(type)) {
            return creator.body(type);
        }

        if (type instanceof GenericArrayType) {
            return creator.body(type);
        }

        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return creator.body(type);
            }
        }

        if (hasBuilder(extractClass(type))) {
            return byBuilder(type, creator);
        }

        if (hasAllArgumentsConstructor(extractClass(type))) {
            return byConstructor(type, creator);
        }

        if (hasNoArgumentsConstructor(extractClass(type))) {
            return bySetter(type, creator);
        }

        throw new GenerationException(format(NOT_FOUND_FACTORY_METHODS, type));
    }


    private static JCLambda bySetter(Type type, ToModelMapperCreator creator) {
        throw new NotImplementedException("TODO:");
    }

    private static JCLambda byConstructor(Type type, ToModelMapperCreator creator) {
        throw new NotImplementedException("TODO:");
    }

    private static JCLambda byBuilder(Type type, ToModelMapperCreator creator) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, creator.valueName))
                .expression(() -> creator.body(type))
                .generate();
    }


    private JCExpression body(Type type) {
        if (type instanceof Class) {
            return body((Class<?>) type);
        }

        if (type instanceof ParameterizedType) {
            return body((ParameterizedType) type);
        }

        if (type instanceof GenericArrayType) {
            return body((GenericArrayType) type);
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type.getTypeName()));
    }

    private JCExpression body(Class<?> modelClass) {
        if (byte[].class.equals(modelClass)) {
            return select(BINARY_MAPPING_TYPE, TO_BINARY);
        }
        if (modelClass.isArray()) {
            if (isJavaPrimitiveType(modelClass.getComponentType())) {
                return select(ARRAY_MAPPING_TYPE, selectToArrayJavaPrimitiveMethod(modelClass));
            }
            JCExpression parameterMapper = toModelMapper(modelClass.getComponentType());
            List<JCExpression> arguments = List.of(newReference(type(modelClass)), parameterMapper);
            return method(ARRAY_MAPPING_TYPE, TO_ARRAY).addArguments(arguments).apply();
        }
        if (isPrimitiveType(modelClass)) {
            return select(PRIMITIVE_MAPPING_TYPE, selectToPrimitiveMethod(modelClass));
        }
        return forProperties(modelClass);
    }

    private JCExpression body(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType.getTypeName()));
        }
        Class<?> rawClass = (Class<?>) rawType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (isCollectionType(rawClass)) {
            JCExpression parameterMapper = toModelMapper(typeArguments[0]);
            return method(ARRAY_MAPPING_TYPE, selectToCollectionMethod(rawClass)).addArguments(List.of(parameterMapper)).apply();
        }
        if (Map.class.isAssignableFrom(rawClass)) {
            if (isComplexType(typeArguments[0])) {
                throw new GenerationException(format(UNSUPPORTED_TYPE, typeArguments[0]));
            }
            JCExpression keyToModelMapper = toModelMapper(typeArguments[0]);
            JCExpression keyFromModelMapper = fromModelMapper(typeArguments[0]);
            JCExpression valueMapper = toModelMapper(typeArguments[1]);
            List<JCExpression> arguments = List.of(keyToModelMapper, keyFromModelMapper, valueMapper);
            return method(ENTITY_MAPPING_TYPE, TO_MAP).addArguments(arguments).apply();
        }

        return forProperties(parameterizedType, rawClass);
    }

    private JCExpression body(GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        List<JCExpression> arguments = List.of(newReference(type(genericArrayType)), toModelMapper(genericComponentType));
        List<TypeModel> typeParameters = List.of(type(genericComponentType));
        return method(ARRAY_MAPPING_TYPE, TO_ARRAY).addTypeParameters(typeParameters).addArguments(arguments).apply();
    }


    private JCMethodInvocation forProperties(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = method(type(modelClass), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            JCMethodInvocation fieldMappers = forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(List.of(fieldMappers)).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

    private JCMethodInvocation forProperties(ParameterizedType parameterizedType, Class<?> rawClass) {
        JCMethodInvocation builderInvocation = method(type(parameterizedType), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            JCMethodInvocation fieldMapping = forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(List.of(fieldMapping)).apply();
        }

        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }


    private JCMethodInvocation forField(String fieldName, Type fieldType) {
        boolean javaPrimitiveType = isJavaPrimitiveType(fieldType);
        if (isLazyValue(fieldType)) {
            return forLazyField(fieldName, ((ParameterizedType) fieldType).getActualTypeArguments()[0], javaPrimitiveType);
        }
        ListBuffer<JCExpression> arguments = new ListBuffer<>();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        String method = javaPrimitiveType ? MAP_OR_DEFAULT_NAME : MAP_NAME;
        return method(method(valueName, MAPPING_METHOD_NAME).apply(), method).addArguments(arguments.toList()).apply();
    }

    private JCMethodInvocation forLazyField(String fieldName, Type fieldType, boolean javaPrimitiveType) {
        ListBuffer<JCExpression> arguments = new ListBuffer<>();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        JCLambda supplier = newLambda()
                .expression(() -> {
                    String method = javaPrimitiveType ? MAP_OR_DEFAULT_NAME : MAP_NAME;
                    return method(method(valueName, MAPPING_METHOD_NAME).apply(), method).addArguments(arguments.toList()).apply();
                })
                .generate();
        return method(type(LazyValue.class), LAZY_NAME).addArguments(List.of(supplier)).apply();
    }
}
