package io.art.generator.creator.configuration;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.generator.caller.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.generator.reflection.*;
import lombok.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfigurationSourceMethods.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfiguratorMethods.*;
import static io.art.generator.constants.ConfiguratorConstants.NestedConfigurationMethods.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeConstants.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.selector.ConfigurationSourceMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@RequiredArgsConstructor
public class CustomConfigurationCreator {
    private final ImmutableMap<Class<?>, String> configurationClasses;

    public static NewMethod createConfigureMethod(Type configurationClass, ImmutableMap<Class<?>, String> configurationClasses) {
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(extractClass(configurationClass));
        NewMethod method = overrideMethod(CONFIGURE_METHOD, type(configurationClass));
        CustomConfigurationCreator creator = new CustomConfigurationCreator(configurationClasses);
        ImmutableArray<JCExpression> constructorParameters = creator.createConstructorParameters(properties);
        return method.statement(() -> returnExpression(newObject(type(configurationClass), constructorParameters)));
    }

    private ImmutableArray<JCExpression> createConstructorParameters(ImmutableArray<ExtractedProperty> properties) {
        ImmutableArray.Builder<JCExpression> constructorParameters = immutableArrayBuilder();
        for (ExtractedProperty property : properties) {
            Type type = property.type();
            if (isJavaPrimitiveType(type)) {
                throw new ValidationException(formatSignature(property), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
            }
            constructorParameters.add(createPropertyProvider(property));
        }
        return constructorParameters.build();
    }

    private JCExpression createPropertyProvider(ExtractedProperty property) {
        return method(CONFIGURE_METHOD_INPUT, GET_NESTED)
                .addArguments(literal(property.name()))
                .addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, property.name()))
                        .expression(() -> createPropertyProvider(property.name(), property.type()))
                        .generate())
                .apply();
    }

    private JCMethodInvocation createPropertyProvider(String parameter, Type type) {
        if (isClass(type)) {
            return createPropertyProvider(parameter, (Class<?>) type);
        }
        if (isParametrized(type)) {
            return createPropertyProvider(parameter, (ParameterizedType) type);
        }
        if (isGenericArray(type)) {
            return createPropertyProvider(parameter, (GenericArrayType) type);
        }
        throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCMethodInvocation createPropertyProvider(String parameter, Class<?> type) {
        if (type.isArray()) {
            return createArrayPropertyProvider(parameter, type);
        }

        String configuratorName = configurationName(type);

        if (configurationClasses.containsKey(type)) {
            return method(parameter, GET_NESTED)
                    .addArguments(literal(parameter))
                    .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                    .apply();
        }

        return method(parameter, selectConfigurationSourceMethod(type)).apply();
    }

    private JCMethodInvocation createPropertyProvider(String parameter, ParameterizedType type) {
        if (isOptional(type)) {
            return method(OPTIONAL_TYPE, OF_NULLABLE_NAME)
                    .addArguments(createPropertyProvider(parameter, extractFirstTypeParameter(type)))
                    .apply();
        }

        if (!isCollectionType(type)) {
            throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
        }

        String lambdaParameter = sequenceName(parameter);
        MethodCaller propertyProvider = method(parameter, AS_ARRAY)
                .addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                        .expression(() -> createPropertyProvider(lambdaParameter, extractFirstTypeParameter(type)))
                        .generate());

        if (isSetType(type) || isImmutableSetType(type)) {
            if (isMutableType(type)) {
                return method(SET_FACTORY_TYPE, SET_OF_NAME)
                        .addArguments(propertyProvider.apply())
                        .apply();
            }
            return method(SET_FACTORY_TYPE, IMMUTABLE_SET_OF_NAME)
                    .addArguments(propertyProvider.apply())
                    .apply();
        }

        if (isListType(type) || isImmutableArrayType(type)) {
            if (isMutableType(type)) {
                propertyProvider = method(propertyProvider.apply(), TO_MUTABLE_NAME);
            }
            return propertyProvider.apply();
        }

        throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCMethodInvocation createPropertyProvider(String parameter, GenericArrayType type) {
        Type componentType = type.getGenericComponentType();
        String lambdaParameter = sequenceName(parameter);
        return method(parameter, AS_ARRAY)
                .addTypeParameter(type(componentType))
                .addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                        .expression(() -> createPropertyProvider(lambdaParameter, componentType))
                        .generate())
                .next(TO_ARRAY_NAME, toArray -> toArray
                        .addTypeParameter(type(componentType))
                        .addArguments(newReference(type(type))))
                .apply();

    }

    private JCMethodInvocation createArrayPropertyProvider(String parameter, Class<?> type) {
        boolean primitiveType = isJavaPrimitiveType(type.getComponentType());
        Class<?> componentType = primitiveType
                ? JAVA_PRIMITIVE_MAPPINGS.get(type.getComponentType())
                : type.getComponentType();

        if (configurationClasses.containsKey(componentType)) {
            String configuratorName = configurationName(componentType);
            return method(parameter, GET_NESTED_ARRAY)
                    .addArguments(literal(parameter))
                    .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                    .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(componentType), 0)))
                    .apply();
        }

        ParameterizedTypeImplementation immutableArrayType = parameterizedType(ImmutableArray.class, componentType);
        JCMethodInvocation asArray = method(parameter, selectConfigurationSourceMethod(immutableArrayType))
                .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(componentType), 0)))
                .apply();

        if (primitiveType) {
            return method(ARRAY_EXTENSIONS_TYPE, UNBOX_NAME)
                    .addArguments(asArray)
                    .apply();
        }

        return asArray;
    }
}
