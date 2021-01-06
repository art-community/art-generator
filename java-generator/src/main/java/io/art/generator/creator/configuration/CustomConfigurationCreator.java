package io.art.generator.creator.configuration;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.core.collection.*;
import io.art.core.source.*;
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
import static io.art.generator.state.GenerationState.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;

@RequiredArgsConstructor
public class CustomConfigurationCreator {
    private final ImmutableSet<Class<?>> configurationClasses;

    public static NewMethod createConfigureMethod(Type configurationClass, ImmutableSet<Class<?>> configurationClasses) {
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
            constructorParameters.add(createConstructorParameter(property));
        }
        return constructorParameters.build();
    }

    private JCExpression createConstructorParameter(ExtractedProperty property) {
        Type type = property.type();
        return method(CONFIGURE_METHOD_INPUT, GET_NESTED)
                .addArguments(literal(property.name()))
                .addArguments(newLambda()
                        .parameter(newParameter(type(NestedConfiguration.class), property.name()))
                        .expression(() -> createConstructorParameter(property, type))
                        .generate())
                .apply();
    }

    private JCMethodInvocation createConstructorParameter(ExtractedProperty property, Type type) {
        if (isClass(type)) {
            return createConstructorParameter(property, (Class<?>) type);
        }
        if (isParametrized(type)) {
            return createConstructorParameter(property, (ParameterizedType) type);
        }
        if (isGenericArray(type)) {
            return createConstructorParameter(property, (GenericArrayType) type);
        }
        throw new ValidationException(formatSignature(type), format(NOT_CONFIGURATION_SOURCE_TYPE, type));

    }

    private JCMethodInvocation createConstructorParameter(ExtractedProperty property, Class<?> type) {
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            if (configurationClasses.contains(componentType)) {
                String configuratorName = computeCustomConfiguratorClassName(componentType);
                Class<?> resultComponentType = componentType;
                return method(property.name(), GET_NESTED_ARRAY)
                        .addArguments(literal(property.name()))
                        .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                        .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(resultComponentType), 0)))
                        .apply();
            }
            boolean primitiveType = isJavaPrimitiveType(componentType);
            if (primitiveType) componentType = JAVA_PRIMITIVE_MAPPINGS.get(componentType);
            ParameterizedTypeImplementation immutableArrayType = parameterizedType(ImmutableArray.class, componentType);
            Class<?> resultComponentType = componentType;
            JCMethodInvocation asArray = method(property.name(), selectConfigurationSourceMethod(immutableArrayType))
                    .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(resultComponentType), 0)))
                    .apply();
            if (primitiveType) {
                return method(ARRAY_EXTENSIONS_TYPE, UNBOX_NAME)
                        .addArguments(asArray)
                        .apply();
            }
            return asArray;
        }

        String configuratorName = computeCustomConfiguratorClassName(type);

        if (configurationClasses.contains(type)) {
            return method(property.name(), GET_NESTED)
                    .addArguments(literal(property.name()))
                    .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                    .apply();
        }

        return method(property.name(), selectConfigurationSourceMethod(type)).apply();
    }

    private JCMethodInvocation createConstructorParameter(ExtractedProperty property, ParameterizedType type) {
        if (isOptional(type)) {
            return method(OPTIONAL_TYPE, OF_NULLABLE_NAME)
                    .addArguments(createConstructorParameter(property, extractFirstTypeParameter(type)))
                    .apply();
        }

        if (!isCollectionType(type)) {
            throw new ValidationException(formatSignature(property), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
        }

        Type parameterType = extractFirstTypeParameter(type);

        MethodCaller propertyProvider = method(property.name(), AS_ARRAY)
                .addArguments(newLambda()
                        .parameter(newParameter(type(NestedConfiguration.class), property.name() + "Array"))
                        .expression(() -> createConstructorParameter(property.toBuilder().name(property.name() + "Array").type(parameterType).build(), parameterType))
                        .generate());

        if (isClass(parameterType)) {
            if (configurationClasses.contains(parameterType)) {
                propertyProvider = method(property.name(), AS_ARRAY).addArguments(invokeReference(select(computeCustomConfiguratorClassName(parameterType), INSTANCE_FIELD_NAME), CONFIGURE_NAME));
            } else {
                propertyProvider = method(property.name(), selectConfigurationSourceMethod(parameterizedType(ImmutableArray.class, parameterType)));
            }
        }

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

        throw new ValidationException(formatSignature(property), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCMethodInvocation createConstructorParameter(ExtractedProperty property, GenericArrayType type) {
        Type componentType = type.getGenericComponentType();
        return method(property.name(), AS_ARRAY)
                .addArguments(newLambda()
                        .parameter(newParameter(type(NestedConfiguration.class), property.name() + "Array"))
                        .expression(() -> createConstructorParameter(property.toBuilder().name(property.name() + "Array").type(componentType).build(), (ParameterizedType) componentType))
                        .generate())
                .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(componentType), 0)))
                .apply();

    }
}
