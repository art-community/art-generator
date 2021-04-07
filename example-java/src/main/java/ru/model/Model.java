package ru.model;

import io.art.core.collection.*;
import io.art.core.property.*;
import io.art.value.immutable.Value;
import io.art.value.immutable.*;
import lombok.*;
import static io.art.core.constants.CompilerSuppressingWarnings.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings(ALL)
public class Model {
    int FInteger;
    char FCharacter;
    short FShort;
    byte FByte;
    boolean FBoolean;
    long FLong;
    double FDouble;
    float FFloat;

    Integer FBInteger;
    Short FBShort;
    Byte FBByte;
    Boolean FBBoolean;
    Long FBLong;
    String FBString;
    Double FBDouble;
    Float FBFloat;

    UUID FUUID;
    LocalDateTime FlocalDateTime;
    ZonedDateTime FzonedDateTime;
    Date FDate;

    int[] FAint;
    char[] FAchar;
    short[] FAshort;
    byte[] FAbyte;
    boolean[] FAboolean;
    long[] FAlong;
    double[] FAdouble;
    float[] FAfloat;

    Integer[] FABInteger;
    Character[] FABCharacter;
    Short[] FABShort;
    Byte[] FABByte;
    Boolean[] FABBoolean;
    Long[] FABLong;
    String[] FABString;
    Double[] FABDouble;
    Float[] FABFloat;

    Collection<Integer> FCInteger;
    Collection<Character> FCCharacter;
    Collection<Short> FCShort;
    Collection<Byte> FCByte;
    Collection<Boolean> FCBoolean;
    Collection<Long> FCLong;
    Collection<String> FCString;
    Collection<Double> FCDouble;
    Collection<Float> FCFloat;

    List<Integer> FLInteger;
    List<Character> FLCharacter;
    List<Short> FLShort;
    List<Byte> FLByte;
    List<Boolean> FLBoolean;
    List<Long> FLLong;
    List<String> FLString;
    List<Double> FLDouble;
    List<Float> FLFloat;

    Set<Integer> FSInteger;
    Set<Character> FSCharacter;
    Set<Short> FSShort;
    Set<Byte> FSByte;
    Set<Boolean> FSBoolean;
    Set<Long> FSLong;
    Set<String> FSString;
    Set<Double> FSDouble;
    Set<Float> FSFloat;

    Map<Integer, Integer> FMInteger;
    Map<Character, Character> FMCharacter;
    Map<Short, Short> FMShort;
    Map<Byte, Byte> FMByte;
    Map<Boolean, Boolean> FMBoolean;
    Map<Long, Long> FMLong;
    Map<String, String> FMString;
    Map<Double, Double> FMDouble;
    Map<Float, Float> pArgFMFloat;

    Model FModel;

    Model[] FAModel;

    List<Model> FLModel;
    List<List<Model>> FLLModel;
    List<Set<Model>> FLSModel;
    List<Collection<Model>> FLCModel;
    List<Map<String, Model>> FLMModel;
    List<Model[]> FLAModel;
    List<Model>[] FALModel;

    Set<Model> FSModel;
    Set<List<Model>> FSLModel;
    Set<Set<Model>> FSSModel;
    Set<Collection<Model>> FSCModel;
    Set<Map<String, Model>> FSMModel;
    Set<Model[]> FSAModel;
    Set<Model>[] FASModel;

    Collection<Model> FCModel;
    Collection<List<Model>> FCLModel;
    Collection<Set<Model>> FCSModel;
    Collection<Collection<Model>> FCCModel;
    Collection<Map<String, Model>> FCMModel;
    Collection<Model[]> FCAModel;
    Collection<Model>[] FACModel;

    Map<String, Model> FMModel;
    Map<String, Model[]> FMAModel;
    Map<String, List<Model>> FMLModel;
    Map<String, Set<Model>> FMSModel;
    Map<String, Collection<Model>> FMCModel;
    Map<String, Map<String, Model>> FMMModel;
    Map<String, Model>[] FAMModel;

    Collection<List<Map<String, Model>>> FCCMModel;
    Collection<Set<Map<String, Model>>> FCLMModel;
    Collection<Collection<Map<String, Model>>> FCSMModel;
    Collection<Map<String, Model>> FCMMModel;

    Collection<List<Map<String, Model>>[]> FCLMAModel;
    Collection<Set<Map<String, Model>>[]> FCSMAModel;
    Collection<Collection<Map<String, Model>>[]> FCCMAModel;
    Collection<Map<String, Model>[]> FCMAModel;

    GenericModel<String, GenericTypeParameterImplementation<String, Integer>> FGModel;
    GenericModel<Long, GenericTypeParameterImplementation<Long, Integer>> FGSModel;
    GenericModel<String, GenericTypeParameterImplementation<String, Integer>>[] FAGModel;
    GenericModel<String[], GenericTypeParameterImplementation<String[], Integer[]>>[] FASAGModel;
    List<GenericModel<String, GenericTypeParameterImplementation<String, Integer>>> FLGModel;
    Set<GenericModel<String, GenericTypeParameterImplementation<String, Integer>>> FSGModel;
    Collection<GenericModel<String, GenericTypeParameterImplementation<String, Integer>>> FCGModel;
    Map<String, GenericModel<String, GenericTypeParameterImplementation<String, Integer>>> FMGMModel;

    LazyProperty<String> FLazyString;
    LazyProperty<Long> FLazyLong;
    Collection<LazyProperty<Long>> FCLazyLong;
    LazyProperty<Collection<Long>> FLazyCLong;
    LazyProperty<Collection<LazyProperty<Long>>> FCLazyCLong;
    LazyProperty<Long>[] FLazyLongA;
    LazyProperty<Long[]> FLazyALong;
    LazyProperty<Collection<Long>[]> FLazyCLongA;
    LazyProperty<Collection<Long[]>> FLazyCALong;
    LazyProperty<Collection<Long[]>>[][] FLazyCLAMultiA;
    Map<String, LazyProperty<Collection<Long[][]>[]>[]>[] MSLazyMCMLongMultiA;

    Optional<String> FOptionalString;
    Optional<Long> FOptionalLong;
    Collection<Optional<Long>> FCOptionalLong;
    Optional<Collection<Long>> FOptionalCLong;
    Optional<Collection<Optional<Long>>> FCOptionalCLong;
    Optional<Long>[] FOptionalLongA;
    Optional<Long[]> FOptionalALong;
    Optional<Collection<Long>[]> FOptionalCLongA;
    Optional<Collection<Long[]>> FOptionalCALong;
    Optional<Collection<Long[]>>[][] FOptionalCLAMultiA;
    Map<String, Optional<Collection<Long[][]>[]>[]>[] MSOptionalMCMLongMultiA;

    Stream<Optional<Collection<Long[][]>[]>[]>[] SASOptionalMCMLongMultiA;
    Queue<Optional<Collection<Long[][]>[]>[]>[] QASOptionalMCMLongMultiA;
    Deque<Optional<Collection<Long[][]>[]>[]>[] DSSOptionalMCMLongMultiA;
    ImmutableArray<Optional<Collection<Long[][]>[]>[]>[] IASOptionalMCMLongMultiA;
    ImmutableSet<Optional<Collection<Long[][]>[]>[]>[] ISSOptionalMCMLongMultiA;
    ImmutableMap<String, Optional<Collection<Long[][]>[]>[]>[] IMSOptionalMCMLongMultiA;

    Stream<String> FStringStream;

    Value FValue;
    Entity FEntity;

    Value[] FAValue;
    Entity[] FAEntity;

    Collection<Value> FCValue;
    Collection<Value[]> FCAValue;
}
