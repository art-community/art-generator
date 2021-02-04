package io.art.generator.inspector;

import io.art.core.collection.*;
import io.art.core.lazy.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.server.validation.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeConstants.*;
import static io.art.core.reflection.GenericArrayTypeImplementation.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class TypeInspector {
    public ImmutableArray<ExtractedProperty> getProperties(Class<?> type) {
        return ExtractedProperty.from(type);
    }

    public ImmutableArray<ExtractedProperty> getSettableProperties(Class<?> type) {
        return getProperties(type)
                .stream()
                .filter(ExtractedProperty::hasSetter)
                .collect(immutableArrayCollector());
    }

    public ImmutableArray<ExtractedProperty> getConstructorProperties(Class<?> type) {
        return getProperties(type)
                .stream()
                .filter(ExtractedProperty::usedInConstructorArguments)
                .collect(immutableArrayCollector());
    }


    public boolean isBoolean(Type fieldType) {
        return fieldType == boolean.class;
    }

    public boolean isVoid(Type fieldType) {
        return fieldType == void.class || fieldType == Void.class;
    }

    public boolean isNotVoid(Type fieldType) {
        return !isVoid(fieldType);
    }

    public boolean isFlux(Type fieldType) {
        return extractClass(fieldType).isAssignableFrom(Flux.class);
    }

    public boolean isMono(Type fieldType) {
        return extractClass(fieldType).isAssignableFrom(Mono.class);
    }

    public boolean isLazyValue(Type fieldType) {
        return LazyValue.class == extractClass(fieldType);
    }

    public boolean isOptional(Type fieldType) {
        return Optional.class == extractClass(fieldType);
    }

    public boolean isCollectionType(Type type) {
        return COLLECTION_TYPES.stream().anyMatch(collection -> collection.isAssignableFrom(extractClass(type)));
    }

    public boolean isListType(Type type) {
        return List.class.isAssignableFrom(extractClass(type));
    }

    public boolean isSetType(Type type) {
        return Set.class.isAssignableFrom(extractClass(type));
    }

    public boolean isMapType(Type type) {
        return Map.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableArrayType(Type type) {
        return ImmutableArray.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableSetType(Type type) {
        return ImmutableSet.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableMapType(Type type) {
        return ImmutableMap.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableType(Type type) {
        return ImmutableMap.class.isAssignableFrom(extractClass(type))
                || ImmutableSet.class.isAssignableFrom(extractClass(type))
                || ImmutableArray.class.isAssignableFrom(extractClass(type));
    }

    public boolean isMutableType(Type type) {
        return !isImmutableType(type);
    }

    public boolean isJavaPrimitiveType(Type type) {
        return JAVA_PRIMITIVE_TYPES.contains(type);
    }

    public boolean isPrimitiveType(Type type) {
        return PRIMITIVE_TYPES.contains(type);
    }

    public boolean isClass(Type type) {
        return type instanceof Class;
    }

    public boolean isParametrized(Type type) {
        return type instanceof ParameterizedType;
    }

    public boolean isGenericArray(Type type) {
        return type instanceof GenericArrayType;
    }

    public boolean isWildcard(Type type) {
        return type instanceof WildcardType;
    }

    public boolean isVariable(Type type) {
        return type instanceof TypeVariable;
    }

    public boolean isComplexType(Type type) {
        return !isPrimitiveType(type);
    }

    public boolean isLibraryType(Type type) {
        if (isParametrized(type)) {
            return isLibraryType(extractClass((ParameterizedType) type));
        }
        if (isGenericArray(type)) {
            return isLibraryType(extractClass((GenericArrayType) type));
        }
        if (isClass(type)) {
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

    public boolean isVoidMethod(Method method) {
        return method.getGenericReturnType() == void.class;
    }

    public boolean isValidatable(Type type) {
        return stream(extractClass(type).getInterfaces()).anyMatch(interfaceType -> interfaceType == Validatable.class);
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

    public boolean hasNoArgumentsConstructor(Type type) {
        Class<?> rawClass = extractClass(type);
        return stream(rawClass.getConstructors())
                .filter(constructor -> isPublic(constructor.getModifiers()))
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    public boolean hasConstructorArgument(Type type, Type argumentType, int argumentIndex) {
        Class<?> rawClass = extractClass(type);
        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (!isPublic(constructor.getModifiers())) {
                continue;
            }
            Parameter[] parameters = constructor.getParameters();
            if (argumentIndex >= parameters.length) {
                continue;
            }
            if (argumentType.equals(parameters[argumentIndex].getParameterizedType())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasConstructorWithAllProperties(Type type) {
        Class<?> rawClass = extractClass(type);
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(rawClass);
        ImmutableArray<Type> argumentTypes = properties.stream().map(ExtractedProperty::type).collect(immutableArrayCollector());
        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (matchConstructorArguments(constructor, argumentTypes)) return true;
        }
        return false;
    }

    private boolean matchConstructorArguments(Constructor<?> constructor, ImmutableArray<Type> argumentTypes){
        if (!isPublic(constructor.getModifiers())) return false;
        Parameter[] parameters = constructor.getParameters();
        if (argumentTypes.size() != parameters.length) return false;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (!parameter.getParameterizedType().equals(argumentTypes.get(i))) {
                return false;
            }
        }
        return true;
    }


    public PrimitiveType primitiveTypeFromJava(Type type) {
        return orThrow(JAVA_TO_PRIMITIVE_TYPE.get(type), () -> new GenerationException(format(UNSUPPORTED_TYPE, type)));
    }

    public Class<?> extractClass(ParameterizedType parameterizedType) {
        return extractClass(parameterizedType.getRawType());
    }

    public Class<?> extractClass(GenericArrayType genericArrayType) {
        return extractClass(genericArrayType.getGenericComponentType());
    }

    public Class<?> extractClass(Type type) {
        if (isClass(type)) {
            return (Class<?>) type;
        }
        if (isParametrized(type)) {
            return extractClass((ParameterizedType) type);
        }
        if (isGenericArray(type)) {
            return extractClass((GenericArrayType) type);
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }


    public Type extractGenericPropertyType(ParameterizedType owner, Type type) {
        if (isVariable(type)) {
            return owner.getActualTypeArguments()[typeVariableIndex((TypeVariable<?>) type)];
        }
        if (isGenericArray(type)) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return genericArrayType(extractGenericPropertyType(owner, componentType));
        }
        if (isParametrized(type)) {
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

    public Type extractFirstTypeParameter(ParameterizedType type) {
        return extractTypeParameter(type, 0);
    }

    public Type extractTypeParameter(ParameterizedType type, int index) {
        return type.getActualTypeArguments()[index];
    }
}
