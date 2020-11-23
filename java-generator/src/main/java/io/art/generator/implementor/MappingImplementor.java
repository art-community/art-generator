package io.art.generator.implementor;

import com.google.common.collect.*;
import io.art.generator.model.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import lombok.experimental.*;
import sun.reflect.generics.reflectiveObjects.*;
import static com.google.common.collect.ImmutableSet.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.generator.constants.GeneratorConstants.Names.*;
import static io.art.generator.creator.mapper.FromModelMapperCreator.*;
import static io.art.generator.creator.mapper.ToModelMapperCreator.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.state.GenerationState.*;
import static java.lang.reflect.Modifier.*;
import static java.util.Objects.*;
import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class MappingImplementor {
    public ImmutableList<NewClass> implementCustomTypeMappings(ImmutableSet<Type> types) {
        types = types.stream().filter(type -> isNull(getGeneratedMapping(type))).collect(toImmutableSet());
        ImmutableList.Builder<NewClass> mappingClasses = ImmutableList.builder();
        Map<Type, String> typeMappings = mapOf();
        Type[] typesArray = types.toArray(new Type[0]);
        for (Type type : typesArray) {
            Class<?> typeAsClass = extractClass(type);
            long id = typeMappings
                    .keySet()
                    .stream()
                    .filter(key -> extractClass(key).getSimpleName().equals(typeAsClass.getSimpleName()))
                    .count();
            typeMappings.put(type, typeAsClass.getSimpleName() + MAPPING_INTERFACE_NAME + id);
            putGeneratedMapping(type, typeAsClass.getSimpleName() + MAPPING_INTERFACE_NAME + id);
        }
        for (Map.Entry<Type, String> entry : typeMappings.entrySet()) {
            Type[] arguments = {
                    entry.getKey(),
                    Entity.class
            };
            ParameterizedTypeImpl toType = ParameterizedTypeImpl.make(ValueToModelMapper.class, arguments, null);
            ParameterizedTypeImpl fromType = ParameterizedTypeImpl.make(ValueFromModelMapper.class, arguments, null);
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
