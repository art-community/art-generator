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

package model.model;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class OtherModel<V1, V2 extends String, V3 extends Number & Comparator<V1>, V4 extends V2> {
    boolean f1;
    String f2;
    V1 f3;
    V2 f4;
    V3 f5;

    List<String> f6;
    List<V1> f7;
    List<?> f8;
    List<? extends String> f9;
    List<? super String> f10;
    List<? extends V1> f11;
    List<? super V2> f12;
    List<? extends V2> f13;
    List<? super V3> f14;
    List<? extends V3> f15;
    List<? super V4> f16;
    List<? extends V4> f17;

    boolean[] f18;
    V1[] f19;
    V2[] f20;
    V3[] f21;
    List<String>[] f22;
    List<?>[] f23;
    List<V1>[] f24;
    List<? extends String>[] f25;
    List<? super String>[] f26;

    boolean[][] f27;
    V1[][] f28;
    List<String>[][] f29;
    List<?>[][] f30;
    List<V1>[][] f31;
    List<? extends String>[][] f32;
    List<? super String>[][] f33;

    List<boolean[]> f34;
    List<V1[]> f35;
    List<String[]>[] f36;
    List<V1[]>[] f37;
    List<? extends String[]>[] f38;
    List<? super String[]>[] f39;

    List<List<Boolean>> f40;
    List<V1> f41;
    List<List<String>> f42;
    List<List<?>> f43;
    List<List<V1>> f44;
    List<List<? extends String>> f45;
    List<List<? super String>> f46;

    List<List<boolean[]>> f47;
    List<List<V1[]>> f48;
    List<List<String[]>> f49;
    List<List<? extends String[]>> f50;
    List<List<? super String[]>> f51;

    InnerModel<String> f52;
    InnerModel<V1> f53;
    InnerModel<? extends String> f54;
    InnerModel<? super String> f55;
    List<InnerModel<String>> f56;

    List<? extends List<? extends V1[]>> f57;
    List<? extends List<? extends V1[]>[]> f58;
    List<? extends List<? extends V1[]>[]>[] f59;

    JavaOtherModel<String, V2, IntersectionType, V4> f60;

    Map<String, String> f61;
    Map<?, String> f62;
    Map<String, ?> f63;
    Map<? super String, ? extends String> f64;
    Map<? super V1, ? extends V1> f65;
    Map<V1, V1> f66;

    Object f67;

    Enum f68;
    List<Enum> f69;
    Enum[] f70;

    Map<Enum, Enum> f71;
    Map<Enum, List<Enum>> f72;
    Map<Enum, List<Enum[]>> f73;
    Map<Enum, List<? extends Enum[]>> f74;

    public static int staticMethod(int p1) {
        return p1;
    }

    public static String staticMethod(String p1) {
        return p1;
    }

    public int simpleMethod(int p1) {
        return p1;
    }

    public int simpleMethod(int[] p1) {
        return p1[0];
    }

    public static <T> void staticMethod(List<T> list) {
    }

    public static int staticMethod(String p1, int... p2) {
        return p2[0];
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class InnerModel<T> {
        boolean f1;
        String f2;
        int f3;
        T f4;

        public static <M extends String> int staticMethod(int p1, M p2) {
            return p1;
        }

        public int simpleMethod(int p1) {
            return p1;
        }
    }

    public static class IntersectionType extends Number implements Comparator<String> {
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
}
