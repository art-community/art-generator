package io.art.generator.inspector;

import io.art.core.collection.*;
import io.art.core.lazy.*;
import io.art.generator.exception.*;
import io.art.value.constants.ValueConstants.ValueType.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.GeneratorConstants.ExceptionMessages.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.reflection.GenericArrayTypeImplementation.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.BOOL;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.BYTE;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.DOUBLE;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.FLOAT;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.INT;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.LONG;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.STRING;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;

@UtilityClass
public class TypeInspector {
    public ImmutableArray<Field> getProperties(Class<?> type) {
        ImmutableArray.Builder<Field> fields = immutableArrayBuilder();
        try {
            Method[] declaredMethods = type.getDeclaredMethods();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                Type fieldType = field.getGenericType();
                String getterName = isBoolean(fieldType) ? IS_NAME + capitalize(field.getName()) : GET_NAME + capitalize(field.getName());
                String setterName = SET_NAME + capitalize(field.getName());
                boolean hasGetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(getterName));
                boolean hasSetter = stream(declaredMethods).anyMatch(method -> method.getName().equals(setterName));
                boolean hasConstructorArgument = hasConstructorArgument(type, field.getName());
                if (hasGetter || hasSetter || hasConstructorArgument) fields.add(field);
            }
            return fields.build();
        } catch (Throwable throwable) {
            throw new GenerationException(throwable);
        }
    }


    public boolean isBoolean(Type fieldType) {
        return fieldType == boolean.class;
    }

    public boolean isLazyValue(Type fieldType) {
        return LazyValue.class == extractClass(fieldType);
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
            boolean libraryBasedType = LIBRARY_BASED_TYPES
                    .stream()
                    .anyMatch(matching -> matching.isAssignableFrom(typeAsClass));
            boolean libraryType = LIBRARY_TYPES
                    .stream()
                    .anyMatch(matching -> matching.equals(typeAsClass));
            return libraryType || libraryBasedType;
        }
        return false;
    }


    public boolean isCollectionType(Class<?> type) {
        if (List.class.isAssignableFrom(type)) {
            return true;
        }
        if (Queue.class.isAssignableFrom(type)) {
            return true;
        }
        if (Deque.class.isAssignableFrom(type)) {
            return true;
        }
        if (Set.class.isAssignableFrom(type)) {
            return true;
        }
        return Collection.class.isAssignableFrom(type);
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
        if (float.class.equals(type) || Float.class.equals(type)) {
            return true;
        }
        if (UUID.class.equals(type)) {
            return true;
        }
        if (LocalDateTime.class.equals(type)) {
            return true;
        }
        if (ZonedDateTime.class.equals(type)) {
            return true;
        }
        return Date.class.equals(type);
    }

    public boolean isComplexType(Type type) {
        return !isPrimitiveType(type);
    }


    public boolean hasBuilder(Type type) {
        Class<?> rawClass = extractClass(type);
        return stream(rawClass.getDeclaredMethods())
                .filter(method -> method.getName().equals(BUILDER_METHOD_NAME))
                .filter(method -> isStatic(method.getModifiers()) && isPublic(method.getModifiers()))
                .filter(method -> method.getParameterCount() == 0)
                .count() == 1;
    }

    public boolean hasAtLeastOneSetter(Type type) {
        Class<?> rawClass = extractClass(type);
        return stream(rawClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith(SET_NAME))
                .filter(method -> isPublic(method.getModifiers()))
                .anyMatch(method -> method.getParameterCount() == 1);
    }

    public boolean hasAtLeastOneFieldConstructorArgument(Type type) {
        Class<?> rawClass = extractClass(type);
        return getProperties(rawClass).stream().anyMatch(field -> hasConstructorArgument(type, field.getName()));
    }

    public boolean hasNoArgumentsConstructor(Type type) {
        Class<?> rawClass = extractClass(type);
        return stream(rawClass.getConstructors())
                .filter(constructor -> isPublic(constructor.getModifiers()))
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    public boolean hasConstructorArgument(Type type, String argumentName) {
        Class<?> rawClass = extractClass(type);
        return stream(rawClass.getConstructors())
                .filter(constructor -> isPublic(constructor.getModifiers()))
                .flatMap(constructor -> stream(constructor.getParameters()))
                .anyMatch(parameter -> argumentName.equals(parameter.getName()));
    }

    public boolean hasConstructorWithArguments(Type type) {
        Class<?> rawClass = extractClass(type);
        List<String> argumentNames = getProperties(rawClass)
                .stream()
                .map(Field::getName)
                .collect(toList());
        return hasConstructorArguments(rawClass, argumentNames);
    }

    public boolean hasConstructorArguments(Type type, List<String> argumentNames) {
        Class<?> rawClass = extractClass(type);
        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (!isPublic(constructor.getModifiers())) continue;
            Parameter[] parameters = constructor.getParameters();
            if (argumentNames.size() != parameters.length) return false;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (!parameter.getName().equals(argumentNames.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }


    public PrimitiveType primitiveTypeFromJava(Type type) {
        if (char.class.equals(type) || Character.class.equals(type)) {
            return STRING;
        }
        if (int.class.equals(type) || Integer.class.equals(type)) {
            return INT;
        }
        if (short.class.equals(type) || Short.class.equals(type)) {
            return INT;
        }
        if (long.class.equals(type) || Long.class.equals(type)) {
            return LONG;
        }
        if (boolean.class.equals(type) || Boolean.class.equals(type)) {
            return BOOL;
        }
        if (double.class.equals(type) || Double.class.equals(type)) {
            return DOUBLE;
        }
        if (byte.class.equals(type) || Byte.class.equals(type)) {
            return BYTE;
        }
        if (float.class.equals(type) || Float.class.equals(type)) {
            return FLOAT;
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
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


    public Type extractGenericPropertyType(ParameterizedType owner, Type type) {
        if (type instanceof TypeVariable<?>) {
            return owner.getActualTypeArguments()[typeVariableIndex((TypeVariable<?>) type)];
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return genericArrayType(extractGenericPropertyType(owner, componentType));
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type[] extractedArguments = new Type[actualTypeArguments.length];
            for (int index = 0, actualTypeArgumentsLength = actualTypeArguments.length; index < actualTypeArgumentsLength; index++) {
                Type actualTypeArgument = actualTypeArguments[index];
                extractedArguments[index] = extractGenericPropertyType(owner, actualTypeArgument);
            }
            return parameterizedType(extractClass(parameterizedType), extractedArguments);
        }
        return type;
    }

    public int typeVariableIndex(TypeVariable<?> typeVariable) {
        TypeVariable<?>[] typeParameters = typeVariable.getGenericDeclaration().getTypeParameters();
        int index = -1;
        for (TypeVariable<?> parameter : typeParameters) {
            index++;
            if (typeVariable.equals(parameter)) return index;
        }
        throw new GenerationException(format(TYPE_VARIABLE_WAS_NOT_FOUND, typeVariable));
    }
}
