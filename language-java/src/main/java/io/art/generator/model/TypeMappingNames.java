package io.art.generator.model;

import lombok.*;

@Getter
@AllArgsConstructor
public class TypeMappingNames {
    private final String from;
    private final String to;

    public static TypeMappingNames typeMappings(String from, String to) {
        return new TypeMappingNames(from, to);
    }
}
