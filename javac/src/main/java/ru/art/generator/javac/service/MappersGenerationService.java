package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapper.*;
import io.art.entity.mapping.*;
import io.art.entity.registry.*;
import lombok.experimental.*;
import ru.art.generator.javac.model.*;
import static com.sun.tools.javac.code.Flags.*;
import static java.util.Arrays.*;
import static ru.art.generator.javac.constants.GeneratorConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.ImportModel.*;
import static ru.art.generator.javac.model.NewClass.*;
import static ru.art.generator.javac.model.NewField.*;
import static ru.art.generator.javac.model.NewMethod.*;
import static ru.art.generator.javac.model.NewVariable.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.ClassMutationService.*;
import static ru.art.generator.javac.service.FromModelMapperGenerationService.*;
import static ru.art.generator.javac.service.MakerService.*;
import static ru.art.generator.javac.service.ToModelMapperGenerationService.*;

@UtilityClass
public class MappersGenerationService {
    public void generateMappers(Class<?> returnClass, Class<?>[] parameterClasses) {
        TypeModel registryType = type(MappersRegistry.class);

        NewField mappersField = newField()
                .name(MAPPERS)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(registryType)
                .initializer(() -> applyMethod(CREATE_MAPPERS));

        NewMethod mappersMethod = newMethod()
                .returnType(registryType)
                .name(MAPPERS)
                .modifiers(PUBLIC | STATIC)
                .statement(() -> returnVariable(MAPPERS));

        NewClass configurationClass = newClass()
                .modifiers(PUBLIC | STATIC)
                .name(CONFIGURATION_CLASS_NAME)
                .addImport(importClass(returnClass.getName()))
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
                .method(CREATE_MAPPERS, generateCreateMappersMethod(returnClass, registryType, parameterClasses))
                .method(MAPPERS, mappersMethod);
        stream(parameterClasses).forEach(parameter -> configurationClass.addImport(importClass(parameter.getName())));
        replaceInnerClass(mainClass(), configurationClass);
    }

    private NewMethod generateCreateMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod createMappersMethod = newMethod()
                .name(CREATE_MAPPERS)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateMappersVariable(registryType));

        createMappersMethod.statement(() -> generatePutToModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            createMappersMethod.statement(() -> generatePutToModel(parameterClass));
        }

        createMappersMethod.statement(() -> generatePutFromModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            createMappersMethod.statement(() -> generatePutFromModel(parameterClass));
        }

        return createMappersMethod.statement(() -> returnVariable(MAPPERS));
    }

    private JCExpressionStatement generatePutToModel(Class<?> modelClass) {
        return execMethodCall(MAPPERS, PUT_TO_MODEL, List.of(classReference(modelClass), generateToModelMapper(modelClass)));
    }

    private JCExpressionStatement generatePutFromModel(Class<?> modelClass) {
        return execMethodCall(MAPPERS, PUT_FROM_MODEL, List.of(classReference(modelClass), generateFromModelMapper(modelClass)));
    }

    private JCVariableDecl generateMappersVariable(TypeModel registryType) {
        return newVariable()
                .name(MAPPERS)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
