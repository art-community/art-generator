package io.art.generator.implementor;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import io.art.configurator.custom.*;
import io.art.core.collection.*;
import io.art.generator.caller.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.model.implementation.configurator.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfigurationSourceMethods.*;
import static io.art.generator.constants.ConfiguratorConstants.ConfiguratorMethods.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.selector.ConfigurationSourceMethodSelector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GenerationState.*;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class ConfiguratorModelImplementor {
    public NewMethod implementCustomConfigurationsMethod(ConfiguratorModuleModel model) {
        TypeModel registryType = CUSTOM_CONFIGURATION_REGISTRY_TYPE;
        NewMethod customConfigurationsMethod = newMethod()
                .name(CUSTOM_CONFIGURATIONS_NAME)
                .parameter(newParameter(CONFIGURATOR_MODULE_MODEL_TYPE, CONFIGURATOR_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        model.getCustomConfigurations().forEach(configuration -> customConfigurationsMethod.statement(() -> maker().Exec(executeRegisterMethod(configuration))));
        return customConfigurationsMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementCustomConfigurators(ConfiguratorModuleModel model) {
        ImmutableSet<Type> configuratorTypes = model.getCustomConfigurations()
                .stream()
                .filter(type -> isNull(getGeneratedCustomConfigurator(type)))
                .collect(immutableSetCollector());
        ImmutableArray.Builder<NewClass> configuratorClasses = immutableArrayBuilder();
        for (Map.Entry<Type, String> entry : computeConfiguratorNames(configuratorTypes).entrySet()) {
            TypeModel configurationType = type(entry.getKey());
            if (!hasConstructorWithAllProperties(configurationType.getType())) {
                //throw new ValidationException("");
            }
            TypeModel configuratorType = type(parameterizedType(CustomConfigurator.class, arrayOf(configurationType.getType())));
            String configuratorName = getGeneratedCustomConfigurator(configurationType.getType());
            NewClass configuratorClass = newClass()
                    .name(configuratorName)
                    .modifiers(PRIVATE | STATIC)
                    .implement(configuratorType)
                    .method(createConfigureMethod(configurationType.getType(), model.getCustomConfigurations()));
            if (!configurationType.isJdk()) {
                configuratorClass.addImport(classImport(configurationType.getFullName()));
            }
            configuratorClasses.add(configuratorClass);
            info(format(GENERATED_CONFIGURATION_PROXY, configurationType.getFullName()));
        }
        return configuratorClasses.build();
    }

    private ImmutableMap<Type, String> computeConfiguratorNames(ImmutableSet<Type> typesArray) {
        Map<Type, String> typeProxies = map();
        for (Type type : typesArray) {
            Class<?> typeAsClass = extractClass(type);
            long id = typeProxies
                    .keySet()
                    .stream()
                    .filter(modelType -> extractClass(modelType).getSimpleName().equals(typeAsClass.getSimpleName()))
                    .count();
            typeProxies.put(type, typeAsClass.getSimpleName() + PROXY_CLASS_SUFFIX + id);
            putGeneratedCustomConfigurator(type, typeAsClass.getSimpleName() + PROXY_CLASS_SUFFIX + id);
        }
        return immutableMapOf(typeProxies);
    }


    private static NewMethod createConfigureMethod(Type configurationClass, ImmutableSet<Class<?>> configurationClasses) {
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(extractClass(configurationClass));
        NewMethod method = overrideMethod(CONFIGURE_METHOD, type(configurationClass));
        ImmutableArray<JCExpression> constructorParameters = getConstructorParameters(configurationClasses, properties);
        return method.statement(() -> returnExpression(newObject(type(configurationClass), constructorParameters)));
    }

    private static JCTree.JCExpression executeRegisterMethod(Class<?> configurationClass) {
        String proxyClassName = computeCustomConfiguratorClassName(configurationClass);
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(classReference(configurationClass), method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                        .addArguments(classReference(proxyClassName), newReference(proxyClassName))
                        .apply())
                .apply();
    }

    public ImmutableArray<JCExpression> getConstructorParameters(ImmutableSet<Class<?>> configurationClasses, ImmutableArray<ExtractedProperty> properties) {
        ImmutableArray.Builder<JCTree.JCExpression> constructorParameters = immutableArrayBuilder();
        for (ExtractedProperty property : properties) {
            constructorParameters.add(createConstructorParameter(configurationClasses, property));
        }
        return constructorParameters.build();
    }

    private JCExpression createConstructorParameter(ImmutableSet<Class<?>> configurationClasses, ExtractedProperty property) {
        String source = CONFIGURE_METHOD.getParameters()[0].getName();
        Type type = property.type();
        if (type instanceof ParameterizedType) {
            if (isCollectionType(type)) {
                Type componentType = ((ParameterizedType) type).getActualTypeArguments()[0];
                if (configurationClasses.contains(componentType)) {
                    JCMethodInvocation proxy = method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                            .addArguments(classReference(computeCustomConfiguratorClassName(componentType)), newReference(computeCustomConfiguratorClassName(componentType)))
                            .apply();
                    MethodCaller propertyProvider = method(source, isListType(type) || isImmutableArrayType(type) ? GET_NESTED_LIST : GET_NESTED_SET)
                            .addArguments(literal(property.name()))
                            .addArguments(invokeReference(proxy, CONFIGURE_NAME));
                    if (!isImmutableType(type)) {
                        propertyProvider = method(propertyProvider.apply(), TO_MUTABLE);
                    }
                    return propertyProvider.apply();
                }
                MethodCaller propertyProvider = method(source, selectConfigurationSourceMethod(type)).addArgument(literal(property.name()));
                if (!isImmutableType(type)) {
                    propertyProvider = method(propertyProvider.apply(), TO_MUTABLE);
                }
                return propertyProvider.apply();
            }
        }
        if (type instanceof Class) {
            if (configurationClasses.contains(type)) {
                JCMethodInvocation proxy = method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                        .addArguments(classReference(computeCustomConfiguratorClassName(type)), newReference(computeCustomConfiguratorClassName(type)))
                        .apply();
                return method(source, GET_NESTED)
                        .addArguments(literal(property.name())).addArguments(invokeReference(proxy, CONFIGURE_NAME))
                        .apply();
            }
            return method(source, selectConfigurationSourceMethod(type))
                    .addArgument(literal(property.name()))
                    .apply();
        }
        throw new GenerationException(format(NOT_CONFIGURATION_SOURCE_TYPE, type));
    }

}
