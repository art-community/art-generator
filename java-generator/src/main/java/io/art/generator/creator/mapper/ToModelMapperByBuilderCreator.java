package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.model.*;
import lombok.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.constants.GeneratorConstants.TypeModels.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.NamingService.*;
import java.lang.reflect.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ToModelMapperByBuilderCreator {
    private final String entityName = sequenceName(ENTITY_NAME);
    private final ToModelPropertyMappingCreator propertyMappingCreator = new ToModelPropertyMappingCreator(entityName);

    JCExpression create(Class<?> type) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, entityName))
                .expression(() -> forProperties(type))
                .generate();
    }

    JCExpression create(ParameterizedType type) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, entityName))
                .expression(() -> forProperties(type, extractClass(type)))
                .generate();
    }

    private JCMethodInvocation forProperties(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = method(type(modelClass), BUILDER_METHOD_NAME).apply();
        for (ExtractedProperty property : getProperties(modelClass)) {
            JCMethodInvocation propertyMapper = propertyMappingCreator.forProperty(property.name(), property.type());
            builderInvocation = method(builderInvocation, property.name()).addArguments(propertyMapper).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

    private JCMethodInvocation forProperties(ParameterizedType parameterizedType, Class<?> rawClass) {
        JCMethodInvocation builderInvocation = method(type(parameterizedType), BUILDER_METHOD_NAME).apply();
        for (ExtractedProperty property : getProperties(rawClass)) {
            JCMethodInvocation fieldMapping = propertyMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type()));
            builderInvocation = method(builderInvocation, property.name()).addArguments(fieldMapping).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

}
