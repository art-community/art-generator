package io.art.generator.creator.decorate;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.model.*;
import lombok.experimental.*;

import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
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
                .parameter(newParameter(MODULE_CONFIGURATOR_TYPE, CONFIGURATOR_NAME))
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> newVariable()
                        .name(MODULE_MODEL_NAME)
                        .type(MODULE_MODEL_TYPE)
                        .initializer(() -> method(CONFIGURATOR_NAME, CONFIGURE_NAME).apply()).generate())
                .statement(() -> newVariable()
                        .name(VALUE_MODEL_NAME)
                        .type(VALUE_MODULE_MODEL_TYPE)
                        .initializer(() -> method(MODULE_MODEL_NAME, GET_VALUE_MODEL_NAME).apply()).generate())
                .statement(() -> newVariable()
                        .name(CONFIGURATOR_MODEL_NAME)
                        .type(CONFIGURATOR_MODULE_MODEL_TYPE)
                        .initializer(() -> method(MODULE_MODEL_NAME, GET_CONFIGURATOR_MODEL_NAME).apply()).generate())
                .statement(() -> newVariable()
                        .name(SERVER_MODEL_NAME)
                        .type(SERVER_MODEL_TYPE)
                        .initializer(() -> method(MODULE_MODEL_NAME, GET_SERVER_MODEL_NAME).apply()).generate())
                .statement(() -> newVariable()
                        .name(COMMUNICATOR_MODEL_NAME)
                        .type(COMMUNICATOR_MODEL_TYPE)
                        .initializer(() -> method(MODULE_MODEL_NAME, GET_COMMUNICATOR_MODEL_NAME).apply()).generate())
                .statement(() -> newVariable()
                        .name(STORAGE_MODEL_NAME)
                        .type(STORAGE_MODULE_MODEL_TYPE)
                        .initializer(() -> method(MODULE_MODEL_NAME, GET_STORAGE_MODEL_NAME).apply()).generate())
                .statement(() -> returnExpression(customizeMethod()));
    }

    private JCMethodInvocation customizeMethod() {
        return method(MODULE_MODEL_NAME, CUSTOMIZE_NAME).addArguments(customizerLambda()).apply();
    }

    private JCLambda customizerLambda() {
        return newLambda()
                .parameter(newParameter(MODULE_CUSTOMIZER_TYPE, CUSTOMIZER_NAME))
                .expression(() ->
                        method(CUSTOMIZER_NAME, SERVER_NAME).addArguments(serverLambda())
                                .next(VALUE_NAME, value -> value.addArguments(valueLambda()))
                                .next(COMMUNICATOR_NAME, communicator -> communicator.addArguments(communicatorLambda()))
                                .next(CONFIGURATOR_NAME, configurator -> configurator.addArguments(configuratorLambda()))
                                .next(STORAGE_NAME, storage -> storage.addArguments(storageLambda()))
                                .apply()
                )
                .generate();
    }

    private JCLambda valueLambda() {
        return newLambda()
                .parameter(newParameter(VALUE_CUSTOMIZER_TYPE, VALUE_CUSTOMIZER_NAME))
                .expression(() -> method(VALUE_CUSTOMIZER_NAME, REGISTRY_NAME)
                        .addArguments(method(MAPPERS_NAME)
                                .addArguments(ident(VALUE_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda configuratorLambda() {
        return newLambda()
                .parameter(newParameter(CONFIGURATOR_CUSTOMIZER_TYPE, CONFIGURATOR_CUSTOMIZER_NAME))
                .expression(() -> method(CONFIGURATOR_CUSTOMIZER_NAME, REGISTRY_NAME)
                        .addArguments(method(CUSTOM_CONFIGURATIONS_NAME)
                                .addArguments(ident(CONFIGURATOR_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda serverLambda() {
        return newLambda()
                .parameter(newParameter(SERVER_CUSTOMIZER_TYPE, SERVER_NAME))
                .expression(() -> method(SERVER_NAME, REGISTRY_NAME)
                        .addArguments(method(SERVICES_NAME)
                                .addArguments(ident(SERVER_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda communicatorLambda() {
        return newLambda()
                .parameter(newParameter(COMMUNICATOR_CUSTOMIZER_TYPE, COMMUNICATOR_NAME))
                .expression(() -> method(COMMUNICATOR_NAME, REGISTRY_NAME)
                        .addArguments(method(COMMUNICATORS_NAME)
                                .addArguments(ident(COMMUNICATOR_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda storageLambda(){
        return newLambda()
                .parameter(newParameter(STORAGE_CUSTOMIZER_TYPE, STORAGE_NAME))
                .expression(() -> method(STORAGE_NAME, REGISTRY_NAME)
                        .addArguments(method(STORAGES_NAME)
                                .addArguments(ident(STORAGE_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }
}
