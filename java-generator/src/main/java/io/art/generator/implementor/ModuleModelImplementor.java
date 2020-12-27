package io.art.generator.implementor;

import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.LoggingMessages.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.creator.provider.ProviderClassCreator.*;
import static io.art.generator.loader.ModelLoader.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.ClassGenerationService.*;
import static io.art.generator.service.ClassMutationService.*;
import static java.text.MessageFormat.*;

@UtilityClass
public class ModuleModelImplementor {
    public void implementModuleModel() {
        generateClass(createProviderClass(loadModel()), mainClass().getPackageName());

        NewMethod mainMethod = newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(MAIN_NAME)
                .returnType(VOID_TYPE)
                .parameter(newParameter(STRING_ARRAY_TYPE, MAIN_METHOD_ARGUMENTS_NAME))
                .addImport(classImport(MODULE_LAUNCHER_TYPE.getFullName()))
                .addImport(classImport(providerClassFullName()))
                .statement(() -> method(MODULE_LAUNCHER_TYPE, LAUNCH_NAME).addArguments(method(providerClassName(), PROVIDE_NAME).apply()).execute());

        replaceMethod(mainClass(), mainMethod);
        success(format(GENERATED_MAIN_METHOD, mainClass().getName()));
    }
}
