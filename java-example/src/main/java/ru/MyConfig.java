package ru;

import io.art.core.collection.*;
import lombok.*;
import java.util.*;

@Getter
@ToString
@RequiredArgsConstructor
public class MyConfig {
    private final Integer Finteger;
    private final Short FShort;
    private final Byte FByte;
    private final Long FLong;
    private final Double FDouble;
    private final Float FFloat;
    private final String FString;
    private final List<String> FLString;
    private final Set<String> FSString;
    private final ImmutableArray<String> FAString;
    private final ImmutableSet<String> FISString;
    private final MyConfig nested;
    private final List<MyConfig> LLnested;
    private final Set<MyConfig> LSnested;
    private final ImmutableArray<MyConfig> LILnested;
    private final ImmutableSet<MyConfig> LISnested;
}
