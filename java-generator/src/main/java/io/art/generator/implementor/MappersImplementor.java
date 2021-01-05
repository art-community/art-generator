package io.art.generator.implementor;

import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.LoggingMessages.GENERATED_MAPPER;
import static io.art.generator.constants.MappersConstants.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.logger.GeneratorLogger.*;
import static io.art.generator.model.ImportModel.*;
import static io.art.generator.model.NewClass.*;
import static io.art.generator.model.NewField.*;
import static io.art.generator.model.TypeModel.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.state.GenerationState.*;
import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappersImplementor {
    public ImmutableArray<NewClass> implementTypeMappers(ImmutableSet<Type> types) {
        types = types.stream().filter(type -> isNull(getGeneratedMapper(type))).collect(immutableSetCollector());
        ImmutableArray.Builder<NewClass> mappingClasses = immutableArrayBuilder();
        Map<Type, String> typeMappers = map();
        Type[] typesArray = types.toArray(new Type[0]);
        for (Type type : typesArray) {
            Class<?> typeAsClass = extractClass(type);
            long id = typeMappers
                    .keySet()
                    .stream()
                    .filter(modelType -> extractClass(modelType).getSimpleName().equals(typeAsClass.getSimpleName()))
                    .count();
            typeMappers.put(type, typeAsClass.getSimpleName() + MAPPING_INTERFACE_NAME + id);
            putGeneratedMapper(type, typeAsClass.getSimpleName() + MAPPING_INTERFACE_NAME + id);
        }
        for (Map.Entry<Type, String> entry : typeMappers.entrySet()) {
            Type[] arguments = {
                    entry.getKey(),
                    Entity.class
            };
            ParameterizedType toType = parameterizedType(ValueToModelMapper.class, arguments);
            ParameterizedType fromType = parameterizedType(ValueFromModelMapper.class, arguments);
            TypeModel type = type(entry.getKey());
            NewClass mapping = newClass()
                    .name(entry.getValue())
                    .modifiers(PRIVATE | INTERFACE)
                    .field(newField()
                            .name(TO_MODEL_NAME)
                            .type(type(toType))
                            .initializer(() -> createToModelMapper(entry.getKey())))
                    .field(newField()
                            .name(FROM_MODEL_NAME)
                            .type(type(fromType))
                            .initializer(() -> createFromModelMapper(entry.getKey())));
            if (!type.isJdk()) {
                mapping.addImport(classImport(type.getFullName()));
            }
            mappingClasses.add(mapping);
            info(format(GENERATED_MAPPER, type.getFullName()));
        }
        return mappingClasses.build();
    }
}
