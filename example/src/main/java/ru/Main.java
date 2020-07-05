package ru;

import ru.art.generator.javac.annotation.*;
import static ru.Registry.*;

@Module
public class Main {
    public static void main(String[] args) {
        registry()
                .registerService(MyService.class);
    }
}
