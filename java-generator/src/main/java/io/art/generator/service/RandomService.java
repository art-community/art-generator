package io.art.generator.service;


import static java.lang.Math.abs;
import java.util.*;

public class RandomService {
    private final static Random RANDOM = new Random();

    public static String randomName(String prefix) {
        return prefix + abs(RANDOM.nextInt());
    }
}
