package io.art.generator.model;

import lombok.*;
import java.lang.reflect.*;

@Getter
@Builder
@EqualsAndHashCode
public class MappingTypeMetaData {
    private final Type type;
    private final int index;
}
