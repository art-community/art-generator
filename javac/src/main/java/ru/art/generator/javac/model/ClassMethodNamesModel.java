package ru.art.generator.javac.model;

import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.constants.Constants.*;
import static ru.art.generator.javac.factory.CollectionsFactory.*;
import static ru.art.generator.javac.model.NewClass.*;
import static ru.art.generator.javac.model.NewField.*;
import static ru.art.generator.javac.model.TypeModel.*;
import java.util.*;

@RequiredArgsConstructor
public class ClassMethodNamesModel {
    private final ExistedClass from;

    public NewClass toClass() {
        NewClass classModel = newClass()
                .name(from.getName() + "Methods")
                .modifiers(PUBLIC | STATIC | INTERFACE);

        Set<String> set = setOf();
        for (Map.Entry<String, ExistedMethod> entry : from.getMethods().entrySet()) {
            if (IGNORING_METHODS.contains(entry.getKey())) {
                continue;
            }
            set.add(entry.getKey());
            classModel.field(entry.getKey(), newField()
                    .modifiers(PUBLIC | STATIC | FINAL)
                    .name(entry.getKey())
                    .type(type(String.class.getName()))
                    .constant(entry.getKey()));
        }
        classModel.field("methods", newField().modifiers(PUBLIC | STATIC | FINAL)
                .name("methods")
                .type(type(String[].class.getName()))
                .arrayOf(type(String.class.getName()), set));
        return classModel;
    }

    public static ClassMethodNamesModel methodNames(ExistedClass from) {
        return new ClassMethodNamesModel(from);
    }
}
