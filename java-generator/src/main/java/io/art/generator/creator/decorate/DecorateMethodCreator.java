package io.art.generator.creator.decorate;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.ModelMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.NewVariable.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class DecorateMethodCreator {
    public NewMethod createDecorateMethod() {
        return newMethod()
                .modifiers(PRIVATE | STATIC)
                .name(DECORATE_NAME)
                .parameter(newParameter(MODULE_MODELER_TYPE, MODELER_NAME))
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> newVariable()
                        .name(MODULE_MODEL_NAME)
                        .type(MODULE_MODEL_TYPE)
                        .initializer(() -> applyMethod(MODELER_NAME, APPLY_NAME)).generate())
                .statement(() -> newVariable()
                        .name(SERVER_MODEL_NAME)
                        .type(SERVER_MODEL_TYPE)
                        .initializer(() -> applyMethod(MODULE_MODEL_NAME, GET_SERVER_MODEL)).generate())
                .statement(() -> returnMethodCall(customizerMethod()));
    }

    private JCMethodInvocation customizerMethod() {
        return applyMethod(MODULE_MODEL_NAME, CUSTOMIZE_NAME, List.of(customizerLambda()));
    }

    private JCLambda customizerLambda() {
        return newLambda()
                .parameter(newParameter(MODULE_CUSTOMIZER_TYPE, CUSTOMIZER_NAME))
                .expression(() -> applyMethod(CUSTOMIZER_NAME, SERVER_NAME, List.of(serverLambda())))
                .generate();
    }

    private JCLambda serverLambda() {
        return newLambda()
                .parameter(newParameter(SERVER_CUSTOMIZER_TYPE, SERVER_NAME))
                .expression(() -> applyMethod(SERVER_NAME, REGISTRY_NAME, List.of(applyMethod(SERVICES_NAME, List.of(ident(SERVER_MODEL_NAME))))))
                .generate();
    }
}
