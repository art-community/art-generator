package io.art.generator.inspector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.core.factory.SetFactory.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;

@UtilityClass
public class TypeInspector {
    public ImmutableArray<Field> getProperties(Class<?> type) {
        ImmutableArray.Builder<Field> fields = immutableArrayBuilder();
        try {
            for (Field field : type.getDeclaredFields()) {
                Type fieldType = field.getGenericType();
                String getterName = isBoolean(fieldType) ? IS_PREFIX + capitalize(field.getName()) : GET_PREFIX + capitalize(field.getName());
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

    public boolean isBoolean(Type fieldType) {
        return fieldType == boolean.class || fieldType == Boolean.class;
    }

    public boolean isLibraryType(Type type) {
        if (type instanceof ParameterizedType) {
            return isLibraryType(extractClass((ParameterizedType) type));
        }
        if (type instanceof GenericArrayType) {
            return isLibraryType(extractClass((GenericArrayType) type));
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

    public boolean isJavaPrimitiveType(Type type) {
        if (char.class.equals(type)) {
            return true;
        }
        if (int.class.equals(type)) {
            return true;
        }
        if (short.class.equals(type)) {
            return true;
        }
        if (long.class.equals(type)) {
            return true;
        }
        if (boolean.class.equals(type)) {
            return true;
        }
        if (double.class.equals(type)) {
            return true;
        }
        if (byte.class.equals(type)) {
            return true;
        }
        return float.class.equals(type);
    }

    public boolean isCustomType(Type type) {
        return !isPrimitiveType(type);
    }

    public Class<?> extractClass(ParameterizedType parameterizedType) {
        return extractClass(parameterizedType.getRawType());
    }

    public Class<?> extractClass(GenericArrayType genericArrayType) {
        return extractClass(genericArrayType.getGenericComponentType());
    }

    public Class<?> extractClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            return extractClass((ParameterizedType) type);
        }
        if (type instanceof GenericArrayType) {
            return extractClass((GenericArrayType) type);
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }

    public ImmutableSet<Type> collectCustomTypes(Type root) {
        if (root instanceof Class) {
            return collectCustomTypes((Class<?>) root);
        }

        if (root instanceof ParameterizedType) {
            return collectCustomTypes(root, (ParameterizedType) root);
        }

        if (root instanceof GenericArrayType) {
            return immutableSetOf(((GenericArrayType) root).getGenericComponentType());
        }

        throw new GenerationException(format(UNSUPPORTED_TYPE, root));
    }

    public ImmutableSet<Type> collectCustomTypes(Class<?> root) {
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        if (isLibraryType(root)) {
            return types.build();
        }
        if (root.isArray()) {
            return collectCustomTypes(root.getComponentType());
        }
        types.add(root);
        for (Field property : getProperties(root)) {
            Type type = property.getGenericType();

            if (type.equals(root)) {
                continue;
            }

            if (type instanceof Class) {
                Class<?> typeAsClass = (Class<?>) type;
                if (typeAsClass.isArray() && root.equals(typeAsClass.getComponentType())) {
                    continue;
                }
                types.addAll(collectCustomTypes(type));
                continue;
            }

            if (type instanceof ParameterizedType) {
                types.addAll(collectCustomTypes(root, (ParameterizedType) type));
            }
        }
        return types.build();
    }

    private static ImmutableSet<Type> collectCustomTypes(Type root, ParameterizedType parameterizedType) {
        ImmutableSet.Builder<Type> types = immutableSetBuilder();
        Class<?> rawClass = extractClass(parameterizedType.getRawType());
        if (!root.equals(rawClass)) {
            types.addAll(collectCustomTypes(rawClass));
        }
        Type[] arguments = parameterizedType.getActualTypeArguments();
        for (Type argument : arguments) {
            if (root.equals(argument)) {
                continue;
            }
            if (argument instanceof Class) {
                Class<?> argumentAsClass = (Class<?>) argument;
                if (argumentAsClass.isArray() && root.equals(argumentAsClass.getComponentType())) {
                    continue;
                }
                types.addAll(collectCustomTypes(argumentAsClass));
                continue;
            }
            if (argument instanceof ParameterizedType) {
                types.addAll(collectCustomTypes(root, (ParameterizedType) argument));
            }
        }
        return types.build();
    }

}
