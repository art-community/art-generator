package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.*;
import io.art.core.lazy.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import lombok.*;
import static io.art.core.factory.ArrayFactory.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import java.lang.reflect.*;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelFieldMappingCreator {
    private final String entityName;

    JCTree.JCMethodInvocation forField(String fieldName, Type fieldType) {
        boolean javaPrimitiveType = isJavaPrimitiveType(fieldType);
        if (isLazyValue(fieldType)) {
            return forLazyField(fieldName, ((ParameterizedType) fieldType).getActualTypeArguments()[0], javaPrimitiveType);
        }
        List<JCTree.JCExpression> arguments = dynamicArray();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        return mappingMethod(javaPrimitiveType, arguments);
    }

    JCTree.JCMethodInvocation forLazyField(String fieldName, Type fieldType, boolean javaPrimitiveType) {
        List<JCTree.JCExpression> arguments = dynamicArray();
        arguments.add(literal(fieldName));
        if (javaPrimitiveType) {
            arguments.add(select(type(PrimitiveType.class), primitiveTypeFromJava(fieldType).name()));
        }
        arguments.add(toModelMapper(fieldType));
        JCTree.JCLambda lambda = newLambda().expression(() -> mappingMethod(javaPrimitiveType, arguments)).generate();
        return method(type(LazyValue.class), LAZY_NAME)
                .addArguments(lambda)
                .apply();
    }

    JCTree.JCMethodInvocation mappingMethod(boolean javaPrimitiveType, List<JCTree.JCExpression> arguments) {
        String method = javaPrimitiveType ? MAP_OR_DEFAULT_NAME : MAP_NAME;
        return method(method(entityName, MAPPING_METHOD_NAME).apply(), method)
                .addArguments(arguments)
                .apply();
    }
}
