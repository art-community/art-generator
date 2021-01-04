package ru;

import lombok.*;

@Getter
@AllArgsConstructor
public class MyConfig {
    private final String value;
    private final MyConfig nested;
}
