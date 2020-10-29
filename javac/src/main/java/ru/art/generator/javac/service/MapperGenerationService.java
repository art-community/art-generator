package ru.art.generator.javac.service;

import io.art.entity.mapper.*;
import io.art.entity.registry.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import static ru.art.generator.javac.model.NewClass.*;
import static ru.art.generator.javac.model.NewField.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.model.NewVariable.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.MakerService.*;

@UtilityClass
public class MapperGenerationService {
    public void generateMappers(Class<?> modelClass) {
        if (mainClass().hasInnerInterface("Mappers")) {
            return;
        }
        NewField mappersField = newField()
                .name("mappers")
                .type(type(MappersRegistry.class))
                .initializer(() -> MakerService.callMethod("createMappers"));

        NewMethod createMappersMethod = newMethod()
                .name("createMappers")
                .returnType(type(MappersRegistry.class))
                .modifiers(STATIC)
                .statement(() -> newVariable()
                        .name("registry")
                        .initializer(() -> newObject(type(MappersRegistry.class)))
                        .type(type(MappersRegistry.class))
                        .generate())
                .statement(() -> maker().Return(ident("registry")));

        NewClass interfaceClass = newClass()
                .modifiers(INTERFACE)
                .name("Mappers")
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importClass(MappersRegistry.class.getName()))
                .field("mappers", mappersField)
                .method("createMappers", createMappersMethod);

        addInnerClass(mainClass(), interfaceClass);
    }
}
