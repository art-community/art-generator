package io.art.generator.model;

import io.art.core.collection.*;
import lombok.*;
import java.lang.reflect.*;

@Getter
@AllArgsConstructor
public class ServerModelMetaData {
    private final ImmutableSet<Type> mappingTypes;
    private final ImmutableSet<Class<?>> serviceClasses;
}
