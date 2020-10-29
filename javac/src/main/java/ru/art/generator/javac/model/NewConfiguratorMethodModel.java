package ru.art.generator.javac.model;

import io.art.model.module.*;
import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static com.sun.tools.javac.util.List.*;
import static ru.art.generator.javac.constants.Constants.MethodNames.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.MakerService.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class NewConfiguratorMethodModel {
    private final ModuleModel model;

    public NewMethod generate() {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(CONFIGURE_METHOD_NAME)
                .returnType(type(ModuleModel.class.getName()))
                .statement(() -> maker().Return(maker().Apply(nil(), ident(MODULE_METHOD_NAME), nil())));
    }

    public static NewConfiguratorMethodModel configuratorMethod(ModuleModel model) {
        return new NewConfiguratorMethodModel(model);
    }
}
