package ru;

import ru.art.generator.javac.annotation.*;

@Module
public class Main {
    public static void main(String[] args) {
        System.out.println(MyService.myMethod);
    }
}
