package ru.art.generator.javac.service;

import lombok.*;
import lombok.experimental.*;
import static ru.art.generator.javac.constants.GeneratorConstants.ProcessorOptions.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ClassMethodNamesModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.ModelService.*;

@UtilityClass
public class GenerationService {
    @SneakyThrows
    public void generate() {
        if (processingEnvironment().getOptions().get(DISABLE_OPTION) != null) {
            return;
        }
        generateClassMethodNames();
        generateModelImplementation();
        classLoader().close();
    }


    private void generateClassMethodNames() {
        getExistedClasses()
                .values()
                .forEach(existed -> addFields(existed, methodNames(existed).toClass().fields().values()));
    }
}
