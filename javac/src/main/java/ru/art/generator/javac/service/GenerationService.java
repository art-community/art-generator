package ru.art.generator.javac.service;

import lombok.*;
import lombok.experimental.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        generateClassMethodNames();
    }

    private void generateClassMethodNames() {
        getExistedClasses()
                .values()
                .stream()
                .forEach(existed -> addFields(existed, methodNames(existed).toClass().fields().values()));
    }
}
