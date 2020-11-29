package io.art.generator.implementor;

import io.art.core.collection.*;
import io.art.generator.model.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import lombok.experimental.*;
import static io.art.core.collection.ImmutableArray.*;
import static io.art.core.collection.ImmutableSet.*;
import static io.art.core.factory.MapFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.reflection.ParameterizedTypeImplementation.*;
import static io.art.generator.state.GenerationState.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappersImplementor {
    public ImmutableArray<NewClass> implementCustomTypeMappers(ImmutableSet<Type> types) {
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
            NewClass mapping = NewClass.newClass()
                    .name(entry.getValue())
                    .modifiers(INTERFACE)
                    .field(TO_MODEL_NAME, NewField.newField()
                            .name(TO_MODEL_NAME)
                            .type(TypeModel.type(toType))
                            .initializer(() -> createToModelMapper(entry.getKey())))
                    .field(FROM_MODEL_NAME, NewField.newField()
                            .name(FROM_MODEL_NAME)
                            .type(TypeModel.type(fromType))
                            .initializer(() -> createFromModelMapper(entry.getKey())));
            mappingClasses.add(mapping);
        }
        return mappingClasses.build();
    }
}
