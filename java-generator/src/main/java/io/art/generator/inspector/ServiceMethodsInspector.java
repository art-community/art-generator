package io.art.generator.inspector;

import com.google.common.collect.*;
import lombok.experimental.*;
import static com.google.common.collect.ImmutableList.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Arrays.*;
import java.lang.reflect.*;

@UtilityClass
public class ServiceMethodsInspector {
    public ImmutableList<Method> getServiceMethods(Class<?> serviceClass) {
        return stream(serviceClass.getDeclaredMethods())
                .filter(method -> isPublic(method.getModifiers()))
                .collect(toImmutableList());
    }
}