package ru.art.generator.javac.determiner;

import lombok.experimental.*;
import static java.lang.reflect.Modifier.*;

@UtilityClass
public class ModifiersDeterminer {
    public boolean isPublic(int modifiers) {
        return (modifiers & PUBLIC) != 0;
    }

    public boolean isStatic(int modifiers) {
        return (modifiers & STATIC) != 0;
    }
}
