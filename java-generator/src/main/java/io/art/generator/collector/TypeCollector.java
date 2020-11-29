package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

public class TypeCollector {
    private final Set<Type> types = setOf();

    public static ImmutableSet<Type> collectCustomTypes(Type root) {
        TypeCollector collector = new TypeCollector();
        collector.collectTypes(root);
        return immutableSetOf(collector.types);
    }

    private void collectTypes(Type type) {
        if (types.contains(type)) {
            return;
        }

        if (isLibraryType(type)) {
            return;
        }

        if (type instanceof Class) {
            collectTypes((Class<?>) type);
            return;
        }

        if (type instanceof ParameterizedType) {
            collectTypes((ParameterizedType) type);
            return;
        }

        if (type instanceof GenericArrayType) {
            collectTypes(((GenericArrayType) type).getGenericComponentType());
            return;
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }

    private void collectTypes(Class<?> type) {
        if (types.contains(type)) {
            return;
        }

        if (isLibraryType(type)) {
            return;
        }

        if (type.isArray()) {
            return;
        }

        types.add(type);

        for (Field property : getProperties(type)) {
            Type propertyType = property.getGenericType();

            if (types.contains(propertyType) || propertyType.equals(type)) {
                continue;
            }

            if (propertyType instanceof Class) {
                Class<?> propertyTypeAsClass = (Class<?>) propertyType;
                if (propertyTypeAsClass.isArray()) {
                    Class<?> componentType = propertyTypeAsClass.getComponentType();
                    if (type.equals(componentType)) {
                        continue;
                    }
                    collectTypes(componentType);
                    continue;
                }
                collectTypes(propertyType);
                continue;
            }

            if (propertyType instanceof GenericArrayType) {
                collectTypes(((GenericArrayType) propertyType).getGenericComponentType());
                continue;
            }

            if (propertyType instanceof ParameterizedType) {
                collectTypes((ParameterizedType) propertyType);
            }
        }
    }

    private void collectTypes(ParameterizedType type) {
        if (isLibraryType(type)) {
            return;
        }

        Class<?> rawClass = extractClass(type.getRawType());
        if (types.contains(type) || types.contains(rawClass)) {
            return;
        }

        types.add(type);
        Type[] arguments = type.getActualTypeArguments();
        for (Type argumentType : arguments) {
            if (types.contains(argumentType) || argumentType.equals(type)) {
                continue;
            }
            if (argumentType instanceof Class) {
                Class<?> argumentAsClass = (Class<?>) argumentType;
                if (argumentAsClass.isArray()) {
                    Class<?> componentType = argumentAsClass.getComponentType();
                    collectTypes(componentType);
                    continue;
                }
                collectTypes(argumentAsClass);
                continue;
            }
            if (argumentType instanceof GenericArrayType) {
                collectTypes(((GenericArrayType) argumentType).getGenericComponentType());
                continue;
            }
            if (argumentType instanceof ParameterizedType) {
                collectTypes((ParameterizedType) argumentType);
            }
        }
    }
}
