package ru.art.generator.javac.factory;

import lombok.experimental.*;
import java.util.*;

@UtilityClass
public class CollectionsFactory {
    @SafeVarargs
    public <T> Set<T> setOf(T... values) {
        return new LinkedHashSet<>(Arrays.asList(values));
    }
}
