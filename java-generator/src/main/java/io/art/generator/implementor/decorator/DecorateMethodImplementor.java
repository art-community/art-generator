package io.art.generator.implementor.decorator;

import com.sun.tools.javac.util.*;
import io.art.generator.model.*;
import io.art.model.configurator.*;
import io.art.model.module.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.constants.GeneratorConstants.Names.PROVIDER_CLASS_NAME_SUFFIX;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;

@UtilityClass
public class DecorateMethodImplementor {
    public static NewMethod implementDecorateMethod() {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name("decorate")
                .parameter(newParameter(type(ModuleModel.class), "model"))
                .returnType(type(ModuleModel.class.getName()))
                .statement(() -> maker().Return(
                        applyMethod("model", "configure", List.of(
                                newLambda()
                                        .parameter(newParameter(type(ConfiguratorModel.class), "configurator"))
                                        .expression(() ->
                                                applyMethod(applyMethod("configurator",
                                                        "value", List.of(
                                                                newLambda()
                                                                        .parameter(newParameter(type(ValueConfiguratorModel.class), "value"))
                                                                        .expression(() -> applyMethod("value", "registry", List.of(applyMethod(
                                                                                mainClass().getName() + PROVIDER_CLASS_NAME_SUFFIX,
                                                                                "mappers")))
                                                                        )
                                                                        .generate()
                                                        )),
                                                        "server", List.of(
                                                                newLambda()
                                                                        .parameter(newParameter(type(ServerConfiguratorModel.class), "server"))
                                                                        .expression(() -> applyMethod("server", "registry", List.of(applyMethod(
                                                                                mainClass().getName() + PROVIDER_CLASS_NAME_SUFFIX,
                                                                                "services")))
                                                                        )
                                                                        .generate()
                                                        )))
                                        .generate()
                                )
                        ))
                );
    }
}
