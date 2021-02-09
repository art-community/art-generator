package io.art.generator.substitutor;

import lombok.experimental.*;
import java.lang.reflect.*;

@UtilityClass
public class TypeSubstitutor {
    public Type substituteWildcard(WildcardType wildcardType) {
        Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length != 0) return lowerBounds[0];

        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length != 0) return upperBounds[0];
        return Object.class;
    }
}
