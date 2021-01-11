package io.art.generator.inspector;

import io.art.core.collection.*;
import io.art.generator.exception.*;
import lombok.experimental.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.extensions.CollectionExtensions.*;
import static io.art.core.extensions.StringExtensions.*;
import static io.art.generator.constants.ExceptionMessages.*;
import static io.art.generator.formater.SignatureFormatter.*;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class CommunicatorsMethodsInspector {
    public static ImmutableArray<Method> getCommunicatorMethods(Class<?> serviceClass) {
        Set<String> duplicates = duplicates(serviceClass.getDeclaredMethods(), Method::getName);
        if (isNotEmpty(duplicates)) {
            throw new ValidationException(formatSignature(serviceClass), format(OVERRIDDEN_METHODS_NOT_SUPPORTED, toCommaDelimitedString(duplicates)));
        }
        return stream(serviceClass.getDeclaredMethods())
                .filter(method -> isPublic(method.getModifiers()) && !method.isSynthetic() && !method.isDefault() && !method.isBridge())
                .collect(immutableArrayCollector());
    }
}
