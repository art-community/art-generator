package io.art.generator.javac.model;

import io.art.core.constants.*;
import io.art.model.module.*;
import io.art.model.server.*;
import io.art.server.specification.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.javac.constants.GeneratorConstants.MethodNames.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.model.ImportModel.*;
import static io.art.generator.javac.model.NewMethod.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.MakerService.*;

@UtilityClass
public class NewConfigureMethod {
    public static NewMethod configureMethod(ModuleModel model) {
        return newMethod()
                .modifiers(PUBLIC | STATIC)
                .name(CONFIGURE_METHOD_NAME)
                .returnType(type(ModuleModel.class.getName()))
                .addClassImport(importClass(ServerModel.class.getName()))
                .addClassImport(importClass(ServiceSpecification.class.getName()))
                .addClassImport(importClass(ServiceMethodSpecification.class.getName()))
                .addClassImport(importClass(MethodProcessingMode.class.getName()))
                .statement(() -> maker().Return(applyMethod(MODULE_METHOD_NAME)));
    }
}
