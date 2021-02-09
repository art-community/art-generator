package io.art.generator.model;

import io.art.core.collection.*;
import lombok.Builder;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.JavaDialect.JAVA;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.dialect;
import static io.art.generator.inspector.TypeInspector.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@Getter
@Builder(toBuilder = true)
@ToString
@Accessors(fluent = true)
public class ExtractedProperty {
    private static final Map<Type, ImmutableArray<ExtractedProperty>> CACHE = map();

    private final String name;
    private final Type owner;
    private final Type type;
    private final int index;
    private final boolean hasGetter;
    private final boolean hasSetter;
    private final boolean usedInConstructorArguments;
    private final boolean booleanProperty;
    private final String getterName;
    private final String setterName;

    public static ImmutableArray<ExtractedProperty> collectProperties(Type type) {
        Class<?> rawClass = extractClass(type);
        ImmutableArray<ExtractedProperty> cached = CACHE.get(rawClass);
        if (nonNull(cached)) return cached;
        ImmutableArray.Builder<ExtractedProperty> properties = immutableArrayBuilder();
        Type superType = rawClass.getGenericSuperclass();
        if (nonNull(superType)) properties.addAll(collectProperties(superType));
        int lastIndex = properties.size();
        Method[] declaredMethods = rawClass.getDeclaredMethods();
        Field[] declaredFields = rawClass.getDeclaredFields();
        for (int index = 0; index < declaredFields.length; index++) {
            Field field = declaredFields[index];
            Type fieldType = field.getGenericType();
            boolean booleanProperty = isBoolean(fieldType);
            String getterName = (isBoolean(fieldType) && dialect() == JAVA ? IS_NAME : GET_NAME) + capitalize(field.getName());
            String setterName = SET_NAME + capitalize(field.getName());
            boolean hasGetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(getterName));
            boolean hasSetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(setterName));
            boolean usedInConstructorArguments = hasConstructorArgument(type, field.getGenericType(), lastIndex + index);
            if (hasGetter || hasSetter || usedInConstructorArguments) {
                properties.add(ExtractedProperty.builder()
                        .name(field.getName())
                        .owner(type)
                        .type(field.getGenericType())
                        .index(lastIndex + index)
                        .hasGetter(hasGetter)
                        .hasSetter(hasSetter)
                        .getterName(getterName)
                        .setterName(setterName)
                        .usedInConstructorArguments(usedInConstructorArguments)
                        .booleanProperty(booleanProperty)
                        .build());
            }
        }
        cached = properties.build();
        CACHE.put(rawClass, cached);
        return cached;
    }
}
