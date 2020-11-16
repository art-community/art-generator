package io.art.generator.determiner;

import com.google.common.collect.*;
import io.art.generator.exception.*;
import io.art.value.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.model.TypeModel.type;
import static io.art.generator.service.JavacService.select;
import static java.lang.Character.isUpperCase;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class MappingFieldsDeterminer {
    public ImmutableList<Field> getMappingFields(Class<?> type) {
        ImmutableList.Builder<Field> fields = ImmutableList.builder();
        try {
            for (Method method : type.getDeclaredMethods()) {
                String getterName = method.getName();
                if (getterName.startsWith(GET_PREFIX)) {
                    String fieldName = getterName.substring(GET_PREFIX.length());
                    String decapitalizedFieldName = isUpperCase(fieldName.charAt(0)) ? fieldName : decapitalize(fieldName);
                    Field[] declaredFields = type.getDeclaredFields();
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
    
    public boolean typeIsPrimitive(Type type) {
        if (String.class.equals(type)) {
            return true;
        }
        if (char.class.equals(type) || Character.class.equals(type)) {
            return true;
        }
        if (int.class.equals(type) || Integer.class.equals(type)) {
            return true;
        }
        if (short.class.equals(type) || Short.class.equals(type)) {
            return true;
        }
        if (long.class.equals(type) || Long.class.equals(type)) {
            return true;
        }
        if (boolean.class.equals(type) || Boolean.class.equals(type)) {
            return true;
        }
        if (double.class.equals(type) || Double.class.equals(type)) {
            return true;
        }
        if (byte.class.equals(type) || Byte.class.equals(type)) {
            return true;
        }
        return float.class.equals(type) || Float.class.equals(type);
    }
}
