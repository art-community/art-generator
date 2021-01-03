package io.art.generator.model;

import io.art.core.collection.*;
import lombok.Builder;
import lombok.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

@Getter
@Builder
@Accessors(fluent = true)
public class ExtractedProperty {
    private static final Map<Type, ImmutableArray<ExtractedProperty>> CACHE = new ConcurrentHashMap<>();

    private final String name;
    private final Type type;
    private final int index;
    private final boolean hasGetter;
    private final boolean hasSetter;
    private final boolean usedInConstructorArguments;
    private final boolean booleanProperty;
    private final String getterName;
    private final String setterName;

    public static ImmutableArray<ExtractedProperty> from(Class<?> type) {
        ImmutableArray<ExtractedProperty> cached = CACHE.get(type);
        if (nonNull(cached)) return cached;
        ImmutableArray.Builder<ExtractedProperty> properties = immutableArrayBuilder();
        Class<?> superclass = type.getSuperclass();
        if (nonNull(superclass)) properties.addAll(from(superclass));
        int lastIndex = properties.size();
        Method[] declaredMethods = type.getDeclaredMethods();
        Field[] declaredFields = type.getDeclaredFields();
        for (int index = 0; index < declaredFields.length; index++) {
            Field field = declaredFields[index];
            Type fieldType = field.getGenericType();
            boolean booleanProperty = isBoolean(fieldType);
            String getterName = booleanProperty ? IS_NAME + capitalize(field.getName()) : GET_NAME + capitalize(field.getName());
            String setterName = SET_NAME + capitalize(field.getName());
            boolean hasGetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(getterName));
            boolean hasSetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(setterName));
            boolean usedInConstructorArguments = hasConstructorArgument(type, field.getGenericType(), lastIndex + index);
            if (hasGetter || hasSetter || usedInConstructorArguments) {
                properties.add(ExtractedProperty.builder()
                        .name(field.getName())
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
        CACHE.put(type, cached);
        return cached;
    }
}
