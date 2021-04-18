package io.art.generator.implementor;

import io.art.generator.model.*;
import lombok.experimental.*;

import static com.sun.tools.javac.code.Flags.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.ClassMutationService.*;
import static java.text.MessageFormat.*;

@UtilityClass
public class MainMethodImplementor {
    public static void implementMainMethods() {
        for (ExistedClass moduleClass : moduleClasses().values()) {
            NewMethod mainMethod = newMethod()
                    .modifiers(PUBLIC | STATIC)
                    .name(MAIN_NAME)
                    .returnType(VOID_TYPE)
                    .parameter(newParameter(STRING_ARRAY_TYPE, MAIN_METHOD_ARGUMENTS_NAME))
                    .addImport(classImport(MODULE_LAUNCHER_TYPE.getFullName()))
                    .addImport(classImport(moduleClass.getPackageName() + DOT + moduleClass.getName() + PROVIDER_CLASS_SUFFIX))
                    .statement(() -> method(MODULE_LAUNCHER_TYPE, LAUNCH_NAME).addArguments(method(moduleClass.getName() + PROVIDER_CLASS_SUFFIX, PROVIDE_NAME).apply()).execute());

            replaceMethod(moduleClass, mainMethod);
            success(format(GENERATED_MAIN_METHOD, moduleClass.getFullName()));
        }
    }
}
