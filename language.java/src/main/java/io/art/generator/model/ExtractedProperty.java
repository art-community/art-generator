package io.art.generator.model;

import io.art.core.collection.*;
import lombok.Builder;
import lombok.*;
import lombok.experimental.*;

import java.lang.reflect.*;
import java.util.*;

import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.JavaDialect.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.type.TypeInspector.*;
import static java.util.Arrays.*;
import static java.util.Objects.*;

@Getter
@ToString
@Builder(toBuilder = true)
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExtractedProperty {
    private static final Map<Type, ImmutableArray<ExtractedProperty>> CACHE = map();

    @EqualsAndHashCode.Include
    private final String name;
    private final Type type;
    private final Type owner;
    private final int index;
    private final boolean hasGetter;
    private final boolean hasSetter;
    private final boolean byConstructor;
    private final boolean booleanProperty;
    private final String getterName;
    private final String setterName;

    public static ImmutableArray<ExtractedProperty> collectProperties(Type type) {
        Class<?> rawClass = extractClass(type);
        ImmutableArray<ExtractedProperty> cached = CACHE.get(rawClass);
        if (nonNull(cached)) return cached;
        ImmutableArray.Builder<ExtractedProperty> properties = immutableArrayBuilder();
        Type superType = rawClass.getGenericSuperclass();
        if (nonNull(superType)) {
            ImmutableArray<ExtractedProperty> parentProperties = collectProperties(superType).stream()
                    .map(property -> property
                            .toBuilder()
                            .byConstructor(property.byConstructor() && hasConstructorArgument(type, property))
                            .build())
                    .collect(immutableArrayCollector());
            properties.addAll(parentProperties);
        }
        int parentProperties = properties.size();
        int parentConstructorProperties = (int) properties.build()
                .stream()
                .filter(ExtractedProperty::byConstructor)
                .count();
        Method[] declaredMethods = rawClass.getDeclaredMethods();
        Field[] declaredFields = rawClass.getDeclaredFields();
        for (int index = 0; index < declaredFields.length; index++) {
            Field field = declaredFields[index];
            Type fieldType = field.getGenericType();
            boolean booleanProperty = isBoolean(fieldType);
            String getterName = (booleanProperty && dialect() == JAVA ? IS_NAME : GET_NAME) + capitalize(field.getName());
            String setterName = SET_NAME + capitalize(field.getName());
            boolean hasGetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(getterName));
            boolean hasSetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(setterName));
            boolean byConstructor = hasConstructorArgument(type, ExtractedProperty.builder()
                    .name(field.getName())
                    .type(fieldType)
                    .index(parentConstructorProperties + index)
                    .build());
            if (hasGetter || hasSetter || byConstructor) {
                properties.add(ExtractedProperty.builder()
                        .name(field.getName())
                        .owner(type)
                        .type(field.getGenericType())
                        .index(parentProperties + index)
                        .hasGetter(hasGetter)
                        .hasSetter(hasSetter)
                        .getterName(getterName)
                        .setterName(setterName)
                        .byConstructor(byConstructor)
                        .booleanProperty(booleanProperty)
                        .build());
            }
        }
        cached = properties.build();
        CACHE.put(rawClass, cached);
        return cached;
    }
}
