package ru.art.generator.javac.model;

import com.google.common.collect.*;
import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.model.NewClass.*;
import static ru.art.generator.javac.model.NewField.*;
import java.util.*;

@RequiredArgsConstructor
public class ClassMethodNamesModel {
    private final ExistedClass from;

    public NewClass toClass() {
        NewClass classModel = newClass()
                .name(from.getName() + METHODS_SUFFIX)
                .modifiers(PUBLIC | STATIC | INTERFACE);
        ImmutableSet.Builder<String> set = ImmutableSet.builder();
        for (Map.Entry<String, ExistedMethod> entry : from.getMethods().entrySet()) {
            if (IGNORING_METHODS.contains(entry.getKey())) {
                continue;
            }
            set.add(entry.getKey());
            classModel.field(entry.getKey(), newField()
                    .modifiers(PUBLIC | STATIC | FINAL)
                    .name(entry.getKey())
                    .type(TypeModel.type(String.class.getName()))
                    .constant(entry.getKey()));
        }
        NewField methodsField = newField()
                .modifiers(PUBLIC | STATIC | FINAL)
                .name(METHODS_FIELD)
                .type(TypeModel.type(String[].class.getName()))
                .arrayOf(TypeModel.type(String.class.getName()), set.build());

        return classModel.field(METHODS_FIELD, methodsField);
    }

    public static ClassMethodNamesModel methodNames(ExistedClass from) {
        return new ClassMethodNamesModel(from);
    }
}
