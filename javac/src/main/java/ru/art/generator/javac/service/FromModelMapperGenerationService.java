package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewLambda.*;
import static ru.art.generator.javac.model.NewParameter.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.MakerService.*;
import java.lang.reflect.*;

@UtilityClass
public class FromModelMapperGenerationService {
    public static JCLambda generateFromModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(modelClass), MODEL))
                .expression(() -> generateContent(modelClass))
                .generate();
    }

    private static JCMethodInvocation generateContent(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = applyClassMethod(type(Entity.class), ENTITY_BUILDER);
        for (Method method : modelClass.getDeclaredMethods()) {
            String getterName = method.getName();
            if (getterName.startsWith(GET_PREFIX)) {
                String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                Class<?> fieldType = method.getReturnType();
                builderInvocation = generateFieldMapping(builderInvocation, fieldName, fieldType);
            }
        }
        return applyMethod(builderInvocation, BUILD);
    }

    private static JCMethodInvocation generateFieldMapping(JCMethodInvocation builderInvocation, String fieldName, Class<?> fieldType) {
        if (String.class.equals(fieldType)) {
            List<JCExpression> mapping = List.of(maker().Literal(fieldName),
                    newLambda().expression(() -> applyMethod(MODEL, GET_PREFIX + capitalize(fieldName))).generate(),
                    select(type(PrimitiveMapping.class), fromString)
            );
            return applyMethod(builderInvocation, LAZY_PUT, mapping);
        }

        throw new IllegalStateException();
    }
}
