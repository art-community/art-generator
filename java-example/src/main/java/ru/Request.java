package ru;

import io.art.value.mapping.*;
import lombok.*;
import static io.art.value.immutable.Entity.*;
import static io.art.value.mapping.ArrayMapping.fromCollection;
import static io.art.value.mapping.ArrayMapping.fromList;
import java.util.*;

@Value
@Builder
public class Request {
    String stringValue;
    List<Set<String>> stringListValue;
    Set<List<String>> stringSetValue;
    Collection<Collection<Collection<String>>> stringCollectionValue;
    Map<String, List<String>> mapValue;

    public io.art.value.immutable.Value toValue() {
        return entityBuilder()
                .lazyPut("stringCollectionValue", () -> stringCollectionValue, fromCollection(fromCollection(fromCollection(PrimitiveMapping.fromString))))
                .lazyPut("mapValue", () -> mapValue, EntityMapping.fromMap(PrimitiveMapping.toString, PrimitiveMapping.fromString, fromList(PrimitiveMapping.fromString)))
                .build();
    }
}
