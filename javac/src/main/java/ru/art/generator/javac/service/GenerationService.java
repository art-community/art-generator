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
        System.out.println("Generation started");
        getExistedClasses().values()
                .stream()
                .map(ClassMethodsConstantsModel::new)
                .forEach(model -> addInnerClass(mainClass(), model.toClass()));
        System.out.println(mainClass().getPackageUnit());
    }
}
