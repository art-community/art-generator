package io.art.generator.javac.implementor.mapping;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;
import io.art.generator.javac.model.*;
import io.art.launcher.*;
import io.art.model.module.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapping.*;
import io.art.value.registry.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.*;
import static io.art.generator.javac.constants.GeneratorConstants.*;
import static io.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.javac.context.GenerationContext.*;
import static io.art.generator.javac.implementor.mapping.FromModelMapperImplementor.*;
import static io.art.generator.javac.implementor.mapping.ToModelMapperImplementor.*;
import static io.art.generator.javac.model.ImportModel.*;
import static io.art.generator.javac.model.NewClass.*;
import static io.art.generator.javac.model.NewField.*;
import static io.art.generator.javac.model.NewMethod.*;
import static io.art.generator.javac.model.NewVariable.*;
import static io.art.generator.javac.model.TypeModel.*;
import static io.art.generator.javac.service.ClassMutationService.*;
import static io.art.generator.javac.service.MakerService.*;
import static java.util.Arrays.*;

@UtilityClass
public class MappingImplementor {
    public void implementMethodMapping(Class<?> returnClass, Class<?>[] parameterClasses) {
        TypeModel registryType = type(MappersRegistry.class);

        NewField model = newField()
                .name("model")
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(type(ModuleModel.class))
                .initializer(() -> applyClassMethod(type(mainClass().getName()), "configure"));

        NewField mappersField = newField()
                .name(MAPPERS)
                .modifiers(PRIVATE | FINAL | STATIC)
                .type(registryType)
                .initializer(() -> applyMethod(CREATE_MAPPERS));

        NewClass configurationClass = newClass()
                .modifiers(PRIVATE | STATIC)
                .name(mainClass().getName() + CONFIGURATOR_CLASS_NAME_SUFFIX)
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
                .field("model", model)
                .field(MAPPERS, mappersField)
                .method(CREATE_MAPPERS, generateCreateMappersMethod(returnClass, registryType, parameterClasses));
        stream(parameterClasses).forEach(parameter -> configurationClass.addImport(importClass(parameter.getName())));
        replaceInnerClass(mainClass(), configurationClass);
        NewMethod mainMethod = newMethod()
                .modifiers(PUBLIC | STATIC)
                .name("main")
                .returnType(type(void.class))
                .parameter(NewParameter.newParameter(type(String[].class), "args"))
                .addClassImport(importClass(ModuleLauncher.class.getName()))
                .statement(() -> maker().Exec(applyClassMethod(type(ModuleLauncher.class), "launch", List.of(applyClassMethod(type(mainClass().getName()), "configure")))));
        replaceMethod(mainClass(), mainMethod);
    }

    private NewMethod generateCreateMappersMethod(Class<?> returnClass, TypeModel registryType, Class<?>[] parameterClasses) {
        NewMethod createMappersMethod = newMethod()
                .name(CREATE_MAPPERS)
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> generateMappersVariable(registryType));

        createMappersMethod.statement(() -> implementToModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            createMappersMethod.statement(() -> implementToModel(parameterClass));
        }

        createMappersMethod.statement(() -> implementFromModel(returnClass));
        for (Class<?> parameterClass : parameterClasses) {
            createMappersMethod.statement(() -> implementFromModel(parameterClass));
        }

        return createMappersMethod.statement(() -> returnVariable(MAPPERS));
    }

    private JCTree.JCExpressionStatement implementToModel(Class<?> modelClass) {
        return execMethodCall(MAPPERS, REGISTER, List.of(classReference(modelClass), implementToModelMapper(modelClass)));
    }

    private JCTree.JCExpressionStatement implementFromModel(Class<?> modelClass) {
        return execMethodCall(MAPPERS, REGISTER, List.of(classReference(modelClass), implementFromModelMapper(modelClass)));
    }

    private JCTree.JCVariableDecl generateMappersVariable(TypeModel registryType) {
        return newVariable()
                .name(MAPPERS)
                .initializer(() -> newObject(registryType))
                .type(registryType)
                .generate();
    }
}
