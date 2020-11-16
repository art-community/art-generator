package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Response {
    char f0;
    int f1;
    short f2;
    byte f3;
    boolean f4;
    long f5;
    double f6;

    Integer f7;
    Short f8;
    Byte f9;
    Boolean f10;
    Long f11;
    String f12;

    int[] f13;
    short[] f14;
    byte[] f15;
    boolean[] f16;
    long[] f17;
    double[] f18;

    Integer[] f19;
    Short[] f20;
    Byte[] f21;
    Boolean[] f22;
    Long[] f23;
    String[] f24;

    Collection<Integer> f25;
    Collection<Short> f26;
    Collection<Byte> f27;
    Collection<Boolean> f28;
    Collection<Long> f29;
    Collection<String> f30;

    List<Integer> f31;
    List<Short> f32;
    List<Byte> f33;
    List<Boolean> f34;
    List<Long> f35;
    List<String> f36;

    Set<Integer> f37;
    Set<Short> f38;
    Set<Byte> f39;
    Set<Boolean> f40;
    Set<Long> f41;
    Set<String> f42;

//    Model f43;
//    Model[] f44;
//    List<Model> f45;
//    Set<Model> f46;
//    Collection<Collection<Model>> collectionModels;
//    Collection<Model[]> collectionArrayModels;
}
