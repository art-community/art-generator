package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Response {
    char Fchar;
    int Fint;
    short Fshort;
    byte Fbyte;
    boolean Fboolean;
    long Flong;
    double Fdouble;
    float Ffloat;

    Integer FBInteger;
    Short FBShort;
    Byte FBByte;
    Boolean FBBoolean;
    Long FBLong;
    String FBString;
    Double FBDouble;
    Float FBFloat;

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
//    Model f43;
//    Model[] f44;
//    List<Model> f45;
//    Set<Model> f46;
//    Collection<Collection<Model>> collectionModels;
//    Collection<Model[]> collectionArrayModels;
}
