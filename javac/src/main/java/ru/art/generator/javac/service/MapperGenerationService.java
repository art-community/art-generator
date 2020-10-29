package ru.art.generator.javac.service;

import com.google.common.collect.*;
import com.sun.tools.javac.util.*;
import io.art.entity.mapper.*;
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
        NewField toModel = newField()
                .name("toModel")
                .type(type(ImmutableMap.class.getName(), ImmutableList.of(type(Class.class.getName()), type(ValueToModelMapper.class.getName()))))
                .asNull();

        NewMethod createToModelMappers = newMethod()
                .name("createToModelMappers")
                .returnType(type(ImmutableMap.class.getName(), ImmutableList.of(type(Class.class.getName()), type(ValueToModelMapper.class.getName()))))
                .modifiers(STATIC)
                .statement(() -> newVariable()
                        .name("builder")
                        .initializer(() -> callClassMethod(type(ImmutableMap.class.getName()), "builder"))
                        .type(type(
                                ImmutableMap.class.getName(),
                                "Builder",
                                List.of(
                                        type(Class.class.getName()),
                                        type(ValueToModelMapper.class.getName())
                                )
                        ))
                        .generate())
                .statement(() -> returnMethodCall("builder", "build"));

        NewField fromModel = newField()
                .name("fromModel")
                .type(type(ImmutableMap.class.getName(), ImmutableList.of(type(Class.class.getName()), type(ValueFromModelMapper.class.getName()))))
                .asNull();

        NewClass interfaceClass = newClass()
                .modifiers(INTERFACE)
                .name("Mappers")
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importPackage("com.google.common.collect"))
                .field("toModel", toModel)
                .field("fromModel", fromModel)
                .method("createToModelMappers", createToModelMappers);

        addInnerClass(mainClass(), interfaceClass);
    }
}
