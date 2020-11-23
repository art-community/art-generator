package io.art.generator.inspector;

import com.google.common.collect.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;

@UtilityClass
public class TypeInspector {
    public ImmutableList<Field> getProperties(Class<?> type) {
        ImmutableList.Builder<Field> fields = ImmutableList.builder();
        try {
            for (Field field : type.getDeclaredFields()) {
                String getterName = GET_PREFIX + capitalize(field.getName());
                boolean hasGetter = stream(type.getMethods()).anyMatch(method -> method.getName().equals(getterName));
                if (hasGetter) {
                    fields.add(field);
                }
            }
            return fields.build();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }

    public boolean isLibraryType(Type type) {
        if (type instanceof ParameterizedType) {
            return isLibraryType(extractClass((ParameterizedType) type));
        }
        if (type instanceof Class) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return isLibraryType(typeAsClass.getComponentType());
            }
            boolean jdkBaseType = LIBRARY_BASE_TYPES
                    .stream()
                    .anyMatch(matching -> matching.isAssignableFrom(typeAsClass));
            boolean jdkType = LIBRARY_TYPES
                    .stream()
                    .anyMatch(matching -> matching.equals(typeAsClass));
            return jdkType || jdkBaseType;
        }
        return false;
    }

    public boolean isPrimitiveType(Type type) {
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

    public boolean isCustomType(Type type) {
        return !isPrimitiveType(type);
    }

    public Class<?> extractClass(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new GenerationException(format(UNSUPPORTED_TYPE, rawType));
        }
        return (Class<?>) rawType;
    }

    public Class<?> extractClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            return extractClass((ParameterizedType) type);
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }

    public ImmutableSet<Type> collectCustomTypes(Type root) {
        ImmutableSet.Builder<Type> types = ImmutableSet.builder();

        if (root instanceof Class) {
            return collectCustomTypes((Class<?>) root);
        }

        if (root instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) root;
            stream(parameterizedType.getActualTypeArguments())
                    .filter(type -> !type.equals(root) && (!extractClass(type).isArray() || !root.equals(extractClass(type).getComponentType())))
                    .flatMap(type -> collectCustomTypes(type).stream())
                    .forEach(types::add);
            Class<?> rawClass = extractClass(parameterizedType.getRawType());
            if (!root.equals(rawClass)) {
                types.addAll(collectCustomTypes(rawClass));
            }
            return types.build();
        }

        return types.build();
    }

    public ImmutableSet<Type> collectCustomTypes(Class<?> root) {
        ImmutableSet.Builder<Type> types = ImmutableSet.builder();
        if (isLibraryType(root)) {
            return types.build();
        }
        if (root.isArray()) {
            return collectCustomTypes(root.getComponentType());
        }
        types.add(root);
        getProperties(root)
                .stream()
                .map(Field::getGenericType)
                .filter(type -> !type.equals(root) && (!extractClass(type).isArray() || !root.equals(extractClass(type).getComponentType())))
                .flatMap(type -> collectCustomTypes(type).stream())
                .forEach(types::add);
        return types.build();
    }
}
