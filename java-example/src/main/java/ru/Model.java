package ru;

import lombok.*;
import java.util.*;

@Value
@Builder
public class Model {
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
}
