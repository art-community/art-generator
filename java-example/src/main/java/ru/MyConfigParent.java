package ru;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class MyConfigParent {
    private final MyConfig parent;
}
