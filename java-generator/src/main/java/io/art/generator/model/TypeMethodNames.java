package io.art.generator.model;

import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TypeMethodNames {
    private final String from;
    private final String to;
    private String configurationSource;

    public static TypeMethodNames typeMethods(String from, String to) {
        return new TypeMethodNames(from, to);
    }

    public static TypeMethodNames typeMethods(String from, String to, String configurationSource) {
        return new TypeMethodNames(from, to, configurationSource);
    }
}
