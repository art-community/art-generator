package ru.art.generator.javac;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.*;

@Retention(SOURCE)
@Target({TYPE, METHOD})
public @interface Module {
}
