package io.art.generator.creator.provider;

import io.art.generator.model.*;
import io.art.model.implementation.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.collector.ServiceTypesCollector.*;
import static io.art.generator.constants.GeneratorConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.decorate.DecorateMethodCreator.*;
import static io.art.generator.implementor.MappersImplementor.*;
import static io.art.generator.implementor.ServerModelImplementor.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Arrays.*;

@UtilityClass
public class ProviderClassCreator {
    public NewClass createProviderClass(ModuleModel model) {
        NewClass providerClass = newClass().modifiers(PUBLIC).name(providerClassName());

        stream(IMPORTING_CLASSES).map(ImportModel::classImport).forEach(providerClass::addImport);

        NewField modelField = newField()
                .name(MODEL_STATIC_NAME)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(MODULE_MODEL_TYPE)
                .initializer(() -> method(DECORATE_NAME)
                        .addArguments(method(type(mainClass().asClass()), modelMethod().getName()).apply())
                        .apply());

        NewMethod modelMethod = newMethod()
                .name(PROVIDE_NAME)
                .modifiers(PUBLIC | FINAL | STATIC)
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> returnVariable(MODEL_STATIC_NAME));

        return providerClass
                .field(modelField)
                .method(modelMethod)
                .method(createDecorateMethod())
                .inners(implementTypeMappers(collectModelTypes(model.getServerModel())))
                .method(implementServerModel(model.getServerModel()));
    }
}
