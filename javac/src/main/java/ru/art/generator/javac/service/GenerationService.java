package ru.art.generator.javac.service;

import lombok.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.service.ClassMutationService.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        getExistedClasses().values()
                .stream()
                .map(existed -> new ClassMethodsModel(existed))
                .forEach(model -> addInnerClass(mainClass(), model.toClass()));
        System.out.println("Generation started");
    }
}
