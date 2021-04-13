package io.art.generator.factory;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.handler.ExceptionHandler.*;
import static io.art.core.singleton.SingletonsRegistry.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.JavaDialect.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static java.util.Objects.*;

@UtilityClass
public class ReferenceFactory {
    public Object fieldOwner(Class<?> objectClass, boolean isStatic) {
        return isStatic
                ? null
                : dialect() == KOTLIN && nonNull(nullIfException(() -> objectClass.getField(INSTANCE_FIELD_NAME)))
                ? wrapExceptionCall(() -> objectClass.getField(INSTANCE_FIELD_NAME).get(null), GenerationException::new)
                : singleton(objectClass, () -> wrapExceptionCall(() -> objectClass.getConstructor().newInstance(), GenerationException::new));
    }

    public JCExpression callOwner(Class<?> objectClass, boolean isStatic) {
        return isStatic
                ? type(objectClass).generateBaseType()
                : dialect() == KOTLIN && nonNull(nullIfException(() -> objectClass.getField(INSTANCE_FIELD_NAME)))
                ? select(type(objectClass), INSTANCE_FIELD_NAME)
                : method(SINGLETON_REGISTRY_TYPE, SINGLETON_NAME)
                .addArguments(classReference(objectClass))
                .addArguments(newReference(type(objectClass)))
                .apply();
    }
}
