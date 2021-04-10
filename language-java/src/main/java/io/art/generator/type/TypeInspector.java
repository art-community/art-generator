package io.art.generator.type;

import io.art.core.collection.*;
import io.art.core.property.*;
import io.art.core.source.*;
import io.art.generator.exception.*;
import io.art.generator.model.*;
import io.art.server.validation.*;
import io.art.value.constants.ValueModuleConstants.ValueType.*;
import io.art.value.immutable.Value;
import lombok.experimental.*;
import reactor.core.publisher.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.reflection.GenericArrayTypeImplementation.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeConstants.*;
import static io.art.generator.model.ExtractedProperty.*;
import static io.art.generator.type.TypeMatcher.*;
import static io.art.generator.type.TypeSubstitutor.*;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class TypeInspector {
    public ImmutableArray<ExtractedProperty> getProperties(Type type) {
        return collectProperties(type);
    }

    public ImmutableArray<ExtractedProperty> getGettableProperties(Type type) {
        return getProperties(type)
                .stream()
                .filter(ExtractedProperty::hasGetter)
                .collect(immutableArrayCollector());
    }

    public ImmutableArray<ExtractedProperty> getSettableProperties(Type type) {
        return getProperties(type)
                .stream()
                .filter(ExtractedProperty::hasSetter)
                .collect(immutableArrayCollector());
    }

    public ImmutableArray<ExtractedProperty> getConstructorProperties(Type type) {
        return getProperties(type)
                .stream()
                .filter(ExtractedProperty::byConstructor)
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
        return LazyProperty.class == extractClass(fieldType);
    }

    public boolean isOptional(Type fieldType) {
        return Optional.class == extractClass(fieldType);
    }

    public boolean isCollection(Type type) {
        return COLLECTION_TYPES.stream().anyMatch(collection -> collection.isAssignableFrom(extractClass(type)));
    }

    public boolean isObject(Type type) {
        return type == Object.class;
    }

    public boolean isList(Type type) {
        return List.class.isAssignableFrom(extractClass(type));
    }

    public boolean isSet(Type type) {
        return Set.class.isAssignableFrom(extractClass(type));
    }

    public boolean isMap(Type type) {
        return Map.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableArray(Type type) {
        return ImmutableArray.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableSet(Type type) {
        return ImmutableSet.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableMap(Type type) {
        return ImmutableMap.class.isAssignableFrom(extractClass(type));
    }

    public boolean isImmutableCollection(Type type) {
        return ImmutableMap.class.isAssignableFrom(extractClass(type))
                || ImmutableSet.class.isAssignableFrom(extractClass(type))
                || ImmutableArray.class.isAssignableFrom(extractClass(type));
    }

    public boolean isMutableCollection(Type type) {
        return !isImmutableCollection(type);
    }

    public boolean isJavaPrimitive(Type type) {
        return JAVA_PRIMITIVE_TYPES.contains(type);
    }

    public boolean isPrimitive(Type type) {
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

    public boolean isValue(Type type) {
        return Value.class.isAssignableFrom(extractClass(type));
    }

    public boolean isNestedConfiguration(Type type) {
        return NestedConfiguration.class.isAssignableFrom(extractClass(type));
    }

    public boolean isByteArray(Type type) {
        return byte[].class.equals(type);
    }

    public boolean isArray(Type type) {
        return isGenericArray(type) || extractClass(type).isArray();
    }

    public boolean isUserType(Type type) {
        return !isPrimitive(type);
    }

    public boolean isCoreType(Type type) {
        if (isWildcard(type)) {
            return isCoreType(substituteWildcard((WildcardType) type));
        }
        if (isParametrized(type)) {
            return isCoreType(extractClass((ParameterizedType) type));
        }
        if (isGenericArray(type)) {
            return isCoreType(extractClass((GenericArrayType) type));
        }
        if (isClass(type)) {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isArray()) {
                return isCoreType(typeAsClass.getComponentType());
            }
            boolean coreBased = CORE_BASE_TYPES
                    .stream()
                    .anyMatch(matching -> matching.isAssignableFrom(typeAsClass));
            boolean core = CORE_TYPES
                    .stream()
                    .anyMatch(matching -> matching.equals(typeAsClass));
            return core || coreBased;
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

    public boolean hasConstructorArgument(Type type, ExtractedProperty property) {
        Class<?> rawClass = extractClass(type);
        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (!isPublic(constructor.getModifiers())) {
                continue;
            }
            Parameter[] parameters = constructor.getParameters();
            if (property.index() >= parameters.length) {
                continue;
            }
            Parameter parameter = parameters[property.index()];
            if (!parameter.getName().equals(property.name())) {
                continue;
            }
            if (isParametrized(type) && typeMatches((ParameterizedType) type, property.type(), parameter.getParameterizedType())) {
                return true;
            }
            if (typeMatches(property.type(), parameter.getParameterizedType())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasConstructorWithAllProperties(Type type) {
        ImmutableArray<ExtractedProperty> properties = getConstructorProperties(type);
        return matchConstructorArguments(type, properties.stream().map(ExtractedProperty::type).collect(immutableArrayCollector()));
    }

    public boolean matchConstructorArguments(Type type, ImmutableArray<Type> argumentTypes) {
        Class<?> rawClass = extractClass(type);
        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (!isPublic(constructor.getModifiers())) continue;
            Parameter[] parameters = constructor.getParameters();
            if (argumentTypes.size() != parameters.length) continue;
            for (int i = 0; i < parameters.length; i++) {
                Type parameterType = parameters[i].getParameterizedType();
                Type argumentType = argumentTypes.get(i);
                if (isParametrized(type) && typeMatches((ParameterizedType) type, parameterType, argumentType)) {
                    return true;
                }
                if (typeMatches(parameterType, argumentType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Type boxed(Type primitiveType) {
        return isJavaPrimitive(primitiveType) ? JAVA_PRIMITIVE_MAPPINGS.get(primitiveType) : primitiveType;
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
        if (isWildcard(type)) {
            return extractClass(substituteWildcard((WildcardType) type));
        }
        throw new GenerationException(format(UNSUPPORTED_TYPE, type));
    }


    public Type extractGenericPropertyType(ParameterizedType owner, Type type) {
        if (isWildcard(type)) {
            return extractGenericPropertyType(owner, substituteWildcard((WildcardType) type));
        }
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
