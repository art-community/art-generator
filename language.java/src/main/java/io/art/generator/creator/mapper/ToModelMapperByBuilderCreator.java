package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import static io.art.core.wrapper.ExceptionWrapper.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.model.NewLambda.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.NamingService.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

public class ToModelMapperByBuilderCreator {
    private final static String entityName = sequenceName(ENTITY_NAME);
    private final static ToModelPropertyMappingCreator propertyMappingCreator = new ToModelPropertyMappingCreator(entityName);

    static JCExpression create(Class<?> type) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, entityName))
                .expression(() -> forProperties(type))
                .generate();
    }

    static JCExpression create(ParameterizedType type) {
        return newLambda()
                .parameter(newParameter(ENTITY_TYPE, entityName))
                .expression(() -> forProperties(type))
                .generate();
    }

    private static JCMethodInvocation forProperties(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = method(type(modelClass), BUILDER_METHOD_NAME).apply();
        Class<?> builderClass = wrapExceptionCall(() -> modelClass.getMethod(BUILDER_METHOD_NAME).getReturnType(), GenerationException::new);
        for (ExtractedProperty property : getProperties(modelClass)) {
            if (stream(builderClass.getMethods()).noneMatch(method -> method.getName().equals(property.name()))) {
                continue;
            }
            JCMethodInvocation propertyMapper = propertyMappingCreator.forProperty(property.name(), property.type());
            builderInvocation = method(builderInvocation, property.name()).addArguments(propertyMapper).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

    private static JCMethodInvocation forProperties(ParameterizedType parameterizedType) {
        JCMethodInvocation builderInvocation = method(type(parameterizedType), BUILDER_METHOD_NAME).apply();
        Class<?> modelClass = extractClass(parameterizedType);
        Class<?> builderClass = wrapExceptionCall(() -> modelClass.getMethod(BUILDER_METHOD_NAME).getReturnType(), GenerationException::new);
        for (ExtractedProperty property : getProperties(parameterizedType)) {
            if (stream(builderClass.getMethods()).noneMatch(method -> method.getName().equals(property.name()))) {
                continue;
            }
            JCMethodInvocation fieldMapping = propertyMappingCreator.forProperty(property.name(), extractGenericPropertyType(parameterizedType, property.type()));
            builderInvocation = method(builderInvocation, property.name()).addArguments(fieldMapping).apply();
        }
        return method(builderInvocation, BUILD_METHOD_NAME).apply();
    }

}
