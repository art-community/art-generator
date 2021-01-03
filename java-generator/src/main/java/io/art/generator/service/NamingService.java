package io.art.generator.service;


import static io.art.core.factory.MapFactory.*;
import static java.lang.Math.*;
import java.util.*;

public class NamingService {
    private static final Random RANDOM = new Random();
    private static final Map<String, Integer> counter = concurrentHashMap();

    public static String randomName(String prefix) {
        return prefix + abs(RANDOM.nextInt());
    }

    public static String sequenceName(String prefix) {
        if (!counter.containsKey(prefix)) {
            counter.put(prefix, 0);
            return prefix + 0;
        }
        return prefix + counter.computeIfPresent(prefix, (name, value) -> ++value);
    }
}
