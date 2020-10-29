package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapper.*;
import io.art.entity.mapping.*;
import io.art.entity.registry.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.toString;
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
                .name(MAPPERS)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(registryType)
                .initializer(() -> applyMethod(CREATE_MAPPERS));

        NewMethod createMappersMethod = newMethod()
                .name(CREATE_MAPPERS)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateRegistryVariable(registryType))
                .statement(() -> generateCreateRegistryMethodContent(modelClass))
                .statement(() -> maker().Return(ident(MAPPERS)));

        NewMethod mappersMethod = newMethod()
                .returnType(registryType)
                .name(MAPPERS)
                .modifiers(PUBLIC | STATIC)
                .statement(() -> maker().Return(ident(MAPPERS)));

        NewClass configurationClass = newClass()
                .modifiers(PUBLIC | STATIC)
                .name(CONFIGURATION_CLASS_NAME)
                .addImport(importClass(PrimitiveMapping.class.getName()))
                .addImport(importClass(ArrayMapping.class.getName()))
                .addImport(importClass(EntityMapping.class.getName()))
                .addImport(importClass(BinaryMapping.class.getName()))
                .addImport(importClass(ArrayValue.class.getName()))
                .addImport(importClass(BinaryValue.class.getName()))
                .addImport(importClass(Entity.class.getName()))
                .addImport(importClass(Value.class.getName()))
                .addImport(importClass(ValueToModelMapper.class.getName()))
                .addImport(importClass(ValueFromModelMapper.class.getName()))
                .addImport(importClass(MappersRegistry.class.getName()))
                .field(MAPPERS, mappersField)
                .method(CREATE_MAPPERS, createMappersMethod)
                .method(MAPPERS, mappersMethod);

        replaceInnerClass(mainClass(), configurationClass);
    }

    private JCTree.JCExpressionStatement generateCreateRegistryMethodContent(Class<?> modelClass) {
        return maker().Exec(applyMethod(MAPPERS, PUT_TO_MODEL, List.of(
                classReference(modelClass),
                newLambda()
                        .parameter(newParameter(type(Entity.class), VALUE))
                        .expression(() -> applyMethod(
                                applyMethod(
                                        applyClassMethod(type(modelClass.getName()), BUILDER),
                                        VALUE,
                                        List.of(applyMethod(VALUE, MAP, List.of(maker().Literal(VALUE), select(type(PrimitiveMapping.class), toString))))
                                ),
                                BUILD)
                        )
                        .generate())));
    }

    private JCTree.JCVariableDecl generateRegistryVariable(TypeModel registryType) {
        return newVariable()
                .name(MAPPERS)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
