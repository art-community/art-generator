package io.art.generator.comparator;

import lombok.experimental.*;
import static io.art.generator.inspector.TypeInspector.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class TypeMatcher {
    public boolean typeMatches(Type first, Type second) {
        if (first == second) return true;
        first = boxed(first);
        second = boxed(second);
        if (isWildcard(first) && isWildcard(second)) return Objects.equals(first, second);
        if (isWildcard(first)) {
            WildcardType wildcard = (WildcardType) first;
            Type[] upperBounds = wildcard.getUpperBounds();
            if (upperBounds.length == 0) return isObject(second);
            return typeMatches(upperBounds[0], second);
        }
        if (isWildcard(second)) {
            WildcardType wildcard = (WildcardType) second;
            Type[] upperBounds = wildcard.getUpperBounds();
            if (upperBounds.length == 0) return isObject(first);
            return typeMatches(upperBounds[0], first);
        }
        if (isClass(first)) return first.equals(second);
        if (isParametrized(first) && !isParametrized(second)) return false;
        if (isParametrized(first) && isParametrized(second)) {
            ParameterizedType parameterizedFirst = (ParameterizedType) first;
            ParameterizedType parameterizedSecond = (ParameterizedType) second;
            if (!Objects.equals(parameterizedFirst.getRawType(), parameterizedSecond.getRawType())) {
                return false;
            }
            Type[] firstArguments = parameterizedFirst.getActualTypeArguments();
            Type[] secondArguments = parameterizedSecond.getActualTypeArguments();
            if (firstArguments.length != secondArguments.length) return false;
            for (int index = 0; index < firstArguments.length; index++) {
                if (!typeMatches(firstArguments[index], secondArguments[index])) return false;
            }
            return true;
        }
        if (isGenericArray(first) && !isGenericArray(second)) return false;
        if (isGenericArray(first) && isGenericArray(second)) {
            GenericArrayType firstGenericArray = (GenericArrayType) first;
            GenericArrayType secondGenericArray = (GenericArrayType) second;
            return typeMatches(firstGenericArray.getGenericComponentType(), secondGenericArray.getGenericComponentType());
        }
        return false;
    }

    public boolean typeMatches(ParameterizedType owner, Type first, Type second) {
        if (first == second) return true;
        first = extractGenericPropertyType(owner, boxed(first));
        second = extractGenericPropertyType(owner, boxed(second));
        if (isWildcard(first) && isWildcard(second)) return Objects.equals(first, second);
        if (isWildcard(first)) {
            WildcardType wildcard = (WildcardType) first;
            Type[] upperBounds = wildcard.getUpperBounds();
            if (upperBounds.length == 0) return isObject(second);
            return typeMatches(owner, upperBounds[0], second);
        }
        if (isWildcard(second)) {
            WildcardType wildcard = (WildcardType) second;
            Type[] upperBounds = wildcard.getUpperBounds();
            if (upperBounds.length == 0) return isObject(first);
            return typeMatches(owner, upperBounds[0], first);
        }
        if (isClass(first)) return first.equals(second);
        if (isParametrized(first) && !isParametrized(second)) return false;
        if (isParametrized(first) && isParametrized(second)) {
            ParameterizedType parameterizedFirst = (ParameterizedType) first;
            ParameterizedType parameterizedSecond = (ParameterizedType) second;
            if (!Objects.equals(parameterizedFirst.getRawType(), parameterizedSecond.getRawType())) {
                return false;
            }
            Type[] firstArguments = parameterizedFirst.getActualTypeArguments();
            Type[] secondArguments = parameterizedSecond.getActualTypeArguments();
            if (firstArguments.length != secondArguments.length) return false;
            for (int index = 0; index < firstArguments.length; index++) {
                if (!typeMatches(owner, firstArguments[index], secondArguments[index])) {
                    return false;
                }
            }
            return true;
        }
        if (isGenericArray(first) && !isGenericArray(second)) return false;
        if (isGenericArray(first) && isGenericArray(second)) {
            GenericArrayType firstGenericArray = (GenericArrayType) first;
            GenericArrayType secondGenericArray = (GenericArrayType) second;
            return typeMatches(owner, firstGenericArray.getGenericComponentType(), secondGenericArray.getGenericComponentType());
        }
        return false;
    }
}
