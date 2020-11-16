package io.art.generator.determiner;

import com.google.common.collect.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.lang.Character.isUpperCase;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class MappingFieldsDeterminer {
    public ImmutableList<Field> getMappingFields(Class<?> mappingClass) {
        ImmutableList.Builder<Field> fields = ImmutableList.builder();
        try {
            for (Method method : mappingClass.getDeclaredMethods()) {
                String getterName = method.getName();
                if (getterName.startsWith(GET_PREFIX)) {
                    String fieldName = getterName.substring(GET_PREFIX.length());
                    String decapitalizedFieldName = isUpperCase(fieldName.charAt(0)) ? fieldName : decapitalize(fieldName);
                    Field[] declaredFields = mappingClass.getDeclaredFields();
                    stream(declaredFields).filter(field -> field.getName().equals(decapitalizedFieldName)).forEach(fields::add);
                }
            }
            return fields.build();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public boolean typeIsKnown(Type type) {
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            return typeIsKnown(rawType);
        }
        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                boolean foundByKnownTypes = KNOWN_TYPES
                        .stream()
                        .anyMatch(knownType -> knownType.isAssignableFrom(typeAsClass.getComponentType()));
                boolean foundByKnownStrictTypes = KNOWN_STRICT_TYPES
                        .stream()
                        .anyMatch(knownType -> knownType.equals(typeAsClass.getComponentType()));
                return foundByKnownStrictTypes || foundByKnownTypes;
            }
            boolean foundByKnownTypes = KNOWN_TYPES
                    .stream()
                    .anyMatch(knownType -> knownType.isAssignableFrom(typeAsClass));
            boolean foundByKnownStrictTypes = KNOWN_STRICT_TYPES
                    .stream()
                    .anyMatch(knownType -> knownType.equals(typeAsClass));
            return foundByKnownStrictTypes || foundByKnownTypes;
        }
        return false;
    }
}
