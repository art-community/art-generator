package io.art.generator.service;


import static java.lang.Math.abs;
import java.util.*;
import java.util.concurrent.atomic.*;

public class NamingService {
    private final static Random RANDOM = new Random();
    private final static AtomicInteger counter = new AtomicInteger();

    public static String randomName(String prefix) {
        return prefix + abs(RANDOM.nextInt());
    }

    public static String sequenceName(String prefix) {
        return prefix + counter.incrementAndGet();
    }
}
