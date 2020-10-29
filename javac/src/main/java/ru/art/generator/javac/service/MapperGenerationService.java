package ru.art.generator.javac.service;

import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
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
import static ru.art.generator.javac.model.NewParameter.*;
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
        TypeModel registryType = type(MappersRegistry.class);
        NewField mappersField = newField()
                .name("mappers")
                .type(registryType)
                .initializer(() -> MakerService.applyMethod("createMappers"));

        NewMethod createMappersMethod = newMethod()
                .name("createMappers")
                .returnType(registryType)
                .modifiers(STATIC)
                .statement(() -> newVariable()
                        .name("registry")
                        .initializer(() -> newObject(registryType))
                        .type(registryType)
                        .generate())
                .statement(() -> maker().Exec(applyMethod("registry", "putToModel", List.of(
                        classReference(modelClass),
                        maker().Lambda(
                                List.of(newParameter(type(Value.class), "value").generate()),
                                maker().Apply(List.nil(), maker().Select(applyClassMethod(type(modelClass.getName()), "builder"), name("build")), List.nil())
                        )
                ))))
                .statement(() -> maker().Return(ident("registry")));

        NewClass interfaceClass = newClass()
                .modifiers(INTERFACE)
                .name("Mappers")
                .addImport(importClass(Value.class.getName()))
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importClass(MappersRegistry.class.getName()))
                .field("mappers", mappersField)
                .method("createMappers", createMappersMethod);

        addInnerClass(mainClass(), interfaceClass);
    }
}
