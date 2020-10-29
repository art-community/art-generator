package ru.art.generator.javac.model;

import com.google.common.collect.*;
import lombok.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.model.NewField.*;
import java.util.*;

@RequiredArgsConstructor
public class ClassMethodNamesModel {
    private final ExistedClass from;
    private final Set<String> methods;

    public ImmutableSet<NewField> generateFields() {
        ImmutableSet.Builder<NewField> fields = ImmutableSet.builder();
        for (Map.Entry<String, ExistedMethod> entry : from.getMethods().entrySet()) {
            if (!methods.contains(entry.getKey())) {
                continue;
            }
            fields.add(newField()
                    .modifiers(PUBLIC | STATIC | FINAL)
                    .name(entry.getKey())
                    .type(TypeModel.type(String.class.getName()))
                    .constant(entry.getKey()));
        }
        return fields.build();
    }

    public static ClassMethodNamesModel methodNames(ExistedClass from, Set<String> methods) {
        return new ClassMethodNamesModel(from, methods);
    }
}
