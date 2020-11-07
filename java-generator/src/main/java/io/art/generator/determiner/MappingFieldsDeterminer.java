package io.art.generator.determiner;

import com.google.common.collect.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import java.lang.reflect.*;

@UtilityClass
public class MappingFieldsDeterminer {
    public boolean typeIsKnown(Type type) {
        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;

            boolean foundByKnownTypes = KNOWN_TYPES
                    .stream()
                    .anyMatch(knownType -> typeAsClass.isArray() ? knownType.isAssignableFrom(typeAsClass.getComponentType()) : knownType.isAssignableFrom(typeAsClass));
            boolean foundByKnownStrictTypes = KNOWN_STRICT_TYPES
                    .stream()
                    .anyMatch(knownType -> typeAsClass.isArray() ? knownType.equals(typeAsClass.getComponentType()) : knownType.equals(typeAsClass));
            return foundByKnownStrictTypes || foundByKnownTypes;
        }
        return false;
    }

    public boolean typeIsUnknown(Type type) {
        return !typeIsKnown(type);
    }

    public ImmutableList<Field> getMappingFields(Class<?> modelClass) {
        ImmutableList.Builder<Field> types = ImmutableList.builder();
        try {
            for (Method method : modelClass.getDeclaredMethods()) {
                String getterName = method.getName();
                if (getterName.startsWith(GET_PREFIX)) {
                    String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                    types.add(modelClass.getDeclaredField(fieldName));
                }
            }
            return types.build();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public ImmutableSet<Field> collectUnknownTypeFieldsRecursive(Class<?> modelClass) {
        ImmutableSet.Builder<Field> allFields = ImmutableSet.builder();
        for (Field field : getMappingFields(modelClass)) {
            Class<?> fieldType = field.getType();
            if (typeIsUnknown(fieldType) && !fieldType.equals(modelClass)) {
                allFields.add(field);
                allFields.addAll(collectUnknownTypeFieldsRecursive(fieldType));
            }
        }
        return allFields.build();
    }
}
