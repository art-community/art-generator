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
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeConstants.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewMethod.*;
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
            constructorParameters.add(createConstructorParameter(property, type));
        }
        return constructorParameters.build();
    }

    private JCExpression createConstructorParameter(ExtractedProperty property, Type type) {
        if (isClass(type)) {
            return createConstructorParameter(property, (Class<?>) type);
        }
        if (isParametrized(type)) {
            return createConstructorParameter(property, (ParameterizedType) type);
        }
        throw new ValidationException(formatSignature(property), format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

    private JCMethodInvocation createConstructorParameter(ExtractedProperty property, Class<?> type) {
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            boolean primitiveType = isJavaPrimitiveType(componentType);
            if (primitiveType) componentType = JAVA_PRIMITIVE_MAPPINGS.get(componentType);
            ParameterizedTypeImplementation immutableArrayType = parameterizedType(ImmutableArray.class, componentType);
            JCMethodInvocation toArray = method(createConstructorParameter(property, immutableArrayType), TO_ARRAY_NAME)
                    .addArguments(newArray(type(componentType), 0))
                    .apply();
            if (primitiveType) {
                return method(ARRAY_EXTENSIONS_TYPE, UNBOX_NAME)
                        .addArguments(toArray)
                        .apply();
            }
            return toArray;
        }

        String configuratorName = computeCustomConfiguratorClassName(type);

        if (configurationClasses.contains(type)) {
            return method(CONFIGURE_METHOD_INPUT, GET_NESTED)
                    .addArguments(literal(property.name()))
                    .addArguments(invokeReference(select(configuratorName, INSTANCE_FIELD_NAME), CONFIGURE_NAME))
                    .apply();
        }

        return method(CONFIGURE_METHOD_INPUT, selectConfigurationSourceMethod(property, type))
                .addArgument(literal(property.name()))
                .apply();
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

        MethodCaller propertyProvider = configurationClasses.contains(parameterType)
                ? method(CONFIGURE_METHOD_INPUT, GET_NESTED_ARRAY)
                .addArguments(literal(property.name()))
                .addArguments(invokeReference(select(computeCustomConfiguratorClassName(parameterType), INSTANCE_FIELD_NAME), CONFIGURE_NAME))

                : method(CONFIGURE_METHOD_INPUT, selectConfigurationSourceMethod(property, parameterizedType(ImmutableArray.class, parameterType)))
                .addArgument(literal(property.name()));

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
}
