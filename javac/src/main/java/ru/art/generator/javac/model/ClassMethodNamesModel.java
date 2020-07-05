package ru.art.generator.javac.model;

import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.constants.Constants.INIT_METHOD_NAME;
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

        for (Map.Entry<String, ExistedMethod> entry : from.getMethods().entrySet()) {
            if (entry.getKey().equals(INIT_METHOD_NAME)) {
                continue;
            }
            classModel.field(entry.getKey(), newField()
                    .modifiers(PUBLIC | STATIC)
                    .name(entry.getKey())
                    .type(type(String.class.getName()))
                    .constant(entry.getKey()));
        }

        return classModel;
    }
}
