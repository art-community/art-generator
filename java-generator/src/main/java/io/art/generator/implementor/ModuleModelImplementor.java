package io.art.generator.implementor;

import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import io.art.launcher.*;
import io.art.model.module.*;
import io.art.server.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.decorate.DecorateMethodCreator.*;
import static io.art.generator.implementor.MappersImplementor.*;
import static io.art.generator.implementor.ServerModelImplementor.*;
import static io.art.generator.loader.ModelLoader.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.ClassMutationService.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Arrays.*;

@UtilityClass
public class ModuleModelImplementor {
    public void implementModuleModel() {
        ModuleModel model = loadModel();
        String providerClassName = mainClass().getName() + PROVIDER_CLASS_NAME_SUFFIX;
        NewClass providerClass = newClass().modifiers(PUBLIC | STATIC).name(providerClassName);

        stream(IMPORTING_CLASSES)
                .map(ImportModel::classImport)
                .forEach(providerClass::addImport);

        NewField modelField = newField()
                .name(MODEL_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyMethod(DECORATE_NAME, List.of(applyClassMethod(type(mainClass().asClass()), CONFIGURE_NAME))));

        NewField servicesRegistryField = newField()
                .name(SERVICES_REGISTRY_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ServiceSpecificationRegistry.class))
                .initializer(() -> applyMethod(SERVICES_NAME));

        implementCustomTypeMappers(collectCustomTypes(model.getServerModel())).forEach(mapping -> replaceInnerClass(mainClass(), mapping));

        NewMethod mainMethod = newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(MAIN_NAME)
                .returnType(type(void.class))
                .parameter(newParameter(type(String[].class), MAIN_METHOD_ARGUMENTS_NAME))
                .statement(() -> maker().Exec(applyClassMethod(type(ModuleLauncher.class), LAUNCH_NAME, List.of(select(providerClassName, MODEL_NAME)))));

        providerClass
                .field(MODEL_NAME, modelField)
                .field(SERVICES_REGISTRY_NAME, servicesRegistryField)
                .method(SERVICES_NAME, implementServerModel(model.getServerModel()))
                .method(DECORATE_NAME, createDecorateMethod());

        replaceMethod(mainClass(), mainMethod);
        replaceInnerClass(mainClass(), providerClass);
    }
}
