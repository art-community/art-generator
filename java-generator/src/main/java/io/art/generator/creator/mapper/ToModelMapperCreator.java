package io.art.generator.creator.mapper;

import com.sun.tools.javac.tree.JCTree.*;
import lombok.*;
import static io.art.generator.constants.GeneratorConstants.MappersConstants.*;
import static io.art.generator.context.GeneratorContext.*;
import static io.art.generator.inspector.TypeInspector.*;
import static io.art.generator.service.JavacService.*;
import static io.art.generator.service.NamingService.*;
import static io.art.generator.state.GenerationState.*;
import static java.util.Objects.*;
import java.lang.reflect.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ToModelMapperCreator {
    public static JCExpression toModelMapper(Type type) {
        String generatedMapper = getGeneratedMapper(type);
        if (nonNull(generatedMapper)) {
            return select(select(providerClassName(), generatedMapper), TO_MODEL_NAME);
        }
        return createToModelMapper(type);
    }

    public static JCExpression createToModelMapper(Type type) {
        String entityName = sequenceName(ENTITY_NAME);
        Class<?> rawClass = extractClass(type);

        if (hasBuilder(rawClass)) {
            return new ToModelMapperCreatorByBuilder(new ToModelFieldMappingCreator(entityName)).body(type);
        }

        if (hasAtLeastOneFieldConstructorArgument(rawClass) || hasAtLeastOneSetter(rawClass)) {
            return new ToModelMapperCreatorByInitializer(new ToModelFieldMappingCreator(entityName)).body(type);
        }

        throw new IllegalStateException();
    }
}
