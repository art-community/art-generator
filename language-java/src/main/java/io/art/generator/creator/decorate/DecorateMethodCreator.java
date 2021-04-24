package io.art.generator.creator.decorate;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.model.*;
import io.art.model.implementation.module.*;
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
    public NewMethod createDecorateMethod(ModuleModel model) {
        return newMethod()
                .modifiers(PRIVATE | STATIC)
                .name(DECORATE_NAME)
                .parameter(newParameter(MODULE_CONFIGURATOR_TYPE, CONFIGURATOR_NAME))
                .returnType(MODULE_MODEL_TYPE)
                .statement(() -> newVariable()
                        .name(MODULE_MODEL_NAME)
                        .type(MODULE_MODEL_TYPE)
                        .initializer(() -> method(CONFIGURATOR_NAME, CONFIGURE_NAME).apply()).generate())
                .statement(() -> returnExpression(customizeMethod(model)));
    }

    private JCMethodInvocation customizeMethod(ModuleModel model) {
        return method(MODULE_MODEL_NAME, CUSTOMIZE_NAME).addArguments(customizerLambda(model)).apply();
    }

    private JCLambda customizerLambda(ModuleModel model) {
        return newLambda()
                .parameter(newParameter(MODULE_CUSTOMIZER_TYPE, CUSTOMIZER_NAME))
                .expression(() -> customizeModules(model))
                .generate();
    }

    private JCExpression customizeModules(ModuleModel model){
        JCExpression customization = ident(CUSTOMIZER_NAME);

        customization = model.getValueModel().getMappedTypes().isEmpty() ?
                customization : methodCall(customization, VALUE_NAME, valueLambda());

        if (!model.getValueModel().getMappedTypes().isEmpty())
            customization = methodCall(customization, VALUE_NAME, valueLambda());

        if (!model.getStorageModel().getStorages().isEmpty())
                customization = methodCall(customization, STORAGE_NAME, storageLambda());

        if (!model.getCommunicatorModel().getCommunicators().isEmpty())
                customization = methodCall(customization, COMMUNICATOR_NAME, communicatorLambda());

        if (!model.getServerModel().getServices().isEmpty())
                customization = methodCall(customization, SERVER_NAME, serverLambda());

        if (!model.getConfiguratorModel().getCustomConfigurations().isEmpty())
                customization = methodCall(customization, CONFIGURATOR_NAME, configuratorLambda());

        return customization;
    }

    private JCLambda valueLambda() {
        return newLambda()
                .parameter(newParameter(VALUE_CUSTOMIZER_TYPE, VALUE_CUSTOMIZER_NAME))
                .expression(() -> method(VALUE_CUSTOMIZER_NAME, REGISTRY_NAME)
                        .addArguments(method(MAPPERS_NAME)
                                .addArguments(methodCall(MODULE_MODEL_NAME, GET_VALUE_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda configuratorLambda() {
        return newLambda()
                .parameter(newParameter(CONFIGURATOR_CUSTOMIZER_TYPE, CONFIGURATOR_CUSTOMIZER_NAME))
                .expression(() -> method(CONFIGURATOR_CUSTOMIZER_NAME, REGISTRY_NAME)
                        .addArguments(method(CUSTOM_CONFIGURATIONS_NAME)
                                .addArguments(methodCall(MODULE_MODEL_NAME, GET_CONFIGURATOR_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda serverLambda() {
        return newLambda()
                .parameter(newParameter(SERVER_CUSTOMIZER_TYPE, SERVER_NAME))
                .expression(() -> method(SERVER_NAME, REGISTRY_NAME)
                        .addArguments(method(SERVICES_NAME)
                                .addArguments(methodCall(MODULE_MODEL_NAME, GET_SERVER_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }

    private JCLambda communicatorLambda() {
        return newLambda()
                .parameter(newParameter(COMMUNICATOR_CUSTOMIZER_TYPE, COMMUNICATOR_NAME))
                .expression(() -> method(COMMUNICATOR_NAME, REGISTRY_NAME)
                        .addArguments(method(COMMUNICATORS_NAME)
                                .addArguments(methodCall(MODULE_MODEL_NAME, GET_COMMUNICATOR_MODEL_NAME))
                                        .apply())
                        .apply())
                .generate();
    }

    private JCLambda storageLambda(){
        return newLambda()
                .parameter(newParameter(STORAGE_CUSTOMIZER_TYPE, STORAGE_NAME))
                .expression(() -> method(STORAGE_NAME, REGISTRY_NAME)
                        .addArguments(method(STORAGES_NAME)
                                .addArguments(methodCall(MODULE_MODEL_NAME, GET_STORAGE_MODEL_NAME))
                                .apply())
                        .apply())
                .generate();
    }
}
