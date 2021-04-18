package io.art.generator.implementor;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import io.art.configurator.custom.*;
import io.art.configurator.model.*;
import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.model.implementation.configurator.*;
import lombok.experimental.*;

import java.lang.reflect.*;
import java.util.*;

import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.type.TypeCollector.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.configuration.CustomConfigurationCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static io.art.generator.type.TypeInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.text.MessageFormat.*;

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
        model.getCustomConfigurations()
                .stream()
                .map(CustomConfigurationModel::getConfigurationClass)
                .collect(immutableSetCollector())
                .forEach(type -> customConfigurationsMethod.statement(() -> maker().Exec(executeRegisterMethod(type))));
        return customConfigurationsMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementCustomConfigurators(ConfiguratorModuleModel model) {
        Set<Class<?>> types = set();
        for (CustomConfigurationModel configurationModel : model.getCustomConfigurations()) {
            for (Type type : collectModelTypes(configurationModel.getConfigurationClass())) {
                if (!isClass(type) || configurations().contains(type)) continue;
                configurations().compute(type);
                types.add((Class<?>) type);
            }
        }
        ImmutableArray.Builder<NewClass> configuratorClasses = immutableArrayBuilder();
        for (Class<?> type : types) {
            TypeModel configurationType = type(type);
            if (!hasConstructorWithAllProperties(configurationType.getType())) {
                String signature = formatSignature(configurationType.getType());
                throw new ValidationException(signature, format(NOT_FOUND_ALL_ARGS_CONSTRUCTOR, configurationType.getType()));
            }
            TypeModel configuratorType = type(parameterizedType(CustomConfigurator.class, configurationType.getType()));
            String configuratorName = configurations().get(configurationType.getType());
            NewClass configuratorClass = newClass()
                    .name(configuratorName)
                    .modifiers(PRIVATE | STATIC)
                    .implement(configuratorType)
                    .field(newField()
                            .name(INSTANCE_FIELD_NAME)
                            .type(configuratorType)
                            .modifiers(PRIVATE | STATIC)
                            .initializer(() -> newObject(configuratorName)))
                    .method(createConfigureMethod(type));
            if (!configurationType.isJdk()) {
                configuratorClass.addImport(classImport(configurationType.getFullName()));
            }
            configuratorClasses.add(configuratorClass);
            info(format(GENERATED_CONFIGURATION_PROXY, configurationType.getFullName()));
        }
        return configuratorClasses.build();
    }

    private static JCTree.JCExpression executeRegisterMethod(Class<?> configurationClass) {
        String className = configurations().get(configurationClass);
        JCMethodInvocation byClass = method(CONFIGURATOR_MODEL_NAME, BY_CLASS_NAME).addArguments(classReference(configurationClass)).apply();
        return method(REGISTRY_NAME, REGISTER_NAME)
                .addArguments(byClass, select(className, INSTANCE_FIELD_NAME))
                .apply();
    }
}
