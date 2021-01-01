package io.art.generator.creator.provider;

import io.art.core.collection.*;
import io.art.generator.caller.*;
import io.art.generator.model.*;
import io.art.model.implementation.module.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.collector.ServiceTypesCollector.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.LoggingMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.decorate.DecorateMethodCreator.*;
import static io.art.generator.implementor.CommunicatorModelImplementor.*;
import static io.art.generator.implementor.MappersImplementor.*;
import static io.art.generator.implementor.ServerModelImplementor.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Arrays.*;
import javax.lang.model.element.*;

@UtilityClass
public class ProviderClassCreator {
    public NewClass createProviderClass(ModuleModel model) {
        NewClass providerClass = newClass().modifiers(PUBLIC).name(providerClassName());

        stream(IMPORTING_CLASSES).map(ImportModel::classImport).forEach(providerClass::addImport);

        ImmutableArray<NewClass> mappers = implementTypeMappers(collectModelTypes(model.getServerModel()));
        success(GENERATED_MAPPERS);

        NewMethod serverModel = implementServerModel(model.getServerModel());
        success(GENERATED_SERVICE_SPECIFICATIONS);

        NewMethod communicatorModel = implementCommunicator(model.getCommunicatorModel());
        success(GENERATED_COMMUNICATOR_PROXIES);

        return providerClass
                .field(createModelField())
                .method(createModelMethod())
                .method(createDecorateMethod())
                .inners(mappers)
                .method(serverModel)
                .method(communicatorModel);
    }

    private NewMethod createModelMethod() {
        return newMethod()
                .name(PROVIDE_NAME)
                .modifiers(PUBLIC | FINAL | STATIC)
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> returnVariable(MODEL_STATIC_NAME));
    }

    private NewField createModelField() {
        MethodCaller configureStatic = method(type(mainClass().asClass()), configureMethod().getName());

        MethodCaller singletonMethod = method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                .addArgument(classReference(mainClass().asClass()))
                .addArgument(newReference(type(mainClass().asClass())));
        MethodCaller configureSingleton = method(singletonMethod.apply(), configureMethod().getName());

        return newField()
                .name(MODEL_STATIC_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(MODULE_MODEL_TYPE)
                .initializer(() -> method(DECORATE_NAME)
                        .addArguments(configureMethod().getDeclaration().getModifiers().getFlags().contains(Modifier.STATIC)
                                ? configureStatic.apply()
                                : configureSingleton.apply())
                        .apply());
    }
}
