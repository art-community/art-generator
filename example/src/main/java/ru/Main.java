package ru;

import ru.art.generator.javac.annotation.*;
import static ru.art.generator.javac.model.ModuleModel.*;

@Module
public class Main {
    public static void main(String[] args) {
        module().service(MyService.class);
    }
}
