package io.art.generator.creator.decorate;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class DecorateMethodCreator {
    public NewMethod createDecorateMethod() {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(DECORATE_NAME)
                .parameter(newParameter(MODULE_MODEL_TYPE, MODEL_NAME))
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> maker().Return(createModelConfigureMethod()));
    }

    private JCMethodInvocation createModelConfigureMethod() {
        return applyMethod(MODEL_NAME, CONFIGURE_NAME, List.of(createConfiguratorLambda()));
    }

    private JCLambda createConfiguratorLambda() {
        return newLambda()
                .parameter(newParameter(CONFIGURATOR_MODEL_TYPE, CONFIGURATOR_NAME))
                .expression(DecorateMethodCreator::createConfiguratorLambdaBody)
                .generate();
    }

    private JCMethodInvocation createConfiguratorLambdaBody() {
        return applyMethod(CONFIGURATOR_NAME, SERVER_NAME, List.of(createServerLambda()));
    }

    private JCLambda createServerLambda() {
        return newLambda()
                .parameter(newParameter(SERVER_CONFIGURATOR_MODEL_TYPE, SERVER_NAME))
                .expression(() -> applyMethod(SERVER_NAME, REGISTRY_NAME, List.of(ident(SERVICES_REGISTRY_NAME))))
                .generate();
    }
}
