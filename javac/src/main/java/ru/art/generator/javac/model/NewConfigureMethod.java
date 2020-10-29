package ru.art.generator.javac.model;

import io.art.model.module.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.util.List.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MethodNames.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.service.MakerService.*;

@UtilityClass
public class NewConfigureMethod {
    public static NewMethod configureMethod(ModuleModel model) {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(CONFIGURE_METHOD_NAME)
                .returnType(TypeModel.type(ModuleModel.class.getName()))
                .statement(() -> maker().Return(maker().Apply(nil(), ident(MODULE_METHOD_NAME), nil())));
    }
}
