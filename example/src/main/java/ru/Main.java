package ru;

import ru.art.generator.javac.annotation.*;
import java.util.*;

@Module
public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(MyService.methods));
    }
}
