package io.art.generator.inspector;

import io.art.core.collection.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodsInspector {
    public ImmutableArray<Method> getServiceMethods(Class<?> serviceClass) {
        return stream(serviceClass.getDeclaredMethods())
                .filter(method -> isPublic(method.getModifiers()))
                .collect(immutableArrayCollector());
    }
}
