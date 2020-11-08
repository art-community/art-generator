package io.art.generator.model;

import com.google.common.collect.*;
import lombok.*;
import java.lang.reflect.*;

@Getter
@AllArgsConstructor
public class ServerModelMetaData {
    private final ImmutableSet<Type> mappingTypes;
    private final ImmutableSet<Class<?>> serviceClasses;
}
