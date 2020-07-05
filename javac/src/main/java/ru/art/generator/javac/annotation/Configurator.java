package ru.art.generator.javac.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.*;

@Target(METHOD)
@Retention(RUNTIME)
public @interface Configurator {
}
