package ru;

import io.art.value.mapping.*;
import lombok.*;
import static io.art.value.immutable.Entity.*;
import static io.art.value.mapping.ArrayMapping.fromCollection;
import java.util.*;

@Value
@Builder
public class Request {
    String stringValue;
    List<String> stringListValue;
    Set<String> stringSetValue;
    Collection<Collection<Collection<String>>> stringCollectionValue;

    public io.art.value.immutable.Value toValue() {
        return entityBuilder()
                .lazyPut("stringCollectionValue", () -> stringCollectionValue, fromCollection(fromCollection(fromCollection(PrimitiveMapping.fromString))))
                .build();
    }
}
