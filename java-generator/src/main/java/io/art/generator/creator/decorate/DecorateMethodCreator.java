package io.art.generator.creator.decorate;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import io.art.model.configurator.*;
import io.art.model.module.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class DecorateMethodCreator {
    public NewMethod createDecorateMethod() {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(DECORATE_NAME)
                .parameter(newParameter(type(ModuleModel.class), MODEL_NAME))
                .returnType(type(ModuleModel.class.getName()))
                .statement(() -> maker().Return(createModelConfigureMethod(mainClass().getName() + PROVIDER_CLASS_NAME_SUFFIX)));
    }

    private JCMethodInvocation createModelConfigureMethod(String providerClassName) {
        return applyMethod(MODEL_NAME, CONFIGURE_NAME, List.of(createConfiguratorLambda(providerClassName)));
    }

    private JCTree.JCLambda createConfiguratorLambda(String providerClassName) {
        return newLambda()
                .parameter(newParameter(type(ConfiguratorModel.class), CONFIGURATOR_NAME))
                .expression(() -> applyMethod(
                        applyMethod(CONFIGURATOR_NAME, VALUE_NAME, List.of(createValueLambda(providerClassName))),
                        SERVER_NAME, List.of(createServerLambda(providerClassName)))
                )
                .generate();
    }

    private JCTree.JCLambda createValueLambda(String providerClassName) {
        return newLambda()
                .parameter(newParameter(type(ValueConfiguratorModel.class), VALUE_NAME))
                .expression(() -> applyMethod(VALUE_NAME, REGISTRY_NAME, List.of(ident(MAPPERS_REGISTRY_NAME))))
                .generate();
    }

    private JCTree.JCLambda createServerLambda(String providerClassName) {
        return newLambda()
                .parameter(newParameter(type(ServerConfiguratorModel.class), SERVER_NAME))
                .expression(() -> applyMethod(SERVER_NAME, REGISTRY_NAME, List.of(applyMethod(providerClassName, SERVICES_NAME))))
                .generate();
    }
}
