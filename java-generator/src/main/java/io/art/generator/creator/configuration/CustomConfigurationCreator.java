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
import static io.art.generator.service.NamingService.*;
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
            constructorParameters.add(createPropertyProvider(property));
        }
        return constructorParameters.build();
    }

    private JCExpression createPropertyProvider(ExtractedProperty property) {
        Type type = property.type();
        return method(CONFIGURE_METHOD_INPUT, GET_NESTED)
                .addArguments(literal(property.name()))
                .addArguments(newLambda()
                        .parameter(newParameter(type(NestedConfiguration.class), property.name()))
                        .expression(() -> createPropertyProvider(property, type))
                        .generate())
                .apply();
    }

    private JCMethodInvocation createPropertyProvider(ExtractedProperty property, Type type) {
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

    private JCMethodInvocation createPropertyProvider(ExtractedProperty property, Class<?> type) {
        if (type.isArray()) {
            return createArrayPropertyProvider(property, type);
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

    private JCMethodInvocation createPropertyProvider(ExtractedProperty property, ParameterizedType type) {
        if (isOptional(type)) {
            return method(OPTIONAL_TYPE, OF_NULLABLE_NAME)
                    .addArguments(createPropertyProvider(property, extractFirstTypeParameter(type)))
                    .apply();
        }

        if (!isCollectionType(type)) {
            throw new ValidationException(formatSignature(property), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
        }

        MethodCaller propertyProvider = computeParameterizedPropertyProvider(property, extractFirstTypeParameter(type));

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

    private JCMethodInvocation createPropertyProvider(ExtractedProperty property, GenericArrayType type) {
        Type componentType = type.getGenericComponentType();
        String parameter = sequenceName(property.name());
        ExtractedProperty newProperty = property.toBuilder().name(parameter).type(componentType).build();
        return method(property.name(), AS_ARRAY)
                .addTypeParameter(type(componentType))
                .addArguments(newLambda()
                        .parameter(newParameter(NESTED_CONFIGURATION_TYPE, parameter))
                        .expression(() -> createPropertyProvider(newProperty, componentType))
                        .generate())
                .next(TO_ARRAY_NAME, toArray -> toArray.addTypeParameter(type(componentType)).addArguments(newReference(type(type))))
                .apply();

    }

    private MethodCaller computeParameterizedPropertyProvider(ExtractedProperty property, Type parameterType) {
        String parameter = sequenceName(property.name());
        JCLambda lambda = newLambda()
                .parameter(newParameter(NESTED_CONFIGURATION_TYPE, parameter))
                .expression(() -> createPropertyProvider(property.toBuilder().name(parameter).type(parameterType).build(), parameterType))
                .generate();
        if (isClass(parameterType)) {
            if (configurationClasses.contains(parameterType)) {
                String configuratorName = computeCustomConfiguratorClassName(parameterType);
                return method(property.name(), AS_ARRAY).addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME));
            }
            return method(property.name(), selectConfigurationSourceMethod(parameterizedType(ImmutableArray.class, parameterType)));
        }

        return method(property.name(), AS_ARRAY).addArguments(lambda);
    }

    private JCMethodInvocation createArrayPropertyProvider(ExtractedProperty property, Class<?> type) {
        boolean primitiveType = isJavaPrimitiveType(type.getComponentType());
        Class<?> componentType = primitiveType
                ? JAVA_PRIMITIVE_MAPPINGS.get(type.getComponentType())
                : type.getComponentType();

        if (configurationClasses.contains(componentType)) {
            String configuratorName = computeCustomConfiguratorClassName(componentType);
            return method(property.name(), GET_NESTED_ARRAY)
                    .addArguments(literal(property.name()))
                    .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                    .next(TO_ARRAY_NAME, toArray -> toArray.addArguments(newArray(type(componentType), 0)))
                    .apply();
        }

        ParameterizedTypeImplementation immutableArrayType = parameterizedType(ImmutableArray.class, componentType);
        JCMethodInvocation asArray = method(property.name(), selectConfigurationSourceMethod(immutableArrayType))
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
