package ru;

import io.art.core.caster.*;
import io.art.core.collection.*;
import io.art.core.source.*;
import lombok.*;
import ru.model.*;
import static java.util.Optional.ofNullable;
import java.time.*;
import java.util.*;

@Getter
@ToString
@RequiredArgsConstructor
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
    private final LocalDateTime localDateTime;
    private final ZonedDateTime zonedDateTime;
    private final Date date;

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
    private final MyConfig[] FAModel;

    private final Optional<String> test;

    private final Optional<String>[] testA;

    private final List<String>[] FModelLA;
    private final Set<String>[] FModelSA;

    private final ImmutableArray<String>[] FAModeSA;
    private final ImmutableSet<String>[] FAModelSA;
    private final ImmutableSet<ImmutableSet<String>>[] FAModelSAS;
    private final ImmutableSet<ImmutableSet<String>[]>[] FAModelSASA;

    public static void main(String[] args) {
        ConfigurationSource s = null;
        Optional<String>[] testAS = Caster.cast(s.getNested("testA").asArray(testA -> ofNullable(testA.asString())).toArray(new Optional[0]));
    }
}
