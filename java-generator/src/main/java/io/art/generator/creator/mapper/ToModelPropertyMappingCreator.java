package io.art.generator.creator.mapper;

import io.art.core.collection.*;
import lombok.*;
import static com.sun.tools.javac.tree.JCTree.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelPropertyMappingCreator {
    private final String entityName;

    JCMethodInvocation forProperty(String fieldName, Type fieldType) {
        boolean javaPrimitiveType = isJavaPrimitiveType(fieldType);
        ImmutableArray.Builder<JCExpression> arguments = immutableArrayBuilder();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(PRIMITIVE_ENUM_TYPE, primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        return mapMethodCall(javaPrimitiveType, arguments.build());
    }

    private JCMethodInvocation mapMethodCall(boolean javaPrimitiveType, ImmutableArray<JCExpression> arguments) {
        String method = javaPrimitiveType ? MAP_OR_DEFAULT_NAME : MAP_NAME;
        return method(method(entityName, MAPPING_METHOD_NAME).apply(), method).addArguments(arguments).apply();
    }
}
