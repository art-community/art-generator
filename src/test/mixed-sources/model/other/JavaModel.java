/*
 * ART
 *
 * Copyright 2019-2021 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model.other;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JavaModel extends JavaClass implements JavaInterface {
    boolean f1;
    String f2;
    List<String> f3;
    List<?> f4;
    List<? extends String> f5;
    List<? super String> f6;
    boolean[] f7;
    List<String>[] f8;
    List<?>[] f9;
    List<? extends String>[] f10;
    List<? super String>[] f11;
    boolean[][] f12;
    List<String>[][] f13;
    List<?>[][] f14;
    List<? extends String>[][] f15;
    List<? super String>[][] f16;
    List<boolean[]> f17;
    List<String[]>[] f18;
    List<? extends String[]>[] f19;
    List<? super String[]>[] f20;
    List<List<Boolean>> f21;
    List<List<String>> f22;
    List<List<?>> f23;
    List<List<? extends String>> f24;
    List<List<? super String>> f25;
    List<List<boolean[]>> f26;
    List<List<String[]>> f27;
    List<List<? extends String[]>> f28;
    List<List<? super String[]>> f29;
    InnerModel f30;
    List<InnerModel> f31;
    Map<String, String> f32;
    Map<?, String> f33;
    Map<String, ?> f34;
    Enum f35;
    List<Enum> f36;
    Enum[] f37;
    Map<Enum, Enum> f38;
    Map<Enum, List<Enum>> f39;
    Map<Enum, List<Enum[]>> f40;
    Map<Enum, List<? extends Enum[]>> f41;
    GenericClass<?> genericField;

    public static int staticMethod(int argument) {
        return argument;
    }

    public static String staticMethod(String argument) {
        return argument;
    }

    public int instanceMethod(int p1) {
        return p1;
    }

    public int instanceMethod(int[] p1) {
        return p1[0];
    }


    // Must be ignored
    public static <T> void genericStaticMethod(List<T> list) {
    }

    // Must be ignored
    public static <T> T genericStaticMethod() {
        return null;
    }

    // Must be ignored
    public <T> void genericInstanceMethod(List<T> list) {
    }

    // Must be ignored
    public <T> T genericInstanceMethod() {
        return null;
    }

    public static int staticMethod(String argument1, int... argument2) {
        return argument2[0];
    }

    @Override
    protected boolean protectedAbstractMethod() {
        return false;
    }

    @Override
    public boolean publicAbstractMethod() {
        return false;
    }

    @Override
    boolean internalAbstractMethod() {
        return false;
    }

    @Override
    public String implementableMethod(String argument) {
        return null;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class InnerModel extends Number implements Comparator<String> {
        boolean f1;
        String f2;
        int f3;
        JavaModel f5;

        public static int staticMethod(int argument) {
            return argument;
        }

        public int instanceMethod(int argument) {
            return argument;
        }

        @Override
        public int intValue() {
            return 0;
        }

        @Override
        public long longValue() {
            return 0;
        }

        @Override
        public float floatValue() {
            return 0;
        }

        @Override
        public double doubleValue() {
            return 0;
        }

        @Override
        public int compare(String o1, String o2) {
            return 0;
        }
    }

    public enum Enum {
        FIRST,
    }

    // Must be ignored as class
    public static class GenericClass<T> {

    }
}
