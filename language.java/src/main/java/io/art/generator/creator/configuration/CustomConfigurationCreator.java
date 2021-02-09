package io.art.generator.creator.configuration;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.core.reflection.*;
import io.art.generator.caller.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
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
import static io.art.generator.selector.ConfigurationSourceMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@UtilityClass
public class CustomConfigurationCreator {
    public NewMethod createConfigureMethod(Class<?> configurationClass) {
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(extractClass(configurationClass));
        NewMethod method = overrideMethod(CONFIGURE_METHOD, type(configurationClass)).parameters(setOf(newParameter(CONFIGURATION_SOURCE_TYPE, SOURCE_NAME)));
        ImmutableArray<JCExpression> constructorParameters = createConstructorParameters(properties);
        return method.statement(() -> returnExpression(newObject(type(configurationClass), constructorParameters)));
    }

    private ImmutableArray<JCExpression> createConstructorParameters(ImmutableArray<ExtractedProperty> properties) {
        ImmutableArray.Builder<JCExpression> constructorParameters = immutableArrayBuilder();
        for (ExtractedProperty property : properties) {
            constructorParameters.add(createPropertyProvider(property));
        }
        return constructorParameters.build();
    }

    private JCExpression createPropertyProvider(ExtractedProperty property) {
        Type type = property.type();
        if (isNestedConfiguration(type)) {
            return method(SOURCE_NAME, GET_NESTED).addArguments(literal(property.name())).apply();
        }
        if (isOptional(type)) {
            return method(OPTIONAL_TYPE, OF_NULLABLE_NAME)
                    .addArguments(method(SOURCE_NAME, GET_NESTED)
                            .addArguments(literal(property.name()))
                            .addArguments(newLambda()
                                    .parameter(newParameter(NESTED_CONFIGURATION_TYPE, property.name()))
                                    .expression(() -> createPropertyProvider(property.name(), extractFirstTypeParameter((ParameterizedType) type)))
                                    .generate())
                            .apply())
                    .apply();
        }
        return method(SOURCE_NAME, GET_NESTED)
                .addArguments(literal(property.name()))
                .addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, property.name()))
                        .expression(() -> createPropertyProvider(property.name(), type))
                        .generate())
                .apply();
    }

    private JCExpression createPropertyProvider(String property, Type type) {
        if (isClass(type)) {
            return createPropertyProvider(property, (Class<?>) type);
        }
        if (isParametrized(type)) {
            return createPropertyProvider(property, (ParameterizedType) type);
        }
        if (isGenericArray(type)) {
            return createPropertyProvider(property, (GenericArrayType) type);
        }
        throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCExpression createPropertyProvider(String property, Class<?> type) {
        if (type.isArray()) {
            return createArrayPropertyProvider(property, type);
        }

        String configuratorName = configurations().get(type);

        if (configurations().contains(type)) {
            return method(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME)
                    .addArguments(ident(property))
                    .apply();
        }

        return method(property, selectConfigurationSourceMethod(type)).apply();
    }

    private JCExpression createPropertyProvider(String property, ParameterizedType type) {
        Type firstType = extractFirstTypeParameter(type);
        if (isOptional(type)) {
            return method(OPTIONAL_TYPE, OF_NULLABLE_NAME)
                    .addArguments(createPropertyProvider(property, firstType))
                    .apply();
        }

        if (isMap(type) || isImmutableMap(type)) {
            if (firstType != String.class) {
                throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
            }
            Type secondType = extractTypeParameter(type, 1);
            String lambdaParameter = sequenceName(property);
            MethodCaller propertyProvider = method(property, AS_MAP);
            if (!isNestedConfiguration(secondType)) {
                propertyProvider = propertyProvider.addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                        .expression(() -> createPropertyProvider(lambdaParameter, secondType))
                        .generate());
            }
            if (isMap(type)) {
                propertyProvider = method(propertyProvider.apply(), TO_MUTABLE_NAME);
            }
            return propertyProvider.apply();
        }

        if (!isCollection(type)) {
            throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
        }

        String lambdaParameter = sequenceName(property);
        MethodCaller propertyProvider = method(property, AS_ARRAY);
        if (!isNestedConfiguration(firstType)) {
            propertyProvider = propertyProvider.addArguments(newLambda()
                    .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                    .expression(() -> createPropertyProvider(lambdaParameter, firstType))
                    .generate());
        }
        if (isSet(type) || isImmutableSet(type)) {
            if (isMutableCollection(type)) {
                return method(SET_FACTORY_TYPE, SET_OF_NAME)
                        .addArguments(propertyProvider.apply())
                        .apply();
            }
            return method(SET_FACTORY_TYPE, IMMUTABLE_SET_OF_NAME)
                    .addArguments(propertyProvider.apply())
                    .apply();
        }

        if (isList(type) || isImmutableArray(type)) {
            if (isMutableCollection(type)) {
                propertyProvider = method(propertyProvider.apply(), TO_MUTABLE_NAME);
            }
            return propertyProvider.apply();
        }

        throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCExpression createPropertyProvider(String property, GenericArrayType type) {
        String lambdaParameter = sequenceName(property);
        Type componentType = type.getGenericComponentType();
        MethodCaller asArray = method(property, AS_ARRAY);
        if (!isNestedConfiguration(componentType)) {
            asArray = asArray.addArguments(newLambda()
                    .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                    .expression(() -> createPropertyProvider(lambdaParameter, componentType))
                    .generate());
        }
        return asArray.next(TO_ARRAY_RAW_NAME, toArray -> toArray.addArguments(newReference(type(type)))).apply();

    }

    private JCMethodInvocation createArrayPropertyProvider(String property, Class<?> type) {
        boolean primitiveType = isJavaPrimitive(type.getComponentType());
        Class<?> componentType = primitiveType
                ? JAVA_PRIMITIVE_MAPPINGS.get(type.getComponentType())
                : type.getComponentType();

        if (configurations().contains(componentType)) {
            String lambdaParameter = sequenceName(property);
            MethodCaller asArray = method(property, AS_ARRAY);
            if (!isNestedConfiguration(componentType)) {
                asArray = asArray.addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, lambdaParameter))
                        .expression(() -> createPropertyProvider(lambdaParameter, componentType))
                        .generate());
            }
            return asArray.next(TO_ARRAY_RAW_NAME, toArray -> toArray.addArguments(newReference(type(type)))).apply();
        }

        ParameterizedTypeImplementation immutableArrayType = parameterizedType(ImmutableArray.class, componentType);
        JCMethodInvocation asArray = method(property, selectConfigurationSourceMethod(immutableArrayType))
                .next(TO_ARRAY_RAW_NAME, toArray -> toArray.addArguments(newReference(type(type))))
                .apply();

        if (primitiveType) {
            return method(ARRAY_EXTENSIONS_TYPE, UNBOX_NAME)
                    .addArguments(asArray)
                    .apply();
        }

        return asArray;
    }
}
