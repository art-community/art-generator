package io.art.generator.implementor;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import io.art.configurator.custom.*;
import io.art.core.collection.*;
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
import static io.art.generator.service.NamingService.*;
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
        ImmutableSet<Class<?>> customConfigurations = model.getCustomConfigurations();
        customConfigurations.forEach(configuration -> customConfigurationsMethod.statement(() -> maker().Exec(executeRegisterMethod(configuration))));
        return customConfigurationsMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementConfigurationProxies(ConfiguratorModuleModel model) {
        ImmutableSet<Type> types = model.getCustomConfigurations().stream().filter(type -> isNull(getGeneratedConfigurationProxy(type))).collect(immutableSetCollector());
        ImmutableArray.Builder<NewClass> proxyClasses = immutableArrayBuilder();
        Map<Type, String> typeProxies = map();
        Type[] typesArray = types.toArray(new Type[0]);
        for (Type type : typesArray) {
            Class<?> typeAsClass = extractClass(type);
            long id = typeProxies
                    .keySet()
                    .stream()
                    .filter(modelType -> extractClass(modelType).getSimpleName().equals(typeAsClass.getSimpleName()))
                    .count();
            typeProxies.put(type, typeAsClass.getSimpleName() + PROXY_CLASS_SUFFIX + id);
            putGeneratedConfigurationProxy(type, typeAsClass.getSimpleName() + PROXY_CLASS_SUFFIX + id);
            info(format(GENERATED_CONFIGURATION_PROXY, type.getTypeName()));
        }
        for (Map.Entry<Type, String> entry : typeProxies.entrySet()) {
            Type configurationType = entry.getKey();
            if (!hasConstructorWithAllProperties(configurationType)) {
                //throw new ValidationException("");
            }
            TypeModel proxyType = type(parameterizedType(CustomConfigurationProxy.class, arrayOf(configurationType)));
            String proxyClassName = computeProxyClassName(configurationType);
            TypeModel type = type(entry.getKey());
            NewClass proxy = newClass()
                    .name(proxyClassName)
                    .modifiers(PRIVATE | STATIC)
                    .implement(proxyType)
                    .method(createConfigureMethod(configurationType, model.getCustomConfigurations()));
            if (!type.isJdk()) {
                proxy.addImport(classImport(type.getFullName()));
            }
            proxyClasses.add(proxy);
        }
        return proxyClasses.build();
    }


    private static NewMethod createConfigureMethod(Type configurationClass, ImmutableSet<Class<?>> configurationClasses) {
        String proxyClassName = computeProxyClassName(configurationClass);
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(extractClass(configurationClass));
        NewMethod method = overrideMethod(CONFIGURE_METHOD, type(configurationClass));
        ImmutableArray<JCExpression> constructorParameters = getConstructorParameters(configurationClasses, properties, proxyClassName);
        return method.statement(() -> returnExpression(newObject(type(configurationClass), constructorParameters)));
    }

    private static JCTree.JCExpression executeRegisterMethod(Class<?> configurationClass) {
        String proxyClassName = computeProxyClassName(configurationClass);
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(classReference(configurationClass), method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                        .addArguments(classReference(proxyClassName), newReference(proxyClassName))
                        .apply())
                .apply();
    }

    public ImmutableArray<JCExpression> getConstructorParameters(ImmutableSet<Class<?>> configurationClasses, ImmutableArray<ExtractedProperty> properties, String proxyClassName) {
        ImmutableArray.Builder<JCTree.JCExpression> constructorParameters = immutableArrayBuilder();
        for (ExtractedProperty property : properties) {
            constructorParameters.add(createConstructorParameter(configurationClasses, property));
        }
        return constructorParameters.build();
    }

    private JCExpression createConstructorParameter(ImmutableSet<Class<?>> configurationClasses, ExtractedProperty property) {
        String source = CONFIGURE_METHOD.getParameters()[0].getName();
        if (configurationClasses.contains(property.type())) {
            JCMethodInvocation proxy = method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                    .addArguments(classReference(computeProxyClassName(property.type())), newReference(computeProxyClassName(property.type())))
                    .apply();
            return method(source, GET_NESTED)
                    .addArguments(literal(property.name())).addArguments(invokeReference(proxy, CONFIGURE_NAME))
                    .apply();
        }
        return method(source, selectConfigurationSourceMethod(property.type()))
                .addArgument(literal(property.name()))
                .apply();
    }

    private String computeProxyClassName(Type proxyClass) {
        String proxy = getGeneratedConfigurationProxy(proxyClass);
        if (nonNull(proxy)) return proxy;
        putGeneratedConfigurationProxy(proxyClass, proxy = sequenceName(extractClass(proxyClass).getSimpleName() + PROXY_CLASS_SUFFIX));
        return proxy;
    }
}
