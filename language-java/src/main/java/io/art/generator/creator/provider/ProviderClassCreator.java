package io.art.generator.creator.provider;

import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.model.modeling.module.*;
import java.lang.reflect.*;
import java.util.*;
import javax.lang.model.element.Modifier;
import lombok.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collector.SetCollector.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extensions.CollectionExtensions.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.collector.CommunicatorTypesCollector.*;
import static io.art.generator.collector.ServiceTypesCollector.*;
import static io.art.generator.collector.StorageTypesCollector.*;
import static io.art.generator.constants.Imports.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.decorate.DecorateMethodCreator.*;
import static io.art.generator.factory.ReferenceFactory.*;
import static io.art.generator.finder.ConfigureMethodFinder.*;
import static io.art.generator.implementor.CommunicatorModelImplementor.*;
import static io.art.generator.implementor.ConfiguratorModelImplementor.*;
import static io.art.generator.implementor.MappersImplementor.*;
import static io.art.generator.implementor.ServerModelImplementor.*;
import static io.art.generator.implementor.StorageModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static io.art.generator.type.TypeCollector.*;
import static java.util.Arrays.*;

@UtilityClass
public class ProviderClassCreator {
    public NewClass createProviderClass(ModuleModel model) {
        NewClass providerClass = newClass().modifiers(PUBLIC)
                .addImport(classImport(moduleClass().getFullName()))
                .name(moduleClass().getName() + PROVIDER_CLASS_SUFFIX);

        stream(IMPORTING_CLASSES).map(ImportModel::classImport).forEach(providerClass::addImport);

        Set<Type> types = combineToSet(
                model.getValueModel().getMappedTypes().stream().flatMap(type -> collectModelTypes(type).stream()).collect(setCollector()),
                collectServerTypes(model.getServerModel()).toMutable(),
                collectCommunicatorTypes(model.getCommunicatorModel()).toMutable(),
                collectStorageTypes(model.getStorageModel()).toMutable()
        );

        Set<NewMethod> providerMethods = set();

        if (!types.isEmpty()) providerMethods.add(implementMappersMethod(immutableSetOf(types)));
        ImmutableArray<NewClass> mappers = implementTypeMappers(immutableSetOf(types));
        success(GENERATED_MAPPERS);

        if (!model.getServerModel().getServices().isEmpty()) providerMethods.add(implementServicesMethod(model.getServerModel()));
        success(GENERATED_SERVICE_SPECIFICATIONS);

        if (!model.getCommunicatorModel().getCommunicators().isEmpty()) providerMethods.add(implementCommunicatorsMethod(model.getCommunicatorModel()));
        ImmutableArray<NewClass> communicatorProxies = implementCommunicatorProxies(model.getCommunicatorModel());
        success(GENERATED_COMMUNICATOR_PROXIES);

        if (!model.getConfiguratorModel().getCustomConfigurations().isEmpty()) providerMethods.add(implementCustomConfigurationsMethod(model.getConfiguratorModel()));
        ImmutableArray<NewClass> customConfigurators = implementCustomConfigurators(model.getConfiguratorModel());
        success(GENERATED_CUSTOM_CONFIGURATION_PROXIES);

        String classImport = letIfNotEmpty(
                moduleClass().getPackageName(),
                name -> name + DOT + STORAGE_NAME + DOT + moduleClass().getName() + STORAGE_INTERFACES_SUFFIX,
                STORAGE_NAME + DOT + moduleClass().getName() + STORAGE_INTERFACES_SUFFIX
        );
        providerClass.addImport(classImport(classImport));
        Map<String, NewClass> storageSpaces = implementStorageSpaces(model.getStorageModel());
        if (!model.getStorageModel().getStorages().isEmpty()) providerMethods.add(implementStoragesMethod(storageSpaces));
        success(GENERATED_STORAGE_SPACES);


        return providerClass
                .field(createModelField())
                .method(createProvideMethod())
                .method(createDecorateMethod(model))
                .methods(providerMethods)
                .inners(mappers)
                .inners(communicatorProxies)
                .inners(customConfigurators)
                .inners(storageSpaces.values().stream().collect(immutableArrayCollector()));
    }


    public NewClass createProviderStub(ExistedClass moduleClass) {
        return newClass().modifiers(PUBLIC)
                .addImports(stream(IMPORTING_CLASSES).map(ImportModel::classImport).collect(setCollector()))
                .addImport(classImport(moduleClass.getFullName()))
                .name(moduleClass.getName() + PROVIDER_CLASS_SUFFIX)
                .method(newMethod()
                        .name(PROVIDE_NAME)
                        .modifiers(PUBLIC | FINAL | STATIC)
                        .returnType(MODULE_MODEL_TYPE)
                        .statement(() -> throwException(NOT_IMPLEMENTED_EXCEPTION_TYPE, literal(PROVIDE_NAME))));
    }

    private NewMethod createProvideMethod() {
        return newMethod()
                .name(PROVIDE_NAME)
                .modifiers(PUBLIC | FINAL | STATIC)
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> returnVariable(MODEL_STATIC_NAME));
    }

    @SneakyThrows
    private NewField createModelField() {
        ExistedClass moduleClass = moduleClass();
        ExistedMethod configureMethod = findConfigureAnnotatedMethod(moduleClass);
        JCExpression reference = callOwner(moduleClass.asClass(), configureMethod.getDeclaration().getModifiers().getFlags().contains(Modifier.STATIC));
        JCExpression decorate = method(reference, configureMethod.getName()).apply();
        return newField()
                .name(MODEL_STATIC_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(MODULE_MODEL_TYPE)
                .initializer(() -> method(DECORATE_NAME).addArguments(decorate).apply());
    }
}
