package ru.art.generator.javac.service;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import io.art.entity.immutable.*;
import io.art.entity.mapping.*;
import lombok.experimental.*;
import static io.art.core.extensions.StringExtensions.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.*;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.toString;
import static ru.art.generator.javac.constants.GeneratorConstants.MappersConstants.PrimitiveMappingMethods.*;
import static ru.art.generator.javac.context.GenerationContext.*;
import static ru.art.generator.javac.model.NewLambda.*;
import static ru.art.generator.javac.model.NewParameter.*;
import static ru.art.generator.javac.model.TypeModel.*;
import static ru.art.generator.javac.service.MakerService.*;
import java.lang.reflect.*;

@UtilityClass
public class ToModelMapperGenerationService {
    public static JCLambda generateToModelMapper(Class<?> modelClass) {
        return newLambda()
                .parameter(newParameter(type(Entity.class), VALUE))
                .expression(() -> generateContent(modelClass))
                .generate();
    }

    private static JCMethodInvocation generateContent(Class<?> modelClass) {
        JCMethodInvocation builderInvocation = applyClassMethod(type(modelClass.getName()), BUILDER);
        for (Method method : modelClass.getDeclaredMethods()) {
            String getterName = method.getName();
            if (getterName.startsWith(GET_PREFIX)) {
                String fieldName = decapitalize(getterName.substring(GET_PREFIX.length()));
                Class<?> fieldType = method.getReturnType();
                builderInvocation = applyMethod(builderInvocation, fieldName, generateFieldMapping(fieldName, fieldType));
            }
        }
        return applyMethod(builderInvocation, BUILD);
    }

    private static List<JCExpression> generateFieldMapping(String fieldName, Class<?> fieldType) {
        if (String.class.equals(fieldType)) {
            List<JCExpression> mapping = List.of(maker().Literal(fieldName), select(type(PrimitiveMapping.class), toString));
            return List.of(applyMethod(VALUE, MAP, mapping));
        }

        if (Integer.class.equals(fieldType)) {
            List<JCExpression> mapping = List.of(maker().Literal(fieldName), select(type(PrimitiveMapping.class), toInt));
            return List.of(applyMethod(VALUE, MAP, mapping));
        }

        throw new IllegalStateException();
    }
}
