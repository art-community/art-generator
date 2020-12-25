package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.TypeModel.*;
import java.lang.reflect.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelMapperCreatorByInitializer {
    private final ToModelFieldMappingCreator fieldMappingCreator;

    JCMethodInvocation forProperties(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = method(type(modelClass), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(modelClass)) {
            String fieldName = field.getName();
            Type fieldType = field.getGenericType();
            JCMethodInvocation fieldMappers = fieldMappingCreator.forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(fieldMappers).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

    JCMethodInvocation forProperties(ParameterizedType parameterizedType, Class<?> rawClass) {
        JCMethodInvocation builderInvocation = method(type(parameterizedType), BUILDER_METHOD_NAME).apply();
        for (Field field : getProperties(rawClass)) {
            String fieldName = field.getName();
            Type fieldType = extractGenericPropertyType(parameterizedType, field.getGenericType());
            JCMethodInvocation fieldMapping = fieldMappingCreator.forField(fieldName, fieldType);
            builderInvocation = method(builderInvocation, fieldName).addArguments(fieldMapping).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }
}
