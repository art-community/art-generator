package ru;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class MyConfig {
    private final Integer integer;
    private final String value;
    private final MyConfig nested;
}
