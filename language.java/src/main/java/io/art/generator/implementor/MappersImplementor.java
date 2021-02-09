package io.art.generator.implementor;

import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import lombok.experimental.*;
import static com.sun.tools.javac.code.Flags.PRIVATE;
import static com.sun.tools.javac.code.Flags.STATIC;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.caller.MethodCaller.*;
import static io.art.generator.constants.LoggingMessages.*;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.constants.MappersConstants.ValueCustomizerMethods.*;
import static io.art.generator.constants.Names.*;
import static io.art.generator.constants.TypeModels.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.creator.registry.RegistryVariableCreator.*;
import static io.art.generator.type.TypeInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.NewMethod.*;
import static io.art.generator.model.NewParameter.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.state.GeneratorState.*;
import static java.lang.reflect.Modifier.INTERFACE;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@UtilityClass
public class MappersImplementor {
    public NewMethod implementMappersMethod(ImmutableSet<Type> types) {
        TypeModel registryType = VALUE_MAPPER_REGISTRY_TYPE;
        NewMethod mappersMethod = newMethod()
                .name(MAPPERS_NAME)
                .parameter(newParameter(VALUE_MODULE_MODEL_TYPE, VALUE_MODEL_NAME))
                .returnType(registryType)
                .modifiers(PRIVATE | STATIC)
                .statement(() -> createRegistryVariable(registryType));
        types.stream().forEach(type -> mappersMethod
                .statement(() -> method(REGISTRY_NAME, REGISTER_TO_MODEL)
                        .addArguments(classReference(extractClass(type)))
                        .addArguments(select(mappers().get(type), TO_MODEL_NAME))
                        .execute())
                .statement(() -> method(REGISTRY_NAME, REGISTER_FROM_MODEL)
                        .addArguments(classReference(extractClass(type)))
                        .addArguments(select(mappers().get(type), FROM_MODEL_NAME))
                        .execute()));
        return mappersMethod.statement(() -> returnVariable(REGISTRY_NAME));
    }

    public ImmutableArray<NewClass> implementTypeMappers(ImmutableSet<Type> types) {
        types = types
                .stream()
                .filter(type -> isNull(mappers().get(type)))
                .peek(type -> mappers().compute(type))
                .collect(immutableSetCollector());
        ImmutableArray.Builder<NewClass> mappingClasses = immutableArrayBuilder();
        for (Type modelType : types) {
            String mapperName = mappers().compute(modelType);
            Type[] arguments = {
                    modelType,
                    Entity.class
            };
            ParameterizedType toType = parameterizedType(ValueToModelMapper.class, arguments);
            ParameterizedType fromType = parameterizedType(ValueFromModelMapper.class, arguments);
            TypeModel type = type(modelType);
            NewClass mapping = newClass()
                    .name(mapperName)
                    .modifiers(PRIVATE | INTERFACE)
                    .field(newField()
                            .name(TO_MODEL_NAME)
                            .type(type(toType))
                            .initializer(() -> createToModelMapper(modelType)))
                    .field(newField()
                            .name(FROM_MODEL_NAME)
                            .type(type(fromType))
                            .initializer(() -> createFromModelMapper(modelType)));
            if (!type.isJdk()) {
                mapping.addImport(classImport(type.getFullName()));
            }
            mappingClasses.add(mapping);
            info(format(GENERATED_MAPPER, type.getFullName()));
        }
        return mappingClasses.build();
    }
}
