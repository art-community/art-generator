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

package model;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class JavaModel {
    boolean f1;
    String f2;

    List<String> f6;
    List<?> f8;
    List<? extends String> f9;
    List<? super String> f10;

    boolean[] f18;
    List<String>[] f22;
    List<?>[] f23;
    List<? extends String>[] f25;
    List<? super String>[] f26;

    boolean[][] f27;
    List<String>[][] f29;
    List<?>[][] f30;
    List<? extends String>[][] f32;
    List<? super String>[][] f33;

    List<boolean[]> f34;
    List<String[]>[] f36;
    List<? extends String[]>[] f38;
    List<? super String[]>[] f39;

    List<List<Boolean>> f40;
    List<List<String>> f42;
    List<List<?>> f43;
    List<List<? extends String>> f45;
    List<List<? super String>> f46;

    List<List<boolean[]>> f47;
    List<List<String[]>> f49;
    List<List<? extends String[]>> f50;
    List<List<? super String[]>> f51;

    InnerModel f55;
    List<InnerModel> f56;

    JavaModel f60;

    Map<String, String> f61;
    Map<?, String> f62;
    Map<String, ?> f63;
    Map<? super String, ? extends String> f64;

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
    public static class InnerModel {
        boolean f1;
        String f2;
        int f3;
        JavaModel f5;

        public static <M extends String> int staticMethod(int p1, M p2) {
            return p1;
        }

        public int simpleMethod(int p1) {
            return p1;
        }
    }


    @Getter
    @ToString
    @AllArgsConstructor
    public static class GenericModel<T> {
        boolean f1;
        String f2;
        int f3;
        T f4;
        JavaModel f5;

        public static <M extends String> int staticMethod(int p1, M p2) {
            return p1;
        }

        public int simpleMethod(int p1) {
            return p1;
        }
    }


    public enum Enum {
        FIRST,
    }
}
