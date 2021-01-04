package io.art.generator.model;

import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TypeMappingNames {
    private final String from;
    private final String to;
    private String configurationSource;

    public static TypeMappingNames typeMappings(String from, String to) {
        return new TypeMappingNames(from, to);
    }

    public static TypeMappingNames typeMappings(String from, String to, String configurationSource) {
        return new TypeMappingNames(from, to, configurationSource);
    }
}
