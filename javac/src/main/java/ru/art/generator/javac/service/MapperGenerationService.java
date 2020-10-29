package ru.art.generator.javac.service;

import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapper.*;
import io.art.entity.mapping.*;
import io.art.entity.registry.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import static ru.art.generator.javac.model.NewClass.*;
import static ru.art.generator.javac.model.NewField.*;
import static ru.art.generator.javac.model.NewLambda.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.model.NewParameter.*;
import static ru.art.generator.javac.model.NewVariable.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.MakerService.*;

@UtilityClass
public class MapperGenerationService {
    public void generateMappers(Class<?> modelClass) {
        TypeModel registryType = type(MappersRegistry.class);

        NewField mappersField = newField()
                .name("mappers")
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(registryType)
                .initializer(() -> MakerService.applyMethod("createMappers"));

        NewMethod createMappersMethod = newMethod()
                .name("createMappers")
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> newVariable()
                        .name("registry")
                        .initializer(() -> newObject(registryType))
                        .type(registryType)
                        .generate())
                .statement(() -> maker().Exec(applyMethod("registry", "putToModel", List.of(
                        classReference(modelClass),
                        newLambda()
                                .parameter(newParameter(type(Entity.class), "value"))
                                .expression(() -> applyMethod(
                                        applyMethod(
                                                applyClassMethod(type(modelClass.getName()), "builder"),
                                                "value",
                                                List.of(applyMethod("value", "map", List.of(maker().Literal("value"), select(type(PrimitiveMapping.class), "toString"))))
                                        ),
                                        "build")
                                )
                                .generate())))
                )
                .statement(() -> maker().Return(ident("registry")));

        NewClass configurationClass = newClass()
                .modifiers(PUBLIC | STATIC)
                .name("Configuration")
                .addImport(importClass(PrimitiveMapping.class.getName()))
                .addImport(importClass(Entity.class.getName()))
                .addImport(importClass(Value.class.getName()))
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importClass(MappersRegistry.class.getName()))
                .field("mappers", mappersField)
                .method("createMappers", createMappersMethod)
                .method("mappers", newMethod().returnType(registryType).name("mappers").modifiers(PUBLIC | STATIC).statement(() -> maker().Return(ident("mappers"))));

        replaceInnerClass(mainClass(), configurationClass);
    }
}
