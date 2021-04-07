package ru.configuration;

import io.art.core.collection.*;
import io.art.core.source.*;
import lombok.*;
import static io.art.core.constants.CompilerSuppressingWarnings.*;
import java.time.*;
import java.util.*;

@Getter
@ToString
@RequiredArgsConstructor
@SuppressWarnings(ALL)
public class MyConfig {
    private final Integer FBInteger;
    private final Short FBShort;
    private final Byte FBByte;
    private final Boolean FBBoolean;
    private final Long FBLong;
    private final String FBString;
    private final Double FBDouble;
    private final Float FBFloat;
    private final UUID FUUID;
    private final LocalDateTime FlocalDateTime;
    private final ZonedDateTime FzonedDateTime;
    private final Date Fdate;

    private final int[] FAint;
    private final char[] FAchar;
    private final short[] FAshort;
    private final byte[] FAbyte;
    private final boolean[] FAboolean;
    private final long[] FAlong;
    private final double[] FAdouble;
    private final float[] FAfloat;

    private final Integer[] FABInteger;
    private final Character[] FABCharacter;
    private final Short[] FABShort;
    private final Byte[] FABByte;
    private final Boolean[] FABBoolean;
    private final Long[] FABLong;
    private final String[] FABString;
    private final Double[] FABDouble;
    private final Float[] FABFloat;

    private final List<Integer> FLInteger;
    private final List<Character> FLCharacter;
    private final List<Short> FLShort;
    private final List<Byte> FLByte;
    private final List<Boolean> FLBoolean;
    private final List<Long> FLLong;
    private final List<String> FLString;
    private final List<Double> FLDouble;
    private final List<Float> FLFloat;

    private final Set<Integer> FSInteger;
    private final Set<Character> FSCharacter;
    private final Set<Short> FSShort;
    private final Set<Byte> FSByte;
    private final Set<Boolean> FSBoolean;
    private final Set<Long> FSLong;
    private final Set<String> FSString;
    private final Set<Double> FSDouble;
    private final Set<Float> FSFloat;

    private final MyConfig FModel;
    private final List<MyConfig> FModelL;
    private final Set<MyConfig> FModelS;
    private final MyConfig FAModel;

    private final MyConfig[] FModelA;

    private final List<MyConfig[]> FModelLA;
    private final List<MyConfig>[] FModelAL;
    private final List<MyConfig[]>[] FModeALA;

    private final Set<MyConfig[]> FModelSA;
    private final Set<MyConfig>[] FModelAS;
    private final Set<MyConfig[]>[] FModelSAA;

    private final ImmutableArray<MyConfig[]> FModelAA;
    private final ImmutableArray<MyConfig>[] FModelAAA;
    private final ImmutableArray<MyConfig[]>[] FModelAAAA;

    private final ImmutableSet<MyConfig[]> FModelSAAAAA;
    private final ImmutableSet<MyConfig>[] FModelSAAAAAA;
    private final ImmutableSet<MyConfig[]>[] FModelLSAA;

    private final List<ImmutableArray<MyConfig>> FModelLAAAAA;
    private final List<ImmutableArray<MyConfig>[]> FModelLAA;

    private final List<ImmutableSet<MyConfig>> FModelLS;
    private final List<ImmutableSet<MyConfig>[]> FModelLSA;

    private final ImmutableArray<List<MyConfig>> FModelLL;
    private final ImmutableArray<List<MyConfig>[]> FModelLLLA;

    private final ImmutableSet<List<MyConfig>> FModelLSL;
    private final ImmutableSet<List<MyConfig>[]> FModelLSLA;
    private final ImmutableSet<List<MyConfigParent>[]> FPModelLSLA;

    private final ImmutableMap<String, List<MyConfigParent>[]> FPModelLSLAIM;
    private final Map<String, List<MyConfigParent>[]> FPModelLSLAM;
    private final Map<String, Map<String, List<MyConfigParent>>[]> FPModelLSLAMM;
    private final Map<String, Map<String, Optional<List<MyConfigParent>>>[]> FPModelLSLAMOM;

    private final Optional<ImmutableMap<String, Set<String>>> FSIM;
    private final Optional<Map<String, String>> FSM;
    private final Optional<Map<String, String>[]> FSMA;

    private final NestedConfiguration FNested;
    private final Map<String, NestedConfiguration> FNestedMap;
    private final ImmutableMap<String, NestedConfiguration> FNestedIMap;
    private final List<NestedConfiguration> FNestedList;
    private final ImmutableArray<NestedConfiguration> FNestedIList;
    private final Set<NestedConfiguration> FNestedSet;
    private final ImmutableSet<NestedConfiguration> FNestedISet;
}
