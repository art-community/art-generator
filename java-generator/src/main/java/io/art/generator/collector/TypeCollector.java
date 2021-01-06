package io.art.generator.collector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.inspector.TypeInspector.*;
import static java.text.MessageFormat.*;
import java.lang.reflect.*;
import java.util.*;

public class TypeCollector {
    private final Set<Type> types = set();

    public static ImmutableSet<Type> collectModelTypes(Type root) {
        TypeCollector collector = new TypeCollector();
        collector.collectTypes(root);
        return immutableSetOf(collector.types);
    }

    private void collectTypes(Type type) {
        if (types.contains(type)) {
            return;
        }

        if (isClass(type)) {
            collectTypes((Class<?>) type);
            return;
        }

        if (isParametrized(type)) {
            collectTypes((ParameterizedType) type);
            return;
        }

        if (isGenericArray(type)) {
            collectTypes(((GenericArrayType) type));
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
            collectTypes(type.getComponentType());
            return;
        }

        types.add(type);

        for (ExtractedProperty property : getProperties(type)) {
            Type propertyType = property.type();

            if (types.contains(propertyType) || propertyType.equals(type)) {
                continue;
            }

            if (isClass(propertyType)) {
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

            if (isGenericArray(propertyType)) {
                collectTypes(((GenericArrayType) propertyType));
                continue;
            }

            if (isParametrized(propertyType)) {
                collectTypes((ParameterizedType) propertyType);
            }
        }
    }

    private void collectTypes(ParameterizedType type) {
        if (types.contains(type)) {
            return;
        }

        if (!isLibraryType(type)) {
            types.add(type);
        }

        Type[] arguments = type.getActualTypeArguments();
        for (Type argumentType : arguments) {
            if (types.contains(argumentType) || argumentType.equals(type)) {
                continue;
            }
            collectTypes(argumentType);
        }
    }

    private void collectTypes(GenericArrayType type) {
        if (types.contains(type)) {
            return;
        }

        collectTypes(type.getGenericComponentType());
    }
}
